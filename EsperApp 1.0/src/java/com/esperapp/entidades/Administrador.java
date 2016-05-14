/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esperapp.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Rafael
 */
@Entity
@Table(name = "Administrador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Administrador.findAll", query = "SELECT a FROM Administrador a"),
    @NamedQuery(name = "Administrador.findByCorreoId", query = "SELECT a FROM Administrador a WHERE a.correoId = :correoId"),
    @NamedQuery(name = "Administrador.findByNombre", query = "SELECT a FROM Administrador a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "Administrador.findByContrasena", query = "SELECT a FROM Administrador a WHERE a.contrasena = :contrasena")})
public class Administrador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Correo_Id")
    private String correoId;
    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "Contrasena")
    private String contrasena;

    public Administrador() {
    }

    public Administrador(String correoId) {
        this.correoId = correoId;
    }

    public Administrador(String correoId, String nombre, String contrasena) {
        this.correoId = correoId;
        this.nombre = nombre;
        this.contrasena = contrasena;
    }

    public String getCorreoId() {
        return correoId;
    }

    public void setCorreoId(String correoId) {
        this.correoId = correoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (correoId != null ? correoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Administrador)) {
            return false;
        }
        Administrador other = (Administrador) object;
        if ((this.correoId == null && other.correoId != null) || (this.correoId != null && !this.correoId.equals(other.correoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.esperapp.entidades.Administrador[ correoId=" + correoId + " ]";
    }
    
}
