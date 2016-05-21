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
import com.esperapp.entidades.Trabajo;
import com.esperapp.entidades.TurnoBackUp;
import java.util.List;
import java.util.Vector;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AsignacionTurnosLocal {


    public boolean loginAdmin(String nombre, String contra);

    public boolean loginEmpleado(String cedula, String contra,String sede);

    public String asignaTurnos(String CorreoUsuario, Sede Id_Sede);

    public String BuscarSede(String CorreoUsuario, String Id_Sede);

    public void CambiarEstado(String Id_Receptor);
    
    

    public boolean AgregarEntidad(String Nit, String Nombre, String NombreContacto, String Direccion, String TelefonoContacto, String Correo);

    public int AgregarSede(String NitEntidad, String Nombre, String NombreContacto, String TelefonoContacto, String Correo, String Direccion);

    public Vector<String> BuscarEntidad(String Nit);

    public TurnoBackUp TurnoReceptor(String cedulaEmp, String idSede);

    public Trabajo HallarReceptor(String cedulaEmp);

   

    public String BuscarReceptorLibre();

    public boolean AgregarEmpleado(String cedula, String nombre, String contrasena, String sede);

     public int  AgregarReceptor(String sede, String idServicio);

    
    public List<Entidad> BuscarEntidadesNombres();

    

    public List<String> BuscarSedeCodigo(String cod);

    public List<Sede> BuscarSedesClase(String Nit);

    public List<Empleado> BuscarEmpleado(String cedula);
    
    public boolean loginUsuario(String idCorreo, String contra);
    
    public boolean AgregarServicio (String codigoSede, String servicio);
    
    public List<Servicio> BuscarServiciosSede (String codigoSede);
    
    public boolean RegistarUsuario(String Nombre, String CorreoUsuario, String contrasena);

    public List<Sede> BuscarSedesClase();

    
    
    
     
     
}
