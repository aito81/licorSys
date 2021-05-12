/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.abiti.licorsys.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Santi
 */
@Entity
@Table(name = "egreso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Egreso.findAll", query = "SELECT e FROM Egreso e")
    , @NamedQuery(name = "Egreso.findByEgreso", query = "SELECT e FROM Egreso e WHERE e.egreso = :egreso")
    , @NamedQuery(name = "Egreso.findByMonto", query = "SELECT e FROM Egreso e WHERE e.monto = :monto")
    , @NamedQuery(name = "Egreso.findByFecha", query = "SELECT e FROM Egreso e WHERE e.fecha = :fecha")
    , @NamedQuery(name = "Egreso.findByEmpleado", query = "SELECT e FROM Egreso e WHERE e.empleado = :empleado")
    , @NamedQuery(name = "Egreso.findByContado", query = "SELECT e FROM Egreso e WHERE e.contado = :contado")})
public class Egreso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "egreso")
    private Integer egreso;
    @Basic(optional = false)
    @Column(name = "monto")
    private double monto;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "empleado")
    private Integer empleado;
    @Basic(optional = false)
    @Column(name = "contado")
    private boolean contado;
    @JoinColumn(name = "proveedor", referencedColumnName = "proveedor")
    @ManyToOne
    private Proveedor proveedor;
    @JoinColumn(name = "tipo_egreso", referencedColumnName = "tipo_egreso")
    @ManyToOne(optional = false)
    private TipoEgreso tipoEgreso;
    @JoinColumn(name = "usuario", referencedColumnName = "usuario")
    @ManyToOne(optional = false)
    private Usuario usuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "egreso")
    private List<Transaccion> transaccionList;

    public Egreso() {
    }

    public Egreso(Integer egreso) {
        this.egreso = egreso;
    }

    public Egreso(Integer egreso, double monto, Date fecha, boolean contado) {
        this.egreso = egreso;
        this.monto = monto;
        this.fecha = fecha;
        this.contado = contado;
    }

    public Integer getEgreso() {
        return egreso;
    }

    public void setEgreso(Integer egreso) {
        this.egreso = egreso;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Integer empleado) {
        this.empleado = empleado;
    }

    public boolean getContado() {
        return contado;
    }

    public void setContado(boolean contado) {
        this.contado = contado;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public TipoEgreso getTipoEgreso() {
        return tipoEgreso;
    }

    public void setTipoEgreso(TipoEgreso tipoEgreso) {
        this.tipoEgreso = tipoEgreso;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @XmlTransient
    public List<Transaccion> getTransaccionList() {
        return transaccionList;
    }

    public void setTransaccionList(List<Transaccion> transaccionList) {
        this.transaccionList = transaccionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (egreso != null ? egreso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Egreso)) {
            return false;
        }
        Egreso other = (Egreso) object;
        if ((this.egreso == null && other.egreso != null) || (this.egreso != null && !this.egreso.equals(other.egreso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.abiti.licorsys.model.Egreso[ egreso=" + egreso + " ]";
    }
    
}
