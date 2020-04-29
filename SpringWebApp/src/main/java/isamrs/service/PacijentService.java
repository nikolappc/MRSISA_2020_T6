package isamrs.service;

import java.util.Collection;

import isamrs.domain.Pacijent;


public interface PacijentService {
	/*Pacijent getPacijent();
	void izmijeniUlogovanog(Pacijent novi);
	*/
	Pacijent findOne(int id);
	Pacijent save(Pacijent pacijent);
	Pacijent findByEmail(String email);
}
