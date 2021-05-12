/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.abiti.licorsys.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Santi
 */
@Entity
@Table(name = "turno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Turno.findAll", query = "SELECT t FROM Turno t")
    , @NamedQuery(name = "Turno.findByTurno", query = "SELECT t FROM Turno t WHERE t.turno = :turno")
    , @NamedQuery(name = "Turno.findByDescripcion", query = "SELECT t FROM Turno t WHERE t.descripcion = :descripcion")
    , @NamedQuery(name = "Turno.findByHorarioEntrada", query = "SELECT t FROM Turno t WHERE t.horarioEntrada = :horarioEntrada")
    , @NamedQuery(name = "Turno.findByHorarioSalida", query = "SELECT t FROM Turno t WHERE t.horarioSalida = :horarioSalida")})
public class Turno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "turno")
    private Integer turno;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "horario_entrada")
    private String horarioEntrada;
    @Basic(optional = false)
    @Column(name = "horario_salida")
    private String horarioSalida;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "turno")
    private List<Empleado> empleadoList;

    public Turno() {
    }

    public Turno(Integer turno) {
        this.turno = turno;
    }

    public Turno(Integer turno, String descripcion, String horarioEntrada, String horarioSalida) {
        this.turno = turno;
        this.descripcion = descripcion;
        this.horarioEntrada = horarioEntrada;
        this.horarioSalida = horarioSalida;
    }

    public Integer getTurno() {
        return turno;
    }

    public void setTurno(Integer turno) {
        this.turno = turno;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getHorarioEntrada() {
        return horarioEntrada;
    }

    public void setHorarioEntrada(String horarioEntrada) {
        this.horarioEntrada = horarioEntrada;
    }

    public String getHorarioSalida() {
        return horarioSalida;
    }

    public void setHorarioSalida(String horarioSalida) {
        this.horarioSalida = horarioSalida;
    }

    @XmlTransient
    public List<Empleado> getEmpleadoList() {
        return empleadoList;
    }

    public void setEmpleadoList(List<Empleado> empleadoList) {
        this.empleadoList = empleadoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (turno != null ? turno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Turno)) {
            return false;
        }
        Turno other = (Turno) object;
        if ((this.turno == null && other.turno != null) || (this.turno != null && !this.turno.equals(other.turno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.abiti.licorsys.model.Turno[ turno=" + turno + " ]";
    }
    
}
