package isamrs.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isamrs.domain.Pregled;
import isamrs.domain.Sala;
import isamrs.dto.SalaTerminiDTO;
import isamrs.repository.PregledRepository;
import isamrs.repository.SalaRepository;
import net.bytebuddy.implementation.bytecode.Throw;

@Service
public class SalaService {

	@Autowired
	private SalaRepository salaRepo;
	
	@Autowired
	private PregledRepository pregledRepo;
	
	public Collection<Sala> findAll() {
		
		return salaRepo.findAll();
	}

	public Sala findOne(Integer id) {
		return salaRepo.findById(id).orElseGet(null);
	}


	public Sala create(Sala sala) {
		return salaRepo.save(sala);
	}

	public Sala update(Integer id,Sala sala) {
		Sala salaForUpdate = salaRepo.findById(id).orElseGet(null);
		//Ovde ce biti neka logika za update
		return salaRepo.save(sala);
	}

	
	public void delete(Integer id) throws Exception {
		salaRepo.deleteById(id);			
		
	}
	
}
