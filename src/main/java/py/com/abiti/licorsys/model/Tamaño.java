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

/**
 *
 * @author matia
 */
@Entity
@Table(name = "tama\u00f1o")
@NamedQueries({
    @NamedQuery(name = "Tama\u00f1o.findAll", query = "SELECT t FROM Tama\u00f1o t"),
    @NamedQuery(name = "Tama\u00f1o.findByTama\u00f1o", query = "SELECT t FROM Tama\u00f1o t WHERE t.tama\u00f1o = :tama\u00f1o"),
    @NamedQuery(name = "Tama\u00f1o.findByDescripcion", query = "SELECT t FROM Tama\u00f1o t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "Tama\u00f1o.findByMl", query = "SELECT t FROM Tama\u00f1o t WHERE t.ml = :ml")})
public class Tamaño implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tama\u00f1o")
    private Integer tamaño;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "ml")
    private double ml;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tama\u00f1o")
    private List<TipoProducto> tipoProductoList;

    public Tamaño() {
    }

    public Tamaño(Integer tamaño) {
        this.tamaño = tamaño;
    }

    public Tamaño(Integer tamaño, String descripcion, double ml) {
        this.tamaño = tamaño;
        this.descripcion = descripcion;
        this.ml = ml;
    }

    public Integer getTamaño() {
        return tamaño;
    }

    public void setTamaño(Integer tamaño) {
        this.tamaño = tamaño;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getMl() {
        return ml;
    }

    public void setMl(double ml) {
        this.ml = ml;
    }

    public List<TipoProducto> getTipoProductoList() {
        return tipoProductoList;
    }

    public void setTipoProductoList(List<TipoProducto> tipoProductoList) {
        this.tipoProductoList = tipoProductoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tamaño != null ? tamaño.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tamaño)) {
            return false;
        }
        Tamaño other = (Tamaño) object;
        if ((this.tamaño == null && other.tamaño != null) || (this.tamaño != null && !this.tamaño.equals(other.tamaño))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.abiti.licorsys.model.Tama\u00f1o[ tama\u00f1o=" + tamaño + " ]";
    }
    
}
