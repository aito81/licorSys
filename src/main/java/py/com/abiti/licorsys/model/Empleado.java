/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.abiti.licorsys.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Santi
 */
@Entity
@Table(name = "empleado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empleado.findAll", query = "SELECT e FROM Empleado e")
    , @NamedQuery(name = "Empleado.findByEmpleado", query = "SELECT e FROM Empleado e WHERE e.empleado = :empleado")
    , @NamedQuery(name = "Empleado.findBySueldo", query = "SELECT e FROM Empleado e WHERE e.sueldo = :sueldo")})
public class Empleado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "empleado")
    private Integer empleado;
    @Basic(optional = false)
    @Column(name = "sueldo")
    private double sueldo;
    @JoinColumn(name = "cargo", referencedColumnName = "cargo")
    @ManyToOne(optional = false)
    private Cargo cargo;
    @JoinColumn(name = "persona", referencedColumnName = "persona")
    @ManyToOne(optional = false)
    private Persona persona;
    @JoinColumn(name = "turno", referencedColumnName = "turno")
    @ManyToOne(optional = false)
    private Turno turno;

    public Empleado() {
    }

    public Empleado(Integer empleado) {
        this.empleado = empleado;
    }

    public Empleado(Integer empleado, double sueldo) {
        this.empleado = empleado;
        this.sueldo = sueldo;
    }

    public Integer getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Integer empleado) {
        this.empleado = empleado;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (empleado != null ? empleado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empleado)) {
            return false;
        }
        Empleado other = (Empleado) object;
        if ((this.empleado == null && other.empleado != null) || (this.empleado != null && !this.empleado.equals(other.empleado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.abiti.licorsys.model.Empleado[ empleado=" + empleado + " ]";
    }
    
}
