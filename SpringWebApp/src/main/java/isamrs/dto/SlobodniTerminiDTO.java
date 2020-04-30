package isamrs.dto;

import isamrs.domain.Lekar;
import isamrs.domain.Pregled;
import isamrs.domain.Sala;
import isamrs.domain.Termin;
import isamrs.domain.TipPosete;

public class SlobodniTerminiDTO {
	
	private Lekar lekar;
	private Sala sala;
	private Termin termin;
	private TipPosete tipPosete;
	
	
	
	
	public SlobodniTerminiDTO() {
		super();
	}
	public Lekar getLekar() {
		return lekar;
	}
	public void setLekar(Lekar lekar) {
		this.lekar = lekar;
	}
	public Sala getSala() {
		return sala;
	}
	public void setSala(Sala sala) {
		this.sala = sala;
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
	
	public Pregled napraviPregled() {
		return new Pregled("", 0, termin, tipPosete, null, sala, null, lekar, null);
	}
}
