package isamrs.service;

import java.util.Collection;
import java.util.List;

import isamrs.domain.Lekar;
import isamrs.domain.Pregled;
import isamrs.domain.Sala;

public interface PregledService extends Service<Pregled, Integer> {
	
	List<Pregled> findByKartonId(Integer id);

	public Collection<Pregled> findPreglediKlinike(Integer idKlinike);
	
	public Collection<Pregled> findPredefinisaniPreglediKlinike(Integer idKlinike);
	
	public List<Pregled> getBuduciPotvrdjeniPregledi(Integer id);

    Collection<Pregled> findByLekar(Lekar lekar);

	Iterable<? extends Pregled> findBySala(Sala s);
}
