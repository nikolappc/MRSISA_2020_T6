package isamrs.dto;

import java.util.Date;

public class PretragaKlinikeDTO {
	private Date datum;
	private String nazivTipa;
	private String grad;
	private String drzava;
	private double ocjena;
	public Date getDatum() {
		return datum;
	}
	public void setDatum(Date datum) {
		this.datum = datum;
	}
	public String getNazivTipa() {
		return nazivTipa;
	}
	public void setNazivTipa(String nazivTipa) {
		this.nazivTipa = nazivTipa;
	}
	public String getGrad() {
		return grad;
	}
	public void setGrad(String grad) {
		this.grad = grad;
	}
	public String getDrzava() {
		return drzava;
	}
	public void setDrzava(String drzava) {
		this.drzava = drzava;
	}
	public double getOcjena() {
		return ocjena;
	}
	public void setOcjena(double ocjena) {
		this.ocjena = ocjena;
	}
	public PretragaKlinikeDTO(Date datum, String nazivTipa, String grad, String drzava, double ocjena) {
		super();
		this.datum = datum;
		this.nazivTipa = nazivTipa;
		this.grad = grad;
		this.drzava = drzava;
		this.ocjena = ocjena;
	}
	
	public PretragaKlinikeDTO() {}
}
