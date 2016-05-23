/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esperapp.entidades;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Joako
 */
@Entity
@Table(name = "Turno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Turno.findAll", query = "SELECT t FROM Turno t"),
    @NamedQuery(name = "Turno.findByIdTurno", query = "SELECT t FROM Turno t WHERE t.idTurno = :idTurno"),
    @NamedQuery(name = "Turno.findByNumTurno", query = "SELECT t FROM Turno t WHERE t.numTurno = :numTurno"),
    @NamedQuery(name = "Turno.findByFecha", query = "SELECT t FROM Turno t WHERE t.fecha = :fecha"),
    @NamedQuery(name = "Turno.findByAtendido", query = "SELECT t FROM Turno t WHERE t.atendido = :atendido")})
public class Turno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "Id_Turno")
    private String idTurno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "Num_Turno")
    private String numTurno;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "Atendido")
    private String atendido;
    @JoinColumn(name = "Usuario", referencedColumnName = "Correo_Id")
    @ManyToOne(optional = false)
    private Usuario usuario;
    @JoinColumn(name = "ServicioID", referencedColumnName = "Id_Servicio")
    @ManyToOne(optional = false)
    private Servicio servicioID;
    @JoinColumn(name = "Sede", referencedColumnName = "ID_Sede")
    @ManyToOne(optional = false)
    private Sede sede;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "turno")
    private Collection<TurnoBackUp> turnoBackUpCollection;

    public Turno() {
    }

    public Turno(String idTurno) {
        this.idTurno = idTurno;
    }

    public Turno(String idTurno, String numTurno, Date fecha, String atendido) {
        this.idTurno = idTurno;
        this.numTurno = numTurno;
        this.fecha = fecha;
        this.atendido = atendido;
    }

    public String getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(String idTurno) {
        this.idTurno = idTurno;
    }

    public String getNumTurno() {
        return numTurno;
    }

    public void setNumTurno(String numTurno) {
        this.numTurno = numTurno;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getAtendido() {
        return atendido;
    }

    public void setAtendido(String atendido) {
        this.atendido = atendido;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Servicio getServicioID() {
        return servicioID;
    }

    public void setServicioID(Servicio servicioID) {
        this.servicioID = servicioID;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
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
        hash += (idTurno != null ? idTurno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Turno)) {
            return false;
        }
        Turno other = (Turno) object;
        if ((this.idTurno == null && other.idTurno != null) || (this.idTurno != null && !this.idTurno.equals(other.idTurno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.esperapp.entidades.Turno[ idTurno=" + idTurno + " ]";
    }
    
}
