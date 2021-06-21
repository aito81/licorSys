package py.com.abiti.licorsys.view;

import org.w3c.dom.Text;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import py.com.abiti.licorsys.jpa.JpaProveedor;
import py.com.abiti.licorsys.model.Proveedor;
import py.com.abiti.licorsys.util.JpaUtil;
import py.com.abiti.licorsys.util.ViewConfig;

@ViewConfig(uri = "altaProveedor" ,displayName = "proveedor")
public class AltaProveedorView extends CustomComponent implements View  {
     
	private VerticalLayout mainLayout;
	
	private  HorizontalLayout botonLayout;
	
	private Button btnGuardar;
	private Button btnSalir;
	
	
	private TextField txtNombreProveedor;

	private JpaProveedor jpaProveedor = new JpaProveedor(JpaUtil.getEntityManagerFactory());

	
	
    public AltaProveedorView()  {
    	
    mainLayout = new VerticalLayout();
    botonLayout = new HorizontalLayout();
     
    setCompositionRoot(mainLayout);
    
    crearComponentes ();
    btnGuardar.addClickListener(e -> guardarProveedor() );
    
    mostrarCoponemtes();
    
     
	}

    

	


	private void guardarProveedor() {
		
		if (txtNombreProveedor.getValue().isEmpty()) {
			
			Notification.show("El Nombre no puede quedar vacio", Notification.TYPE_WARNING_MESSAGE);
			txtNombreProveedor.focus();
			return ;
			
		}
		
		Proveedor addProveedor = new Proveedor();
		addProveedor.setDescripcion(txtNombreProveedor.getValue());
		
		try {
		
		   jpaProveedor.create(addProveedor);
				
			Notification.show("Proveedor agregada correctamente");
			
			txtNombreProveedor.clear();
			
		}catch (Exception e) {
			
		Notification.show(e.getMessage(), Notification.TYPE_ERROR_MESSAGE);
			
			// TODO: handle exception
		}
			
				
		  
		   
		
	
		
		
	}





	private void mostrarCoponemtes(){
		 mainLayout.addComponent(txtNombreProveedor);
		 
		 botonLayout.addComponents(btnGuardar,btnSalir);
		 mainLayout.addComponent(botonLayout);
		 
		
		
	}


	private void crearComponentes() {
		
		txtNombreProveedor = new TextField();
		txtNombreProveedor.setCaption("NOMBRE PROVEEDOR");
		btnGuardar = new Button();
		btnGuardar.setCaption("GUARDAR");
		btnSalir = new Button();
		btnSalir.setCaption("SALIR");
		
		
		
		
		// TODO Auto-generated method stub

		
	}
	
	

}
