package isamrs.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import isamrs.domain.Pregled;
import isamrs.domain.Sala;
import isamrs.domain.TipPosete;

@Repository
public interface PregledRepository extends JpaRepository<Pregled, Integer> {

	@Query("SELECT p FROM Pregled p WHERE p.zdravstveniKarton.id = ?1")
	public List<Pregled> findByIdKarton(Integer id);

	@Query("SELECT p FROM Pregled p WHERE p.sala = ?1")
	public List<Pregled> findBySala(Sala sala);

	@Query("SELECT p FROM Pregled p WHERE p.tipPosete = ?1")
	public List<Pregled> findByTip(TipPosete tp);
}
