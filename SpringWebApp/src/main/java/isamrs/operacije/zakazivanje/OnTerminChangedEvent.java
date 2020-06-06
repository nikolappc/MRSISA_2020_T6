package isamrs.operacije.zakazivanje;

import isamrs.domain.Operacija;
import isamrs.domain.Pacijent;
import isamrs.domain.Termin;
import org.springframework.context.ApplicationEvent;

public class OnTerminChangedEvent extends ApplicationEvent{
	
	private static final long serialVersionUID = 1L;
	private Pacijent user;
	private Termin termin;
	private Operacija operacija;
	public OnTerminChangedEvent(Pacijent user, Termin termin, Operacija operacija) {
		super(user);
		this.termin = termin;
		this.user = user;
		this.operacija = operacija;
	}
	public Pacijent getUser() {
		return user;
	}
	public void setUser(Pacijent user) {
		this.user = user;
	}

	public Termin getTermin() {
		return termin;
	}

	public void setTermin(Termin termin) {
		this.termin = termin;
	}

	public Operacija getOperacija() {
		return operacija;
	}

	public void setOperacija(Operacija operacija) {
		this.operacija = operacija;
	}
}
