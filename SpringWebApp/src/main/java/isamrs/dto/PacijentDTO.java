package isamrs.dto;

import isamrs.domain.Pacijent;

public class PacijentDTO extends OsobaDTO{

    private ZdravstveniKartonDTO zdravstveniKarton;

    public PacijentDTO(Pacijent pacijent) {
        super(pacijent);
    }

    public ZdravstveniKartonDTO getZdravstveniKarton() {
        return zdravstveniKarton;
    }

    public void setZdravstveniKarton(ZdravstveniKartonDTO zdravstveniKarton) {
        this.zdravstveniKarton = zdravstveniKarton;
    }


}
