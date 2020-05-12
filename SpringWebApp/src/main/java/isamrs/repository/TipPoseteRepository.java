package isamrs.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import isamrs.domain.TipPosete;

@Repository
public interface TipPoseteRepository extends JpaRepository<TipPosete, Integer> {
	@Query("SELECT t FROM TipPosete t WHERE t.tip = 1")
	public Collection<TipPosete> findPregledi();
	
	@Query("SELECT t FROM TipPosete t WHERE t.naziv = ?1")
	public TipPosete findByNaziv(String naziv);
}
