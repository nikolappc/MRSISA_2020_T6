package isamrs.service;

import java.util.List;

import isamrs.domain.Pregled;
import isamrs.domain.Sala;

public interface PregledService extends Service<Pregled, Integer> {
	
	List<Pregled> findByKartonId(Integer id);
}
