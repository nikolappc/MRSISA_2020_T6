package isamrs.repository;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import isamrs.domain.Pacijent;


@Repository
public class InMemoryPacijentRepository implements PacijentRepository {
	private final Pacijent ulogovani = new Pacijent("Hari", "Poter", "12345678", "054/111-111", "0", "Dijagon Aleja, London, UK", "potter@gmail.com", 1);
	
	@Override
	public Pacijent getPacijent() {
		return this.ulogovani;
	}
	
}
