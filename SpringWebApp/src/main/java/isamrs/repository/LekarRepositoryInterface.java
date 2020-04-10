package isamrs.repository;

import java.util.Collection;

import isamrs.domain.Lekar;
public interface LekarRepositoryInterface {
	Collection<Lekar> findAll();

	Lekar findOne(Long id);

	Lekar create(Lekar lekar);

	Lekar update(Lekar lekar);

	void delete(Long id);
}
