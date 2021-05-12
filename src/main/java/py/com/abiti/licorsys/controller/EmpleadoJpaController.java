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
import py.com.abiti.licorsys.model.Cargo;
import py.com.abiti.licorsys.model.Empleado;
import py.com.abiti.licorsys.model.Persona;
import py.com.abiti.licorsys.model.Turno;

/**
 *
 * @author Santi
 */
public class EmpleadoJpaController implements Serializable {

    public EmpleadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empleado empleado) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cargo cargo = empleado.getCargo();
            if (cargo != null) {
                cargo = em.getReference(cargo.getClass(), cargo.getCargo());
                empleado.setCargo(cargo);
            }
            Persona persona = empleado.getPersona();
            if (persona != null) {
                persona = em.getReference(persona.getClass(), persona.getPersona());
                empleado.setPersona(persona);
            }
            Turno turno = empleado.getTurno();
            if (turno != null) {
                turno = em.getReference(turno.getClass(), turno.getTurno());
                empleado.setTurno(turno);
            }
            em.persist(empleado);
            if (cargo != null) {
                cargo.getEmpleadoList().add(empleado);
                cargo = em.merge(cargo);
            }
            if (persona != null) {
                persona.getEmpleadoList().add(empleado);
                persona = em.merge(persona);
            }
            if (turno != null) {
                turno.getEmpleadoList().add(empleado);
                turno = em.merge(turno);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empleado empleado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado persistentEmpleado = em.find(Empleado.class, empleado.getEmpleado());
            Cargo cargoOld = persistentEmpleado.getCargo();
            Cargo cargoNew = empleado.getCargo();
            Persona personaOld = persistentEmpleado.getPersona();
            Persona personaNew = empleado.getPersona();
            Turno turnoOld = persistentEmpleado.getTurno();
            Turno turnoNew = empleado.getTurno();
            if (cargoNew != null) {
                cargoNew = em.getReference(cargoNew.getClass(), cargoNew.getCargo());
                empleado.setCargo(cargoNew);
            }
            if (personaNew != null) {
                personaNew = em.getReference(personaNew.getClass(), personaNew.getPersona());
                empleado.setPersona(personaNew);
            }
            if (turnoNew != null) {
                turnoNew = em.getReference(turnoNew.getClass(), turnoNew.getTurno());
                empleado.setTurno(turnoNew);
            }
            empleado = em.merge(empleado);
            if (cargoOld != null && !cargoOld.equals(cargoNew)) {
                cargoOld.getEmpleadoList().remove(empleado);
                cargoOld = em.merge(cargoOld);
            }
            if (cargoNew != null && !cargoNew.equals(cargoOld)) {
                cargoNew.getEmpleadoList().add(empleado);
                cargoNew = em.merge(cargoNew);
            }
            if (personaOld != null && !personaOld.equals(personaNew)) {
                personaOld.getEmpleadoList().remove(empleado);
                personaOld = em.merge(personaOld);
            }
            if (personaNew != null && !personaNew.equals(personaOld)) {
                personaNew.getEmpleadoList().add(empleado);
                personaNew = em.merge(personaNew);
            }
            if (turnoOld != null && !turnoOld.equals(turnoNew)) {
                turnoOld.getEmpleadoList().remove(empleado);
                turnoOld = em.merge(turnoOld);
            }
            if (turnoNew != null && !turnoNew.equals(turnoOld)) {
                turnoNew.getEmpleadoList().add(empleado);
                turnoNew = em.merge(turnoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = empleado.getEmpleado();
                if (findEmpleado(id) == null) {
                    throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.");
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
            Empleado empleado;
            try {
                empleado = em.getReference(Empleado.class, id);
                empleado.getEmpleado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.", enfe);
            }
            Cargo cargo = empleado.getCargo();
            if (cargo != null) {
                cargo.getEmpleadoList().remove(empleado);
                cargo = em.merge(cargo);
            }
            Persona persona = empleado.getPersona();
            if (persona != null) {
                persona.getEmpleadoList().remove(empleado);
                persona = em.merge(persona);
            }
            Turno turno = empleado.getTurno();
            if (turno != null) {
                turno.getEmpleadoList().remove(empleado);
                turno = em.merge(turno);
            }
            em.remove(empleado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empleado> findEmpleadoEntities() {
        return findEmpleadoEntities(true, -1, -1);
    }

    public List<Empleado> findEmpleadoEntities(int maxResults, int firstResult) {
        return findEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<Empleado> findEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleado.class));
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

    public Empleado findEmpleado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleado> rt = cq.from(Empleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
