package isamrs.service;

import java.util.Collection;

import isamrs.domain.Lekar;

public interface LekarService {
	Collection<Lekar> findAll();

	Lekar findOne(Long id);

	Lekar create(Lekar lekar);

	Lekar update(Long id,Lekar lekar);

	void delete(Long id);
}
