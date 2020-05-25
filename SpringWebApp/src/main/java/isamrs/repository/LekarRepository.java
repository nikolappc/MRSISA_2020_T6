package isamrs.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import isamrs.domain.Lekar;
import isamrs.domain.Ocena;
import isamrs.domain.Pacijent;
import isamrs.domain.Pregled;
public interface LekarRepository extends JpaRepository<Lekar, Integer>{
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
}
