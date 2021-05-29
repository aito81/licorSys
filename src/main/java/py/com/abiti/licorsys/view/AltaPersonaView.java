package py.com.abiti.licorsys.view;

import org.w3c.dom.Text;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ItemCaptionGenerator;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import py.com.abiti.licorsys.LicorSysUI;
import py.com.abiti.licorsys.jpa.JpaGenero;
import py.com.abiti.licorsys.jpa.JpaPersona;
import py.com.abiti.licorsys.model.Genero;
import py.com.abiti.licorsys.model.Persona;
import py.com.abiti.licorsys.util.JpaUtil;
import py.com.abiti.licorsys.util.StringUtils;
import py.com.abiti.licorsys.util.ViewConfig;


@ViewConfig(uri = "alta", displayName = "persona")
public class AltaPersonaView extends CustomComponent implements View {
	
	private VerticalLayout mainLayout;
	
	private HorizontalLayout BotonLayout;
	
	private Button btnGuardar; 
	private Button btnSalir;
	
	private TextField txtNombre;
	private TextField txtApellido;
	private TextField txtDireccion;
	private TextField txtNroDoc;
	private TextField txtRuc;
	private TextField txtTelefono;
	private DateField dfFecNac;
	private ComboBox<Genero>  cbxGenero;
	
	private JpaPersona jpaPersona = new JpaPersona(JpaUtil.getEntityManagerFactory());
	private JpaGenero jpaGenero = new JpaGenero(JpaUtil.getEntityManagerFactory());
	 
	
	
	
	
	
	
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
		
		
		BotonLayout.addComponents(btnGuardar, btnSalir);
		
		mainLayout.addComponent(BotonLayout); 
		
		
	}















	private void guardarPersona() {
		
		if (controlardatos() == false) {
			
			return;
			
		}
		
		Persona addPersona = new Persona();
		addPersona.setNombre(txtNombre.getValue().toUpperCase());
		addPersona.setApellido(txtApellido.getValue().toUpperCase());
		addPersona.setDireccion(txtDireccion.getValue().toUpperCase());
		addPersona.setNumeroDocumento(txtNroDoc.getValue());
		addPersona.setRuc(txtRuc.getValue());
		addPersona.setTelefono(txtTelefono.getValue());
		addPersona.setGenero(cbxGenero.getValue());
		
		if (!dfFecNac.isEmpty()) {
			
			addPersona.setFechaNacimiento(StringUtils.convertirLocalDateToDate(dfFecNac.getValue()));
			
		}
		
		try {
			
			jpaPersona.create(addPersona);
			
			Notification.show("Persona agregada correctamente.");
			
		} catch (Exception e) {
			
			Notification.show(e.getMessage(), Notification.TYPE_ERROR_MESSAGE);
		}
		
		
		
	}















	private boolean controlardatos() {
		
		if (txtNombre.getValue().isEmpty()) {
			
			Notification.show("El nombre no puede quedar vacio", Notification.TYPE_WARNING_MESSAGE);
			txtNombre.focus();
			return false;
			
		}
		
		if (txtApellido.getValue().isEmpty()) {
			
			Notification.show("El apellido no puede quedar vacio", Notification.TYPE_WARNING_MESSAGE);
			txtApellido.focus();
			return false;
			
		}
		
		if (txtDireccion.getValue().isEmpty()) {
			
			Notification.show("La direccion no puede quedar vacia", Notification.TYPE_WARNING_MESSAGE);
			txtDireccion.focus();
			return false;
			
		}
		
		if (txtNroDoc.getValue().isEmpty()) {
			
			Notification.show("el numero de documento no puede quedar vacio", Notification.TYPE_ERROR_MESSAGE);
			txtNroDoc.focus();
			return false;
			
			
		}
		
		return true;
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
		cbxGenero = new ComboBox<Genero>();
		cbxGenero.setCaption("GENERO");
		cargarCombo();
		
		
		btnSalir = new Button();
		btnSalir.setCaption("salir");
		btnSalir.setStyleName(ValoTheme.BUTTON_DANGER);
		
		btnGuardar = new Button();
		btnGuardar.setCaption("Guardar");
		btnGuardar.setStyleName(ValoTheme.BUTTON_PRIMARY);
		
		
		
		btnSalir.addClickListener(e -> salir());
		
		

		
	}















	private void salir() {
		
		LicorSysUI.getCurrent().getNavigator().navigateTo("");
		
	}















	private void cargarCombo() {
		
		cbxGenero.setItems(jpaGenero.findGeneroEntities());
		cbxGenero.setEmptySelectionAllowed(false);
		cbxGenero.setItemCaptionGenerator(gen -> gen.getDescripcion());
		
	}
	
	
	

}
