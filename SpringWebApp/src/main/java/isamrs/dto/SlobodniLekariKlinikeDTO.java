package isamrs.dto;

import java.util.Date;

public class SlobodniLekariKlinikeDTO {
	private int idKlinike;
	private Date datum;
	private String nazivTipa;
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
	public SlobodniLekariKlinikeDTO(int idKlinike, Date datum, String nazivTipa) {
		super();
		this.idKlinike = idKlinike;
		this.datum = datum;
		this.nazivTipa = nazivTipa;
	}
	
	public SlobodniLekariKlinikeDTO() {}
}
