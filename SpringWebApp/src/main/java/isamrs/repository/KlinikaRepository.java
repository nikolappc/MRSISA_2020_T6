package isamrs.repository;

import java.util.ArrayList;
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
import isamrs.domain.Ocena;
import isamrs.domain.Operacija;
import isamrs.domain.Pregled;
import isamrs.dto.KlinikaZaPacijentaDTO;
@Repository
public interface KlinikaRepository extends JpaRepository<Klinika, Integer>{
	
	@Query("SELECT k FROM Klinika k WHERE k.adresa.grad LIKE %?1% AND k.adresa.drzava LIKE %?2% "
			+ " AND (SELECT avg(o.vrednost) FROM Ocena o WHERE o MEMBER OF k.ocena) >= ?3 "
			+ "AND (SELECT count(st) FROM StavkaCenovnika st WHERE st.tipPosete.naziv = ?4 AND st MEMBER OF k.cenovnik.stavkaCenovnika) > 0")
	public List<Klinika> pretragaZakazivanje(String grad, String drzava, double ocjena, String nazivTipa);
	
	@Query("SELECT COUNT (o.id) FROM Klinika k JOIN k.ocena o WHERE k.id = ?2 AND o.pacijent.id = ?1")
	public Integer pacijentOcijenioKliniku(int idPacijenta, int idKlinike);
	
	@Query("SELECT o FROM Klinika k JOIN k.ocena o WHERE k.id = ?1 AND o.pacijent.id = ?2")
	public Ocena getOcenaPacijenta(int idKlinike, int idPacijenta);
	
	@Query("SELECT COUNT (pr.id) FROM Pregled pr WHERE pr.lekar.klinika.id = ?2 AND pr.zdravstveniKarton.pacijent.id = ?1")
	public Integer pacijentPosjetioKliniku(int idPacijenta, int idKlinike);


	@Query("SELECT k FROM Klinika k JOIN  k.lekari l JOIN l.operacije o WHERE o.id = ?1")
    Klinika findByOperacija(Integer id);

	@Query("SELECT k FROM Klinika k JOIN  k.lekari l WHERE l.id = ?1")
	Klinika findByLekar(Integer id);
	
	@Query("SELECT AVG(o.vrednost) FROM Klinika k JOIN k.ocena o WHERE k.id = ?1")
	public Double prosekKlinike(Integer id);
	
	@Query("SELECT date_trunc(?2, t.pocetak), count(*) FROM Klinika k JOIN k.pregledi p JOIN p.termin t "
			+ "WHERE k.id = ?1 group by 1 order by 1")
	public ArrayList<Object[]> dnevniIzvestaj(Integer id,String tip);
	
	@Query("SELECT date_trunc(?2, t.pocetak), count(*) FROM Klinika k JOIN k.operacije o JOIN o.termin t "
			+ "WHERE k.id = ?1 group by 1 order by 1")
	public ArrayList<Object[]> dnevniIzvestajOperacije(Integer id,String tip);
	
	@Query("SELECT SUM(sc.cena) FROM Klinika k JOIN k.pregledi p  JOIN k.cenovnik cen2 JOIN p.termin t JOIN p.tipPosete tip JOIN tip.stavkeCenovnika sc JOIN sc.cenovnik cen1 "
			+ "WHERE k.id = ?1 and t.pocetak >= ?2 and t.kraj <= ?3 and cen1.id = cen2.id")
	public Double preglediUIntervalu(Integer id,Date pocetak, Date kraj);

	
	@Query("SELECT SUM(sc.cena) FROM Klinika k JOIN k.operacije p  JOIN k.cenovnik cen2 JOIN p.termin t JOIN p.tipPosete tip JOIN tip.stavkeCenovnika sc JOIN sc.cenovnik cen1 "
			+ "WHERE k.id = ?1 and t.pocetak >= ?2 and t.kraj <= ?3 and cen1.id = cen2.id")
	public Double operacijeUIntervalu(Integer id,Date pocetak, Date kraj);
}
