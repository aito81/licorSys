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
import py.com.abiti.licorsys.model.Tamaño;
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
 * @author matia
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
            Tamaño tamaño = tipoProducto.getTamaño();
            if (tamaño != null) {
                tamaño = em.getReference(tamaño.getClass(), tamaño.getTamaño());
                tipoProducto.setTamaño(tamaño);
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
            if (tamaño != null) {
                tamaño.getTipoProductoList().add(tipoProducto);
                tamaño = em.merge(tamaño);
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
            Tamaño tamañoOld = persistentTipoProducto.getTamaño();
            Tamaño tamañoNew = tipoProducto.getTamaño();
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
            if (tamañoNew != null) {
                tamañoNew = em.getReference(tamañoNew.getClass(), tamañoNew.getTamaño());
                tipoProducto.setTamaño(tamañoNew);
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
            if (tamañoOld != null && !tamañoOld.equals(tamañoNew)) {
                tamañoOld.getTipoProductoList().remove(tipoProducto);
                tamañoOld = em.merge(tamañoOld);
            }
            if (tamañoNew != null && !tamañoNew.equals(tamañoOld)) {
                tamañoNew.getTipoProductoList().add(tipoProducto);
                tamañoNew = em.merge(tamañoNew);
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
            Tamaño tamaño = tipoProducto.getTamaño();
            if (tamaño != null) {
                tamaño.getTipoProductoList().remove(tipoProducto);
                tamaño = em.merge(tamaño);
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
