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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
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
    @NamedQuery(name = "TurnoBackUp.findByComentario", query = "SELECT t FROM TurnoBackUp t WHERE t.comentario = :comentario")})
public class TurnoBackUp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "Consecutivo")
    private String consecutivo;
    @Size(max = 60)
    @Column(name = "Comentario")
    private String comentario;
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

    public String getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
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
