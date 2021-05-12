/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.abiti.licorsys.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
@Table(name = "proveedor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proveedor.findAll", query = "SELECT p FROM Proveedor p")
    , @NamedQuery(name = "Proveedor.findByProveedor", query = "SELECT p FROM Proveedor p WHERE p.proveedor = :proveedor")
    , @NamedQuery(name = "Proveedor.findByDescripcion", query = "SELECT p FROM Proveedor p WHERE p.descripcion = :descripcion")})
public class Proveedor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "proveedor")
    private Integer proveedor;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(mappedBy = "proveedor")
    private List<Egreso> egresoList;

    public Proveedor() {
    }

    public Proveedor(Integer proveedor) {
        this.proveedor = proveedor;
    }

    public Proveedor(Integer proveedor, String descripcion) {
        this.proveedor = proveedor;
        this.descripcion = descripcion;
    }

    public Integer getProveedor() {
        return proveedor;
    }

    public void setProveedor(Integer proveedor) {
        this.proveedor = proveedor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<Egreso> getEgresoList() {
        return egresoList;
    }

    public void setEgresoList(List<Egreso> egresoList) {
        this.egresoList = egresoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (proveedor != null ? proveedor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proveedor)) {
            return false;
        }
        Proveedor other = (Proveedor) object;
        if ((this.proveedor == null && other.proveedor != null) || (this.proveedor != null && !this.proveedor.equals(other.proveedor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.abiti.licorsys.model.Proveedor[ proveedor=" + proveedor + " ]";
    }
    
}
