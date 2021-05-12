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
import py.com.abiti.licorsys.controller.exceptions.IllegalOrphanException;
import py.com.abiti.licorsys.controller.exceptions.NonexistentEntityException;
import py.com.abiti.licorsys.model.TipoEgreso;

/**
 *
 * @author Santi
 */
public class TipoEgresoJpaController implements Serializable {

    public TipoEgresoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoEgreso tipoEgreso) {
        if (tipoEgreso.getEgresoList() == null) {
            tipoEgreso.setEgresoList(new ArrayList<Egreso>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Egreso> attachedEgresoList = new ArrayList<Egreso>();
            for (Egreso egresoListEgresoToAttach : tipoEgreso.getEgresoList()) {
                egresoListEgresoToAttach = em.getReference(egresoListEgresoToAttach.getClass(), egresoListEgresoToAttach.getEgreso());
                attachedEgresoList.add(egresoListEgresoToAttach);
            }
            tipoEgreso.setEgresoList(attachedEgresoList);
            em.persist(tipoEgreso);
            for (Egreso egresoListEgreso : tipoEgreso.getEgresoList()) {
                TipoEgreso oldTipoEgresoOfEgresoListEgreso = egresoListEgreso.getTipoEgreso();
                egresoListEgreso.setTipoEgreso(tipoEgreso);
                egresoListEgreso = em.merge(egresoListEgreso);
                if (oldTipoEgresoOfEgresoListEgreso != null) {
                    oldTipoEgresoOfEgresoListEgreso.getEgresoList().remove(egresoListEgreso);
                    oldTipoEgresoOfEgresoListEgreso = em.merge(oldTipoEgresoOfEgresoListEgreso);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoEgreso tipoEgreso) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoEgreso persistentTipoEgreso = em.find(TipoEgreso.class, tipoEgreso.getTipoEgreso());
            List<Egreso> egresoListOld = persistentTipoEgreso.getEgresoList();
            List<Egreso> egresoListNew = tipoEgreso.getEgresoList();
            List<String> illegalOrphanMessages = null;
            for (Egreso egresoListOldEgreso : egresoListOld) {
                if (!egresoListNew.contains(egresoListOldEgreso)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Egreso " + egresoListOldEgreso + " since its tipoEgreso field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Egreso> attachedEgresoListNew = new ArrayList<Egreso>();
            for (Egreso egresoListNewEgresoToAttach : egresoListNew) {
                egresoListNewEgresoToAttach = em.getReference(egresoListNewEgresoToAttach.getClass(), egresoListNewEgresoToAttach.getEgreso());
                attachedEgresoListNew.add(egresoListNewEgresoToAttach);
            }
            egresoListNew = attachedEgresoListNew;
            tipoEgreso.setEgresoList(egresoListNew);
            tipoEgreso = em.merge(tipoEgreso);
            for (Egreso egresoListNewEgreso : egresoListNew) {
                if (!egresoListOld.contains(egresoListNewEgreso)) {
                    TipoEgreso oldTipoEgresoOfEgresoListNewEgreso = egresoListNewEgreso.getTipoEgreso();
                    egresoListNewEgreso.setTipoEgreso(tipoEgreso);
                    egresoListNewEgreso = em.merge(egresoListNewEgreso);
                    if (oldTipoEgresoOfEgresoListNewEgreso != null && !oldTipoEgresoOfEgresoListNewEgreso.equals(tipoEgreso)) {
                        oldTipoEgresoOfEgresoListNewEgreso.getEgresoList().remove(egresoListNewEgreso);
                        oldTipoEgresoOfEgresoListNewEgreso = em.merge(oldTipoEgresoOfEgresoListNewEgreso);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoEgreso.getTipoEgreso();
                if (findTipoEgreso(id) == null) {
                    throw new NonexistentEntityException("The tipoEgreso with id " + id + " no longer exists.");
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
            TipoEgreso tipoEgreso;
            try {
                tipoEgreso = em.getReference(TipoEgreso.class, id);
                tipoEgreso.getTipoEgreso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoEgreso with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Egreso> egresoListOrphanCheck = tipoEgreso.getEgresoList();
            for (Egreso egresoListOrphanCheckEgreso : egresoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoEgreso (" + tipoEgreso + ") cannot be destroyed since the Egreso " + egresoListOrphanCheckEgreso + " in its egresoList field has a non-nullable tipoEgreso field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoEgreso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoEgreso> findTipoEgresoEntities() {
        return findTipoEgresoEntities(true, -1, -1);
    }

    public List<TipoEgreso> findTipoEgresoEntities(int maxResults, int firstResult) {
        return findTipoEgresoEntities(false, maxResults, firstResult);
    }

    private List<TipoEgreso> findTipoEgresoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoEgreso.class));
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

    public TipoEgreso findTipoEgreso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoEgreso.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoEgresoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoEgreso> rt = cq.from(TipoEgreso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
