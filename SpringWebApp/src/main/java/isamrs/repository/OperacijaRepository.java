package isamrs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import isamrs.domain.Operacija;

@Repository
public interface OperacijaRepository extends JpaRepository<Operacija, Integer> {
	
	@Query("SELECT o FROM Operacija o WHERE o.zdravstveniKarton.id = ?1")
	public List<Operacija> findByIdKarton(Integer id);
}