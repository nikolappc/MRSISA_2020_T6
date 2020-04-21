package isamrs.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import isamrs.domain.TipKlinike;
import isamrs.exceptions.NotFoundException;
import isamrs.repository.TipKlinikeRepository;


@org.springframework.stereotype.Service
public class TipKlinikeService implements Service<TipKlinike, Long>{

	@Autowired
	private TipKlinikeRepository tipRepo;
	
	@Override
	public Collection<TipKlinike> findAll() {
		return tipRepo.findAll();
	}

	@Override
	public TipKlinike findOne(Long id) {
		Optional<TipKlinike> tip = tipRepo.findById(id);
		if(tip.isPresent()) {
			return tip.get();
		}
		throw new NotFoundException();
	}

	@Override
	public TipKlinike create(TipKlinike t) {
		return tipRepo.save(t);
	}

	@Override
	public TipKlinike update(Long id, TipKlinike t) {
		return tipRepo.save(t);
	}

	@Override
	public void delete(Long id) {
		tipRepo.deleteById(id);
	}

}
