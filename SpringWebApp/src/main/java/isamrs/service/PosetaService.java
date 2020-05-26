package isamrs.service;

import isamrs.domain.Lekar;
import isamrs.domain.MedicinskoOsoblje;
import isamrs.domain.Pacijent;
import isamrs.domain.Pregled;
import isamrs.domain.TipPosete;
import isamrs.dto.PregledDTO;
import isamrs.repository.LekarRepository;
import isamrs.repository.PregledRepository;
import isamrs.repository.TipPoseteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PosetaService {

	@Autowired
	private PregledRepository pregledRepo;
	
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

}
