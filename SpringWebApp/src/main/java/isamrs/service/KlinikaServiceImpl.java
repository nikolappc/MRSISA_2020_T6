package isamrs.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.OptBoolean;

import isamrs.domain.Klinika;
import isamrs.exceptions.NotFoundException;
import isamrs.repository.KlinikaRepository;

@org.springframework.stereotype.Service
public class KlinikaServiceImpl implements Service<Klinika, Integer>{

	@Autowired
	private KlinikaRepository repo;
	
	@Override
	public Collection<Klinika> findAll() {
		return repo.findAll();
	}

	@Override
	public Klinika findOne(Integer id) {
		Optional<Klinika> klinika = repo.findById(id);
		if(klinika.isPresent()) {
			return  klinika.get();			
		}
		throw new NotFoundException();
	}

	@Override
	public Klinika create(Klinika t) {
		return repo.save(t);
	}

	@Override
	public Klinika update(Integer id, Klinika t) {
		if(!repo.findById(id).isPresent()){
			throw new NotFoundException();
		}
		t.setId(id);
		return repo.save(t);
	}

	@Override
	public void delete(Integer id) {
		repo.deleteById(id);
	}
}
