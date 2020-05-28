package isamrs.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isamrs.domain.GodisnjiOdmor;
import isamrs.domain.Lekar;
import isamrs.repository.LekarRepository;
import isamrs.repository.OdmorRepository;

@Service
public class OdmorService {

	
	@Autowired
	private LekarRepository lekarRepo;
	
	@Autowired
	private OdmorRepository odmorRepo;
	
	
	public Collection<GodisnjiOdmor> findAll(Integer klinikaID) {
		// TODO Napraviti da uzme sve zahteve od lekara njegove klinike
		return null;
	}

	

	public GodisnjiOdmor create(GodisnjiOdmor t, Integer lekarID) {
		GodisnjiOdmor go = odmorRepo.save(t);
		Lekar l = lekarRepo.findById(lekarID).orElseGet(null);
		l.addGodisnjiOdmor(go);
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
