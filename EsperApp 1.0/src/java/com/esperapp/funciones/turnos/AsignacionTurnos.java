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
public class AsignacionTurnos implements AsignacionTurnosLocal {

    public AsignacionTurnos() {
    }
    @PersistenceContext (unitName = "EsperAppPU")
        private EntityManager em;
    
    @Override
    public String asignaTurnos(String CorreoUsuario, Sede Id_Sede){
        Date fecha = new Date();
        fecha.getTime();
        Query q;
        q = em.createNativeQuery("select * from dbo.Turno");
        List <Turno>turnos=q.getResultList();
        int ultimoTurno = turnos.size();
        Turno auxT = new Turno();
        ultimoTurno = ultimoTurno+1;
        String ultimoTurn = Integer.toString(ultimoTurno);
        Usuario us = em.find(Usuario.class, CorreoUsuario);
        auxT.setFecha(fecha);
        if(turnos.isEmpty()){
            auxT.setNumTurno("1");
            auxT.setIdTurno("1");
        }else{
            auxT.setNumTurno(ultimoTurn);
            auxT.setIdTurno(ultimoTurn);
        }
        auxT.setUsuario(us);
        auxT.setSede(Id_Sede);
        
        auxT.setAtendido("0");
        
        
        
        em.merge(auxT);
        
        //em.persist(auxT);
        return ultimoTurn;
    }
    @Override
    public boolean loginAdmin(String idCorreo, String contra){
        boolean retorno = false;
        Administrador admin = new Administrador();
        
        try{
            admin = em.find(Administrador.class, idCorreo);
            if(admin.getCorreoId().equals(idCorreo)){
                System.out.println("Nombre valido");
                if(admin.getContrasena().equals(contra)){
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
    public String BuscarSede(String CorreoUsuario, String iDSede){
        Sede sed = em.find(Sede.class, iDSede);
        System.out.println("sede: " + sed.getNombre());
        String turno = this.asignaTurnos(CorreoUsuario, sed);
        
        return turno;
    }
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
    
    public String BuscarReceptorLibre(){
        
        String upd = new String();
        upd= "select Id_Receptor from Receptor where Id_Receptor = '0'  ";
        Vector<String> libres = new Vector<String>(); 
        Query q;
        q=em.createNativeQuery(upd);
        libres = (Vector<String>) q.getResultList();
        
        return libres.get(0);
      
        
    }
    
    @Override
    public boolean AgregarEntidad(String Nit, String Nombre, String NombreContacto, String Direccion, String TelefonoContacto,String Correo){
    
        boolean retornar ;
        
        
        Entidad entidadNueva = em.find(Entidad.class, Nit);
        
        if(entidadNueva == null){
            entidadNueva = new Entidad();
            entidadNueva.setIDNit(Nit);
            entidadNueva.setNombre(Nombre);
            entidadNueva.setNombreContacto(NombreContacto);
            entidadNueva.setDireccion(Direccion);
            entidadNueva.setTelefonoContacto(TelefonoContacto);
            entidadNueva.setCorreoContacto(Correo);
            em.merge(entidadNueva);
            
            retornar = true;
        
        }else{
        
            retornar = false;
        }
        
        return retornar;
        
    }
    
    /**
     *
     * @param NitEntidad
     * @param Nombre
     * @param NombreContacto
     * @param TelefonoContacto
     * @param Correo
     * @return
     */
    @Override
     public int AgregarSede(String NitEntidad, String Nombre, String NombreContacto, String TelefonoContacto, String Correo){
    
        int retornar = 1 ;
        
       String upd = new String();
       String upd1 = new String();
       String upd2 = new String();
       upd1= "select Nombre from Sede where Nombre = '"+Nombre+"'";
       
       upd2="select Nombre from Sede";
       
        upd= "select * from Sede where Entidad = '"+NitEntidad+"'";
        List<String> sedes ; 
        List<String> nombresSede;
        Query q,q1,q2;
        
        
        q=em.createNativeQuery(upd);
        sedes =  q.getResultList();
        
        q2= em.createNativeQuery(upd2);
        nombresSede = q2.getResultList();
        
        
        
         Entidad entidad = new Entidad();
        
        
          try{
               entidad = em.find(Entidad.class, NitEntidad);
             
         }catch(Exception ex){
             entidad=null;
         }
        
        
        if( nombresSede==null){
            
            Sede sedeNueva = new Sede();
   
            if(entidad != null){
                
            sedeNueva.setEntidad(entidad);
            sedeNueva.setIDSede("1");
            sedeNueva.setNombre(Nombre);
            sedeNueva.setNombreContacto(NombreContacto);
            sedeNueva.setTelefonoContacto(TelefonoContacto);
            sedeNueva.setCorreoContacto(Correo);
            em.persist(sedeNueva);
            
            retornar = 1;
            
            }else{
                
                retornar = 0;
                System.out.println("No se encontro la entidad");
                
            }
            
         
        }else{
        
        
            
            
            Sede sedeNueva = new Sede();
            
            q1=em.createNativeQuery(upd1);
            List<String> sedesVerificar ; 
            sedesVerificar = q1.getResultList();
   
            if(entidad != null && sedesVerificar.isEmpty()){
                
             sedeNueva.setEntidad(entidad);
             int ultimoId = nombresSede.size();
             ultimoId = ultimoId+1;
             String IdSede = Integer.toString(ultimoId);
             sedeNueva.setIDSede(IdSede);
             sedeNueva.setNombre(Nombre);
             sedeNueva.setNombreContacto(NombreContacto);
             sedeNueva.setTelefonoContacto(TelefonoContacto);
             sedeNueva.setCorreoContacto(Correo);
             em.persist(sedeNueva);
            
             retornar = ultimoId;
            
            }else{
                
                retornar = 0;
                System.out.println("No se encontro la entidad o ya existe una sede con el mismo nombre");
                
            }
            
        
        }
        
        return retornar;
        
        
    }
     
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
     public  TurnoBackUp TurnoReceptor(String cedulaEmp){
         List <Turno> turnos = new ArrayList<Turno>();
         Date fecha = new Date();
         fecha.getTime();
         TurnoBackUp retorno = new TurnoBackUp();
         Trabajo tr= new Trabajo();
         Query q ;
         q=em.createNativeQuery("select * from Turno",Turno.class);
         System.out.println("antes try");
         try{
             turnos= q.getResultList();
             
             if(!turnos.isEmpty()){ 
                 for(Turno t: turnos){
                     System.out.println("turno: " + t.toString());
                     if(t.getAtendido().equals("0")){
                         TurnoBackUp tb = new TurnoBackUp();
                         tb.setCorreoId(t.getUsuario().getCorreoId());
                        
                         tb.setTurno(t);
                         tr= this.HallarReceptor(cedulaEmp);
                         tb.setReceptor(tr);
                         tb.setFecha(fecha);
                         em.persist(tb);
                     }
                 }
             }else{
                 System.out.println("no encontro turno");
                 retorno = null;
             }
         }catch(Exception ex){
             System.out.println("No hay turnos"+ex);
         }
         return retorno;
     }
    @Override
     public Trabajo HallarReceptor(String cedulaEmp){
         Trabajo t = new Trabajo();
         try{
             t=em.find(Trabajo.class, cedulaEmp);
             
         }catch(Exception ex){
             t=null;
         }
         return t;
     }

    @Override
    public boolean AgregarEmpleado(String cedula,String nombre,String contrasena,String sede){
      
        boolean retorno =false;
        String upd ;
        Sede sedeBuscar = new Sede();
        String sedeVerificar ;
        EmpleadoPK llave   = new EmpleadoPK();
        EmpleadoPK busLlave = new EmpleadoPK();
        busLlave.setCedula(cedula);
        busLlave.setSede(sede);
      
       
         
        Empleado empleado = new Empleado();
        try{
            
            
            sedeBuscar=em.find(Sede.class, sede);
            
            empleado=em.find(Empleado.class, busLlave);
            
            
            
            
           
             }catch(Exception ex){
             
             System.out.println("NO nada en la base");
             retorno=false;
         }
          
        
        
             
             if( empleado != null ){
                 
                 llave= empleado.getEmpleadoPK();
                 sedeVerificar = llave.getSede();
                 
                 
                
                 if(!sedeVerificar.equals(sede) && sedeBuscar!=null){
                 
                 
                 llave.setCedula(cedula);
                 empleado.setNombre(nombre);
                 empleado.setContrasena(contrasena);
                 llave.setSede(sedeVerificar);
                 empleado.setEmpleadoPK(llave);
                 System.out.println("enontro sede y empleado nuevo");
                 em.merge(empleado);
                 
                  System.out.println("se agrego el empleado");
                 retorno = true;
                 }else{
                 
                     
                    
                     System.out.println("ya existe empleado o la sede no existe");
                 }
             
             
             }else{
                 
                 if(sedeBuscar!=null){
                 
                 Empleado empleado1 = new Empleado();
                 
                 
                 llave.setCedula(cedula);
                 
                 llave.setSede(sedeBuscar.getIDSede());
                 empleado1.setNombre(nombre);
                 empleado1.setContrasena(contrasena);
                 empleado1.setEmpleadoPK(llave);
                 
                 System.out.println("enontro sede y empleado nuevo");
                 em.merge(empleado1);
                 
                  System.out.println("se agrego el empleado");
                 retorno = true;
                 
                 }
                 else{
                 
                     
                    
                     System.out.println(" la sede no existe");
                 }
             
             }
             
         
               
    
        return retorno;
    }
    
    public boolean AgregarReceptor(String sede){
    
         boolean retorno = false ;
         List<String> sedesLista;
         Sede sedeBuscar = new Sede();
         
         
         String upd = new String();
         upd= "select Id_Receptor from Receptor ";
         Query q;
         
        
         
          try{
              
            q = em.createNativeQuery(upd);
            sedesLista = q.getResultList();
            
            sedeBuscar=em.find(Sede.class, sede);
            
            if(sedeBuscar!=null){
                
                int idSede = sedesLista.size() + 1;
                String id = Integer.toString(idSede);
                Receptor receptor = new Receptor();
                receptor.setSede(sedeBuscar);
                receptor.setEstado("0");
                receptor.setIdReceptor(id);
                em.merge(receptor);
                retorno = true;
            
            
            }
            
         
             }catch(Exception ex){
             
             System.out.println("NO existe la sede");
             
         }
         
         
         return retorno;
         
         
     
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
     public List<String> BuscarSedeCodigo (String cod){
     
        List<String> vecRetornar = new Vector<String>() ;
        
        try{
        Sede sede = em.find(Sede.class, cod);
        
        if(sede != null){
            
            vecRetornar.add(0, sede.getEntidad().getIDNit());
            vecRetornar.add(1, sede.getIDSede());
            vecRetornar.add(2, sede.getNombre());
            vecRetornar.add(3, sede.getNombreContacto());
            vecRetornar.add(4, sede.getTelefonoContacto());
            vecRetornar.add(5, sede.getCorreoContacto());
            
        }
        }catch(Exception ex){
             
             System.out.println("error");
             
         }
         
     return vecRetornar;
     
     }
     
     
     
     
         @Override
     public List<Sede> BuscarSedesClase (){
     
        List <Sede> vecRetornar = null;
        
        String upd ;
        upd= "select * from Sede ";
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
}

