/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esperapp.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Rafael
 */
@Embeddable
public class EmpleadoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "Cedula")
    private String cedula;
    @Basic(optional = false)
    @Column(name = "Sede")
    private String sede;

    public EmpleadoPK() {
    }

    public EmpleadoPK(String cedula, String sede) {
        this.cedula = cedula;
        this.sede = sede;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cedula != null ? cedula.hashCode() : 0);
        hash += (sede != null ? sede.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpleadoPK)) {
            return false;
        }
        EmpleadoPK other = (EmpleadoPK) object;
        if ((this.cedula == null && other.cedula != null) || (this.cedula != null && !this.cedula.equals(other.cedula))) {
            return false;
        }
        if ((this.sede == null && other.sede != null) || (this.sede != null && !this.sede.equals(other.sede))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.esperapp.entidades.EmpleadoPK[ cedula=" + cedula + ", sede=" + sede + " ]";
    }
    
}
