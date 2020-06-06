package isamrs.operacije.doktori;

import isamrs.domain.GodisnjiOdmor;
import isamrs.domain.Lekar;
import isamrs.domain.Operacija;
import isamrs.domain.Termin;
import org.springframework.context.ApplicationEvent;

public class OnDoktorDodatEvent extends ApplicationEvent {

    private final Operacija operacija;
    private Termin termin;

    public OnDoktorDodatEvent(Lekar lekar, Operacija o, Termin termin) {
        super(lekar);
        this.operacija = o;
        this.termin = termin;
    }

    public Operacija getOperacija() {
        return operacija;
    }

    public Termin getTermin() {
        return termin;
    }
}
