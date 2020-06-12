package isamrs.operacije.zakazivanje;

import isamrs.domain.Operacija;
import isamrs.domain.Pacijent;
import isamrs.domain.Pregled;
import isamrs.domain.Termin;
import org.springframework.context.ApplicationEvent;

public class OnTerminChangedEventPregled extends ApplicationEvent{
	
	private static final long serialVersionUID = 1L;
	private Pacijent user;
	private Termin termin;
	private Pregled pregled;
	public OnTerminChangedEventPregled(Pacijent user, Termin termin, Pregled pregled) {
		super(user);
		this.termin = termin;
		this.user = user;
		this.pregled = pregled;
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
	public Pregled getPregled() {
		return pregled;
	}
	public void setPregled(Pregled pregled) {
		this.pregled = pregled;
	}

	
}
