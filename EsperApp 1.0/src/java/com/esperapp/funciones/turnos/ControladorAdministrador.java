/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esperapp.funciones.turnos;

import com.esperapp.entidades.Administrador;
import com.esperapp.entidades.Empleado;
import com.esperapp.entidades.EmpleadoPK;
import com.esperapp.entidades.Entidad;
import com.esperapp.entidades.Receptor;
import com.esperapp.entidades.Sede;
import com.esperapp.entidades.Servicio;
import java.util.List;
import java.util.Vector;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Joako
 */
@Stateless
public class ControladorAdministrador implements ControladorAdministradorLocal {
    
    @PersistenceContext(unitName = "EsperAppPU")
    protected EntityManager em;

    public ControladorAdministrador() {
    }

    @Override
    public boolean EditarCuenta(String Correo_Id, String nombre, String contra){
        boolean retorno = false;
        String upd;
        Query q;
        upd = "update Administrador set Nombre = '"+nombre+"', Contrasena='"+contra+"' where Correo_Id = '"+Correo_Id+"'";
        System.out.println(upd);
        q=em.createNativeQuery(upd);
        try{
            q.executeUpdate();
            retorno = true;
        }catch(Exception e){
            retorno = false;
            //e.printStackTrace();
        }
        return retorno;
    }
    //--------------------------------------------------------------------------------
    @Override
    public boolean loginAdmin(String idCorreo, String contra) {
        boolean retorno = false;
        Administrador admin = new Administrador();
        try {
            admin = em.find(Administrador.class, idCorreo);
            if (admin.getCorreoId().equals(idCorreo)) {
                System.out.println("Nombre valido");
                if (admin.getContrasena().equals(contra)) {
                    System.out.println("contraseña valida");
                    retorno = true;
                } else {
                    System.out.println("contraseña invalida");
                }
            } else {
                System.out.println("Nombre invalido");
            }
        } catch (Exception ex) {
            retorno = false;
        }
        return retorno;
    }

    @Override
    public boolean AgregarEntidad(String Nit, String Nombre, String NombreContacto, String Direccion, String TelefonoContacto, String Correo) {
        boolean retornar;
        Entidad entidadNueva = em.find(Entidad.class, Nit);
        if (entidadNueva == null) {
            entidadNueva = new Entidad();
            entidadNueva.setIDNit(Nit);
            entidadNueva.setNombre(Nombre);
            entidadNueva.setNombreContacto(NombreContacto);
            entidadNueva.setDireccion(Direccion);
            entidadNueva.setTelefonoContacto(TelefonoContacto);
            entidadNueva.setCorreoContacto(Correo);
            em.merge(entidadNueva);
            retornar = true;
        } else {
            retornar = false;
        }
        return retornar;
    }

    @Override
    public int AgregarSede(String NitEntidad, String Nombre, String NombreContacto, String TelefonoContacto, String Correo, String Direccion) {
        int retornar = 1;
        String upd = new String();
        String upd1 = new String();
        String upd2 = new String();
        upd1 = "select Nombre from Sede where Nombre = '" + Nombre + "'";
        upd2 = "select Nombre from Sede";
        upd = "select * from Sede where Entidad = '" + NitEntidad + "'";
        List<String> sedes;
        List<String> nombresSede;
        Query q;
        Query q1;
        Query q2;
        q = em.createNativeQuery(upd);
        sedes = q.getResultList();
        q2 = em.createNativeQuery(upd2);
        nombresSede = q2.getResultList();
        Entidad entidad = new Entidad();
        try {
            entidad = em.find(Entidad.class, NitEntidad);
        } catch (Exception ex) {
            entidad = null;
        }
        if (nombresSede == null) {
            Sede sedeNueva = new Sede();
            if (entidad != null) {
                sedeNueva.setEntidad(entidad);
                sedeNueva.setIDSede("1");
                sedeNueva.setNombre(Nombre);
                sedeNueva.setNombreContacto(NombreContacto);
                sedeNueva.setTelefonoContacto(TelefonoContacto);
                sedeNueva.setCorreoContacto(Correo);
                sedeNueva.setDireccion(Direccion);
                em.persist(sedeNueva);
                retornar = 1;
            } else {
                retornar = 0;
                System.out.println("No se encontro la entidad");
            }
        } else {
            Sede sedeNueva = new Sede();
            q1 = em.createNativeQuery(upd1);
            List<String> sedesVerificar;
            sedesVerificar = q1.getResultList();
            if (entidad != null && sedesVerificar.isEmpty()) {
                sedeNueva.setEntidad(entidad);
                int ultimoId = nombresSede.size();
                ultimoId = ultimoId + 1;
                String IdSede = Integer.toString(ultimoId);
                sedeNueva.setIDSede(IdSede);
                sedeNueva.setNombre(Nombre);
                sedeNueva.setNombreContacto(NombreContacto);
                sedeNueva.setTelefonoContacto(TelefonoContacto);
                sedeNueva.setCorreoContacto(Correo);
                sedeNueva.setDireccion(Direccion);
                em.persist(sedeNueva);
                retornar = ultimoId;
            } else {
                retornar = 0;
                System.out.println("No se encontro la entidad o ya existe una sede con el mismo nombre");
            }
        }
        return retornar;
    }

