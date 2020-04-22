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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ZdravstveniKarton findOne(Integer id) {
		Optional<ZdravstveniKarton> karton = zdravstveniKartonRepository.findById(id);
		if(karton.isPresent()) {
			return  karton.get();			
		}
		throw new NotFoundException();
	}

	@Override
	public ZdravstveniKarton create(ZdravstveniKarton t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ZdravstveniKarton update(Integer id, ZdravstveniKarton t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

}
