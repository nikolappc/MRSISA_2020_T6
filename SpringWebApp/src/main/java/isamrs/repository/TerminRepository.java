package isamrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import isamrs.domain.Termin;

@Repository
public interface TerminRepository extends JpaRepository<Termin, Integer>{
}
