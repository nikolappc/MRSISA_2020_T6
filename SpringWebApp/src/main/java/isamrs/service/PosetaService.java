package isamrs.service;

import isamrs.domain.*;
import isamrs.dto.PregledDTO;
import isamrs.repository.LekarRepository;
import isamrs.repository.OperacijaRepository;
import isamrs.repository.PregledRepository;
import isamrs.repository.TipPoseteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedList;

@Service
public class PosetaService {

	@Autowired
	private PregledRepository pregledRepo;

	@Autowired
	private OperacijaRepository operacijaRepository;
	
	@Autowired
	private TipPoseteRepository tipRepo;
	
	@Autowired
	private LekarRepository lekarRepo;

	public Pregled create(Pregled pre) throws Exception{
		return pregledRepo.save(pre);
	}

	public Lekar findOneLekar(Integer id) {
		return lekarRepo.findById(id).orElseGet(null);
	}
	
	public TipPosete findTipByNaziv(String naziv) {
		return tipRepo.findByNaziv(naziv);
	}

	public Pacijent findPacijent(Integer idPregled) {
		Pregled pregled = pregledRepo.findById(idPregled).orElseGet(null);
		return pregled.getZdravstveniKarton().getPacijent();
	}

	public Collection<Poseta> findByLekar(Lekar lekar){
		Collection<Pregled> pregled = pregledRepo.findByLekar(lekar);
		Collection<Operacija> operacija = operacijaRepository.findByLekar(lekar);
		Collection<Poseta> posete = new LinkedList<>();
		posete.addAll(pregled);
		posete.addAll(operacija);
		return posete;
	}

}
