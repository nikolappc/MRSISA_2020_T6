package com.example.demo.repository;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import com.example.demo.domain.Pacijent;


@Repository
public class InMemoryPacijentRepository implements PacijentRepository {
	private final Pacijent ulogovani = new Pacijent("email@gmail.com", "12345678", "Pero", "Peric", "Despota Stefana 7", "Novi Sad", "Srbija", "054/111-111", "0");
	
	@Override
	public Pacijent getPacijent() {
		return this.ulogovani;
	}
	
}
