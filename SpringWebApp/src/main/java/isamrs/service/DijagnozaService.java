package isamrs.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import isamrs.domain.Dijagnoza;
import isamrs.domain.Lek;
import isamrs.exceptions.NotFoundException;
import isamrs.repository.DijagnozaRepository;
import isamrs.repository.LekRepository;

@org.springframework.stereotype.Service
public class DijagnozaService implements Service<Dijagnoza, Integer>{

	@Autowired
	private DijagnozaRepository repo;
	
	@Override
	public Collection<Dijagnoza> findAll() {
		return repo.findAll();
	}

	@Override
	public Dijagnoza findOne(Integer id) {
		Optional<Dijagnoza> o = repo.findById(id);
		if(o.isPresent()) {
			return o.get();
		}
		throw new NotFoundException();
	}

	@Override
	public Dijagnoza create(Dijagnoza t) {
		return repo.save(t);
	}

	@Override
	public Dijagnoza update(Integer id, Dijagnoza t) {
		t.setSifraDijagnoze(id);
		return repo.save(t);
	}

	@Override
	public void delete(Integer id) {
		repo.deleteById(id);
	}
	
}
