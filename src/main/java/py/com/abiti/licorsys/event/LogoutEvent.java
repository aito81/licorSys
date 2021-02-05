package py.com.abiti.licorsys.event;

import com.vaadin.ui.UI;

public class LogoutEvent {
	public LogoutEvent(){
		UI.getCurrent().getPage().setUriFragment("");
	}
}
