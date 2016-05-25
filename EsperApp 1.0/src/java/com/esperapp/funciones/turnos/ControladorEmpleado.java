/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esperapp.funciones.turnos;

import com.esperapp.entidades.Administrador;
import com.esperapp.entidades.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.ejb.Stateless;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
//import org.hibernate.ejb.AbstractQueryImpl;

/**
 *
 * @author user
 */
@Stateless
public class ControladorEmpleado implements ControladorEmpleadoLocal {

    public ControladorEmpleado() {
    }
    @PersistenceContext (unitName = "EsperAppPU")
        private EntityManager em;
    
    
    @Override
    public boolean loginEmpleado(String cedula, String contra, String sede){
        boolean retorno = false;
        Empleado Empl = new Empleado();
        EmpleadoPK llave = new EmpleadoPK();
        String llaveCedula;
        llave.setCedula(cedula);
        llave.setSede(sede);
        try {
            Empl= em.find(Empleado.class, llave);
            
            llave = Empl.getEmpleadoPK();
            llaveCedula= llave.getCedula();
            if(llaveCedula.equals(cedula)){
                System.out.println("Cedula valido");
                if(Empl.getContrasena().equals(contra)){
                    System.out.println("contraseña valida");
                    retorno = true;
                }else{
                    System.out.println("contraseña invalida");
                }
            }else{
                System.out.println("Cedula invalido");
            }
        }catch(Exception ex){
            retorno = false;
        }
        return retorno;
    }
    
    @Override
    public void CambiarEstado(String Id_Receptor){
        Receptor r = em.find(Receptor.class, Id_Receptor);
        String upd = new String();
        if(r.getEstado().equals("0")){
            //r.setEstado("1");
            upd = "update Receptor set Estado = 1 where Id_Receptor = "+Id_Receptor;
        }
        if(r.getEstado().equals("1")){
            //r.setEstado("0");
            upd = "update Receptor set Estado = 0 where Id_Receptor = "+Id_Receptor;
        }
        Query q;
        q=em.createNativeQuery(upd);
        q.executeUpdate();
        
    }
    
    @Override
    public String BuscarReceptorLibre(){
        
        String upd = new String();
        upd= "select Id_Receptor from Receptor where Estado = '0' ";
        Vector<String> libres = new Vector<String>(); 
        Query q;
        q=em.createNativeQuery(upd);
        libres = (Vector<String>) q.getResultList();
        
        return libres.get(0);
      
        
    }
    
    @Override
    public Trabajo HallarReceptor(String cedulaEmp, String idSede){
         Trabajo t = new Trabajo();
         Vector<String> empleadoSede = null ;
         Query q;
         q=em.createNativeQuery("SELECT * FROM Trabajo where Empleado ='"+cedulaEmp+"' and Sede='"+idSede+"'", Trabajo.class);
         
         try{
             t=(Trabajo) q.getSingleResult();
         }catch(Exception ex){
             ex.printStackTrace();
             t=null;
         }
         System.out.println("en trabajo "+t.getEmpleado().getNombre()+" "+t.getReceptor1().getSede().getNombre());
         return t;
     }
    
     
    @Override
     public List<Sede> BuscarSedesClase (String Nit){
     
        List <Sede> vecRetornar = null;
        
        String upd ;
        upd= "select * from Sede where Entidad='"+Nit+"'";
        Query q;
        
        
        try{
            
            q = em.createNativeQuery(upd,Sede.class);
            
            vecRetornar = q.getResultList();
            
            
        
        }catch(Exception ex){
             
             System.out.println("error");
             
         }
         
     return vecRetornar;
     
     }
     
     @Override
     public List<Empleado> BuscarEmpleado (String cedula){
     
        List <Empleado> vecRetornar = null;
        
        String upd ;
        upd= "select * from Empleado  where Cedula = '"+cedula+"'";
        Query q;
        
        
        try{
            
            q = em.createNativeQuery(upd,Empleado.class);
            
            vecRetornar = q.getResultList();
            
            
        
        }catch(Exception ex){
             
             System.out.println("error");
             
         }
         
     return vecRetornar;
     
     }
     
    
    @Override
    public List<String> AtenderCliente(String idSede, String cedula){
          List<String> retorno = new ArrayList<String>() ;
          List<Trabajo> trabajo;
          List<Turno> turnosAtender;
          Turno turno =null;
          
          String upd = new String();
          String upd1 = new String();
          upd = "select * from Turno where Sede='"+idSede+"'and Atendido = '0'";
          Query q;
          Usuario usuario = null;

          try{
              
            q = em.createNativeQuery(upd,Turno.class);
            turnosAtender = q.getResultList();
 
            

            if(!turnosAtender.isEmpty()){
                
                Iterator<Turno> It = turnosAtender.iterator();
                turno = It.next();
                usuario = turno.getUsuario();
                System.out.println("turnoooo-------------------"+turno.getIdTurno());
                String aux = turno.getIdTurno();
                retorno.add(aux);
                retorno.add(usuario.getNombre());
                retorno.add(usuario.getCorreoId());
                           
            
            }
            
         
             }catch(Exception ex){
             
                 ex.printStackTrace();
             System.out.println("NO existe la sede");
             
         }
      
      
      
      return retorno;
}

    @Override
    public String RegistrarComoAtendido(String idTurno, String idReceptor){
    String estado = null, upd, upd2;
    Receptor r;
    Query q;
    upd = "update Turno set Atendido = 1 where Id_Turno = "+idTurno;
    try{
        q=em.createNativeQuery(upd);
        q.executeUpdate();
        CambiarEstado(idReceptor);
        r = em.find(Receptor.class, idReceptor);
        estado = r.getEstado();
    }catch(Exception e){
        e.printStackTrace();
    }
    return estado;
}

    @Override
    public List<Turno> TurnosNoAtendidosEmpleado(String idSede){
          List<Turno> retorno = new ArrayList<Turno>() ;   
          String upd = new String();
         
          upd = "select * from Turno where Sede='"+idSede+"' and Atendido = '0'";
          Query q;

          try{
              
            q = em.createNativeQuery(upd,Turno.class);
            retorno = q.getResultList();
   
            
            
         
             }catch(Exception ex){
             
                 //ex.printStackTrace();
             System.out.println("NO existe la sede");
             
         }
      
      
      
      return retorno;
      }
}

