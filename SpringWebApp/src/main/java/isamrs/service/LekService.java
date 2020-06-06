package isamrs.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import isamrs.domain.Lek;
import isamrs.exceptions.NotFoundException;
import isamrs.repository.LekRepository;
@org.springframework.stereotype.Service
public class LekService implements Service<Lek, Long>{

	@Autowired
	private LekRepository repo;
	
	@Override
	public Collection<Lek> findAll() {
		return repo.findAll();
	}

	@Override
	public Lek findOne(Long id) throws NotFoundException {
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
	public Lek update(Long id, Lek t) throws NotFoundException {
		if(!repo.findById(id).isPresent()){
			throw new NotFoundException();
		}
		t.setSifraLeka(id);
		return repo.save(t);
	}

	@Override
	public void delete(Long id) {
		repo.deleteById(id);
	}

}
