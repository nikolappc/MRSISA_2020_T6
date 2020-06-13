package isamrs.dto;

import isamrs.domain.Termin;
import isamrs.domain.TipKlinike;

import javax.persistence.Column;

public class TipKlinikeDTO extends TipKlinike{

    public TipKlinikeDTO() {
    }

    public TipKlinikeDTO(TipKlinike t) {
        setId(t.getId());
        setTip(t.getTip());
    }

}
