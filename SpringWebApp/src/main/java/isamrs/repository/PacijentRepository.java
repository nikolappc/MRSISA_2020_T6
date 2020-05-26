package isamrs.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import isamrs.domain.Pacijent;
import isamrs.domain.Pregled;

@Repository
public interface PacijentRepository extends JpaRepository<Pacijent, Integer>{
	@Query("SELECT p FROM Pacijent p WHERE p.email = ?1")
	public Pacijent findByEmail(String email);
	
	@Query("SELECT p from Pacijent p WHERE p.responded = false")
	public Collection<Pacijent> findNotConfirmed();
	
	@Query("SELECT p from Pacijent p WHERE p.zdravstveniKarton.id = ?1")
	public Pacijent findByKarton(Integer idKartona);

}
