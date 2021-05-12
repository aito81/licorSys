/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.abiti.licorsys.controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import py.com.abiti.licorsys.controller.exceptions.NonexistentEntityException;
import py.com.abiti.licorsys.model.Egreso;
import py.com.abiti.licorsys.model.Transaccion;

/**
 *
 * @author Santi
 */
public class TransaccionJpaController implements Serializable {

    public TransaccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Transaccion transaccion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Egreso egreso = transaccion.getEgreso();
            if (egreso != null) {
                egreso = em.getReference(egreso.getClass(), egreso.getEgreso());
                transaccion.setEgreso(egreso);
            }
            em.persist(transaccion);
            if (egreso != null) {
                egreso.getTransaccionList().add(transaccion);
                egreso = em.merge(egreso);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Transaccion transaccion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Transaccion persistentTransaccion = em.find(Transaccion.class, transaccion.getTransaccion());
            Egreso egresoOld = persistentTransaccion.getEgreso();
            Egreso egresoNew = transaccion.getEgreso();
            if (egresoNew != null) {
                egresoNew = em.getReference(egresoNew.getClass(), egresoNew.getEgreso());
                transaccion.setEgreso(egresoNew);
            }
            transaccion = em.merge(transaccion);
            if (egresoOld != null && !egresoOld.equals(egresoNew)) {
                egresoOld.getTransaccionList().remove(transaccion);
                egresoOld = em.merge(egresoOld);
            }
            if (egresoNew != null && !egresoNew.equals(egresoOld)) {
                egresoNew.getTransaccionList().add(transaccion);
                egresoNew = em.merge(egresoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = transaccion.getTransaccion();
                if (findTransaccion(id) == null) {
                    throw new NonexistentEntityException("The transaccion with id " + id + " no longer exists.");
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
            Transaccion transaccion;
            try {
                transaccion = em.getReference(Transaccion.class, id);
                transaccion.getTransaccion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transaccion with id " + id + " no longer exists.", enfe);
            }
            Egreso egreso = transaccion.getEgreso();
            if (egreso != null) {
                egreso.getTransaccionList().remove(transaccion);
                egreso = em.merge(egreso);
            }
            em.remove(transaccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Transaccion> findTransaccionEntities() {
        return findTransaccionEntities(true, -1, -1);
    }

    public List<Transaccion> findTransaccionEntities(int maxResults, int firstResult) {
        return findTransaccionEntities(false, maxResults, firstResult);
    }

    private List<Transaccion> findTransaccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Transaccion.class));
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

    public Transaccion findTransaccion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Transaccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getTransaccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Transaccion> rt = cq.from(Transaccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
