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
import py.com.abiti.licorsys.model.Marca;
import py.com.abiti.licorsys.model.Tamanho;
import py.com.abiti.licorsys.model.Producto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import py.com.abiti.licorsys.controller.exceptions.IllegalOrphanException;
import py.com.abiti.licorsys.controller.exceptions.NonexistentEntityException;
import py.com.abiti.licorsys.model.TipoProducto;

/**
 *
 * @author Santi
 */
public class TipoProductoJpaController implements Serializable {

    public TipoProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoProducto tipoProducto) {
        if (tipoProducto.getProductoList() == null) {
            tipoProducto.setProductoList(new ArrayList<Producto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Marca marca = tipoProducto.getMarca();
            if (marca != null) {
                marca = em.getReference(marca.getClass(), marca.getMarca());
                tipoProducto.setMarca(marca);
            }
            Tamanho tamanho = tipoProducto.getTamanho();
            if (tamanho != null) {
                tamanho = em.getReference(tamanho.getClass(), tamanho.getTamanho());
                tipoProducto.setTamanho(tamanho);
            }
            List<Producto> attachedProductoList = new ArrayList<Producto>();
            for (Producto productoListProductoToAttach : tipoProducto.getProductoList()) {
                productoListProductoToAttach = em.getReference(productoListProductoToAttach.getClass(), productoListProductoToAttach.getProducto());
                attachedProductoList.add(productoListProductoToAttach);
            }
            tipoProducto.setProductoList(attachedProductoList);
            em.persist(tipoProducto);
            if (marca != null) {
                marca.getTipoProductoList().add(tipoProducto);
                marca = em.merge(marca);
            }
            if (tamanho != null) {
                tamanho.getTipoProductoList().add(tipoProducto);
                tamanho = em.merge(tamanho);
            }
            for (Producto productoListProducto : tipoProducto.getProductoList()) {
                TipoProducto oldTipoProductoOfProductoListProducto = productoListProducto.getTipoProducto();
                productoListProducto.setTipoProducto(tipoProducto);
                productoListProducto = em.merge(productoListProducto);
                if (oldTipoProductoOfProductoListProducto != null) {
                    oldTipoProductoOfProductoListProducto.getProductoList().remove(productoListProducto);
                    oldTipoProductoOfProductoListProducto = em.merge(oldTipoProductoOfProductoListProducto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoProducto tipoProducto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoProducto persistentTipoProducto = em.find(TipoProducto.class, tipoProducto.getTipoProducto());
            Marca marcaOld = persistentTipoProducto.getMarca();
            Marca marcaNew = tipoProducto.getMarca();
            Tamanho tamanhoOld = persistentTipoProducto.getTamanho();
            Tamanho tamanhoNew = tipoProducto.getTamanho();
            List<Producto> productoListOld = persistentTipoProducto.getProductoList();
            List<Producto> productoListNew = tipoProducto.getProductoList();
            List<String> illegalOrphanMessages = null;
            for (Producto productoListOldProducto : productoListOld) {
                if (!productoListNew.contains(productoListOldProducto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Producto " + productoListOldProducto + " since its tipoProducto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (marcaNew != null) {
                marcaNew = em.getReference(marcaNew.getClass(), marcaNew.getMarca());
                tipoProducto.setMarca(marcaNew);
            }
            if (tamanhoNew != null) {
                tamanhoNew = em.getReference(tamanhoNew.getClass(), tamanhoNew.getTamanho());
                tipoProducto.setTamanho(tamanhoNew);
            }
            List<Producto> attachedProductoListNew = new ArrayList<Producto>();
            for (Producto productoListNewProductoToAttach : productoListNew) {
                productoListNewProductoToAttach = em.getReference(productoListNewProductoToAttach.getClass(), productoListNewProductoToAttach.getProducto());
                attachedProductoListNew.add(productoListNewProductoToAttach);
            }
            productoListNew = attachedProductoListNew;
            tipoProducto.setProductoList(productoListNew);
            tipoProducto = em.merge(tipoProducto);
            if (marcaOld != null && !marcaOld.equals(marcaNew)) {
                marcaOld.getTipoProductoList().remove(tipoProducto);
                marcaOld = em.merge(marcaOld);
            }
            if (marcaNew != null && !marcaNew.equals(marcaOld)) {
                marcaNew.getTipoProductoList().add(tipoProducto);
                marcaNew = em.merge(marcaNew);
            }
            if (tamanhoOld != null && !tamanhoOld.equals(tamanhoNew)) {
                tamanhoOld.getTipoProductoList().remove(tipoProducto);
                tamanhoOld = em.merge(tamanhoOld);
            }
            if (tamanhoNew != null && !tamanhoNew.equals(tamanhoOld)) {
                tamanhoNew.getTipoProductoList().add(tipoProducto);
                tamanhoNew = em.merge(tamanhoNew);
            }
            for (Producto productoListNewProducto : productoListNew) {
                if (!productoListOld.contains(productoListNewProducto)) {
                    TipoProducto oldTipoProductoOfProductoListNewProducto = productoListNewProducto.getTipoProducto();
                    productoListNewProducto.setTipoProducto(tipoProducto);
                    productoListNewProducto = em.merge(productoListNewProducto);
                    if (oldTipoProductoOfProductoListNewProducto != null && !oldTipoProductoOfProductoListNewProducto.equals(tipoProducto)) {
                        oldTipoProductoOfProductoListNewProducto.getProductoList().remove(productoListNewProducto);
                        oldTipoProductoOfProductoListNewProducto = em.merge(oldTipoProductoOfProductoListNewProducto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoProducto.getTipoProducto();
                if (findTipoProducto(id) == null) {
                    throw new NonexistentEntityException("The tipoProducto with id " + id + " no longer exists.");
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
            TipoProducto tipoProducto;
            try {
                tipoProducto = em.getReference(TipoProducto.class, id);
                tipoProducto.getTipoProducto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoProducto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Producto> productoListOrphanCheck = tipoProducto.getProductoList();
            for (Producto productoListOrphanCheckProducto : productoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoProducto (" + tipoProducto + ") cannot be destroyed since the Producto " + productoListOrphanCheckProducto + " in its productoList field has a non-nullable tipoProducto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Marca marca = tipoProducto.getMarca();
            if (marca != null) {
                marca.getTipoProductoList().remove(tipoProducto);
                marca = em.merge(marca);
            }
            Tamanho tamanho = tipoProducto.getTamanho();
            if (tamanho != null) {
                tamanho.getTipoProductoList().remove(tipoProducto);
                tamanho = em.merge(tamanho);
            }
            em.remove(tipoProducto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoProducto> findTipoProductoEntities() {
        return findTipoProductoEntities(true, -1, -1);
    }

    public List<TipoProducto> findTipoProductoEntities(int maxResults, int firstResult) {
        return findTipoProductoEntities(false, maxResults, firstResult);
    }

    private List<TipoProducto> findTipoProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoProducto.class));
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

    public TipoProducto findTipoProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoProducto.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoProducto> rt = cq.from(TipoProducto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
