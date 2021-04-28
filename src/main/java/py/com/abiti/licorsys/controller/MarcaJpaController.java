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
import py.com.abiti.licorsys.model.Marca;

/**
 *
 * @author matia
 */
public class MarcaJpaController implements Serializable {

    public MarcaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Marca marca) {
        if (marca.getTipoProductoList() == null) {
            marca.setTipoProductoList(new ArrayList<TipoProducto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<TipoProducto> attachedTipoProductoList = new ArrayList<TipoProducto>();
            for (TipoProducto tipoProductoListTipoProductoToAttach : marca.getTipoProductoList()) {
                tipoProductoListTipoProductoToAttach = em.getReference(tipoProductoListTipoProductoToAttach.getClass(), tipoProductoListTipoProductoToAttach.getTipoProducto());
                attachedTipoProductoList.add(tipoProductoListTipoProductoToAttach);
            }
            marca.setTipoProductoList(attachedTipoProductoList);
            em.persist(marca);
            for (TipoProducto tipoProductoListTipoProducto : marca.getTipoProductoList()) {
                Marca oldMarcaOfTipoProductoListTipoProducto = tipoProductoListTipoProducto.getMarca();
                tipoProductoListTipoProducto.setMarca(marca);
                tipoProductoListTipoProducto = em.merge(tipoProductoListTipoProducto);
                if (oldMarcaOfTipoProductoListTipoProducto != null) {
                    oldMarcaOfTipoProductoListTipoProducto.getTipoProductoList().remove(tipoProductoListTipoProducto);
                    oldMarcaOfTipoProductoListTipoProducto = em.merge(oldMarcaOfTipoProductoListTipoProducto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Marca marca) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Marca persistentMarca = em.find(Marca.class, marca.getMarca());
            List<TipoProducto> tipoProductoListOld = persistentMarca.getTipoProductoList();
            List<TipoProducto> tipoProductoListNew = marca.getTipoProductoList();
            List<String> illegalOrphanMessages = null;
            for (TipoProducto tipoProductoListOldTipoProducto : tipoProductoListOld) {
                if (!tipoProductoListNew.contains(tipoProductoListOldTipoProducto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TipoProducto " + tipoProductoListOldTipoProducto + " since its marca field is not nullable.");
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
            marca.setTipoProductoList(tipoProductoListNew);
            marca = em.merge(marca);
            for (TipoProducto tipoProductoListNewTipoProducto : tipoProductoListNew) {
                if (!tipoProductoListOld.contains(tipoProductoListNewTipoProducto)) {
                    Marca oldMarcaOfTipoProductoListNewTipoProducto = tipoProductoListNewTipoProducto.getMarca();
                    tipoProductoListNewTipoProducto.setMarca(marca);
                    tipoProductoListNewTipoProducto = em.merge(tipoProductoListNewTipoProducto);
                    if (oldMarcaOfTipoProductoListNewTipoProducto != null && !oldMarcaOfTipoProductoListNewTipoProducto.equals(marca)) {
                        oldMarcaOfTipoProductoListNewTipoProducto.getTipoProductoList().remove(tipoProductoListNewTipoProducto);
                        oldMarcaOfTipoProductoListNewTipoProducto = em.merge(oldMarcaOfTipoProductoListNewTipoProducto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = marca.getMarca();
                if (findMarca(id) == null) {
                    throw new NonexistentEntityException("The marca with id " + id + " no longer exists.");
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
            Marca marca;
            try {
                marca = em.getReference(Marca.class, id);
                marca.getMarca();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The marca with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<TipoProducto> tipoProductoListOrphanCheck = marca.getTipoProductoList();
            for (TipoProducto tipoProductoListOrphanCheckTipoProducto : tipoProductoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Marca (" + marca + ") cannot be destroyed since the TipoProducto " + tipoProductoListOrphanCheckTipoProducto + " in its tipoProductoList field has a non-nullable marca field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(marca);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Marca> findMarcaEntities() {
        return findMarcaEntities(true, -1, -1);
    }

    public List<Marca> findMarcaEntities(int maxResults, int firstResult) {
        return findMarcaEntities(false, maxResults, firstResult);
    }

    private List<Marca> findMarcaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Marca.class));
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

    public Marca findMarca(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Marca.class, id);
        } finally {
            em.close();
        }
    }

    public int getMarcaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Marca> rt = cq.from(Marca.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
