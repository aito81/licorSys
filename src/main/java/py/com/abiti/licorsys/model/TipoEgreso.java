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
@Table(name = "tipo_egreso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoEgreso.findAll", query = "SELECT t FROM TipoEgreso t")
    , @NamedQuery(name = "TipoEgreso.findByTipoEgreso", query = "SELECT t FROM TipoEgreso t WHERE t.tipoEgreso = :tipoEgreso")
    , @NamedQuery(name = "TipoEgreso.findByDescripcion", query = "SELECT t FROM TipoEgreso t WHERE t.descripcion = :descripcion")})
public class TipoEgreso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tipo_egreso")
    private Integer tipoEgreso;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoEgreso")
    private List<Egreso> egresoList;

    public TipoEgreso() {
    }

    public TipoEgreso(Integer tipoEgreso) {
        this.tipoEgreso = tipoEgreso;
    }

    public TipoEgreso(Integer tipoEgreso, String descripcion) {
        this.tipoEgreso = tipoEgreso;
        this.descripcion = descripcion;
    }

    public Integer getTipoEgreso() {
        return tipoEgreso;
    }

    public void setTipoEgreso(Integer tipoEgreso) {
        this.tipoEgreso = tipoEgreso;
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
        hash += (tipoEgreso != null ? tipoEgreso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoEgreso)) {
            return false;
        }
        TipoEgreso other = (TipoEgreso) object;
        if ((this.tipoEgreso == null && other.tipoEgreso != null) || (this.tipoEgreso != null && !this.tipoEgreso.equals(other.tipoEgreso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.com.abiti.licorsys.model.TipoEgreso[ tipoEgreso=" + tipoEgreso + " ]";
    }
    
}
