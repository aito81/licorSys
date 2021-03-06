/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.abiti.licorsys.controller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import py.com.abiti.licorsys.model.TipoProducto;
import py.com.abiti.licorsys.model.Ingreso;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import py.com.abiti.licorsys.controller.exceptions.IllegalOrphanException;
import py.com.abiti.licorsys.controller.exceptions.NonexistentEntityException;
import py.com.abiti.licorsys.model.Producto;

/**
 *
 * @author Santi
 */
public class ProductoJpaController implements Serializable {

    public ProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) {
        if (producto.getIngresoList() == null) {
            producto.setIngresoList(new ArrayList<Ingreso>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoProducto tipoProducto = producto.getTipoProducto();
            if (tipoProducto != null) {
                tipoProducto = em.getReference(tipoProducto.getClass(), tipoProducto.getTipoProducto());
                producto.setTipoProducto(tipoProducto);
            }
            List<Ingreso> attachedIngresoList = new ArrayList<Ingreso>();
            for (Ingreso ingresoListIngresoToAttach : producto.getIngresoList()) {
                ingresoListIngresoToAttach = em.getReference(ingresoListIngresoToAttach.getClass(), ingresoListIngresoToAttach.getIngreso());
                attachedIngresoList.add(ingresoListIngresoToAttach);
            }
            producto.setIngresoList(attachedIngresoList);
            em.persist(producto);
            if (tipoProducto != null) {
                tipoProducto.getProductoList().add(producto);
                tipoProducto = em.merge(tipoProducto);
            }
            for (Ingreso ingresoListIngreso : producto.getIngresoList()) {
                Producto oldProductoOfIngresoListIngreso = ingresoListIngreso.getProducto();
                ingresoListIngreso.setProducto(producto);
                ingresoListIngreso = em.merge(ingresoListIngreso);
                if (oldProductoOfIngresoListIngreso != null) {
                    oldProductoOfIngresoListIngreso.getIngresoList().remove(ingresoListIngreso);
                    oldProductoOfIngresoListIngreso = em.merge(oldProductoOfIngresoListIngreso);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Producto producto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto persistentProducto = em.find(Producto.class, producto.getProducto());
            TipoProducto tipoProductoOld = persistentProducto.getTipoProducto();
            TipoProducto tipoProductoNew = producto.getTipoProducto();
            List<Ingreso> ingresoListOld = persistentProducto.getIngresoList();
            List<Ingreso> ingresoListNew = producto.getIngresoList();
            List<String> illegalOrphanMessages = null;
            for (Ingreso ingresoListOldIngreso : ingresoListOld) {
                if (!ingresoListNew.contains(ingresoListOldIngreso)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ingreso " + ingresoListOldIngreso + " since its producto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tipoProductoNew != null) {
                tipoProductoNew = em.getReference(tipoProductoNew.getClass(), tipoProductoNew.getTipoProducto());
                producto.setTipoProducto(tipoProductoNew);
            }
            List<Ingreso> attachedIngresoListNew = new ArrayList<Ingreso>();
            for (Ingreso ingresoListNewIngresoToAttach : ingresoListNew) {
                ingresoListNewIngresoToAttach = em.getReference(ingresoListNewIngresoToAttach.getClass(), ingresoListNewIngresoToAttach.getIngreso());
                attachedIngresoListNew.add(ingresoListNewIngresoToAttach);
            }
            ingresoListNew = attachedIngresoListNew;
            producto.setIngresoList(ingresoListNew);
            producto = em.merge(producto);
            if (tipoProductoOld != null && !tipoProductoOld.equals(tipoProductoNew)) {
                tipoProductoOld.getProductoList().remove(producto);
                tipoProductoOld = em.merge(tipoProductoOld);
            }
            if (tipoProductoNew != null && !tipoProductoNew.equals(tipoProductoOld)) {
                tipoProductoNew.getProductoList().add(producto);
                tipoProductoNew = em.merge(tipoProductoNew);
            }
            for (Ingreso ingresoListNewIngreso : ingresoListNew) {
                if (!ingresoListOld.contains(ingresoListNewIngreso)) {
                    Producto oldProductoOfIngresoListNewIngreso = ingresoListNewIngreso.getProducto();
                    ingresoListNewIngreso.setProducto(producto);
                    ingresoListNewIngreso = em.merge(ingresoListNewIngreso);
                    if (oldProductoOfIngresoListNewIngreso != null && !oldProductoOfIngresoListNewIngreso.equals(producto)) {
                        oldProductoOfIngresoListNewIngreso.getIngresoList().remove(ingresoListNewIngreso);
                        oldProductoOfIngresoListNewIngreso = em.merge(oldProductoOfIngresoListNewIngreso);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = producto.getProducto();
                if (findProducto(id) == null) {
                    throw new NonexistentEntityException("The producto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getProducto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Ingreso> ingresoListOrphanCheck = producto.getIngresoList();
            for (Ingreso ingresoListOrphanCheckIngreso : ingresoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the Ingreso " + ingresoListOrphanCheckIngreso + " in its ingresoList field has a non-nullable producto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TipoProducto tipoProducto = producto.getTipoProducto();
            if (tipoProducto != null) {
                tipoProducto.getProductoList().remove(producto);
                tipoProducto = em.merge(tipoProducto);
            }
            em.remove(producto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producto.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Producto findProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
