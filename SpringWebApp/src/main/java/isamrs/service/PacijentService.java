package isamrs.service;

import java.util.Collection;

import isamrs.domain.Lekar;
import isamrs.domain.Pacijent;
import isamrs.domain.Pregled;
import isamrs.dto.PosetaDTO;
import isamrs.registracija.VerificationToken;


public interface PacijentService {
	/*Pacijent getPacijent();
	void izmijeniUlogovanog(Pacijent novi);
	*/
	Pacijent findOne(int id);
	Pacijent save(Pacijent pacijent);
	Pacijent findByEmail(String email);
	Pacijent create(Pacijent pacijent);
	
	void createVerificationToken(Pacijent user, String token);
	VerificationToken getVerificationToken(String token);
	
	Collection<Pacijent> findNotConfirmed();
	Collection<Pacijent> findAll();
	Collection<PosetaDTO> findBuduciPregled(Integer id);

}
