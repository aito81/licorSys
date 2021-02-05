package py.com.abiti.licorsys.util;

import com.vaadin.server.VaadinSession;

import py.com.abiti.licorsys.jpa.JpaUsuario;
import py.com.abiti.licorsys.model.Usuario;




public class UserUtil {
	
	private static final String KEY = "currentuser";
	private static JpaUsuario jpaUsuario = new JpaUsuario(JpaUtil.getEntityManagerFactory());
	
	public static void setUsuario(Usuario user) {
        VaadinSession.getCurrent().setAttribute(KEY, user);
	}
	
	public static Usuario getUsuario() {
    	return (Usuario) VaadinSession.getCurrent().getAttribute(KEY); 
    }
	
	public static void set(Usuario user) {
        VaadinSession.getCurrent().setAttribute(KEY, user);
    }
	
	public static boolean isLoggedIn() {
        return getUsuario() != null;
    }

}
