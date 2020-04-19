package isamrs.service;

import java.util.Collection;

import isamrs.domain.Klinika;
import isamrs.domain.Lekar;

public interface KlinikaService {
	Collection<Klinika> findAll();

	Klinika findOne(Long id);

	Klinika create(Klinika t);

	Klinika update(Long id,Klinika t);

	void delete(Long id);
}
