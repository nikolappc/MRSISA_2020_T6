package isamrs.service;

import java.util.Collection;
import java.util.List;

import isamrs.domain.Lekar;
import isamrs.domain.Pregled;
import isamrs.domain.Sala;
import isamrs.dto.ZakazaniPregledDTO;

public interface PregledService extends Service<Pregled, Integer> {
	
	List<Pregled> findByKartonId(Integer id);

	public Collection<Pregled> findPreglediKlinike(Integer idKlinike);
	
	public Collection<Pregled> findPredefinisaniPreglediKlinike(Integer idKlinike);
	
	public List<ZakazaniPregledDTO> getBuduciPotvrdjeniPregledi(Integer id);

    Collection<Pregled> findByLekar(Lekar lekar);

	Iterable<? extends Pregled> findBySala(Sala s);
}
