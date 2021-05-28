package py.com.abiti.licorsys.jpa;

import javax.persistence.EntityManagerFactory;

import py.com.abiti.licorsys.controller.PersonaJpaController;

public class JpaPersona extends PersonaJpaController {

	public JpaPersona(EntityManagerFactory emf) {
		super(emf);
		// TODO Auto-generated constructor stub
	}

}
