/***********************************************************************
 * Module:  AdministratorKlinickogCentra.java
 * Author:  Dusan
 * Purpose: Defines the Class AdministratorKlinickogCentra
 ***********************************************************************/

package isamrs.domain;

import isamrs.dto.AdminKCDTO;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "administratori_klinickog_centra")
public class AdministratorKlinickogCentra extends Osoba {
	
	@Transient
	public String tip = "ADMIN_KC";
	
	public AdministratorKlinickogCentra() {
		this.setTip("ADMIN_KC");
	}

	public AdministratorKlinickogCentra(String ime, String prezime, String password, String brojTelefona, String jbo, Adresa adresa,
			String email, Integer id) {
		super(ime, prezime, password, brojTelefona, jbo, adresa, email, id);
		this.setTip("ADMIN_KC");
	}

	public AdministratorKlinickogCentra(AdminKCDTO admin) {
		super(admin);
		this.setTip("ADMIN_KC");

	}
}