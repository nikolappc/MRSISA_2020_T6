package isamrs.repository;
import java.util.Collection;
import java.util.List;

import isamrs.domain.*;
import isamrs.dto.PregledDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PregledRepository extends JpaRepository<Pregled, Integer> {

	@Query("SELECT p FROM Pregled p WHERE p.zdravstveniKarton.id = ?1")
	public List<Pregled> findByIdKarton(Integer id);

	@Query("SELECT p FROM Pregled p WHERE p.sala = ?1")
	public List<Pregled> findBySala(Sala sala);

	@Query("SELECT p FROM Pregled p WHERE p.tipPosete = ?1")
	public List<Pregled> findByTip(TipPosete tp);

	@Query("SELECT p FROM Pregled p WHERE p.sala = null")
	public List<Pregled> findZahteve();
	
	
	@Query("SELECT p FROM Pregled p WHERE p.sala = null")
	public boolean checkSala(Sala sala);

	@Query("SELECT p FROM Klinika k JOIN k.pregledi p WHERE k.id = :id")
	public Collection<Pregled> getPreglediKlinike(@Param("id") Integer id);
	
	@Query("SELECT p FROM Klinika k JOIN k.pregledi p WHERE k.id = :id AND p.zdravstveniKarton = null")
	public Collection<Pregled> getPredefinisaniPreglediKlinike(@Param("id") Integer id);

	
	@Query("SELECT p FROM Pregled p WHERE p.lekar = ?1")
	public Collection<Pregled> findByLekar(Lekar l);

}
