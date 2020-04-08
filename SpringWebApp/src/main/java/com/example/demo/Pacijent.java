package com.example.demo;

public class Pacijent {
	private String email;
	private String lozinka;
	private String ime;
	private String prezime;
	private String adresa;
	private String grad;
	private String drzava;
	private String telefon;
	private String osiguranik;

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}
	
	public Pacijent(String email, String lozinka, String ime, String prezime, String adresa, String grad, String drzava,
			String telefon, String osiguranik) {
		super();
		this.email = email;
		this.lozinka = lozinka;
		this.ime = ime;
		this.prezime = prezime;
		this.adresa = adresa;
		this.grad = grad;
		this.drzava = drzava;
		this.telefon = telefon;
		this.osiguranik = osiguranik;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
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

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getOsiguranik() {
		return osiguranik;
	}

	public void setOsiguranik(String osiguranik) {
		this.osiguranik = osiguranik;
	}

	public Pacijent() {}
}
