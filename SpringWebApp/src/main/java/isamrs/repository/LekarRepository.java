package isamrs.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import isamrs.domain.Lekar;
import isamrs.domain.Pacijent;
public interface LekarRepository extends JpaRepository<Lekar, Integer>{
	@Query("SELECT l FROM Lekar l WHERE l.email = ?1")
	public Lekar findByEmail(String email);
}
