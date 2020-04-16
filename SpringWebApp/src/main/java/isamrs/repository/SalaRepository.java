package isamrs.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import isamrs.domain.Sala;


public interface SalaRepository extends JpaRepository<Sala, Long>{
	
	
}
