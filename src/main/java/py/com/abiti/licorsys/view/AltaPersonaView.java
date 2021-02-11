package py.com.abiti.licorsys.view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

import py.com.abiti.licorsys.util.ViewConfig;


@ViewConfig(uri = "alta", displayName = "persona")
public class AltaPersonaView extends CustomComponent implements View {
	
	private VerticalLayout mainLayout;
	
	private Button btnGuardar;
	
	
	
	public AltaPersonaView() {
		
		mainLayout = new VerticalLayout();
		
		setCompositionRoot(mainLayout);
		
		
		
		btnGuardar = new Button();
		btnGuardar.setCaption("Guardar");
		
		mainLayout.addComponent(btnGuardar);
		
		
	}
	
	
	

}
