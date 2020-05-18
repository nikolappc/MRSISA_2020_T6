package isamrs.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import isamrs.domain.Poseta;
import isamrs.domain.Pregled;
import isamrs.domain.Sala;


public interface SalaRepository extends JpaRepository<Sala, Integer>{
	
	
	
}
