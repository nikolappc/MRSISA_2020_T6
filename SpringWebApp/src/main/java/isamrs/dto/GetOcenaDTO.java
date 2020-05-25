package isamrs.dto;

public class GetOcenaDTO {
	private boolean mozeOcjenjivati;
	private double ocjena;
	public boolean isMozeOcjenjivati() {
		return mozeOcjenjivati;
	}
	public void setMozeOcjenjivati(boolean mozeOcjenjivati) {
		this.mozeOcjenjivati = mozeOcjenjivati;
	}
	public double getOcjena() {
		return ocjena;
	}
	public void setOcjena(double ocjena) {
		this.ocjena = ocjena;
	}
	public GetOcenaDTO(boolean mozeOcjenjivati, double ocjena) {
		super();
		this.mozeOcjenjivati = mozeOcjenjivati;
		this.ocjena = ocjena;
	}
	
	public GetOcenaDTO() {}
	
}
