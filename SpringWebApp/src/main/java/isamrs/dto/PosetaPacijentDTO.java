package isamrs.dto;

import isamrs.domain.Pacijent;
import isamrs.domain.Poseta;

public class PosetaPacijentDTO extends PosetaDTO {

    private String kategorija;


    private PacijentDTO pacijent;

    public PosetaPacijentDTO(Poseta p, String tip, Pacijent pacijent) {
        super(p);
        this.kategorija = tip;
        this.pacijent = new PacijentDTO(pacijent);
    }

    public PacijentDTO getPacijent() {
        return pacijent;
    }

    public void setPacijent(PacijentDTO pacijent) {
        this.pacijent = pacijent;
    }

    public String getKategorija() {
        return kategorija;
    }

    public void setKategorija(String kategorija) {
        this.kategorija = kategorija;
    }
}
