package isamrs.domain;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;


import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "pacijent")
public class Pacijent extends Osoba {
	
	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "id_kartona")
	@JsonBackReference
	private ZdravstveniKarton zdravstveniKarton;
	
	@Transient
	public String tip = "PACIJENT";

	public Pacijent() {
		super();
		this.setTip("PACIJENT");
	}

	public Pacijent(String ime, String prezime, String password, String brojTelefona, String jbo, String adresa,
			String email, Integer id, ZdravstveniKarton zdravstveniKarton) {
		super(ime, prezime, password, brojTelefona, jbo, adresa, email, id);
		this.zdravstveniKarton = zdravstveniKarton;
		this.setTip("PACIJENT");
	}

	public Pacijent(String ime, String prezime, String password, String brojTelefona, String jbo, String adresa,
			String email, Integer id) {
		super(ime, prezime, password, brojTelefona, jbo, adresa, email, id);
		this.setTip("PACIJENT");
	}

	public ZdravstveniKarton getZdravstveniKarton() {
		return zdravstveniKarton;
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

	
}