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
import py.com.abiti.licorsys.model.Egreso;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import py.com.abiti.licorsys.controller.exceptions.NonexistentEntityException;
import py.com.abiti.licorsys.model.Proveedor;

/**
 *
 * @author Santi
 */
public class ProveedorJpaController implements Serializable {

    public ProveedorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proveedor proveedor) {
        if (proveedor.getEgresoList() == null) {
            proveedor.setEgresoList(new ArrayList<Egreso>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Egreso> attachedEgresoList = new ArrayList<Egreso>();
            for (Egreso egresoListEgresoToAttach : proveedor.getEgresoList()) {
                egresoListEgresoToAttach = em.getReference(egresoListEgresoToAttach.getClass(), egresoListEgresoToAttach.getEgreso());
                attachedEgresoList.add(egresoListEgresoToAttach);
            }
            proveedor.setEgresoList(attachedEgresoList);
            em.persist(proveedor);
            for (Egreso egresoListEgreso : proveedor.getEgresoList()) {
                Proveedor oldProveedorOfEgresoListEgreso = egresoListEgreso.getProveedor();
                egresoListEgreso.setProveedor(proveedor);
                egresoListEgreso = em.merge(egresoListEgreso);
                if (oldProveedorOfEgresoListEgreso != null) {
                    oldProveedorOfEgresoListEgreso.getEgresoList().remove(egresoListEgreso);
                    oldProveedorOfEgresoListEgreso = em.merge(oldProveedorOfEgresoListEgreso);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Proveedor proveedor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proveedor persistentProveedor = em.find(Proveedor.class, proveedor.getProveedor());
            List<Egreso> egresoListOld = persistentProveedor.getEgresoList();
            List<Egreso> egresoListNew = proveedor.getEgresoList();
            List<Egreso> attachedEgresoListNew = new ArrayList<Egreso>();
            for (Egreso egresoListNewEgresoToAttach : egresoListNew) {
                egresoListNewEgresoToAttach = em.getReference(egresoListNewEgresoToAttach.getClass(), egresoListNewEgresoToAttach.getEgreso());
                attachedEgresoListNew.add(egresoListNewEgresoToAttach);
            }
            egresoListNew = attachedEgresoListNew;
            proveedor.setEgresoList(egresoListNew);
            proveedor = em.merge(proveedor);
            for (Egreso egresoListOldEgreso : egresoListOld) {
                if (!egresoListNew.contains(egresoListOldEgreso)) {
                    egresoListOldEgreso.setProveedor(null);
                    egresoListOldEgreso = em.merge(egresoListOldEgreso);
                }
            }
            for (Egreso egresoListNewEgreso : egresoListNew) {
                if (!egresoListOld.contains(egresoListNewEgreso)) {
                    Proveedor oldProveedorOfEgresoListNewEgreso = egresoListNewEgreso.getProveedor();
                    egresoListNewEgreso.setProveedor(proveedor);
                    egresoListNewEgreso = em.merge(egresoListNewEgreso);
                    if (oldProveedorOfEgresoListNewEgreso != null && !oldProveedorOfEgresoListNewEgreso.equals(proveedor)) {
                        oldProveedorOfEgresoListNewEgreso.getEgresoList().remove(egresoListNewEgreso);
                        oldProveedorOfEgresoListNewEgreso = em.merge(oldProveedorOfEgresoListNewEgreso);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = proveedor.getProveedor();
                if (findProveedor(id) == null) {
                    throw new NonexistentEntityException("The proveedor with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proveedor proveedor;
            try {
                proveedor = em.getReference(Proveedor.class, id);
                proveedor.getProveedor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proveedor with id " + id + " no longer exists.", enfe);
            }
            List<Egreso> egresoList = proveedor.getEgresoList();
            for (Egreso egresoListEgreso : egresoList) {
                egresoListEgreso.setProveedor(null);
                egresoListEgreso = em.merge(egresoListEgreso);
            }
            em.remove(proveedor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Proveedor> findProveedorEntities() {
        return findProveedorEntities(true, -1, -1);
    }

    public List<Proveedor> findProveedorEntities(int maxResults, int firstResult) {
        return findProveedorEntities(false, maxResults, firstResult);
    }

    private List<Proveedor> findProveedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Proveedor.class));
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

    public Proveedor findProveedor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proveedor.class, id);
        } finally {
            em.close();
        }
    }

    public int getProveedorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Proveedor> rt = cq.from(Proveedor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
