package isamrs.dto;

import isamrs.domain.Lekar;

public class LekarOcena {
	private Lekar lekar;
	private double ocena;
	public Lekar getLekar() {
		return lekar;
	}
	public void setLekar(Lekar lekar) {
		this.lekar = lekar;
	}
	public double getOcena() {
		return ocena;
	}
	public void setOcena(double ocena) {
		this.ocena = ocena;
	}
	public LekarOcena(Lekar lekar, double ocena) {
		super();
		this.lekar = lekar;
		this.ocena = ocena;
	}
	public LekarOcena() {
		super();
	}
	
	
	
	
}
