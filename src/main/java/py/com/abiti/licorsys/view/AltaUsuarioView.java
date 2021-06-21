package py.com.abiti.licorsys.view;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import py.com.abiti.licorsys.LicorSysUI;
import py.com.abiti.licorsys.jpa.JpaPersona;
import py.com.abiti.licorsys.jpa.JpaUsuario;
import py.com.abiti.licorsys.model.Persona;
import py.com.abiti.licorsys.model.Usuario;
import py.com.abiti.licorsys.util.JpaUtil;
import py.com.abiti.licorsys.util.ViewConfig;


@ViewConfig(uri = "altaUsuario", displayName = "usuario")
public class AltaUsuarioView  extends CustomComponent implements View {
	
	private VerticalLayout mainLayout;
	
	private HorizontalLayout botonLayout;
	
	private Button btnGuardar; 
	private Button btnSalir;
	
	private TextField txtAlias;
	private PasswordField txtClave;
	private PasswordField txtRepetir;
	
	private ComboBox<Persona> cbxPersona;
	private JpaPersona jpaPersona = new JpaPersona(JpaUtil.getEntityManagerFactory());
	private JpaUsuario jpaUsuario = new JpaUsuario(JpaUtil.getEntityManagerFactory());
	
	
	
	
	public AltaUsuarioView() {
		
		mainLayout = new VerticalLayout();
		botonLayout = new HorizontalLayout();
		
		setCompositionRoot(mainLayout);
		
		crearComponentes(); 
		
		btnGuardar.addClickListener(e -> guardarUsuario() );
		btnSalir.addClickListener(e -> salir());
		
		mostrarComponentes();
	}





	private void mostrarComponentes() {
		mainLayout.addComponent(txtAlias);
		mainLayout.addComponent(txtClave);
		mainLayout.addComponent(txtRepetir);
		mainLayout.addComponent(cbxPersona);
		
		
		botonLayout.addComponents(btnGuardar, btnSalir);
		mainLayout.addComponent(botonLayout);
		
	}





	private void guardarUsuario() {
		
		if (controlarDatos()== false) {
			
			return;
			
		}
		
		if (! (txtRepetir.getValue().equals(txtClave.getValue())) ) {
			Notification.show("Las contraseñas no coinciden" , Type.WARNING_MESSAGE);
			txtClave.clear();
			txtRepetir.clear();
			return;
		}
		
		if (txtClave.getValue().length()<8) {
			Notification.show("La contraseña debe tener al menos 8 caracteres" , Type.WARNING_MESSAGE);
			txtClave.clear();
			txtClave.focus();
			txtRepetir.clear();
		}
		
		
		
		
		Usuario addUsuario = new Usuario();
		addUsuario.setAlias(txtAlias.getValue().toUpperCase());
		addUsuario.setClave(txtClave.getValue().toUpperCase());
		addUsuario.setPersona(cbxPersona.getValue());
		addUsuario.setRepetir(txtRepetir.getValue());
		try {
			jpaUsuario.create(addUsuario);
			
			Notification.show("Persona agregada correctamente");
			
			limpiarCampos();
			
		}catch (Exception e) {
			
			Notification.show(e.getMessage(), Notification.TYPE_ERROR_MESSAGE);
		}
		
		
	}
	
	
	




	private void limpiarCampos() {
		txtAlias.clear();
		txtClave.clear();
		cbxPersona.clear();
		txtRepetir.clear();
		
	}





	private boolean controlarDatos() {
		
		if (txtAlias.getValue().isEmpty()) {
			Notification.show("No puede quedar Vacio" , Notification.TYPE_WARNING_MESSAGE);
			txtAlias.focus();
			return false;
			
		}
		
		if (txtClave.getValue().isEmpty()) {
			Notification.show("El espacio no puede quedar vacio" , Notification.TYPE_WARNING_MESSAGE);
			txtClave.focus();
			return false;
			
		}
		
		if (cbxPersona.getValue() == null) {
			Notification.show("No puede quedar vacio" , Notification.TYPE_WARNING_MESSAGE);
			cbxPersona.focus();
			return false;
			
		}
		
		if (txtRepetir.getValue().isEmpty()) {
			Notification.show("Las contraseñas no coinciden" , Notification.TYPE_WARNING_MESSAGE);
		   txtRepetir.focus();
		   return false;
		}
		
		return true;
		
		
		
	}





	private void crearComponentes() {
		txtAlias = new TextField();
		txtAlias.setCaption("ALIAS");
		txtRepetir = new PasswordField();
		txtRepetir.setCaption("REPETIR CLAVE");
		
		txtClave = new PasswordField();
		txtClave.setCaption("CLAVE");
		cbxPersona = new ComboBox<Persona>();
		btnGuardar = new Button();
		cbxPersona.setCaption("PERSONA");
		cargarCombo();
		
		
		btnSalir = new Button();
		btnSalir.setCaption("Salir");
		btnSalir.setStyleName(ValoTheme.BUTTON_DANGER);
		
		btnGuardar = new Button();
		btnGuardar.setCaption("Guardar");
		btnGuardar.setStyleName(ValoTheme.BUTTON_PRIMARY);
		
		
		
		
	
		
	}





	private void salir() {
		
		LicorSysUI.getCurrent().getNavigator().navigateTo("");
	}





	private void cargarCombo() {
		cbxPersona.setItems(jpaPersona.findPersonaEntities());
		cbxPersona.setEmptySelectionAllowed(false);
		cbxPersona.setItemCaptionGenerator(gen -> nombre(gen) );
	}





	private String nombre(Persona per) {

		String persona ;
		persona = per.getNombre() + " " + per.getApellido();

		return persona;
	}
	
	
	
}


	


;