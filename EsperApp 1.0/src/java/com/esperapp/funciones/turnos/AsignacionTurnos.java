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
public class AsignacionTurnos implements AsignacionTurnosLocal {

    public AsignacionTurnos() {
    }
    @PersistenceContext (unitName = "EsperAppPU")
        private EntityManager em;
    //----------------------METODOS DE TURNOS--------------------------------------
    @Override
    public String asignaTurnos(String CorreoUsuario, String Id_Sede, String Id_Servicio){
        Date fecha = new Date();
        fecha.getTime();
        Query q;
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
                    Servicio servicioID = em.find(Servicio.class, Id_Servicio);
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
                    auxT.setServicioID(servicioID);

                    auxT.setAtendido("0");
        
        
        
        em.merge(auxT);
             
         }catch(Exception ex){
             System.out.println("Error al buscar turnos ---------");
         }
      
       
        
        //em.persist(auxT);
        return ultimoTurnRetornar;
    }
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
    public Trabajo asignarTrabajadorParaUsuario(String Cedula){
        Trabajo retorno = new Trabajo();
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
    /*public String BuscarSede(String CorreoUsuario, String iDSede){
        Sede sed = em.find(Sede.class, iDSede);
        System.out.println("sede: " + sed.getNombre());
        String turno = this.asignaTurnos(CorreoUsuario, sed);
        
        return turno;
    }*/
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
     public int AgregarSede(String NitEntidad, String Nombre, String NombreContacto, String TelefonoContacto, String Correo, String Direccion){
    
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
            sedeNueva.setDireccion(Direccion);
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
             sedeNueva.setDireccion(Direccion);
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
     /*
     public  TurnoBackUp TurnoReceptor(String cedulaEmp, String idSede){
         List <Turno> turnos = new ArrayList<Turno>();
         Date fecha = new Date();
         fecha.getTime();
         Turno t = new Turno();
         TurnoBackUp tb = new TurnoBackUp();
         Trabajo tr= new Trabajo();
         Query q, q1 ;
         int max;
         System.out.println("cedula: "+cedulaEmp+"Sede= "+idSede);
         q=em.createNativeQuery("select * from Turno where Sede='"+idSede+"' and Atendido='0'",Turno.class);
         q1=em.createNativeQuery("select * from Turno_BackUp", TurnoBackUp.class);
         try{
             turnos= q.getResultList();
             max = q1.getResultList().size()+1;
             
             if(!turnos.isEmpty()){ 
                Iterator<Turno> itTurno = turnos.iterator();
                t=itTurno.next();
                tb.setConsecutivo(String.valueOf(max));
                tb.setCedula(cedulaEmp);
                tb.setCorreoId(t.getUsuario().getCorreoId());
                tb.setFecha(fecha);
                tr=HallarReceptor(cedulaEmp, idSede);
                tb.setReceptor(tr);
                tb.setTurno(t);
                em.merge(tb);
             }else{
                 System.out.println("no encontro turno");
                 tb = null;
             }
         }catch(Exception ex){
             System.out.println("No hay turnos "+ex.getLocalizedMessage());
         }
         return tb;
     }*/
    
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
     /*public Turno PedirTurnoEmpleado(String idSede, String Cedula){
         Query q;
         List<Turno> Turnos;
         q=em.createNativeQuery("select * from Turno where Atendido='0' and Sede='"+idSede+"'", Turno.class );
         try{
             Turnos = q.getResultList();
             
         }catch(Exception ex){
             ex.printStackTrace();
         }
     }
     public Trabajo NoAtendido(String turno, String idSede, String idServicio){
         Query q, q1, q2; 
         List<Trabajo> trabajadores;
         Turno t;
         Trabajo retorno = null;
         Receptor receptor;
         q=em.createNativeQuery("select * from Trabajo where Sede='"+idSede+"'", Trabajo.class);
         q1=em.createNativeQuery("select * from Turno where Atendido='0' and Id_Turno='"+turno+"'", Turno.class );
         q2=em.createNativeQuery("select * from Receptor where Id_Servicio='"+idServicio+"' and Estado='0'", Receptor.class);
         q2=em.createNativeQuery("select * from Empleado where Sede='"+idSede+"'", Receptor.class);
         
         try{
             trabajadores = q.getResultList();
             
             t = (Turno) q1.getSingleResult();
             receptor=(Receptor) q2.getSingleResult();
             if(t.getServicioID().getIdServicio().equals(idServicio)){
                 retorno.setReceptor1(receptor);
                 retorno.setEmpleado(receptor.getTrabajo().getEmpleado());
             }
         }catch(Exception e){
             e.printStackTrace();
         }
         return retorno;
    }
     */
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
    
    public int  AgregarReceptor(String sede, String idServicio){
    
         int retorno = -1 ;
         List<String> receptoresLista;
         Sede sedeBuscar = null;
         Servicio servicio = null;
         
         String upd = new String();
         upd= "select Id_Receptor from Receptor ";
         Query q;
         
        
         
          try{
              
            q = em.createNativeQuery(upd);
            receptoresLista = q.getResultList();
            
            sedeBuscar=em.find(Sede.class, sede);
            servicio = em.find(Servicio.class, idServicio);
            
            if(sedeBuscar!=null && servicio!=null){
                
                
                int idReceptor = receptoresLista.size() + 1;
                String id = Integer.toString(idReceptor);
                Receptor receptor = new Receptor();
                receptor.setSede(sedeBuscar);
                receptor.setEstado("0");
                receptor.setIdReceptor(id);
                receptor.setIdServicio(servicio);
                em.merge(receptor);
                retorno = idReceptor ;
            
            
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
            vecRetornar.add(6, sede.getDireccion());
            
        }
        }catch(Exception ex){
             
             System.out.println("error");
             
         }
         
     return vecRetornar;
     
     }
     
     public List<Sede> BuscarSedesClase (){
     
        List <Sede> vecRetornar = null;
        
        String upd ;
        upd= "select * from Sede";
        Query q;
        
        
        try{
            
            q = em.createNativeQuery(upd,Sede.class);
            
            vecRetornar = q.getResultList();
            
            
        
        }catch(Exception ex){
             
             System.out.println("error");
             
         }
         
     return vecRetornar;
     
     }
     
     
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
     
     //Metodos usuario-----------------------------------------------------------------------------------------------
   
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
   
    //-----------------------------------------------------------------
    
    public boolean AgregarServicio (String codigoSede, String servicio){
    
       boolean retorno = false ;
         List<String> serviciosLista;
         Sede sedeBuscar = new Sede();
         
         
         String upd = new String();
         upd= "select Id_Servicio from Servicio ";
         Query q;
         
        
         
          try{
              
            q = em.createNativeQuery(upd);
            serviciosLista = q.getResultList();
            
            sedeBuscar=em.find(Sede.class, codigoSede);
            
            if(sedeBuscar!=null){
                
                int idServicio = serviciosLista.size() + 1;
                 String id = Integer.toString(idServicio);
                Servicio servicioNuevo= new Servicio();
                servicioNuevo.setTipo(servicio);
                servicioNuevo.setIdServicio(id);
                servicioNuevo.setSede(sedeBuscar);
                em.merge(servicioNuevo);
                retorno = true;
            
            
            }
            
         
             }catch(Exception ex){
             
             System.out.println("NO existe la sede");
             
         }
                  
         return retorno;

    }
     
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
        public List<String> AtenderCliente(String idSede, String cedula){
          List<String> retorno = new ArrayList<String>() ;
          Trabajo trabajo;
          List<Turno> turnosAtender;
          Turno turno =null;
          Empleado emp = new Empleado();
          String upd = new String();
          String upd1 = new String();
          upd = "select * from Turno where Sede='"+idSede+"'and Atendido = '0'";
          
          Query q, q1;
          Usuario usuario = null;

          try{
            
            emp = em.find(Empleado.class, cedula);
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

    
}

