package isamrs.dto;

import isamrs.domain.Adresa;

public class IzmjenaOsobeDTO {
	private int id;
	private String ime;
	private String prezime;
	private String password;
	private AdresaDTO adresa;
	private String brojTelefona;
	private String jbo;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public AdresaDTO getAdresa() {
		return adresa;
	}
	public void setAdresa(AdresaDTO adresa) {
		this.adresa = adresa;
	}
	public String getBrojTelefona() {
		return brojTelefona;
	}
	public void setBrojTelefona(String brojTelefona) {
		this.brojTelefona = brojTelefona;
	}
	public String getJbo() {
		return jbo;
	}
	public void setJbo(String jbo) {
		this.jbo = jbo;
	}
	public IzmjenaOsobeDTO(int id, String ime, String prezime, String password, AdresaDTO adresa, String brojTelefona,
			String jbo) {
		super();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.password = password;
		this.adresa = adresa;
		this.brojTelefona = brojTelefona;
		this.jbo = jbo;
	}
	
	public IzmjenaOsobeDTO() {}
}
