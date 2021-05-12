/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.abiti.licorsys.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Santi
 */
@Entity
@Table(name = "transaccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transaccion.findAll", query = "SELECT t FROM Transaccion t")
    , @NamedQuery(name = "Transaccion.findByTransaccion", query = "SELECT t FROM Transaccion t WHERE t.transaccion = :transaccion")
    , @NamedQuery(name = "Transaccion.findByProducto", query = "SELECT t FROM Transaccion t WHERE t.producto = :producto")
    , @NamedQuery(name = "Transaccion.findByMonto", query = "SELECT t FROM Transaccion t WHERE t.monto = :monto")
    , @NamedQuery(name = "Transaccion.findByFecha", query = "SELECT t FROM Transaccion t WHERE t.fecha = :fecha")
    , @NamedQuery(name = "Transaccion.findByObservacion", query = "SELECT t FROM Transaccion t WHERE t.observacion = :observacion")
    , @NamedQuery(name = "Transaccion.findByCantidad", query = "SELECT t FROM Transaccion t WHERE t.cantidad = :cantidad")})
public class Transaccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "transaccion")
    private Integer transaccion;
    @Column(name = "producto")
    private Integer producto;
    @Basic(optional = false)
    @Column(name = "monto")
    private double monto;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "observacion")
    private String observacion;
    @Basic(optional = false)
    @Column(name = "cantidad")
    private double cantidad;
    @JoinColumn(name = "egreso", referencedColumnName = "egreso")
    @ManyToOne(optional = false)
    private Egreso egreso;

    public Transaccion() {
    }

    public Transaccion(Integer transaccion) {
        this.transaccion = transaccion;
    }

    public Transaccion(Integer transaccion, double monto, Date fecha, double cantidad) {
        this.transaccion = transaccion;
        this.monto = monto;
        this.fecha = fecha;
        this.cantidad = cantidad;
    }

    public Integer getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(Integer transaccion) {
        this.transaccion = transaccion;
    }

    public Integer getProducto() {
        return producto;
    }

    public void setProducto(Integer producto) {
        this.producto = producto;
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

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public Egreso getEgreso() {
        return egreso;
    }

    public void setEgreso(Egreso egreso) {
        this.egreso = egreso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transaccion != null ? transaccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transaccion)) {
            return false;
        }
        Transaccion other = (Transaccion) object;
        if ((this.transaccion == null && other.transaccion != null) || (this.transaccion != null && !this.transaccion.equals(other.transaccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.abiti.licorsys.model.Transaccion[ transaccion=" + transaccion + " ]";
    }
    
}
