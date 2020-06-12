package isamrs.service;

import isamrs.domain.Termin;
import isamrs.repository.PregledRepository;
import isamrs.repository.TerminRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TerminService {

	@Autowired
	private TerminRepository terminRepo;
	
	

	public Termin create(Termin pre) throws Exception{
		Termin postojeci = terminRepo.findByTermin(pre.getPocetak(), pre.getKraj());
		if(postojeci!=null){
			return postojeci;
		}
		return terminRepo.save(pre);
	}

}
