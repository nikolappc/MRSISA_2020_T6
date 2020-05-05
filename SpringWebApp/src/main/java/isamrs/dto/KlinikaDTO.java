package isamrs.dto;

import isamrs.domain.TipKlinike;

public class KlinikaDTO {
	private Integer id;
	private String naziv;
	private String adresa;
	private String opis;
	public TipKlinike tipKlinike;
	
	
	
	public KlinikaDTO(Integer id,String naziv, String adresa, String opis, TipKlinike tipKlinike) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
		this.tipKlinike = tipKlinike;
	}
	
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getAdresa() {
		return adresa;
	}
	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	public TipKlinike getTipKlinike() {
		return tipKlinike;
	}
	public void setTipKlinike(TipKlinike tipKlinike) {
		this.tipKlinike = tipKlinike;
	}
	
	
}
