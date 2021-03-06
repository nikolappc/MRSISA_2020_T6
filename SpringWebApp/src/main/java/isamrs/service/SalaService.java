package isamrs.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import isamrs.domain.AdministratorKlinike;
import isamrs.domain.Klinika;
import isamrs.domain.Operacija;
import isamrs.domain.Pregled;
import isamrs.domain.Sala;
import isamrs.dto.SalaTerminiDTO;
import isamrs.dto.TerminDTO;
import isamrs.repository.AdministratorKlinikeRepository;
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
	
	@Autowired
	private AdministratorKlinikeRepository adminRepo;
	
	public Collection<Sala> findAll(Integer idAdmina) {
		AdministratorKlinike ak;
		try {
			ak = adminRepo.findById(idAdmina).get();			
		} catch (Exception e) {
			return null;
		}
		ak.getKlinika().getSala();
		return ak.getKlinika().getSala();
	}
	
	
	public Collection<SalaTerminiDTO> findAllDTO(Integer idAdmina){
		ArrayList<SalaTerminiDTO> lista = new ArrayList<SalaTerminiDTO>();
		
		for (Sala s : this.findAll(idAdmina)) {
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

	@Transactional(readOnly = false)
	public Sala create(Sala sala, Integer idAdmina) {
		AdministratorKlinike ak;
		try {
			ak = adminRepo.findById(idAdmina).get();			
		} catch (Exception e) {
			return null;
		}
		Sala s = salaRepo.save(sala);
		ak.getKlinika().getSala().add(s);
		return s;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Sala update(Integer id,Sala sala) throws Exception {
		Sala salaForUpdate = salaRepo.findById(id).orElseGet(null);
		
		if(salaForUpdate == null)
			return null;
		//Ukoliko sala nema preglede i operacije izmeni je
		Date today = new Date();
		List<Pregled> pregledi = pregledRepo.findBySala(sala,today);
		if(pregledi.isEmpty()) {
			List<Operacija> operacije = operacijaRepo.findBySala(sala,today);
			if(operacije.isEmpty()) {
				salaForUpdate.setNaziv(sala.getNaziv());
				return salaRepo.save(salaForUpdate);
			}
			else
				throw new Exception();
		}
		else
			throw new Exception();
		
	}

	@Transactional(readOnly = false,  propagation = Propagation.REQUIRES_NEW)
	public void delete(Integer id) throws Exception {
		Date today = new Date();
		Sala s = salaRepo.findById(id).orElseGet(null);
		if (pregledRepo.findBySala(s,today).size() > 0)
			throw new Exception();
		if (operacijaRepo.findBySala(s,today).size() > 0)
			throw new Exception();
		
		salaRepo.deleteById(id);
	}


	public Collection<Sala> findByKlinika(Klinika k) {
		return salaRepo.findByKlinika(k);
	}
	
}
