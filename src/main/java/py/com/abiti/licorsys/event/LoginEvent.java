package py.com.abiti.licorsys.event;

import py.com.abiti.licorsys.model.Usuario;

public class LoginEvent {
	private Usuario user;
	
	public LoginEvent(Usuario user){
		this.user = user;
	}
	
	public Usuario getUser(){
		return user;
	}

}
