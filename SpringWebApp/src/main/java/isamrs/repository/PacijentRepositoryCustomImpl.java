package isamrs.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import isamrs.domain.Pacijent;

@Repository
public class PacijentRepositoryCustomImpl implements PacijentRepositoryCustom{
	private final Pacijent ulogovani = new Pacijent("Harry", "Potter", "harry12345", "065/823-115", "000", "Hogwarts", "harry@gmail.com", 1);
	//private final Pacijent ulogovani = new Pacijent("Ron", "Weasley", "ron12345", "065/823-115", "000", "Hogwarts", "ron@gmail.com", 2);

	@Override
	public Pacijent getPacijent() {
		return this.ulogovani;
	}
	
	@Override
	public void izmijeniUlogovanog(Pacijent novi) {
		this.ulogovani.setIme(novi.getIme());
		this.ulogovani.setPassword(novi.getPassword());
		this.ulogovani.setPrezime(novi.getPrezime());
		this.ulogovani.setAdresa(novi.getAdresa());
		this.ulogovani.setBrojTelefona(novi.getBrojTelefona());
		this.ulogovani.setJbo(novi.getJbo());
		this.ulogovani.setId(novi.getId());
	}
	
	

	
}