    @Override
    public Vector<String> BuscarEntidad(String Nit) {
        Vector<String> vecRetornar = new Vector<String>();
        Entidad entidad = em.find(Entidad.class, Nit);
        if (entidad != null) {
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
    public boolean AgregarEmpleado(String cedula, String nombre, String contrasena, String sede) {
        boolean retorno = false;
        String upd;
        Sede sedeBuscar = new Sede();
        String sedeVerificar;
        EmpleadoPK llave = new EmpleadoPK();
        EmpleadoPK busLlave = new EmpleadoPK();
        busLlave.setCedula(cedula);
        busLlave.setSede(sede);
        Empleado empleado = new Empleado();
        try {
            sedeBuscar = em.find(Sede.class, sede);
            empleado = em.find(Empleado.class, busLlave);
        } catch (Exception ex) {
            System.out.println("NO nada en la base");
            retorno = false;
        }
        if (empleado != null) {
            llave = empleado.getEmpleadoPK();
            sedeVerificar = llave.getSede();
            if (!sedeVerificar.equals(sede) && sedeBuscar != null) {
                llave.setCedula(cedula);
                empleado.setNombre(nombre);
                empleado.setContrasena(contrasena);
                llave.setSede(sedeVerificar);
                empleado.setEmpleadoPK(llave);
                System.out.println("enontro sede y empleado nuevo");
                em.merge(empleado);
                System.out.println("se agrego el empleado");
                retorno = true;
            } else {
                System.out.println("ya existe empleado o la sede no existe");
            }
        } else {
            if (sedeBuscar != null) {
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
            } else {
                System.out.println(" la sede no existe");
            }
        }
        return retorno;
    }

    @Override
    public int AgregarReceptor(String sede, String idServicio) {
        int retorno = -1;
        List<String> receptoresLista;
        Sede sedeBuscar = null;
        Servicio servicio = null;
        String upd = new String();
        upd = "select Id_Receptor from Receptor ";
        Query q;
        try {
            q = em.createNativeQuery(upd);
            receptoresLista = q.getResultList();
            sedeBuscar = em.find(Sede.class, sede);
            servicio = em.find(Servicio.class, idServicio);
            if (sedeBuscar != null && servicio != null) {
                int idReceptor = receptoresLista.size() + 1;
                String id = Integer.toString(idReceptor);
                Receptor receptor = new Receptor();
                receptor.setSede(sedeBuscar);
                receptor.setEstado("0");
                receptor.setIdReceptor(id);
                receptor.setIdServicio(servicio);
                em.merge(receptor);
                retorno = idReceptor;
            }
        } catch (Exception ex) {
            System.out.println("NO existe la sede");
        }
        return retorno;
    }

    @Override
    public List<Entidad> BuscarEntidadesNombres() {
        List<Entidad> vecRetornar = null;
        String upd;
        upd = "select * from Entidad ";
        Query q;
        try {
            q = em.createNativeQuery(upd, Entidad.class);
            vecRetornar = q.getResultList();
        } catch (Exception ex) {
            System.out.println("error");
        }
        return vecRetornar;
    }

    @Override
    public List<String> BuscarSedeCodigo(String cod) {
        List<String> vecRetornar = new Vector<String>();
        try {
            Sede sede = em.find(Sede.class, cod);
            if (sede != null) {
                vecRetornar.add(0, sede.getEntidad().getIDNit());
                vecRetornar.add(1, sede.getIDSede());
                vecRetornar.add(2, sede.getNombre());
                vecRetornar.add(3, sede.getNombreContacto());
                vecRetornar.add(4, sede.getTelefonoContacto());
                vecRetornar.add(5, sede.getCorreoContacto());
                vecRetornar.add(6, sede.getDireccion());
            }
        } catch (Exception ex) {
            System.out.println("error");
        }
        return vecRetornar;
    }

    @Override
    public List<Sede> BuscarSedesClase() {
        List<Sede> vecRetornar = null;
        String upd;
        upd = "select * from Sede";
        Query q;
        try {
            q = em.createNativeQuery(upd, Sede.class);
            vecRetornar = q.getResultList();
        } catch (Exception ex) {
            System.out.println("error");
        }
        return vecRetornar;
    }

    @Override
    public List<Empleado> BuscarEmpleado(String cedula) {
        List<Empleado> vecRetornar = null;
        String upd;
        upd = "select * from Empleado  where Cedula = '" + cedula + "'";
        Query q;
        try {
            q = em.createNativeQuery(upd, Empleado.class);
            vecRetornar = q.getResultList();
        } catch (Exception ex) {
            System.out.println("error");
        }
        return vecRetornar;
    }

    @Override
    public boolean AgregarServicio(String codigoSede, String servicio) {
        boolean retorno = false;
        List<String> serviciosLista;
        Sede sedeBuscar = new Sede();
        String upd = new String();
        upd = "select Id_Servicio from Servicio ";
        Query q;
        try {
            q = em.createNativeQuery(upd);
            serviciosLista = q.getResultList();
            sedeBuscar = em.find(Sede.class, codigoSede);
            if (sedeBuscar != null) {
                int idServicio = serviciosLista.size() + 1;
                String id = Integer.toString(idServicio);
                Servicio servicioNuevo = new Servicio();
                servicioNuevo.setTipo(servicio);
                servicioNuevo.setIdServicio(id);
                servicioNuevo.setSede(sedeBuscar);
                em.merge(servicioNuevo);
                retorno = true;
            }
        } catch (Exception ex) {
            System.out.println("NO existe la sede");
        }
        return retorno;
    }

    @Override
    public List<Servicio> BuscarServiciosSede(String codigoSede) {
        List<Servicio> vecRetornar = null;
        String upd;
        upd = "select * from Servicio where Sede = '" + codigoSede + "'";
        Query q;
        try {
            q = em.createNativeQuery(upd, Servicio.class);
            vecRetornar = q.getResultList();
        } catch (Exception ex) {
            System.out.println("error");
        }
        return vecRetornar;
    }
    
}
