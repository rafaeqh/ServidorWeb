/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esperapp.entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Joako
 */
@Entity
@Table(name = "Sede")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sede.findAll", query = "SELECT s FROM Sede s"),
    @NamedQuery(name = "Sede.findByIDSede", query = "SELECT s FROM Sede s WHERE s.iDSede = :iDSede"),
    @NamedQuery(name = "Sede.findByNombre", query = "SELECT s FROM Sede s WHERE s.nombre = :nombre"),
    @NamedQuery(name = "Sede.findByNombreContacto", query = "SELECT s FROM Sede s WHERE s.nombreContacto = :nombreContacto"),
    @NamedQuery(name = "Sede.findByTelefonoContacto", query = "SELECT s FROM Sede s WHERE s.telefonoContacto = :telefonoContacto"),
    @NamedQuery(name = "Sede.findByCorreoContacto", query = "SELECT s FROM Sede s WHERE s.correoContacto = :correoContacto"),
    @NamedQuery(name = "Sede.findByDireccion", query = "SELECT s FROM Sede s WHERE s.direccion = :direccion")})
public class Sede implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "ID_Sede")
    private String iDSede;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "Nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "Nombre_Contacto")
    private String nombreContacto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "Telefono_Contacto")
    private String telefonoContacto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "Correo_Contacto")
    private String correoContacto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "Direccion")
    private String direccion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sede1")
    private Collection<Empleado> empleadoCollection;
    @JoinColumn(name = "Entidad", referencedColumnName = "ID_Nit")
    @ManyToOne(optional = false)
    private Entidad entidad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sede")
    private Collection<Servicio> servicioCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sede")
    private Collection<Turno> turnoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sede")
    private Collection<Receptor> receptorCollection;

    public Sede() {
    }

    public Sede(String iDSede) {
        this.iDSede = iDSede;
    }

    public Sede(String iDSede, String nombre, String nombreContacto, String telefonoContacto, String correoContacto, String direccion) {
        this.iDSede = iDSede;
        this.nombre = nombre;
        this.nombreContacto = nombreContacto;
        this.telefonoContacto = telefonoContacto;
        this.correoContacto = correoContacto;
        this.direccion = direccion;
    }

    public String getIDSede() {
        return iDSede;
    }

    public void setIDSede(String iDSede) {
        this.iDSede = iDSede;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public String getCorreoContacto() {
        return correoContacto;
    }

    public void setCorreoContacto(String correoContacto) {
        this.correoContacto = correoContacto;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @XmlTransient
    public Collection<Empleado> getEmpleadoCollection() {
        return empleadoCollection;
    }

    public void setEmpleadoCollection(Collection<Empleado> empleadoCollection) {
        this.empleadoCollection = empleadoCollection;
    }

    public Entidad getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }

    @XmlTransient
    public Collection<Servicio> getServicioCollection() {
        return servicioCollection;
    }

    public void setServicioCollection(Collection<Servicio> servicioCollection) {
        this.servicioCollection = servicioCollection;
    }

    @XmlTransient
    public Collection<Turno> getTurnoCollection() {
        return turnoCollection;
    }

    public void setTurnoCollection(Collection<Turno> turnoCollection) {
        this.turnoCollection = turnoCollection;
    }

    @XmlTransient
    public Collection<Receptor> getReceptorCollection() {
        return receptorCollection;
    }

    public void setReceptorCollection(Collection<Receptor> receptorCollection) {
        this.receptorCollection = receptorCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDSede != null ? iDSede.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sede)) {
            return false;
        }
        Sede other = (Sede) object;
        if ((this.iDSede == null && other.iDSede != null) || (this.iDSede != null && !this.iDSede.equals(other.iDSede))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.esperapp.entidades.Sede[ iDSede=" + iDSede + " ]";
    }
    
}
