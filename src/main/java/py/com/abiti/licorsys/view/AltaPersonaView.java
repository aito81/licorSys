package py.com.abiti.licorsys.view;

import org.w3c.dom.Text;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import py.com.abiti.licorsys.jpa.JpaPersona;
import py.com.abiti.licorsys.model.Genero;
import py.com.abiti.licorsys.model.Persona;
import py.com.abiti.licorsys.util.JpaUtil;
import py.com.abiti.licorsys.util.ViewConfig;


@ViewConfig(uri = "alta", displayName = "persona")
public class AltaPersonaView extends CustomComponent implements View {
	
	private VerticalLayout mainLayout;
	
	private HorizontalLayout BotonLayout;
	
	private Button btnGuardar; 
	private Button btnSalir;
	private Button btnAdd;
	
	private TextField txtNombre;
	private TextField txtApellido;
	private TextField txtDireccion;
	private TextField txtNroDoc;
	private TextField txtRuc;
	private TextField txtTelefono;
	private DateField dfFecNac;
	private ComboBox  cbxGenero;
	
	private JpaPersona jpaPersona = new JpaPersona(JpaUtil.getEntityManagerFactory());
	
	
	
	
	
	
	public AltaPersonaView() {
		
		mainLayout = new VerticalLayout();
		BotonLayout = new HorizontalLayout();
		
		setCompositionRoot(mainLayout);
		
		crearComponentes();
		
		btnGuardar.addClickListener(e -> guardarPersona() );
		
		mostrarComponentes();
		
		
		
		
		
		
	}







	







	private void mostrarComponentes() {

		mainLayout.addComponent(txtNombre);
		mainLayout.addComponents(txtNombre);
		mainLayout.addComponent(txtApellido);
		mainLayout.addComponent(txtDireccion);
		mainLayout.addComponent(txtNroDoc);
		mainLayout.addComponent(txtRuc);
		mainLayout.addComponent(txtTelefono);
		mainLayout.addComponent(dfFecNac);
		mainLayout.addComponent(cbxGenero);
		
		
		mainLayout.addComponent(btnGuardar);
		mainLayout.addComponent(btnSalir);
		mainLayout.addComponent(btnAdd);
		
		mainLayout.addComponent(BotonLayout); 
		BotonLayout.addComponents(btnSalir, btnAdd);
		
	}















	private void guardarPersona() {
		
		Persona addPersona = new Persona();
		addPersona.setNombre(txtNombre.getValue());
		addPersona.setApellido(txtApellido.getValue());
		addPersona.setDireccion(txtDireccion.getValue());
		addPersona.setNumeroDocumento(txtNroDoc.getValue());
		addPersona.setRuc(txtRuc.getValue());
		addPersona.setTelefono(txtTelefono.getValue());
		Genero genero = new Genero();
		genero.setGenero(1);
		genero.setDescripcion("MASCULINO");
		addPersona.setGenero(genero);
		
		try {
			
			jpaPersona.create(addPersona);
			
		} catch (Exception e) {
			
			Notification.show(e.getMessage(), Notification.TYPE_ERROR_MESSAGE);
		}
		
		
		
	}















	private void crearComponentes() {

		txtNombre = new TextField();
		txtNombre.setCaption("NOMBRE");
		txtApellido = new TextField();
		txtApellido.setCaption("APELLIDO");
		txtDireccion = new TextField();
		txtDireccion.setCaption("DIRECCION");
		txtNroDoc = new TextField();
		txtNroDoc.setCaption("NUMERO DOCUMENTO");
		txtRuc = new TextField();
		txtRuc.setCaption("RUC");
		txtTelefono = new TextField();
		txtTelefono.setCaption("TELEFONO");
		dfFecNac = new DateField();
		dfFecNac.setCaption("FECHA NACIMIENTO");
		cbxGenero = new ComboBox();
		cbxGenero.setCaption("GENERO");
		
		
		
		btnSalir = new Button();
		btnSalir.setCaption("salir");
		btnAdd = new Button();
		
		
		btnAdd.setCaption("Add");
	
		
		
		
		
		btnGuardar = new Button();
		btnGuardar.setCaption("Guardar");
		
		

		
	}
	
	
	

}
