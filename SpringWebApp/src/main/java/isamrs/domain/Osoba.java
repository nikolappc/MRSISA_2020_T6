package isamrs.domain;

import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import isamrs.dto.AdminKCDTO;
import isamrs.dto.OsobaDTO;


@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME, property = "tip")
@JsonSubTypes({ 
  @Type(value = AdministratorKlinickogCentra.class, name = "ADMIN_KC"), 
  @Type(value = AdministratorKlinike.class, name = "ADMIN_K"),
  @Type(value = MedicinskaSestra.class, name = "SESTRA"),
  @Type(value = Lekar.class, name = "LEKAR"),
  @Type(value = Pacijent.class, name = "PACIJENT"), 
})
@MappedSuperclass
public abstract class Osoba implements Serializable{

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

	@Column(name = "email", unique = true, nullable = false)
	private String email;
	
	@Transient
	private String tip;


	@OneToOne(cascade = {CascadeType.PERSIST})
	@JoinColumn(name = "id_adrese", referencedColumnName = "id")
	private Adresa adresa;

	
	public Osoba() {
	}

	public Osoba(String ime, String prezime, String password, String brojTelefona, String jbo, String email, Adresa adresa) {
		this.ime = ime;
		this.prezime = prezime;
		this.password = password;
		this.brojTelefona = brojTelefona;
		this.jbo = jbo;
		this.email = email;
		this.adresa = adresa;
	}

	public Osoba(OsobaDTO o) {
		this.ime = o.getIme();
		this.prezime = o.getPrezime();
		this.password = o.getPassword();
		this.brojTelefona = o.getBrojTelefona();
		this.jbo = o.getJbo();
		this.email = o.getEmail();
		this.adresa = o.getAdresa();
		this.id = o.getId();
		this.adresa = o.getAdresa();
	}

    public Adresa getAdresa() {
		return adresa;
	}

	public void setAdresa(Adresa adresa) {
		this.adresa = adresa;
	}

	public Osoba(String ime, String prezime, String password, String brojTelefona, String jbo, Adresa adresa,
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
	
	public Osoba(String ime, String prezime, String password, String brojTelefona, String jbo,
			String email, Integer id) {
		this.ime = ime;
		this.prezime = prezime;
		this.password = password;
		this.brojTelefona = brojTelefona;
		this.jbo = jbo;
		this.email = email;
		this.id = id;
	}

}
