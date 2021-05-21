package py.com.abiti.licorsys.view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import py.com.abiti.licorsys.util.ViewConfig;


@ViewConfig(uri = "alta", displayName = "persona")
public class AltaPersonaView extends CustomComponent implements View {
	
	private VerticalLayout mainLayout;
	
	private Button btnGuardar; 
	private TextField txtNombre;
	private Button btnSalir;
	
	
	
	
	
	
	
	
	
	public AltaPersonaView() {
		
		mainLayout = new VerticalLayout();
		
		setCompositionRoot(mainLayout);
		
		txtNombre = new TextField();
		btnSalir = new Button();
		btnSalir.setCaption("saliforro");
	
		
		
		
		
		btnGuardar = new Button();
		btnGuardar.setCaption("Guardar");
		
		mainLayout.addComponent(btnGuardar);
		mainLayout.addComponent(txtNombre);
		mainLayout.addComponent(btnSalir);
		
		
	}
	
	
	

}
