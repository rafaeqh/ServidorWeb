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
@Table(name = "Entidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Entidad.findAll", query = "SELECT e FROM Entidad e"),
    @NamedQuery(name = "Entidad.findByIDNit", query = "SELECT e FROM Entidad e WHERE e.iDNit = :iDNit"),
    @NamedQuery(name = "Entidad.findByNombre", query = "SELECT e FROM Entidad e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "Entidad.findByNombreContacto", query = "SELECT e FROM Entidad e WHERE e.nombreContacto = :nombreContacto"),
    @NamedQuery(name = "Entidad.findByDireccion", query = "SELECT e FROM Entidad e WHERE e.direccion = :direccion"),
    @NamedQuery(name = "Entidad.findByTelefonoContacto", query = "SELECT e FROM Entidad e WHERE e.telefonoContacto = :telefonoContacto"),
    @NamedQuery(name = "Entidad.findByCorreoContacto", query = "SELECT e FROM Entidad e WHERE e.correoContacto = :correoContacto")})
public class Entidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "ID_Nit")
    private String iDNit;
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
    @Column(name = "Direccion")
    private String direccion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "TelefonoContacto")
    private String telefonoContacto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "Correo_Contacto")
    private String correoContacto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entidad")
    private Collection<Sede> sedeCollection;

    public Entidad() {
    }

    public Entidad(String iDNit) {
        this.iDNit = iDNit;
    }

    public Entidad(String iDNit, String nombre, String nombreContacto, String direccion, String telefonoContacto, String correoContacto) {
        this.iDNit = iDNit;
        this.nombre = nombre;
        this.nombreContacto = nombreContacto;
        this.direccion = direccion;
        this.telefonoContacto = telefonoContacto;
        this.correoContacto = correoContacto;
    }

    public String getIDNit() {
        return iDNit;
    }

    public void setIDNit(String iDNit) {
        this.iDNit = iDNit;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    @XmlTransient
    public Collection<Sede> getSedeCollection() {
        return sedeCollection;
    }

    public void setSedeCollection(Collection<Sede> sedeCollection) {
        this.sedeCollection = sedeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDNit != null ? iDNit.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Entidad)) {
            return false;
        }
        Entidad other = (Entidad) object;
        if ((this.iDNit == null && other.iDNit != null) || (this.iDNit != null && !this.iDNit.equals(other.iDNit))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.esperapp.entidades.Entidad[ iDNit=" + iDNit + " ]";
    }
    
}
