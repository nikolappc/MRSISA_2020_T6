package isamrs.repository;


import java.util.Collection;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import isamrs.domain.Klinika;
import isamrs.domain.Sala;


public interface SalaRepository extends JpaRepository<Sala, Integer>{
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select s from Sala s where s.id = :id")
	@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
	public Sala findOneById(@Param("id")Integer id);

	@Query("select s from Klinika k JOIN k.sala s where k = ?1")
	public Collection<Sala> findByKlinika(Klinika k);
	
}
