package isamrs.dto.post;

import isamrs.domain.Sala;
import isamrs.domain.Termin;
import isamrs.domain.TipPosete;
import isamrs.dto.TipPoseteDTO;

public class PosetaPostDTO {
    private Integer id;
    private Integer pacijentId;
    private String opis;
    private Termin termin;
    private TipPosete tipPosete;
    private Sala sala;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPacijentId() {
        return pacijentId;
    }

    public void setPacijentId(Integer pacijentId) {
        this.pacijentId = pacijentId;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Termin getTermin() {
        return termin;
    }

    public void setTermin(Termin termin) {
        this.termin = termin;
    }

    public TipPosete getTipPosete() {
        return tipPosete;
    }

    public void setTipPosete(TipPosete tipPosete) {
        this.tipPosete = tipPosete;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }
}
