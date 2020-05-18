package isamrs.service;

import java.util.Collection;
import java.util.List;

import isamrs.domain.Operacija;
import isamrs.repository.OperacijaRepository;
import isamrs.repository.PacijentRepository;
import isamrs.repository.PregledRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class OperacijaServiceImpl implements OperacijaService {
	
	@Autowired
	private OperacijaRepository operacijaRepository;

	@Override
	public Collection<Operacija> findAll() {

		return null;
	}

	@Override
	public Operacija findOne(Integer id) {

		return null;
	}

	@Override
	public Operacija create(Operacija t) {

		return null;
	}

	@Override
	public Operacija update(Integer id, Operacija t) {

		return null;
	}

	@Override
	public void delete(Integer id) {

		
	}

	@Override
	public List<Operacija> findByKartonId(Integer id) {
		return operacijaRepository.findByIdKarton(id);
	}
	

}
