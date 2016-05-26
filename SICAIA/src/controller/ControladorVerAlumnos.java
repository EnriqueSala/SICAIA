/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Alumno;
import org.eclipse.persistence.jpa.jpql.parser.JoinFetchBNF;
import persistencia.RecuperarAlumnos;
import persistencia.RecuperarCorreos;
import view.JFCorreo;
import view.JFVerAlumnos;

/**
 *
 * @author Marco
 */
public class ControladorVerAlumnos implements ActionListener{
    JFVerAlumnos vistaver = new JFVerAlumnos();
    RecuperarAlumnos recuperaralumnos = new RecuperarAlumnos();
    Alumno alumno = new Alumno();
    String grado, grupo, asignatura;
    
    
    public ControladorVerAlumnos(JFVerAlumnos vistaver, RecuperarAlumnos recuperaralumnos, String grado, String grupo, String asignatura){
        this.vistaver=vistaver;
        this.recuperaralumnos=recuperaralumnos;
        this.grado=grado;
        this.grupo=grupo;
        this.asignatura=asignatura;
        this.vistaver.btnCorreos.addActionListener(this);
    }
    
    
    public void RellenarTabla(JTable TablaAlumnos){
        ArrayList alumnos = new ArrayList();
        alumnos = recuperaralumnos.listaralumnos(grado, grupo, asignatura);
        Alumno alumno = new Alumno();
        
        DefaultTableModel modeloT = new DefaultTableModel();
        TablaAlumnos.setModel(modeloT);
        
        modeloT.addColumn("Nombres");
        modeloT.addColumn("Apellidos");
        modeloT.addColumn("Grado");
        modeloT.addColumn("Grupo");
        modeloT.addColumn("Asignatura");
        modeloT.addColumn("Tutor");
        
        Object[] columna = new Object[6];
        
        int cantidad = alumnos.size();
        
        
        
        for(int i=0; i<cantidad; i++)
        {
            
            alumno = (Alumno) alumnos.get(i);
            
            columna[0] = alumno.getNombre();
            columna[1] = alumno.getApellidos();
            columna[2] = alumno.getGrado();
            columna[3] = alumno.getGrupo();
            columna[4] = alumno.getAsignatura();
            columna[5] = alumno.getTutor();
            modeloT.addRow(columna);
        }    

    }
    
    public void actionPerformed(ActionEvent e){
        if (e.getSource()==vistaver.btnCorreos){
            JFCorreo vistaCorreo = new JFCorreo();
            vistaCorreo.setVisible(true);
            vistaCorreo.setLocationRelativeTo(null);
            
            Alumno alumno = new Alumno();
            ArrayList alumnos = new ArrayList();
            alumnos = recuperaralumnos.listaralumnos(grado, grupo, asignatura);
            int cantidad = alumnos.size();
        
            ArrayList tutores = new ArrayList();
            RecuperarCorreos correos = new RecuperarCorreos();
            
            
            for(int i=0; i<cantidad; i++){
                alumno = (Alumno) alumnos.get(i);
                String tutor = correos.listartutores(alumno.getTutor());
                if(tutor==null){
                    
                }else{
                tutores.add(tutor);
                }
                
            }
            
            ControladorCorreo controlador = new ControladorCorreo(vistaCorreo,tutores);
        }
    }
    
    
}
