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
import py.com.abiti.licorsys.model.Proveedor;
import py.com.abiti.licorsys.model.TipoEgreso;
import py.com.abiti.licorsys.model.Usuario;
import py.com.abiti.licorsys.model.Transaccion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import py.com.abiti.licorsys.controller.exceptions.IllegalOrphanException;
import py.com.abiti.licorsys.controller.exceptions.NonexistentEntityException;
import py.com.abiti.licorsys.model.Egreso;

/**
 *
 * @author Santi
 */
public class EgresoJpaController implements Serializable {

    public EgresoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Egreso egreso) {
        if (egreso.getTransaccionList() == null) {
            egreso.setTransaccionList(new ArrayList<Transaccion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proveedor proveedor = egreso.getProveedor();
            if (proveedor != null) {
                proveedor = em.getReference(proveedor.getClass(), proveedor.getProveedor());
                egreso.setProveedor(proveedor);
            }
            TipoEgreso tipoEgreso = egreso.getTipoEgreso();
            if (tipoEgreso != null) {
                tipoEgreso = em.getReference(tipoEgreso.getClass(), tipoEgreso.getTipoEgreso());
                egreso.setTipoEgreso(tipoEgreso);
            }
            Usuario usuario = egreso.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getUsuario());
                egreso.setUsuario(usuario);
            }
            List<Transaccion> attachedTransaccionList = new ArrayList<Transaccion>();
            for (Transaccion transaccionListTransaccionToAttach : egreso.getTransaccionList()) {
                transaccionListTransaccionToAttach = em.getReference(transaccionListTransaccionToAttach.getClass(), transaccionListTransaccionToAttach.getTransaccion());
                attachedTransaccionList.add(transaccionListTransaccionToAttach);
            }
            egreso.setTransaccionList(attachedTransaccionList);
            em.persist(egreso);
            if (proveedor != null) {
                proveedor.getEgresoList().add(egreso);
                proveedor = em.merge(proveedor);
            }
            if (tipoEgreso != null) {
                tipoEgreso.getEgresoList().add(egreso);
                tipoEgreso = em.merge(tipoEgreso);
            }
            if (usuario != null) {
                usuario.getEgresoList().add(egreso);
                usuario = em.merge(usuario);
            }
            for (Transaccion transaccionListTransaccion : egreso.getTransaccionList()) {
                Egreso oldEgresoOfTransaccionListTransaccion = transaccionListTransaccion.getEgreso();
                transaccionListTransaccion.setEgreso(egreso);
                transaccionListTransaccion = em.merge(transaccionListTransaccion);
                if (oldEgresoOfTransaccionListTransaccion != null) {
                    oldEgresoOfTransaccionListTransaccion.getTransaccionList().remove(transaccionListTransaccion);
                    oldEgresoOfTransaccionListTransaccion = em.merge(oldEgresoOfTransaccionListTransaccion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Egreso egreso) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Egreso persistentEgreso = em.find(Egreso.class, egreso.getEgreso());
            Proveedor proveedorOld = persistentEgreso.getProveedor();
            Proveedor proveedorNew = egreso.getProveedor();
            TipoEgreso tipoEgresoOld = persistentEgreso.getTipoEgreso();
            TipoEgreso tipoEgresoNew = egreso.getTipoEgreso();
            Usuario usuarioOld = persistentEgreso.getUsuario();
            Usuario usuarioNew = egreso.getUsuario();
            List<Transaccion> transaccionListOld = persistentEgreso.getTransaccionList();
            List<Transaccion> transaccionListNew = egreso.getTransaccionList();
            List<String> illegalOrphanMessages = null;
            for (Transaccion transaccionListOldTransaccion : transaccionListOld) {
                if (!transaccionListNew.contains(transaccionListOldTransaccion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Transaccion " + transaccionListOldTransaccion + " since its egreso field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (proveedorNew != null) {
                proveedorNew = em.getReference(proveedorNew.getClass(), proveedorNew.getProveedor());
                egreso.setProveedor(proveedorNew);
            }
            if (tipoEgresoNew != null) {
                tipoEgresoNew = em.getReference(tipoEgresoNew.getClass(), tipoEgresoNew.getTipoEgreso());
                egreso.setTipoEgreso(tipoEgresoNew);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getUsuario());
                egreso.setUsuario(usuarioNew);
            }
            List<Transaccion> attachedTransaccionListNew = new ArrayList<Transaccion>();
            for (Transaccion transaccionListNewTransaccionToAttach : transaccionListNew) {
                transaccionListNewTransaccionToAttach = em.getReference(transaccionListNewTransaccionToAttach.getClass(), transaccionListNewTransaccionToAttach.getTransaccion());
                attachedTransaccionListNew.add(transaccionListNewTransaccionToAttach);
            }
            transaccionListNew = attachedTransaccionListNew;
            egreso.setTransaccionList(transaccionListNew);
            egreso = em.merge(egreso);
            if (proveedorOld != null && !proveedorOld.equals(proveedorNew)) {
                proveedorOld.getEgresoList().remove(egreso);
                proveedorOld = em.merge(proveedorOld);
            }
            if (proveedorNew != null && !proveedorNew.equals(proveedorOld)) {
                proveedorNew.getEgresoList().add(egreso);
                proveedorNew = em.merge(proveedorNew);
            }
            if (tipoEgresoOld != null && !tipoEgresoOld.equals(tipoEgresoNew)) {
                tipoEgresoOld.getEgresoList().remove(egreso);
                tipoEgresoOld = em.merge(tipoEgresoOld);
            }
            if (tipoEgresoNew != null && !tipoEgresoNew.equals(tipoEgresoOld)) {
                tipoEgresoNew.getEgresoList().add(egreso);
                tipoEgresoNew = em.merge(tipoEgresoNew);
            }
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getEgresoList().remove(egreso);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getEgresoList().add(egreso);
                usuarioNew = em.merge(usuarioNew);
            }
            for (Transaccion transaccionListNewTransaccion : transaccionListNew) {
                if (!transaccionListOld.contains(transaccionListNewTransaccion)) {
                    Egreso oldEgresoOfTransaccionListNewTransaccion = transaccionListNewTransaccion.getEgreso();
                    transaccionListNewTransaccion.setEgreso(egreso);
                    transaccionListNewTransaccion = em.merge(transaccionListNewTransaccion);
                    if (oldEgresoOfTransaccionListNewTransaccion != null && !oldEgresoOfTransaccionListNewTransaccion.equals(egreso)) {
                        oldEgresoOfTransaccionListNewTransaccion.getTransaccionList().remove(transaccionListNewTransaccion);
                        oldEgresoOfTransaccionListNewTransaccion = em.merge(oldEgresoOfTransaccionListNewTransaccion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = egreso.getEgreso();
                if (findEgreso(id) == null) {
                    throw new NonexistentEntityException("The egreso with id " + id + " no longer exists.");
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
            Egreso egreso;
            try {
                egreso = em.getReference(Egreso.class, id);
                egreso.getEgreso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The egreso with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Transaccion> transaccionListOrphanCheck = egreso.getTransaccionList();
            for (Transaccion transaccionListOrphanCheckTransaccion : transaccionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Egreso (" + egreso + ") cannot be destroyed since the Transaccion " + transaccionListOrphanCheckTransaccion + " in its transaccionList field has a non-nullable egreso field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Proveedor proveedor = egreso.getProveedor();
            if (proveedor != null) {
                proveedor.getEgresoList().remove(egreso);
                proveedor = em.merge(proveedor);
            }
            TipoEgreso tipoEgreso = egreso.getTipoEgreso();
            if (tipoEgreso != null) {
                tipoEgreso.getEgresoList().remove(egreso);
                tipoEgreso = em.merge(tipoEgreso);
            }
            Usuario usuario = egreso.getUsuario();
            if (usuario != null) {
                usuario.getEgresoList().remove(egreso);
                usuario = em.merge(usuario);
            }
            em.remove(egreso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Egreso> findEgresoEntities() {
        return findEgresoEntities(true, -1, -1);
    }

    public List<Egreso> findEgresoEntities(int maxResults, int firstResult) {
        return findEgresoEntities(false, maxResults, firstResult);
    }

    private List<Egreso> findEgresoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Egreso.class));
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

    public Egreso findEgreso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Egreso.class, id);
        } finally {
            em.close();
        }
    }

    public int getEgresoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Egreso> rt = cq.from(Egreso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
