package isamrs.repository;

import java.util.Collection;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import isamrs.domain.TipPosete;

@Repository
public interface TipPoseteRepository extends JpaRepository<TipPosete, Integer> {
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select t from TipPosete t where t.id = :id")
	@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
	public TipPosete findOneById(@Param("id")Integer id);
	
	
	@Query("SELECT t FROM TipPosete t WHERE t.tip = 1")
	public Collection<TipPosete> findPregledi();
	
	@Query("SELECT t FROM TipPosete t WHERE t.naziv = ?1")
	public TipPosete findByNaziv(String naziv);
	
	
	@Query("SELECT t FROM Klinika k JOIN k.tipPosete t WHERE k.id = ?1")
	public Collection<TipPosete> findByKlinika(Integer idKlinike);
}
