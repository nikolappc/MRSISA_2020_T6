package isamrs.repository;

import java.util.Collection;

import isamrs.domain.Klinika;
import isamrs.domain.Lekar;

public interface KlinikaRepositoryInterface {
	Collection<Klinika> findAll();

	Klinika findOne(Long id);

	Klinika create(Klinika klinika);

	Klinika update(Klinika klinika);

	void delete(Long id);
}
