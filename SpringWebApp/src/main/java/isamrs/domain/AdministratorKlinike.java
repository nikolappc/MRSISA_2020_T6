package isamrs.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.*;

import javax.persistence.*;


@Entity
@Table(name = "administratori_klinike")
public class AdministratorKlinike extends Osoba {
	
	@Transient
	public String tip = "ADMIN_K";

	@ManyToOne
	@JoinColumn(name = "id_klinike", referencedColumnName = "id")
	@JsonBackReference
	Klinika klinika;


	public AdministratorKlinike() {
		this.setTip("ADMIN_K");
	}

	public AdministratorKlinike(String ime, String prezime, String password, String brojTelefona, String jbo, Adresa adresa,
			String email, Integer id) {
		super(ime, prezime, password, brojTelefona, jbo, adresa, email, id);
		this.setTip("ADMIN_K");
	}

	public AdministratorKlinike(String ime, String prezime, String password, String brojTelefona, String jbo, Adresa adresa, String email) {
		super(ime, prezime, password, brojTelefona, jbo, email, adresa);
	}

	@Override
	public String getTip() {
		return tip;
	}

	@Override
	public void setTip(String tip) {
		this.tip = tip;
	}

	public Klinika getKlinika() {
		return klinika;
	}

	public void setKlinika(Klinika klinika) {
		this.klinika = klinika;
	}
}