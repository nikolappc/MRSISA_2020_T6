package isamrs.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import isamrs.domain.Lek;
import isamrs.exceptions.NotFoundException;
import isamrs.repository.LekRepository;
@org.springframework.stereotype.Service
public class LekService implements Service<Lek, Integer>{

	@Autowired
	private LekRepository repo;
	
	@Override
	public Collection<Lek> findAll() {
		return repo.findAll();
	}

	@Override
	public Lek findOne(Integer id) {
		Optional<Lek> o = repo.findById((long)id);
		if(o.isPresent()) {
			return o.get();
		}
		throw new NotFoundException();
	}

	@Override
	public Lek create(Lek t) {
		return repo.save(t);
	}

	@Override
	public Lek update(Integer id, Lek t) {
		t.setSifraLeka(id);
		return repo.save(t);
	}

	@Override
	public void delete(Integer id) {
		repo.deleteById((long)id);
	}

}
