package isamrs.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isamrs.domain.Sala;
import isamrs.repository.SalaRepository;

@Service
public class SalaService {

	@Autowired
	private SalaRepository salaRepo;
	
	
	public Collection<Sala> findAll() {
		return salaRepo.findAll();
	}

	public Sala findOne(Long id) {
		return salaRepo.findById(id).orElseGet(null);
	}


	public Sala create(Sala sala) {
		Integer id = (int) this.findAll().size();
		sala.setId(id);
		return salaRepo.save(sala);
	}

	public Sala update(Long id,Sala sala) {
		Sala salaForUpdate = salaRepo.findById(id).orElseGet(null);
		//Ovde ce biti neka logika za update
		return salaRepo.save(sala);
	}

	
	public void delete(Long id) {
		salaRepo.deleteById(id);
	}

	
}
