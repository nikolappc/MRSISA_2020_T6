package isamrs.service;

import java.util.List;

import isamrs.domain.Pregled;

public interface PregledService extends Service<Pregled, Integer> {
	
	List<Pregled> findByKartonId(Integer id);
}
