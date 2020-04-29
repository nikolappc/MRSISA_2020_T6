package isamrs.domain;

import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;


//@Entity
//@Inheritance(strategy = TABLE_PER_CLASS)
@MappedSuperclass
public abstract class Osoba{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "ime", unique = false, nullable = false)
	private String ime;

	@Column(name = "prezime", unique = false, nullable = false)
	private String prezime;

	@Column(name = "password", unique = false, nullable = false)
	private String password;

	@Column(name = "brojTelefona", unique = false, nullable = false)
	private String brojTelefona;

	@Column(name = "jbo", unique = false, nullable = false)
	private String jbo;

	@Column(name = "adresa", unique = false, nullable = false)
	private String adresa;

	@Column(name = "email", unique = true, nullable = false)
	private String email;
	
	@Transient
	private String tip;
	
	
	public Osoba() {
	}

	public Osoba(String ime, String prezime, String password, String brojTelefona, String jbo, String adresa,
			String email, Integer id) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.password = password;
		this.brojTelefona = brojTelefona;
		this.jbo = jbo;
		this.adresa = adresa;
		this.email = email;
		this.id = id;
	}
	
	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
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

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adresa == null) ? 0 : adresa.hashCode());
		result = prime * result + ((brojTelefona == null) ? 0 : brojTelefona.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ime == null) ? 0 : ime.hashCode());
		result = prime * result + ((jbo == null) ? 0 : jbo.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((prezime == null) ? 0 : prezime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Osoba other = (Osoba) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Osoba [id=" + id + ", ime=" + ime + ", prezime=" + prezime + ", password=" + password
				+ ", brojTelefona=" + brojTelefona + ", jbo=" + jbo + ", adresa=" + adresa + ", email=" + email + "]";
	}
	

	
}
