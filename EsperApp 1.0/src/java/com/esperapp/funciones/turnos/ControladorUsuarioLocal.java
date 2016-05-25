/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esperapp.funciones.turnos;

import com.esperapp.entidades.Entidad;
import com.esperapp.entidades.Sede;
import com.esperapp.entidades.Servicio;
import com.esperapp.entidades.Trabajo;
import com.esperapp.entidades.Turno;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Joako
 */
public interface ControladorUsuarioLocal {

    Vector<String> BuscarEntidad(String Nit);

    List<Entidad> BuscarEntidadesNombres();

    List<Sede> BuscarSedesClase(String Nit);

    List<Servicio> BuscarServiciosSede(String codigoSede);

    boolean RegistarUsuario(String Nombre, String CorreoUsuario, String contrasena);

    List<Turno> TurnosNoAtendidosEmpleado(String idSede);

    List<Turno> VerHistorialTurnos(String correoId);

    String asignaTurnos(String CorreoUsuario, String Id_Sede, String servicioId);

    Trabajo asignarTrabajadorParaUsuario(String Cedula);

    Turno buscarTurno(String turno);

    boolean cancelarTurno(String Id_Turno);

    boolean loginUsuario(String idCorreo, String contra);
    
}
