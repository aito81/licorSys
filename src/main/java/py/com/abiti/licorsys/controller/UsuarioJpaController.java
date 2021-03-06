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
import py.com.abiti.licorsys.model.Persona;
import py.com.abiti.licorsys.model.Egreso;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import py.com.abiti.licorsys.controller.exceptions.IllegalOrphanException;
import py.com.abiti.licorsys.controller.exceptions.NonexistentEntityException;
import py.com.abiti.licorsys.model.Usuario;

/**
 *
 * @author Santi
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        if (usuario.getEgresoList() == null) {
            usuario.setEgresoList(new ArrayList<Egreso>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persona = usuario.getPersona();
            if (persona != null) {
                persona = em.getReference(persona.getClass(), persona.getPersona());
                usuario.setPersona(persona);
            }
            List<Egreso> attachedEgresoList = new ArrayList<Egreso>();
            for (Egreso egresoListEgresoToAttach : usuario.getEgresoList()) {
                egresoListEgresoToAttach = em.getReference(egresoListEgresoToAttach.getClass(), egresoListEgresoToAttach.getEgreso());
                attachedEgresoList.add(egresoListEgresoToAttach);
            }
            usuario.setEgresoList(attachedEgresoList);
            em.persist(usuario);
            if (persona != null) {
                persona.getUsuarioList().add(usuario);
                persona = em.merge(persona);
            }
            for (Egreso egresoListEgreso : usuario.getEgresoList()) {
                Usuario oldUsuarioOfEgresoListEgreso = egresoListEgreso.getUsuario();
                egresoListEgreso.setUsuario(usuario);
                egresoListEgreso = em.merge(egresoListEgreso);
                if (oldUsuarioOfEgresoListEgreso != null) {
                    oldUsuarioOfEgresoListEgreso.getEgresoList().remove(egresoListEgreso);
                    oldUsuarioOfEgresoListEgreso = em.merge(oldUsuarioOfEgresoListEgreso);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getUsuario());
            Persona personaOld = persistentUsuario.getPersona();
            Persona personaNew = usuario.getPersona();
            List<Egreso> egresoListOld = persistentUsuario.getEgresoList();
            List<Egreso> egresoListNew = usuario.getEgresoList();
            List<String> illegalOrphanMessages = null;
            for (Egreso egresoListOldEgreso : egresoListOld) {
                if (!egresoListNew.contains(egresoListOldEgreso)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Egreso " + egresoListOldEgreso + " since its usuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (personaNew != null) {
                personaNew = em.getReference(personaNew.getClass(), personaNew.getPersona());
                usuario.setPersona(personaNew);
            }
            List<Egreso> attachedEgresoListNew = new ArrayList<Egreso>();
            for (Egreso egresoListNewEgresoToAttach : egresoListNew) {
                egresoListNewEgresoToAttach = em.getReference(egresoListNewEgresoToAttach.getClass(), egresoListNewEgresoToAttach.getEgreso());
                attachedEgresoListNew.add(egresoListNewEgresoToAttach);
            }
            egresoListNew = attachedEgresoListNew;
            usuario.setEgresoList(egresoListNew);
            usuario = em.merge(usuario);
            if (personaOld != null && !personaOld.equals(personaNew)) {
                personaOld.getUsuarioList().remove(usuario);
                personaOld = em.merge(personaOld);
            }
            if (personaNew != null && !personaNew.equals(personaOld)) {
                personaNew.getUsuarioList().add(usuario);
                personaNew = em.merge(personaNew);
            }
            for (Egreso egresoListNewEgreso : egresoListNew) {
                if (!egresoListOld.contains(egresoListNewEgreso)) {
                    Usuario oldUsuarioOfEgresoListNewEgreso = egresoListNewEgreso.getUsuario();
                    egresoListNewEgreso.setUsuario(usuario);
                    egresoListNewEgreso = em.merge(egresoListNewEgreso);
                    if (oldUsuarioOfEgresoListNewEgreso != null && !oldUsuarioOfEgresoListNewEgreso.equals(usuario)) {
                        oldUsuarioOfEgresoListNewEgreso.getEgresoList().remove(egresoListNewEgreso);
                        oldUsuarioOfEgresoListNewEgreso = em.merge(oldUsuarioOfEgresoListNewEgreso);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getUsuario();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Egreso> egresoListOrphanCheck = usuario.getEgresoList();
            for (Egreso egresoListOrphanCheckEgreso : egresoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Egreso " + egresoListOrphanCheckEgreso + " in its egresoList field has a non-nullable usuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Persona persona = usuario.getPersona();
            if (persona != null) {
                persona.getUsuarioList().remove(usuario);
                persona = em.merge(persona);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
