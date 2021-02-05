package py.com.abiti.licorsys.event;

import py.com.abiti.licorsys.LicorSysUI;

public class EventBus {

	public static void register(final Object listener){
		LicorSysUI.getEventBus().register(listener);
	}
	
	public static void unregister(final Object listener){
		LicorSysUI.getEventBus().unregister(listener);
	}
	
	public static void post(final Object listener){
		LicorSysUI.getEventBus().post(listener);
	}
}
