package isamrs.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import isamrs.domain.Lekar;
import isamrs.domain.Pregled;
import isamrs.domain.Sala;
import isamrs.repository.PacijentRepository;
import isamrs.repository.PregledRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class PregledServiceImpl implements PregledService {
	
	@Autowired
	private PregledRepository pregledRepository;

	@Override
	public Collection<Pregled> findAll() {

		return null;
	}

	@Override
	public Pregled findOne(Integer id) {
		return pregledRepository.findById(id).orElseGet(null);
	}

	@Override
	public Pregled create(Pregled t) {
		return pregledRepository.save(t);
	}

	


	@Override
	public Pregled update(Integer id, Pregled t) {
		Pregled pregledForUpdate = pregledRepository.findById(id).orElseGet(null);
		return pregledRepository.save(t);
	}

	@Override
	public void delete(Integer id) {
		pregledRepository.deleteById(id);
		
	}

	@Override
	public List<Pregled> findByKartonId(Integer id) {
		return pregledRepository.findByIdKarton(id);
	}


	@Override
	public Collection<Pregled> findPreglediKlinike(Integer idKlinike) {
		return pregledRepository.getPreglediKlinike(idKlinike);
	}
	
	@Override
	public Collection<Pregled> findPredefinisaniPreglediKlinike(Integer idKlinike) {
		return pregledRepository.getPredefinisaniPreglediKlinike(idKlinike);
	}
	
	@Override
	public List<Pregled> getBuduciPotvrdjeniPregledi(Integer id){
		Date now = new Date();
		System.out.println(id + "********");
		return pregledRepository.getBuduciPotvrdjeniPregledi(id, now);
	}
}
