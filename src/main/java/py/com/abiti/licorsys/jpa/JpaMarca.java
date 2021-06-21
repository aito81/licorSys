package py.com.abiti.licorsys.jpa;

import javax.persistence.EntityManagerFactory;

import py.com.abiti.licorsys.controller.MarcaJpaController;

public class JpaMarca extends MarcaJpaController {

	public JpaMarca(EntityManagerFactory emf) {
		super(emf);
		// TODO Auto-generated constructor stub
	}

}
