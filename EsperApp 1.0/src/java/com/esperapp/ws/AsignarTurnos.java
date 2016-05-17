/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esperapp.ws;

import com.esperapp.entidades.Empleado;
import com.esperapp.entidades.Entidad;
import com.esperapp.entidades.Sede;
import com.esperapp.entidades.Servicio;
import com.esperapp.entidades.TurnoBackUp;

import com.esperapp.funciones.turnos.AsignacionTurnosLocal;
import java.util.List;
import java.util.Vector;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import javax.jws.Oneway;

/**
 *
 * @author user
 */
@WebService(serviceName = "AsignarTurnos")
@Stateless()
public class AsignarTurnos {
    @EJB
    private AsignacionTurnosLocal at;
    
    

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }


    /**
     * Web service operation
     */
    @WebMethod(operationName = "loginAdmin")
    public boolean loginAdmin(@WebParam(name = "idCorreo") String nombre, @WebParam(name = "contra") String contra) {
        boolean retorno = false;
        retorno = at.loginAdmin(nombre, contra);
        return retorno;
    }


    /**
     * Web service operation
     */
    @WebMethod(operationName = "AsignarTurno")
    public String AsignarTurno(@WebParam(name = "CorreoUsuario") String CorreoUsuario, @WebParam(name = "Id_Sede") Sede Id_Sede) {
        String retorno;
        retorno = at.asignaTurnos(CorreoUsuario, Id_Sede);
        return retorno;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "BuscarSede")
    public String BuscarSede(@WebParam(name = "CorreoUsuario") String CorreoUsuario, @WebParam(name = "Sede") String Id_Sede) {
        String retorno = new String();
        retorno = at.BuscarSede(CorreoUsuario, Id_Sede);
        return retorno;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "CambiarEstado")
    @Oneway
    public void CambiarEstado(@WebParam(name = "Id_Receptor") String Id_Receptor) {
        at.CambiarEstado(Id_Receptor);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "AgregarEntidad")
    public boolean AgregarEntidad(@WebParam(name = "Nit") String Nit, @WebParam(name = "Nombre") String Nombre, @WebParam(name = "NombreContacto") String NombreContacto, @WebParam(name = "Direccion") String Direccion, @WebParam(name = "TelefonoContacto") String TelefonoContacto, @WebParam(name = "Correo") String Correo) {
        //TODO write your implementation code here:
       
        
        return at.AgregarEntidad(Nit, Nombre, NombreContacto, Direccion, TelefonoContacto, Correo);
        
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "AgregarSede")
    public int AgregarSede(@WebParam(name = "NitEntidad") String NitEntidad, @WebParam(name = "Nombre") String Nombre, @WebParam(name = "NombreContacto") String NombreContacto, @WebParam(name = "TelefonoContacto") String TelefonoContacto, @WebParam(name = "Correo") String Correo,@WebParam(name = "Direccion") String Direccion) {
        //TODO write your implementation code here:
        return at.AgregarSede(NitEntidad, Nombre, NombreContacto, TelefonoContacto, Correo, Direccion);
        
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "BuscarEntidad")
    public Vector<String> BuscarEntidad(@WebParam(name = "Nit") String Nit) {
        //TODO write your implementation code here:
        return at.BuscarEntidad(Nit);
        
    }

    /**
     * Web service operation
     */
    /*@WebMethod(operationName = "TurnoReceptor")
    public TurnoBackUp TurnoReceptor(String cedulaEmp) {
        //TODO write your implementation code here:
        TurnoBackUp retorno = new TurnoBackUp();
        retorno = at.TurnoReceptor(cedulaEmp);
        return retorno;
    }
*/
    /**
     * Web service operation
     */
    
    @WebMethod(operationName = "AgregarEmpleado")
    public boolean AgregarEmpleado(@WebParam(name = "cedula") String cedula, @WebParam(name = "nombre") String nombre, @WebParam(name = "contrasena") String contrasena, @WebParam(name = "sede") String sede) {
        //TODO write your implementation code here:
        return  at.AgregarEmpleado(cedula, nombre, contrasena, sede);
    }


    /**
     * Web service operation
     */
    @WebMethod(operationName = "BuscarEntidadesNombres")
    public List<Entidad> BuscarEntidadesNombres() {
        //TODO write your implementation code here:
        return at.BuscarEntidadesNombres();
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "BuscarSedeCodigo")
    public List<String> BuscarSedeCodigo(@WebParam(name = "cod") String cod) {
        //TODO write your implementation code here:
        return at.BuscarSedeCodigo(cod);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "BuscarSedesClase")
    public List<Sede> BuscarSedesClase() {
        //TODO write your implementation code here:
        return at.BuscarSedesClase();
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "BuscarEmpleado")
    public List<Empleado> BuscarEmpleado(@WebParam(name = "cedula") String cedula) {
        //TODO write your implementation code here:
        return at.BuscarEmpleado(cedula);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "loginEmpleado")
    public boolean loginEmpleado(@WebParam(name = "cedula") String cedula, @WebParam(name = "contra") String contra, @WebParam(name = "sede") String sede) {
        //TODO write your implementation code here:
        return at.loginEmpleado(cedula, contra, sede);
    }
    
@WebMethod(operationName = "loginUsuario")
    public boolean loginUsuario(@WebParam(name = "correoId") String idCorreo, @WebParam(name = "contrasena") String contra) {
        //TODO write your implementation code here:
        return at.loginUsuario(idCorreo, contra);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "BuscarServiciosSede")
    public List<Servicio> BuscarServiciosSede(@WebParam(name = "codigoSede") String codigoSede) {
        //TODO write your implementation code here:
        return at.BuscarServiciosSede(codigoSede);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "AgregarServicio")
    public boolean AgregarServicio(@WebParam(name = "codigoSede") String codigoSede, @WebParam(name = "servicio") String servicio) {
        //TODO write your implementation code here:
        return at.AgregarServicio(codigoSede, servicio);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "AgregarReceptor")
    public int AgregarReceptor(@WebParam(name = "sede") String sede, @WebParam(name = "idServicio") String idServicio) {
        //TODO write your implementation code here:
        return at.AgregarReceptor(sede, idServicio);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "RegistrarUsuario")
    public boolean RegistrarUsuario(@WebParam(name = "Nombre") String Nombre, @WebParam(name = "CorreoUsuario") String CorreoUsuario, @WebParam(name = "contrasena") String contrasena) {
        //TODO write your implementation code here:
        return at.RegistarUsuario(Nombre, CorreoUsuario, contrasena);
    }

}
