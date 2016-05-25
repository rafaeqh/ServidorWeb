/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esperapp.funciones.turnos;

import com.esperapp.entidades.Empleado;
import com.esperapp.entidades.Sede;
import com.esperapp.entidades.Trabajo;
import com.esperapp.entidades.Turno;
import java.util.List;

/**
 *
 * @author Joako
 */
public interface ControladorEmpleadoLocal {

    List<String> AtenderCliente(String idSede, String cedula);

    List<Empleado> BuscarEmpleado(String cedula);

    String BuscarReceptorLibre();

    List<Sede> BuscarSedesClase(String Nit);

    void CambiarEstado(String Id_Receptor);

    Trabajo HallarReceptor(String cedulaEmp, String idSede);

    String RegistrarComoAtendido(String idTurno, String idReceptor);

    List<Turno> TurnosNoAtendidosEmpleado(String idSede);

    boolean loginEmpleado(String cedula, String contra, String sede);
    
}
