package isamrs.domain;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "administratori_klinike")
public class AdministratorKlinike extends Osoba {
	
	@Transient
	public String tip = "ADMIN_K";
	
	public AdministratorKlinike() {
		this.setTip("ADMIN_K");
	}

	public AdministratorKlinike(String ime, String prezime, String password, String brojTelefona, String jbo, String adresa,
			String email, Integer id) {
		super(ime, prezime, password, brojTelefona, jbo, adresa, email, id);
		this.setTip("ADMIN_K");
	}
	
}