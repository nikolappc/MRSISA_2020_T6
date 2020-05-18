package isamrs.dto;

import isamrs.domain.AdministratorKlinike;
import isamrs.domain.Adresa;
import isamrs.domain.TipKlinike;

import java.util.Collection;

public class KlinikaDTO {
	private Integer id;
	private String naziv;
	private Adresa adresa;
	private String opis;
	public TipKlinike tipKlinike;

	public Collection<AdministratorKlinike> getAdministratori() {
		return administratori;
	}

	public void setAdministratori(Collection<AdministratorKlinike> administratori) {
		this.administratori = administratori;
	}

	public Collection<AdministratorKlinike> administratori;
	
	
	public KlinikaDTO(Integer id,String naziv, Adresa adresa, String opis, TipKlinike tipKlinike) {
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
	public Adresa getAdresa() {
		return adresa;
	}
	public void setAdresa(Adresa adresa) {
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
