package isamrs.service;

import java.util.Collection;
import java.util.List;

import isamrs.domain.Lekar;
import isamrs.domain.Operacija;
import isamrs.domain.Sala;

public interface OperacijaService extends Service<Operacija, Integer> {
	
	List<Operacija> findByKartonId(Integer id);

    Collection<Operacija> findByLekar(Lekar lekar);

    Iterable<? extends Operacija> findBySala(Sala s);
}
