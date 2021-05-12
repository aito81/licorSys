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
import py.com.abiti.licorsys.model.Tamanho;

/**
 *
 * @author Santi
 */
public class TamanhoJpaController implements Serializable {

    public TamanhoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tamanho tamanho) {
        if (tamanho.getTipoProductoList() == null) {
            tamanho.setTipoProductoList(new ArrayList<TipoProducto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<TipoProducto> attachedTipoProductoList = new ArrayList<TipoProducto>();
            for (TipoProducto tipoProductoListTipoProductoToAttach : tamanho.getTipoProductoList()) {
                tipoProductoListTipoProductoToAttach = em.getReference(tipoProductoListTipoProductoToAttach.getClass(), tipoProductoListTipoProductoToAttach.getTipoProducto());
                attachedTipoProductoList.add(tipoProductoListTipoProductoToAttach);
            }
            tamanho.setTipoProductoList(attachedTipoProductoList);
            em.persist(tamanho);
            for (TipoProducto tipoProductoListTipoProducto : tamanho.getTipoProductoList()) {
                Tamanho oldTamanhoOfTipoProductoListTipoProducto = tipoProductoListTipoProducto.getTamanho();
                tipoProductoListTipoProducto.setTamanho(tamanho);
                tipoProductoListTipoProducto = em.merge(tipoProductoListTipoProducto);
                if (oldTamanhoOfTipoProductoListTipoProducto != null) {
                    oldTamanhoOfTipoProductoListTipoProducto.getTipoProductoList().remove(tipoProductoListTipoProducto);
                    oldTamanhoOfTipoProductoListTipoProducto = em.merge(oldTamanhoOfTipoProductoListTipoProducto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tamanho tamanho) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tamanho persistentTamanho = em.find(Tamanho.class, tamanho.getTamanho());
            List<TipoProducto> tipoProductoListOld = persistentTamanho.getTipoProductoList();
            List<TipoProducto> tipoProductoListNew = tamanho.getTipoProductoList();
            List<String> illegalOrphanMessages = null;
            for (TipoProducto tipoProductoListOldTipoProducto : tipoProductoListOld) {
                if (!tipoProductoListNew.contains(tipoProductoListOldTipoProducto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TipoProducto " + tipoProductoListOldTipoProducto + " since its tamanho field is not nullable.");
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
            tamanho.setTipoProductoList(tipoProductoListNew);
            tamanho = em.merge(tamanho);
            for (TipoProducto tipoProductoListNewTipoProducto : tipoProductoListNew) {
                if (!tipoProductoListOld.contains(tipoProductoListNewTipoProducto)) {
                    Tamanho oldTamanhoOfTipoProductoListNewTipoProducto = tipoProductoListNewTipoProducto.getTamanho();
                    tipoProductoListNewTipoProducto.setTamanho(tamanho);
                    tipoProductoListNewTipoProducto = em.merge(tipoProductoListNewTipoProducto);
                    if (oldTamanhoOfTipoProductoListNewTipoProducto != null && !oldTamanhoOfTipoProductoListNewTipoProducto.equals(tamanho)) {
                        oldTamanhoOfTipoProductoListNewTipoProducto.getTipoProductoList().remove(tipoProductoListNewTipoProducto);
                        oldTamanhoOfTipoProductoListNewTipoProducto = em.merge(oldTamanhoOfTipoProductoListNewTipoProducto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tamanho.getTamanho();
                if (findTamanho(id) == null) {
                    throw new NonexistentEntityException("The tamanho with id " + id + " no longer exists.");
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
            Tamanho tamanho;
            try {
                tamanho = em.getReference(Tamanho.class, id);
                tamanho.getTamanho();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tamanho with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<TipoProducto> tipoProductoListOrphanCheck = tamanho.getTipoProductoList();
            for (TipoProducto tipoProductoListOrphanCheckTipoProducto : tipoProductoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tamanho (" + tamanho + ") cannot be destroyed since the TipoProducto " + tipoProductoListOrphanCheckTipoProducto + " in its tipoProductoList field has a non-nullable tamanho field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tamanho);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tamanho> findTamanhoEntities() {
        return findTamanhoEntities(true, -1, -1);
    }

    public List<Tamanho> findTamanhoEntities(int maxResults, int firstResult) {
        return findTamanhoEntities(false, maxResults, firstResult);
    }

    private List<Tamanho> findTamanhoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tamanho.class));
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

    public Tamanho findTamanho(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tamanho.class, id);
        } finally {
            em.close();
        }
    }

    public int getTamanhoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tamanho> rt = cq.from(Tamanho.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
