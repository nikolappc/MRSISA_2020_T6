package isamrs.repository;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import isamrs.domain.Pregled;
import isamrs.dto.PregledDTO;

@Repository
public interface PregledRepository extends JpaRepository<Pregled, Integer> {

	@Query("SELECT p FROM Pregled p WHERE p.zdravstveniKarton.id = ?1")
	public List<Pregled> findByIdKarton(Integer id);
}
