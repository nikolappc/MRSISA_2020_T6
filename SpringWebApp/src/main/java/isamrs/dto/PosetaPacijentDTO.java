package isamrs.dto;

import isamrs.domain.Pacijent;
import isamrs.domain.Poseta;

public class PosetaPacijentDTO extends PosetaDTO {

    private PacijentDTO pacijent;

    public PosetaPacijentDTO(Poseta p, Pacijent pacijent) {
        super(p);
        this.pacijent = new PacijentDTO(pacijent);
    }

    public PacijentDTO getPacijent() {
        return pacijent;
    }

    public void setPacijent(PacijentDTO pacijent) {
        this.pacijent = pacijent;
    }

}
