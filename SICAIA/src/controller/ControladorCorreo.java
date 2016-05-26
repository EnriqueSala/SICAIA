/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.sun.xml.internal.org.jvnet.mimepull.MIMEMessage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;
import model.Correo;
import view.JFCorreo;

/**
 *
 * @author Marco
 */
public class ControladorCorreo implements ActionListener {
   
    JFCorreo vistacorreo = new JFCorreo();
    ArrayList tutores = new ArrayList();
    
    public ControladorCorreo(){
        
    }
    
    public ControladorCorreo(JFCorreo vistaCorreo, ArrayList tutores){
        this.vistacorreo = vistaCorreo;
        this.vistacorreo.btnenviar.addActionListener(this);
        this.tutores=tutores;
    }
    
    
    public boolean enviarcorreo(Correo c){
        try {
            Properties p = new Properties();
            p.put("mail.smtp.host", "smtp.gmail.com");
            p.setProperty("mail.smtp.starttls.enable", "true");
            p.setProperty("mail.smtp.port", "587");
            p.setProperty("mail.smtp.user", c.getUsuarioCorreo());
            p.setProperty("mail.smtp.auth", "true");
            
            Session s = Session.getDefaultInstance(p,null);
            BodyPart texto = new MimeBodyPart();
            texto.setText(c.getMensaje());
            BodyPart adjunto = new MimeBodyPart();
            
            if (!c.getRutaArchivo().equals("")){
                adjunto.setDataHandler(new DataHandler(new FileDataSource(c.getRutaArchivo())));
                adjunto.setFileName(c.getNombreArchivo());
               
            }
            
            MimeMultipart m = new MimeMultipart();
            m.addBodyPart(texto);
            
            if (!c.getRutaArchivo().equals("")){
                m.addBodyPart(adjunto);
            }
            
            MimeMessage mensaje = new MimeMessage(s);
            mensaje.setFrom(new InternetAddress(c.getUsuarioCorreo()));
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(c.getDestino()));
            mensaje.setSubject(c.getAsunto());
            mensaje.setContent(m);
            
            Transport t = s.getTransport("smtp");
            t.connect(c.getUsuarioCorreo(), c.getContraseña());
            t.sendMessage(mensaje, mensaje.getAllRecipients());
            t.close();
            return true;
            
        } catch (Exception e) {
            System.out.println("Error" +e);
            return false;
        }
    }
    
    public void actionPerformed (ActionEvent e){
        
        if(e.getSource() == vistacorreo.btnenviar){
        
        int cantidad = tutores.size();
        System.out.println(tutores);
        
        for(int i=0;i<cantidad;i++){
        Correo c = new Correo();
        JFCorreo vistacorreo = new JFCorreo();
        
            
        c.setContraseña("lnmijkimwhasfmna");
        c.setUsuarioCorreo("marcostarr1940@gmail.com");
        c.setAsunto(vistacorreo.txtasunto.getText());
        c.setMensaje(vistacorreo.txtmensaje.getText());
        
        
        c.setDestino((String) tutores.get(i));
        if((String)tutores.get(i) == null){
            
        }else{
        
        c.setNombreArchivo("");
        c.setRutaArchivo("");
        ControladorCorreo co= new ControladorCorreo();
        
        
        
        
        if(co.enviarcorreo(c)){
            JOptionPane.showConfirmDialog(null, "Exito");
            
        }else{
            JOptionPane.showConfirmDialog(null, "error");
            
        }
        
        }
        }   
            
            
        }
        
    }
    
}
