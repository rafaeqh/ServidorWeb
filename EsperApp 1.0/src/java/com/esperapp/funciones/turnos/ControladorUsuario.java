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
public class ControladorUsuario implements ControladorUsuarioLocal  {

    public ControladorUsuario() {
    }
    @PersistenceContext (unitName = "EsperAppPU")
        private EntityManager em;
    //----------------------METODOS DE TURNOS--------------------------------------
    @Override
    public String asignaTurnos(String CorreoUsuario, String Id_Sede,String servicioId){
        Date fecha = new Date();
        fecha.getTime();
        Query q, q1;
        int ultimoTurno = -1;
        String ultimoTurnRetornar ="-1";
         try{
             
             
             
               q = em.createNativeQuery("select * from Turno");
        
                    List <Turno> turnos = q.getResultList();
                    ultimoTurno = turnos.size()+1;
                    Turno auxT = new Turno();
                    
                   ultimoTurnRetornar = Integer.toString(ultimoTurno);
                    Usuario us = em.find(Usuario.class, CorreoUsuario);
                    Sede sedeBuscar = em.find(Sede.class, Id_Sede);
                    Servicio servicioBuscar = em.find(Servicio.class, servicioId);
                    auxT.setFecha(fecha);
                    if(turnos.isEmpty()){
                        auxT.setNumTurno("1");
                        auxT.setIdTurno("1");
                    }else{
                        auxT.setNumTurno(ultimoTurnRetornar);
                        auxT.setIdTurno(ultimoTurnRetornar);
                    }
                    auxT.setUsuario(us);
                    auxT.setSede(sedeBuscar);
                    auxT.setServicioID(servicioBuscar);
                    auxT.setAtendido("0");
        
        
        
        em.merge(auxT);
             
             
         }catch(Exception ex){
             System.out.println("Error al buscar turnos ---------");
         }
      
       
        
        //em.persist(auxT);
        return ultimoTurnRetornar;
    }
    @Override
    public Turno buscarTurno(String turno){
        Turno t = new Turno();
        try{
           t = em.find(Turno.class, turno);
        }catch(Exception e){
            t=null;
        }
        return t;
    }
    @Override
    public boolean cancelarTurno(String Id_Turno){
        boolean retorno = false;
        Query q = null;
        int i=0;
        Turno t = em.find(Turno.class, Id_Turno);
        String upd = new String();
        System.out.println("Turno "+t.getUsuario().getNombre()+"at "+t.getAtendido());
        if(t.getAtendido().equals("0")){
            upd = "update Turno set Atendido = 2 where Id_Turno = "+Id_Turno;
            System.out.println(upd);
            try{
                q=em.createNativeQuery(upd);
                i= q.executeUpdate();
                retorno = true;
                
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        
        return retorno;      
    }
    @Override
    public Trabajo asignarTrabajadorParaUsuario(String Cedula){
        Trabajo retorno = null;
        Query q;
        q= em.createNativeQuery("select * from Trabajo where Empleado='"+Cedula);
        try{
            retorno = (Trabajo) q.getSingleResult();
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return retorno;
    }
    //--------------------------------------------------------------------------------
     
    @Override
     public Vector<String> BuscarEntidad (String Nit){
     
        Vector <String> vecRetornar = new Vector<String>() ;
         
        Entidad entidad = em.find(Entidad.class, Nit);
        
        if(entidad != null){
            
            vecRetornar.add(0, Nit);
            vecRetornar.add(1, entidad.getNombre());
            vecRetornar.add(2, entidad.getNombreContacto());
            vecRetornar.add(3, entidad.getDireccion());
            vecRetornar.add(4, entidad.getTelefonoContacto());
            vecRetornar.add(5, entidad.getCorreoContacto());
            
        }
         
     return vecRetornar;
     
     }
     
      @Override
     public List<Entidad> BuscarEntidadesNombres (){
     
        List <Entidad> vecRetornar = null;
        
        String upd ;
        upd= "select * from Entidad ";
        Query q;
        
        
        try{
            
            q = em.createNativeQuery(upd,Entidad.class);
            
            vecRetornar = q.getResultList();
            
            
        
        }catch(Exception ex){
             
             System.out.println("error");
             
         }
         
     return vecRetornar;
     
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
    public boolean loginUsuario(String idCorreo, String contra){
        boolean retorno = false;
        Usuario usuario = new Usuario();
        
        try{
            usuario = em.find(Usuario.class, idCorreo);
            if(usuario.getCorreoId().equals(idCorreo)){
                System.out.println("Nombre valido");
                if(usuario.getContrasena().equals(contra)){
                    System.out.println("contraseña valida");
                    retorno = true;
                }else{
                    System.out.println("contraseña invalida");

                }
            }else{
                System.out.println("Nombre invalido");
            }
        }catch(Exception ex){
            retorno = false;
        }
        
       return retorno;
    }
   
     
    @Override
    public List<Servicio> BuscarServiciosSede (String codigoSede){
     
        List <Servicio> vecRetornar = null;
        
        String upd ;
        upd= "select * from Servicio where Sede = '"+codigoSede+"'";
        Query q;
        
        
        try{
            
            q = em.createNativeQuery(upd,Servicio.class);
            
            vecRetornar = q.getResultList();
            
            
        
        }catch(Exception ex){
             
             System.out.println("error");
             
         }
         
     return vecRetornar;
     
     }
    
    @Override
    public boolean RegistarUsuario(String Nombre, String CorreoUsuario, String contrasena){
    
        boolean retornar ;
        
        
        Usuario usuarioNuevo = em.find(Usuario.class, CorreoUsuario);
        
        if(usuarioNuevo == null){
            usuarioNuevo = new Usuario();
            usuarioNuevo.setNombre(Nombre);
            usuarioNuevo.setCorreoId(CorreoUsuario);
            usuarioNuevo.setContrasena(contrasena);
            em.merge(usuarioNuevo);
            
            retornar = true;
        
        }else{
        
            retornar = false;
        }
        
        return retornar;
        
    }

    @Override
    public List<Turno> VerHistorialTurnos(String correoId){
          List<Turno> retorno = new ArrayList<Turno>() ;   
          String upd = new String();
         
          upd = "select * from Turno where Usuario='"+correoId+"'";
          Query q;
          System.out.println("correo "+correoId);

          try{
              
            q = em.createNativeQuery(upd,Turno.class);
            retorno = q.getResultList();
   
            
            
         
             }catch(Exception ex){
             
                 //ex.printStackTrace();
             System.out.println("NO existe la sede");
             
         }
      
      
      
      return retorno;
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

