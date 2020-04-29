package isamrs.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isamrs.domain.Pacijent;
import isamrs.domain.ZdravstveniKarton;
import isamrs.exceptions.NotFoundException;
import isamrs.repository.PacijentRepository;


@Service
public class PacijentServiceImpl implements PacijentService {
	@Autowired
	private PacijentRepository pacijentRepository;

	/*@Override
	public Pacijent getPacijent() {
		Pacijent pacijent = pacijentRepository.getPacijent();
		return pacijent;
	}
	
	@Override
	public void izmijeniUlogovanog(Pacijent novi) {
		pacijentRepository.izmijeniUlogovanog(novi);
	}*/
	
	@Override
	public Pacijent findOne(int id) {
		return pacijentRepository.findById(id).orElse(null);
	}
	
	@Override
	public Pacijent save(Pacijent pacijent) {
		return pacijentRepository.save(pacijent);
	}
	
	@Override
	public Pacijent findByEmail(String email) {
		return pacijentRepository.findByEmail(email);
	}
}
