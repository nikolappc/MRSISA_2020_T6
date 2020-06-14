package isamrs.dto;

import java.util.ArrayList;

import isamrs.domain.Lekar;
import isamrs.domain.TipPosete;

public class LekarDTO {
	private Lekar lekar;
	private ArrayList<TipPosete> tipovi;
	
	
	
	public LekarDTO() {
		super();
	}
	public Lekar getLekar() {
		return lekar;
	}
	public void setLekar(Lekar lekar) {
		this.lekar = lekar;
	}
	public ArrayList<TipPosete> getTipovi() {
		return tipovi;
	}
	public void setTipovi(ArrayList<TipPosete> tipovi) {
		this.tipovi = tipovi;
	}
	
	
	
}
