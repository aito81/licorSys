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
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import py.com.abiti.licorsys.controller.exceptions.IllegalOrphanException;
import py.com.abiti.licorsys.controller.exceptions.NonexistentEntityException;
import py.com.abiti.licorsys.model.Tamaño;

/**
 *
 * @author matia
 */
public class TamañoJpaController implements Serializable {

    public TamañoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tamaño tamaño) {
        if (tamaño.getTipoProductoList() == null) {
            tamaño.setTipoProductoList(new ArrayList<TipoProducto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<TipoProducto> attachedTipoProductoList = new ArrayList<TipoProducto>();
            for (TipoProducto tipoProductoListTipoProductoToAttach : tamaño.getTipoProductoList()) {
                tipoProductoListTipoProductoToAttach = em.getReference(tipoProductoListTipoProductoToAttach.getClass(), tipoProductoListTipoProductoToAttach.getTipoProducto());
                attachedTipoProductoList.add(tipoProductoListTipoProductoToAttach);
            }
            tamaño.setTipoProductoList(attachedTipoProductoList);
            em.persist(tamaño);
            for (TipoProducto tipoProductoListTipoProducto : tamaño.getTipoProductoList()) {
                Tamaño oldTamañoOfTipoProductoListTipoProducto = tipoProductoListTipoProducto.getTamaño();
                tipoProductoListTipoProducto.setTamaño(tamaño);
                tipoProductoListTipoProducto = em.merge(tipoProductoListTipoProducto);
                if (oldTamañoOfTipoProductoListTipoProducto != null) {
                    oldTamañoOfTipoProductoListTipoProducto.getTipoProductoList().remove(tipoProductoListTipoProducto);
                    oldTamañoOfTipoProductoListTipoProducto = em.merge(oldTamañoOfTipoProductoListTipoProducto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tamaño tamaño) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tamaño persistentTamaño = em.find(Tamaño.class, tamaño.getTamaño());
            List<TipoProducto> tipoProductoListOld = persistentTamaño.getTipoProductoList();
            List<TipoProducto> tipoProductoListNew = tamaño.getTipoProductoList();
            List<String> illegalOrphanMessages = null;
            for (TipoProducto tipoProductoListOldTipoProducto : tipoProductoListOld) {
                if (!tipoProductoListNew.contains(tipoProductoListOldTipoProducto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TipoProducto " + tipoProductoListOldTipoProducto + " since its tama\u00f1o field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<TipoProducto> attachedTipoProductoListNew = new ArrayList<TipoProducto>();
            for (TipoProducto tipoProductoListNewTipoProductoToAttach : tipoProductoListNew) {
                tipoProductoListNewTipoProductoToAttach = em.getReference(tipoProductoListNewTipoProductoToAttach.getClass(), tipoProductoListNewTipoProductoToAttach.getTipoProducto());
                attachedTipoProductoListNew.add(tipoProductoListNewTipoProductoToAttach);
            }
            tipoProductoListNew = attachedTipoProductoListNew;
            tamaño.setTipoProductoList(tipoProductoListNew);
            tamaño = em.merge(tamaño);
            for (TipoProducto tipoProductoListNewTipoProducto : tipoProductoListNew) {
                if (!tipoProductoListOld.contains(tipoProductoListNewTipoProducto)) {
                    Tamaño oldTamañoOfTipoProductoListNewTipoProducto = tipoProductoListNewTipoProducto.getTamaño();
                    tipoProductoListNewTipoProducto.setTamaño(tamaño);
                    tipoProductoListNewTipoProducto = em.merge(tipoProductoListNewTipoProducto);
                    if (oldTamañoOfTipoProductoListNewTipoProducto != null && !oldTamañoOfTipoProductoListNewTipoProducto.equals(tamaño)) {
                        oldTamañoOfTipoProductoListNewTipoProducto.getTipoProductoList().remove(tipoProductoListNewTipoProducto);
                        oldTamañoOfTipoProductoListNewTipoProducto = em.merge(oldTamañoOfTipoProductoListNewTipoProducto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tamaño.getTamaño();
                if (findTamaño(id) == null) {
                    throw new NonexistentEntityException("The tama\u00f1o with id " + id + " no longer exists.");
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
            Tamaño tamaño;
            try {
                tamaño = em.getReference(Tamaño.class, id);
                tamaño.getTamaño();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tama\u00f1o with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<TipoProducto> tipoProductoListOrphanCheck = tamaño.getTipoProductoList();
            for (TipoProducto tipoProductoListOrphanCheckTipoProducto : tipoProductoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tama\u00f1o (" + tamaño + ") cannot be destroyed since the TipoProducto " + tipoProductoListOrphanCheckTipoProducto + " in its tipoProductoList field has a non-nullable tama\u00f1o field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tamaño);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tamaño> findTamañoEntities() {
        return findTamañoEntities(true, -1, -1);
    }

    public List<Tamaño> findTamañoEntities(int maxResults, int firstResult) {
        return findTamañoEntities(false, maxResults, firstResult);
    }

    private List<Tamaño> findTamañoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tamaño.class));
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

    public Tamaño findTamaño(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tamaño.class, id);
        } finally {
            em.close();
        }
    }

    public int getTamañoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tamaño> rt = cq.from(Tamaño.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
