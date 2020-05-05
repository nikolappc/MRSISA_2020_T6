package isamrs.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isamrs.domain.Operacija;
import isamrs.domain.Pregled;
import isamrs.domain.Sala;
import isamrs.dto.SalaTerminiDTO;
import isamrs.dto.TerminDTO;
import isamrs.repository.OperacijaRepository;
import isamrs.repository.PregledRepository;
import isamrs.repository.SalaRepository;

@Service
public class SalaService {

	@Autowired
	private SalaRepository salaRepo;
	
	@Autowired
	private PregledRepository pregledRepo;
	
	@Autowired
	private OperacijaRepository operacijaRepo;
	
	public Collection<Sala> findAll() {
	
		return salaRepo.findAll();
	}
	
	
	public Collection<SalaTerminiDTO> findAllDTO(){
		ArrayList<SalaTerminiDTO> lista = new ArrayList<SalaTerminiDTO>();
		
		for (Sala s : salaRepo.findAll()) {
			ArrayList<TerminDTO> pregledi = new ArrayList<TerminDTO>();
			for(Pregled p : pregledRepo.findBySala(s)) {
				pregledi.add(new TerminDTO(p)); 
			}
			
			for(Operacija o : operacijaRepo.findBySala(s)) {
				pregledi.add(new TerminDTO(o));
			}
			
			lista.add(new SalaTerminiDTO(s, pregledi));
		}
		
		return lista;
	}

	public Sala findOne(Integer id) {
		return salaRepo.findById(id).orElseGet(null);
	}


	public Sala create(Sala sala) {
		return salaRepo.save(sala);
	}

	public Sala update(Integer id,Sala sala) throws Exception {
		Sala salaForUpdate = salaRepo.findById(id).orElseGet(null);
		
		
		//Ukoliko sala nema preglede i operacije izmeni je
		List<Pregled> pregledi = pregledRepo.findBySala(sala);
		if(pregledi.isEmpty()) {
			List<Operacija> operacije = operacijaRepo.findBySala(sala);
			if(operacije.isEmpty()) {

				return salaRepo.save(sala);
			}
			else
				throw new Exception();
		}
		else
			throw new Exception();
		
	}

	
	public void delete(Integer id) throws Exception {
		salaRepo.deleteById(id);
	}
	
}
