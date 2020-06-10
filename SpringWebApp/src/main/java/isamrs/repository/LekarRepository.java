package isamrs.repository;

import java.util.Collection;
import java.util.List;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import isamrs.domain.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

public interface LekarRepository extends JpaRepository<Lekar, Integer>{
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select l from Lekar l where l.id = :id")
	@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
	public Lekar findOneById(@Param("id")Integer id);
	
	
	@Query("SELECT l FROM Lekar l WHERE l.email = ?1")
	public Lekar findByEmail(String email);
	
	@Query("SELECT DISTINCT l FROM Lekar l INNER JOIN l.tipoviPoseta tp WHERE tp.naziv = ?2 AND l.klinika.id = ?1")
	public List<Lekar> lekariKlinikeTipa(int idKlinike, String nazivTipa);
	
	@Query("SELECT DISTINCT l FROM Lekar l WHERE l.klinika.id = ?1")
	public List<Lekar> lekariKlinike(int idKlinike);
	
	@Query("SELECT o FROM Lekar l JOIN l.ocena o WHERE l.id = ?2 AND o.pacijent.id = ?1")
	public List<Ocena> pacijentOcijenioLekara(int idPacijenta, int idLekara);
	
	@Query("SELECT o FROM Lekar l JOIN l.ocena o WHERE l.id = ?1 AND o.pacijent.id = ?2")
	public Ocena getOcenaPacijenta(int idLekara, int idPacijenta);
	
	//@Query("SELECT COUNT (pr.id) FROM Pregled pr JOIN pr.lekar l WHERE l.id = ?2 AND pr.zdravstveniKarton.pacijent.id = ?1")
	//@Query("SELECT COUNT (pr) FROM Pregled pr WHERE pr.lekar.id = ?2 AND pr.zdravstveniKarton.pacijent.id = ?1")
	@Query("SELECT pr FROM Pregled pr WHERE pr.lekar.id = ?2 AND pr.zdravstveniKarton.pacijent.id = ?1")
	public List<Pregled> pacijentPosjetioLekara(int idPacijenta, int idLekara);

	@Query("SELECT l FROM Lekar l JOIN l.klinika k WHERE k.id = ?1")
	Collection<Lekar> findByKlinika(Integer klinikaId);
}
