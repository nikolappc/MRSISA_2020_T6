package isamrs.dto;

import javax.persistence.Column;

import isamrs.domain.Adresa;
import isamrs.domain.Osoba;

public class OsobaDTO {

	private Integer id;
	private String ime;
	private String prezime;
	private String password;
	private String brojTelefona;
	private String jbo;
	private Adresa adresa;
	private String email;
	private String tip;
	
	public OsobaDTO() {
		
	}
	
	
	public OsobaDTO(Osoba o) {
		this.ime = o.getIme();
		this.prezime = o.getPrezime();
		this.password = o.getPassword();
		this.brojTelefona = o.getBrojTelefona();
		this.jbo = o.getJbo();
		this.adresa = o.getAdresa();
		this.email = o.getEmail();
		this.id = o.getId();
		this.tip = o.getTip();
	}
	
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	public Adresa getAdresa() {
		return adresa;
	}
	public void setAdresa(Adresa adresa) {
		this.adresa = adresa;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	
	
}
