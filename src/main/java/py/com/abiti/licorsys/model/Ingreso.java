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
@Table(name = "ingreso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ingreso.findAll", query = "SELECT i FROM Ingreso i")
    , @NamedQuery(name = "Ingreso.findByIngreso", query = "SELECT i FROM Ingreso i WHERE i.ingreso = :ingreso")
    , @NamedQuery(name = "Ingreso.findByFecha", query = "SELECT i FROM Ingreso i WHERE i.fecha = :fecha")
    , @NamedQuery(name = "Ingreso.findByMonto", query = "SELECT i FROM Ingreso i WHERE i.monto = :monto")
    , @NamedQuery(name = "Ingreso.findByTotal", query = "SELECT i FROM Ingreso i WHERE i.total = :total")
    , @NamedQuery(name = "Ingreso.findByCantidad", query = "SELECT i FROM Ingreso i WHERE i.cantidad = :cantidad")
    , @NamedQuery(name = "Ingreso.findByUsuario", query = "SELECT i FROM Ingreso i WHERE i.usuario = :usuario")})
public class Ingreso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ingreso")
    private Integer ingreso;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "monto")
    private double monto;
    @Basic(optional = false)
    @Column(name = "total")
    private double total;
    @Basic(optional = false)
    @Column(name = "cantidad")
    private double cantidad;
    @Basic(optional = false)
    @Column(name = "usuario")
    private int usuario;
    @JoinColumn(name = "producto", referencedColumnName = "producto")
    @ManyToOne(optional = false)
    private Producto producto;

    public Ingreso() {
    }

    public Ingreso(Integer ingreso) {
        this.ingreso = ingreso;
    }

    public Ingreso(Integer ingreso, Date fecha, double monto, double total, double cantidad, int usuario) {
        this.ingreso = ingreso;
        this.fecha = fecha;
        this.monto = monto;
        this.total = total;
        this.cantidad = cantidad;
        this.usuario = usuario;
    }

    public Integer getIngreso() {
        return ingreso;
    }

    public void setIngreso(Integer ingreso) {
        this.ingreso = ingreso;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ingreso != null ? ingreso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ingreso)) {
            return false;
        }
        Ingreso other = (Ingreso) object;
        if ((this.ingreso == null && other.ingreso != null) || (this.ingreso != null && !this.ingreso.equals(other.ingreso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.abiti.licorsys.model.Ingreso[ ingreso=" + ingreso + " ]";
    }
    
}
