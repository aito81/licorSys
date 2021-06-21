package py.com.abiti.licorsys.view;

import org.eclipse.persistence.internal.libraries.asm.tree.TryCatchBlockNode;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import py.com.abiti.licorsys.controller.ProveedorJpaController;
import py.com.abiti.licorsys.jpa.JpaMarca;
import py.com.abiti.licorsys.jpa.JpaProveedor;
import py.com.abiti.licorsys.model.Marca;
import py.com.abiti.licorsys.util.JpaUtil;
import py.com.abiti.licorsys.util.ViewConfig;

@ViewConfig(uri = "AltaMarca" ,displayName = "marca")
public class AltaMarcaView extends CustomComponent implements View  {
   
private VerticalLayout mainLayout;
	
	private  HorizontalLayout botonLayout;
	private Button btnGuardar; 
	private Button btnSalir;
	
	private  TextField txtNombreMarca; 
	private JpaMarca jpaMarca = new JpaMarca(JpaUtil.getEntityManagerFactory());
	

	  
	public  AltaMarcaView(){
	
		mainLayout = new VerticalLayout();
	    botonLayout = new HorizontalLayout();
	     
	    setCompositionRoot(mainLayout);
	    
	    crearComponente(); 
	    btnGuardar.addClickListener(e -> guardarNombreMarca() );
	    
	    
	    
		
	}

	private void mostrarComponemtes() {
		// TODO Auto-generated method stub
		
	}

	private void guardarNombreMarca() {
		if (txtNombreMarca.getValue().isEmpty());
		
		Notification.show("El nombre no puede vacio", Notification.TYPE_ERROR_MESSAGE);
		txtNombreMarca.focus();
		
		
		Marca addNombreMarca = new Marca();
		addNombreMarca.setDescripcion(txtNombreMarca.getValue());
		
		try {
		
			jpaMarca.create(addNombreMarca);
			Notification.show("Marca agregada corectamente");
			txtNombreMarca.clear();
		
		}catch  (Exception e ) {
			Notification.show(e.getMessage(), Notification.TYPE_ERROR_MESSAGE);	
		}	
	}

	private void crearComponente() {

		txtNombreMarca = new TextField();
		txtNombreMarca.setCaption("Nombre Marca" );
	}
		// TODO Auto-generated method stub
		
	
	
}  