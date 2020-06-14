package isamrs.domain;

import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;


import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "pacijent")
public class Pacijent extends Osoba {

	@Column(name = "allowed")
	private Boolean allowed;

	@Column(name = "responded")
	private Boolean responded;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_kartona")
	@JsonBackReference(value = "zdravstveniKartonReference")
	private ZdravstveniKarton zdravstveniKarton;

	@Transient
	public String tip = "PACIJENT";

	public Pacijent() {
		super();
		this.setTip("PACIJENT");
	}

	public Pacijent(String ime, String prezime, String password, String brojTelefona, String jbo, Adresa adresa,
			String email, Integer id, ZdravstveniKarton zdravstveniKarton) {
		super(ime, prezime, password, brojTelefona, jbo, adresa, email, id);
		this.zdravstveniKarton = zdravstveniKarton;
		this.setTip("PACIJENT");
	}
	
	public Pacijent(String ime, String prezime, String password, String brojTelefona, String jbo,
			String email, Integer id) {
		super(ime, prezime, password, brojTelefona, jbo, email, id);
	}

	public Pacijent(String ime, String prezime, String password, String brojTelefona, String jbo, Adresa adresa,
			String email, Integer id) {
		super(ime, prezime, password, brojTelefona, jbo, adresa, email, id);
		this.setTip("PACIJENT");
	}

	public ZdravstveniKarton getZdravstveniKarton() {
		return zdravstveniKarton;
	}

	public boolean isAllowed() {
		return allowed;
	}

	public void setAllowed(boolean allowed) {
		this.allowed = allowed;
	}

	public void setZdravstveniKarton(ZdravstveniKarton newZdravstveniKarton) {
		if (this.zdravstveniKarton == null || !this.zdravstveniKarton.equals(newZdravstveniKarton)) {
			if (this.zdravstveniKarton != null)
				this.zdravstveniKarton.setPacijent(null);
			this.zdravstveniKarton = newZdravstveniKarton;
			if (this.zdravstveniKarton != null)
				this.zdravstveniKarton.setPacijent(this);
		}
	}

	public Boolean getResponded() {
		return responded;
	}

	public void setResponded(Boolean responded) {
		this.responded = responded;
	}
	
	
}