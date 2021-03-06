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
import py.com.abiti.licorsys.model.Genero;
import py.com.abiti.licorsys.model.Empleado;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import py.com.abiti.licorsys.controller.exceptions.IllegalOrphanException;
import py.com.abiti.licorsys.controller.exceptions.NonexistentEntityException;
import py.com.abiti.licorsys.model.Persona;
import py.com.abiti.licorsys.model.Usuario;

/**
 *
 * @author Santi
 */
public class PersonaJpaController implements Serializable {

    public PersonaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Persona persona) {
        if (persona.getEmpleadoList() == null) {
            persona.setEmpleadoList(new ArrayList<Empleado>());
        }
        if (persona.getUsuarioList() == null) {
            persona.setUsuarioList(new ArrayList<Usuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Genero genero = persona.getGenero();
            if (genero != null) {
                genero = em.getReference(genero.getClass(), genero.getGenero());
                persona.setGenero(genero);
            }
            List<Empleado> attachedEmpleadoList = new ArrayList<Empleado>();
            for (Empleado empleadoListEmpleadoToAttach : persona.getEmpleadoList()) {
                empleadoListEmpleadoToAttach = em.getReference(empleadoListEmpleadoToAttach.getClass(), empleadoListEmpleadoToAttach.getEmpleado());
                attachedEmpleadoList.add(empleadoListEmpleadoToAttach);
            }
            persona.setEmpleadoList(attachedEmpleadoList);
            List<Usuario> attachedUsuarioList = new ArrayList<Usuario>();
            for (Usuario usuarioListUsuarioToAttach : persona.getUsuarioList()) {
                usuarioListUsuarioToAttach = em.getReference(usuarioListUsuarioToAttach.getClass(), usuarioListUsuarioToAttach.getUsuario());
                attachedUsuarioList.add(usuarioListUsuarioToAttach);
            }
            persona.setUsuarioList(attachedUsuarioList);
            em.persist(persona);
            if (genero != null) {
                genero.getPersonaList().add(persona);
                genero = em.merge(genero);
            }
            for (Empleado empleadoListEmpleado : persona.getEmpleadoList()) {
                Persona oldPersonaOfEmpleadoListEmpleado = empleadoListEmpleado.getPersona();
                empleadoListEmpleado.setPersona(persona);
                empleadoListEmpleado = em.merge(empleadoListEmpleado);
                if (oldPersonaOfEmpleadoListEmpleado != null) {
                    oldPersonaOfEmpleadoListEmpleado.getEmpleadoList().remove(empleadoListEmpleado);
                    oldPersonaOfEmpleadoListEmpleado = em.merge(oldPersonaOfEmpleadoListEmpleado);
                }
            }
            for (Usuario usuarioListUsuario : persona.getUsuarioList()) {
                Persona oldPersonaOfUsuarioListUsuario = usuarioListUsuario.getPersona();
                usuarioListUsuario.setPersona(persona);
                usuarioListUsuario = em.merge(usuarioListUsuario);
                if (oldPersonaOfUsuarioListUsuario != null) {
                    oldPersonaOfUsuarioListUsuario.getUsuarioList().remove(usuarioListUsuario);
                    oldPersonaOfUsuarioListUsuario = em.merge(oldPersonaOfUsuarioListUsuario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Persona persona) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persistentPersona = em.find(Persona.class, persona.getPersona());
            Genero generoOld = persistentPersona.getGenero();
            Genero generoNew = persona.getGenero();
            List<Empleado> empleadoListOld = persistentPersona.getEmpleadoList();
            List<Empleado> empleadoListNew = persona.getEmpleadoList();
            List<Usuario> usuarioListOld = persistentPersona.getUsuarioList();
            List<Usuario> usuarioListNew = persona.getUsuarioList();
            List<String> illegalOrphanMessages = null;
            for (Empleado empleadoListOldEmpleado : empleadoListOld) {
                if (!empleadoListNew.contains(empleadoListOldEmpleado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Empleado " + empleadoListOldEmpleado + " since its persona field is not nullable.");
                }
            }
            for (Usuario usuarioListOldUsuario : usuarioListOld) {
                if (!usuarioListNew.contains(usuarioListOldUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuario " + usuarioListOldUsuario + " since its persona field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (generoNew != null) {
                generoNew = em.getReference(generoNew.getClass(), generoNew.getGenero());
                persona.setGenero(generoNew);
            }
            List<Empleado> attachedEmpleadoListNew = new ArrayList<Empleado>();
            for (Empleado empleadoListNewEmpleadoToAttach : empleadoListNew) {
                empleadoListNewEmpleadoToAttach = em.getReference(empleadoListNewEmpleadoToAttach.getClass(), empleadoListNewEmpleadoToAttach.getEmpleado());
                attachedEmpleadoListNew.add(empleadoListNewEmpleadoToAttach);
            }
            empleadoListNew = attachedEmpleadoListNew;
            persona.setEmpleadoList(empleadoListNew);
            List<Usuario> attachedUsuarioListNew = new ArrayList<Usuario>();
            for (Usuario usuarioListNewUsuarioToAttach : usuarioListNew) {
                usuarioListNewUsuarioToAttach = em.getReference(usuarioListNewUsuarioToAttach.getClass(), usuarioListNewUsuarioToAttach.getUsuario());
                attachedUsuarioListNew.add(usuarioListNewUsuarioToAttach);
            }
            usuarioListNew = attachedUsuarioListNew;
            persona.setUsuarioList(usuarioListNew);
            persona = em.merge(persona);
            if (generoOld != null && !generoOld.equals(generoNew)) {
                generoOld.getPersonaList().remove(persona);
                generoOld = em.merge(generoOld);
            }
            if (generoNew != null && !generoNew.equals(generoOld)) {
                generoNew.getPersonaList().add(persona);
                generoNew = em.merge(generoNew);
            }
            for (Empleado empleadoListNewEmpleado : empleadoListNew) {
                if (!empleadoListOld.contains(empleadoListNewEmpleado)) {
                    Persona oldPersonaOfEmpleadoListNewEmpleado = empleadoListNewEmpleado.getPersona();
                    empleadoListNewEmpleado.setPersona(persona);
                    empleadoListNewEmpleado = em.merge(empleadoListNewEmpleado);
                    if (oldPersonaOfEmpleadoListNewEmpleado != null && !oldPersonaOfEmpleadoListNewEmpleado.equals(persona)) {
                        oldPersonaOfEmpleadoListNewEmpleado.getEmpleadoList().remove(empleadoListNewEmpleado);
                        oldPersonaOfEmpleadoListNewEmpleado = em.merge(oldPersonaOfEmpleadoListNewEmpleado);
                    }
                }
            }
            for (Usuario usuarioListNewUsuario : usuarioListNew) {
                if (!usuarioListOld.contains(usuarioListNewUsuario)) {
                    Persona oldPersonaOfUsuarioListNewUsuario = usuarioListNewUsuario.getPersona();
                    usuarioListNewUsuario.setPersona(persona);
                    usuarioListNewUsuario = em.merge(usuarioListNewUsuario);
                    if (oldPersonaOfUsuarioListNewUsuario != null && !oldPersonaOfUsuarioListNewUsuario.equals(persona)) {
                        oldPersonaOfUsuarioListNewUsuario.getUsuarioList().remove(usuarioListNewUsuario);
                        oldPersonaOfUsuarioListNewUsuario = em.merge(oldPersonaOfUsuarioListNewUsuario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = persona.getPersona();
                if (findPersona(id) == null) {
                    throw new NonexistentEntityException("The persona with id " + id + " no longer exists.");
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
            Persona persona;
            try {
                persona = em.getReference(Persona.class, id);
                persona.getPersona();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The persona with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Empleado> empleadoListOrphanCheck = persona.getEmpleadoList();
            for (Empleado empleadoListOrphanCheckEmpleado : empleadoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the Empleado " + empleadoListOrphanCheckEmpleado + " in its empleadoList field has a non-nullable persona field.");
            }
            List<Usuario> usuarioListOrphanCheck = persona.getUsuarioList();
            for (Usuario usuarioListOrphanCheckUsuario : usuarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the Usuario " + usuarioListOrphanCheckUsuario + " in its usuarioList field has a non-nullable persona field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Genero genero = persona.getGenero();
            if (genero != null) {
                genero.getPersonaList().remove(persona);
                genero = em.merge(genero);
            }
            em.remove(persona);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Persona> findPersonaEntities() {
        return findPersonaEntities(true, -1, -1);
    }

    public List<Persona> findPersonaEntities(int maxResults, int firstResult) {
        return findPersonaEntities(false, maxResults, firstResult);
    }

    private List<Persona> findPersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Persona.class));
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

    public Persona findPersona(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Persona.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Persona> rt = cq.from(Persona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
