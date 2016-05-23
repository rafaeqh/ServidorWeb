/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esperapp.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Joako
 */
@Entity
@Table(name = "Turno_BackUp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TurnoBackUp.findAll", query = "SELECT t FROM TurnoBackUp t"),
    @NamedQuery(name = "TurnoBackUp.findByConsecutivo", query = "SELECT t FROM TurnoBackUp t WHERE t.consecutivo = :consecutivo"),
    @NamedQuery(name = "TurnoBackUp.findByCorreoId", query = "SELECT t FROM TurnoBackUp t WHERE t.correoId = :correoId"),
    @NamedQuery(name = "TurnoBackUp.findByFecha", query = "SELECT t FROM TurnoBackUp t WHERE t.fecha = :fecha"),
    @NamedQuery(name = "TurnoBackUp.findByCedula", query = "SELECT t FROM TurnoBackUp t WHERE t.cedula = :cedula")})
public class TurnoBackUp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "Consecutivo")
    private String consecutivo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "Correo_Id")
    private String correoId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "Cedula")
    private String cedula;
    @JoinColumn(name = "Turno", referencedColumnName = "Id_Turno")
    @ManyToOne(optional = false)
    private Turno turno;
    @JoinColumn(name = "Receptor", referencedColumnName = "Receptor")
    @ManyToOne(optional = false)
    private Trabajo receptor;

    public TurnoBackUp() {
    }

    public TurnoBackUp(String consecutivo) {
        this.consecutivo = consecutivo;
    }

    public TurnoBackUp(String consecutivo, String correoId, Date fecha, String cedula) {
        this.consecutivo = consecutivo;
        this.correoId = correoId;
        this.fecha = fecha;
        this.cedula = cedula;
    }

    public String getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getCorreoId() {
        return correoId;
    }

    public void setCorreoId(String correoId) {
        this.correoId = correoId;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public Trabajo getReceptor() {
        return receptor;
    }

    public void setReceptor(Trabajo receptor) {
        this.receptor = receptor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (consecutivo != null ? consecutivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TurnoBackUp)) {
            return false;
        }
        TurnoBackUp other = (TurnoBackUp) object;
        if ((this.consecutivo == null && other.consecutivo != null) || (this.consecutivo != null && !this.consecutivo.equals(other.consecutivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.esperapp.entidades.TurnoBackUp[ consecutivo=" + consecutivo + " ]";
    }
    
}
