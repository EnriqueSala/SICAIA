/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Empleado;
import model.Grupo;

/**
 *
 * @author Marco
 */
public class RecuperarCorreos {
    
    Conexion conexion;
    
    public RecuperarCorreos(){
        conexion = new Conexion();
    }
    
    
    public String listartutores(String tutor){
       String tutores=null;
       Empleado empleado;
       
        try {
            Connection accesoBD = conexion.getConexion();
            PreparedStatement ps = accesoBD.prepareStatement("select * from empleados where nombre="+"'"+tutor+"'");
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                empleado = new Empleado();
                empleado.setCorreo(rs.getString(6));
                
                tutores = empleado.getCorreo();
                
            }
        } catch (Exception e) {
        }
        return tutores;
    }
}
