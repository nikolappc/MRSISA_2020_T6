package isamrs.dto;

import isamrs.domain.Poseta;
import isamrs.domain.Sala;
import isamrs.domain.Termin;
import isamrs.domain.TipPosete;

public class PosetaDTO {
	private Integer id;
	private String opis;
	private Termin termin;
	private TipPoseteDTO tip;
	private Sala sala;
	private boolean potvrdjen;
	private boolean odradjen;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public TipPoseteDTO getTip() {
		return tip;
	}
	public void setTip(TipPoseteDTO tip) {
		this.tip = tip;
	}
	public Sala getSala() {
		return sala;
	}
	public void setSala(Sala sala) {
		this.sala = sala;
	}
	public PosetaDTO(Integer id, String opis, Termin termin, TipPosete tip, Sala sala) {
		super();
		this.id = id;
		this.opis = opis;
		this.termin = termin;
		this.tip = new TipPoseteDTO(tip);
		this.sala = sala;
	}
	
	public PosetaDTO() {}
	
	public PosetaDTO(Poseta p) {
		this(p.getId(), p.getOpis(), p.getTermin(), p.getTipPosete(), p.getSala());
		this.odradjen = p.isOdradjen();
		this.potvrdjen = p.isPotvrdjen();
	}

	public boolean isPotvrdjen() {
		return potvrdjen;
	}

	public void setPotvrdjen(boolean potvrdjen) {
		this.potvrdjen = potvrdjen;
	}

	public boolean isOdradjen() {
		return odradjen;
	}

	public void setOdradjen(boolean odradjen) {
		this.odradjen = odradjen;
	}
}
