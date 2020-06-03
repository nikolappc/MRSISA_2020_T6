package isamrs.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isamrs.domain.GodisnjiOdmor;
import isamrs.domain.Klinika;
import isamrs.domain.Lekar;
import isamrs.domain.MedicinskoOsoblje;
import isamrs.dto.OdmorDTO;
import isamrs.repository.KlinikaRepository;
import isamrs.repository.LekarRepository;
import isamrs.repository.OdmorRepository;

@Service
public class OdmorService {

	
	@Autowired
	private LekarRepository sestraRepo;
	
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

	

	public GodisnjiOdmor create(GodisnjiOdmor t, Integer lekarID) {
		Lekar l = lekarRepo.findById(lekarID).orElseGet(null);
		l.addGodisnjiOdmor(t);
		GodisnjiOdmor go = odmorRepo.save(t);
		return go;
	}

	public GodisnjiOdmor update(Integer id, GodisnjiOdmor t) {
		if(t.isOdobren()) {
			GodisnjiOdmor go = odmorRepo.findById(id).orElse(null);
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
