package isamrs.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import isamrs.domain.Klinika;
import isamrs.repository.KlinikaRepository;

public class KlinikaService implements IService<Klinika>{

	@Autowired
	private KlinikaRepository repo;
	
	@Override
	public Collection<Klinika> findAll() {
		return repo.findAll();
	}

	@Override
	public Klinika findOne(Long id) {
		// TODO Auto-generated method stub
		return repo.findOne(id);
	}

	@Override
	public Klinika create(Klinika t) {
		// TODO Auto-generated method stub
		return repo.create(t);
	}

	@Override
	public Klinika update(Long id, Klinika t) {
		// TODO Auto-generated method stub
		return repo.update(t);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}
	
}
