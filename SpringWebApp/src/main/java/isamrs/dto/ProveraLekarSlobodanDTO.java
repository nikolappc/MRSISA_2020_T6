package isamrs.dto;

import java.util.Date;

public class ProveraLekarSlobodanDTO {
	private int idLekara;
	private int idKlinike;
	private Date datum;
	private String nazivTipa;
	public int getIdLekara() {
		return idLekara;
	}
	public void setIdLekara(int idLekara) {
		this.idLekara = idLekara;
	}
	public int getIdKlinike() {
		return idKlinike;
	}
	public void setIdKlinike(int idKlinike) {
		this.idKlinike = idKlinike;
	}
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
	public ProveraLekarSlobodanDTO(int idLekara, int idKlinike, Date datum, String nazivTipa) {
		super();
		this.idLekara = idLekara;
		this.idKlinike = idKlinike;
		this.datum = datum;
		this.nazivTipa = nazivTipa;
	}
	
	public ProveraLekarSlobodanDTO() {}

}
