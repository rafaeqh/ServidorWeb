/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esperapp.funciones.turnos;

import com.esperapp.entidades.Empleado;
import com.esperapp.entidades.Entidad;
import com.esperapp.entidades.Sede;
import com.esperapp.entidades.Servicio;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Joako
 */
public interface ControladorAdministradorLocal {

    boolean AgregarEmpleado(String cedula, String nombre, String contrasena, String sede);

    boolean AgregarEntidad(String Nit, String Nombre, String NombreContacto, String Direccion, String TelefonoContacto, String Correo);

    int AgregarReceptor(String sede, String idServicio);

    int AgregarSede(String NitEntidad, String Nombre, String NombreContacto, String TelefonoContacto, String Correo, String Direccion);

    boolean AgregarServicio(String codigoSede, String servicio);

    List<Empleado> BuscarEmpleado(String cedula);

    Vector<String> BuscarEntidad(String Nit);

    List<Entidad> BuscarEntidadesNombres();

    List<String> BuscarSedeCodigo(String cod);

    List<Sede> BuscarSedesClase();

    List<Servicio> BuscarServiciosSede(String codigoSede);

    boolean loginAdmin(String idCorreo, String contra);

    public boolean EditarCuenta(String Correo_Id, String nombre, String contra);
    
}
