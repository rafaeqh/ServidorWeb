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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "Trabajo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Trabajo.findAll", query = "SELECT t FROM Trabajo t"),
    @NamedQuery(name = "Trabajo.findByReceptor", query = "SELECT t FROM Trabajo t WHERE t.receptor = :receptor")})
public class Trabajo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "Receptor")
    private String receptor;
    @JoinColumn(name = "Receptor", referencedColumnName = "Id_Receptor", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Receptor receptor1;
    @JoinColumns({
        @JoinColumn(name = "Empleado", referencedColumnName = "Cedula"),
        @JoinColumn(name = "Sede", referencedColumnName = "Sede")})
    @ManyToOne(optional = false)
    private Empleado empleado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "receptor")
    private Collection<TurnoBackUp> turnoBackUpCollection;

    public Trabajo() {
    }

    public Trabajo(String receptor) {
        this.receptor = receptor;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public Receptor getReceptor1() {
        return receptor1;
    }

    public void setReceptor1(Receptor receptor1) {
        this.receptor1 = receptor1;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    @XmlTransient
    public Collection<TurnoBackUp> getTurnoBackUpCollection() {
        return turnoBackUpCollection;
    }

    public void setTurnoBackUpCollection(Collection<TurnoBackUp> turnoBackUpCollection) {
        this.turnoBackUpCollection = turnoBackUpCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (receptor != null ? receptor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Trabajo)) {
            return false;
        }
        Trabajo other = (Trabajo) object;
        if ((this.receptor == null && other.receptor != null) || (this.receptor != null && !this.receptor.equals(other.receptor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.esperapp.entidades.Trabajo[ receptor=" + receptor + " ]";
    }
    
}
