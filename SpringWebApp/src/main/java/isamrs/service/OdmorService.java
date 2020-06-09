package isamrs.service;

import java.util.ArrayList;
import java.util.Collection;

import isamrs.domain.*;
import isamrs.exceptions.NotFoundException;
import isamrs.repository.SestraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isamrs.dto.OdmorDTO;
import isamrs.repository.KlinikaRepository;
import isamrs.repository.LekarRepository;
import isamrs.repository.OdmorRepository;

@Service
public class OdmorService {

	
	@Autowired
	private SestraRepository sestraRepo;
	
	@Autowired
	private LekarRepository lekarRepo;
	
	@Autowired
	private OdmorRepository odmorRepo;
	
	@Autowired
	private KlinikaRepository klinikaRepo;
	
	
	public Collection<OdmorDTO> findAll(Integer klinikaID) {
		ArrayList<OdmorDTO> odmori = new ArrayList<OdmorDTO>();
		for(Object[] o : odmorRepo.zahteviLekari(klinikaID)) {
			OdmorDTO od = new OdmorDTO((GodisnjiOdmor) o[0],(MedicinskoOsoblje)  o[1]);
			odmori.add(od);
		}
		
		for(Object[] o : odmorRepo.zahteviSestre(klinikaID)) {
			OdmorDTO od = new OdmorDTO((GodisnjiOdmor) o[0],(MedicinskoOsoblje)  o[1]);
			odmori.add(od);
		}
		return odmori;
	}

	

	public GodisnjiOdmor create(GodisnjiOdmor t, Integer lekarID) throws NotFoundException {
		Lekar l = lekarRepo.findById(lekarID).orElseThrow(NotFoundException::new);
		l.addGodisnjiOdmor(t);
		GodisnjiOdmor go = odmorRepo.save(t);
		return go;
	}
	public GodisnjiOdmor createSestra(GodisnjiOdmor t, Integer sestraId) throws NotFoundException {
		MedicinskaSestra med = sestraRepo.findById(sestraId).orElseThrow(NotFoundException::new);
		med.addGodisnjiOdmor(t);
		return odmorRepo.save(t);
	}
	public GodisnjiOdmor update(Integer id, GodisnjiOdmor t) throws NotFoundException {
		if(t.isOdobren()) {
			GodisnjiOdmor go = odmorRepo.findById(id).orElseThrow(NotFoundException::new);
			go.setOdobren(true);
			return odmorRepo.save(go);
		}
		else {
			this.delete(id);
			return null;
		}
	}

	public void delete(Integer id) {
		odmorRepo.deleteById(id);
	}

	
}
