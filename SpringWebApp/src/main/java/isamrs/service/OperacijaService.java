package isamrs.service;

import java.util.List;

import isamrs.domain.Operacija;

public interface OperacijaService extends Service<Operacija, Integer> {
	
	List<Operacija> findByKartonId(Integer id);
}
