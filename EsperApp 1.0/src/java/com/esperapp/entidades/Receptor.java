/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esperapp.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Rafael
 */
@Entity
@Table(name = "Receptor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Receptor.findAll", query = "SELECT r FROM Receptor r"),
    @NamedQuery(name = "Receptor.findByIdReceptor", query = "SELECT r FROM Receptor r WHERE r.idReceptor = :idReceptor"),
    @NamedQuery(name = "Receptor.findByEstado", query = "SELECT r FROM Receptor r WHERE r.estado = :estado")})
public class Receptor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id_Receptor")
    private String idReceptor;
    @Basic(optional = false)
    @Column(name = "Estado")
    private String estado;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "receptor1")
    private Trabajo trabajo;
    @JoinColumn(name = "Id_Servicio", referencedColumnName = "Id_Servicio")
    @ManyToOne(optional = false)
    private Servicio idServicio;
    @JoinColumn(name = "Sede", referencedColumnName = "ID_Sede")
    @ManyToOne(optional = false)
    private Sede sede;

    public Receptor() {
    }

    public Receptor(String idReceptor) {
        this.idReceptor = idReceptor;
    }

    public Receptor(String idReceptor, String estado) {
        this.idReceptor = idReceptor;
        this.estado = estado;
    }

    public String getIdReceptor() {
        return idReceptor;
    }

    public void setIdReceptor(String idReceptor) {
        this.idReceptor = idReceptor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Trabajo getTrabajo() {
        return trabajo;
    }

    public void setTrabajo(Trabajo trabajo) {
        this.trabajo = trabajo;
    }

    public Servicio getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Servicio idServicio) {
        this.idServicio = idServicio;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReceptor != null ? idReceptor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Receptor)) {
            return false;
        }
        Receptor other = (Receptor) object;
        if ((this.idReceptor == null && other.idReceptor != null) || (this.idReceptor != null && !this.idReceptor.equals(other.idReceptor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.esperapp.entidades.Receptor[ idReceptor=" + idReceptor + " ]";
    }
    
}
