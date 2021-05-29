package py.com.abiti.licorsys.jpa;

import javax.persistence.EntityManagerFactory;

import py.com.abiti.licorsys.controller.GeneroJpaController;

public class JpaGenero extends GeneroJpaController{

	public JpaGenero(EntityManagerFactory emf) {
		super(emf);
		// TODO Auto-generated constructor stub
	}

}
