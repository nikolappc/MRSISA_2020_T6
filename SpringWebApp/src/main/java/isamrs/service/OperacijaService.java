package isamrs.service;

import java.util.Collection;
import java.util.List;

import isamrs.domain.Lekar;
import isamrs.domain.Operacija;
import isamrs.domain.Sala;
import isamrs.dto.OperacijaDTO;
import isamrs.exceptions.NotFoundException;

public interface OperacijaService extends Service<Operacija, Integer> {
	
	List<Operacija> findByKartonId(Integer id);

    Collection<Operacija> findByLekar(Lekar lekar);

    Iterable<? extends Operacija> findBySala(Sala s);

	Operacija update(Integer id, OperacijaDTO pregled, Integer idLekar) throws NotFoundException;
}
