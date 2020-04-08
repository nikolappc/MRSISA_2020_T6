package com.example.demo.domain;

import java.util.ArrayList;

public class Pacijent extends Osoba {
	
	private ZdravstveniKarton karton;
	private ArrayList<Posjeta> posjete;

	public Pacijent(Long id,String ime, String prezime, String email, String brojTelefona, String username, String password,
			String adresa, String jbo) {
		super(id, ime, prezime, email, brojTelefona, username, password, adresa, jbo);
		this.posjete = new ArrayList<Posjeta>();
		
	}

}