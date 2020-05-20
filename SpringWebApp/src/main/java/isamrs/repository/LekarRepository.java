package isamrs.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import isamrs.domain.Lekar;
import isamrs.domain.Pacijent;
public interface LekarRepository extends JpaRepository<Lekar, Integer>{
	@Query("SELECT l FROM Lekar l WHERE l.email = ?1")
	public Lekar findByEmail(String email);
	
	@Query("SELECT DISTINCT l FROM Lekar l INNER JOIN l.tipoviPoseta tp WHERE tp.naziv = ?2 AND l.klinika.id = ?1")
	public List<Lekar> lekariKlinikeTipa(int idKlinike, String nazivTipa);
}
