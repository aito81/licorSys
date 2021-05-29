package py.com.abiti.licorsys;

import javax.servlet.annotation.WebServlet;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import py.com.abiti.licorsys.event.LoginEvent;
import py.com.abiti.licorsys.event.LogoutEvent;
import py.com.abiti.licorsys.event.NavigationEvent;
import py.com.abiti.licorsys.util.UserUtil;
import py.com.abiti.licorsys.view.LoginView;
import py.com.abiti.licorsys.view.Main;



/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@Title("SISTEMA DE BODEGA")
public class LicorSysUI extends UI {

	private EventBus eventBus;
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
      
    	setupEventBus();
    	if (UserUtil.isLoggedIn()){
    			setContent(new Main());
    		} else {
    			setContent(new LoginView());
    	}
    	
    }
    
    private void setupEventBus() {
		eventBus = new EventBus();
		
		eventBus.register(this);
		
	}
	
	public static LicorSysUI getCurrent(){
		return (LicorSysUI) UI.getCurrent();
	}
	
	@Subscribe
	public void userLoggedIn(LoginEvent event){
		UserUtil.set(event.getUser());
		setContent(new Main());
	}
	
	public static EventBus getEventBus(){
		return getCurrent().eventBus;
		
	}
	public void navigateTo(NavigationEvent view) {
        getNavigator().navigateTo(view.getViewName());
    }
	
	@Subscribe
    public void logout(LogoutEvent logoutEvent) {
        // Don't invalidate the underlying HTTP session if you are using it for something else
        VaadinSession.getCurrent().getSession().invalidate();
        VaadinSession.getCurrent().close();
        Page.getCurrent().reload();

    } 

    @WebServlet(urlPatterns = "/*", name = "LicorSysUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = LicorSysUI.class, productionMode = false)
    public static class LicorSysUIServlet extends VaadinServlet {
    }
}
