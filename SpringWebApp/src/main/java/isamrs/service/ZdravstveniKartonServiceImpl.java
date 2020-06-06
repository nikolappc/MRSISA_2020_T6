package isamrs.service;

import java.util.Collection;
import java.util.Optional;

import isamrs.domain.Klinika;
import isamrs.domain.ZdravstveniKarton;
import isamrs.exceptions.NotFoundException;
import isamrs.repository.ZdravstveniKartonRepository;

import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class ZdravstveniKartonServiceImpl implements Service<ZdravstveniKarton, Integer> {

	@Autowired
	private ZdravstveniKartonRepository zdravstveniKartonRepository;
	
	@Override
	public Collection<ZdravstveniKarton> findAll() {
		return zdravstveniKartonRepository.findAll();
	}

	@Override
	public ZdravstveniKarton findOne(Integer id) throws NotFoundException {
		Optional<ZdravstveniKarton> karton = zdravstveniKartonRepository.findById(id);
		if(karton.isPresent()) {
			return  karton.get();			
		}
		throw new NotFoundException();
	}

	@Override
	public ZdravstveniKarton create(ZdravstveniKarton t) {
		return zdravstveniKartonRepository.save(t);
	}

	@Override
	public ZdravstveniKarton update(Integer id, ZdravstveniKarton t) throws NotFoundException {
		zdravstveniKartonRepository.findById(id).orElseThrow(NotFoundException::new);
		return zdravstveniKartonRepository.save(t);
	}

	@Override
	public void delete(Integer id) {
		zdravstveniKartonRepository.deleteById(id);
	}

	public ZdravstveniKarton findByPacijent(Integer pacijentId) {

		return zdravstveniKartonRepository.findByPacijent(pacijentId);
	}
}
