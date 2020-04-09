package com.example.demo.repository;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import com.example.demo.domain.Pacijent;


@Repository
public class InMemoryPacijentRepository implements PacijentRepository {
	private final Pacijent ulogovani = new Pacijent(12345L, "Hari", "Poter", "potter@gmail.com", "054/111-111", "hpotter", "12345678", "Dijagon Aleja, London, UK", "000");
	
	@Override
	public Pacijent getPacijent() {
		return this.ulogovani;
	}
	
}
