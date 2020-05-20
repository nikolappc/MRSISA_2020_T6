package isamrs.repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import isamrs.domain.Klinika;
import isamrs.domain.Lekar;
import isamrs.dto.KlinikaZaPacijentaDTO;
@Repository
public interface KlinikaRepository extends JpaRepository<Klinika, Integer>{
	
	//@Query("SELECT k FROM Klinika k WHERE k.id IN (SELECT l.id_klinike FROM Lekar l WHERE ")
	@Query("SELECT k FROM Klinika k WHERE k.adresa.grad LIKE %?1% AND k.adresa.drzava LIKE %?2% "
			+ " AND (SELECT avg(o.vrednost) FROM Ocena o WHERE o MEMBER OF k.ocena) >= ?3 "
			+ "AND (SELECT count(st) FROM StavkaCenovnika st WHERE st.tipPosete.naziv = ?4 AND st MEMBER OF k.cenovnik.stavkaCenovnika) > 0")
	public List<Klinika> pretragaZakazivanje(String grad, String drzava, double ocjena, String nazivTipa);
}
