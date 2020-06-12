package isamrs.operacije.doktori;

import isamrs.domain.Lekar;
import isamrs.domain.Operacija;
import isamrs.domain.Pregled;
import isamrs.domain.Termin;
import org.springframework.context.ApplicationEvent;

public class OnDoktorDodatEventPregled extends ApplicationEvent {

    private final Pregled pregled;
    private Termin termin;

    public OnDoktorDodatEventPregled(Lekar lekar, Pregled  pregled, Termin termin) {
        super(lekar);
        this.pregled = pregled;
        this.termin = termin;
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

    
}
