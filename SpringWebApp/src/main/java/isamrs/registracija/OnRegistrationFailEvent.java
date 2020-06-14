package isamrs.registracija;

import org.springframework.context.ApplicationEvent;

import isamrs.domain.Pacijent;

public class OnRegistrationFailEvent extends ApplicationEvent{
	
	private static final long serialVersionUID = 1L;
	private String appUrl;
	private Pacijent user;
	private String opis;

	public OnRegistrationFailEvent(Pacijent user, String appUrl, String opis) {
		super(user);
		this.user = user;
		this.appUrl = appUrl;
		this.opis = opis;
	}
	public String getAppUrl() {
		return appUrl;
	}
	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}
	public Pacijent getUser() {
		return user;
	}
	public void setUser(Pacijent user) {
		this.user = user;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}
}
