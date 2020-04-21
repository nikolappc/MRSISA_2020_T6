package isamrs.repository;

import isamrs.domain.Pacijent;

public interface PacijentRepositoryCustom {
	Pacijent getPacijent();
	void izmijeniUlogovanog(Pacijent novi);
}
