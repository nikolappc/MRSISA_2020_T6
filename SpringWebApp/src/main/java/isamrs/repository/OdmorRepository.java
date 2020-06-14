package isamrs.repository;

import java.util.ArrayList;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import isamrs.domain.GodisnjiOdmor;
import isamrs.domain.MedicinskoOsoblje;
import isamrs.domain.Lekar;
import isamrs.domain.MedicinskaSestra;

@Repository
public interface OdmorRepository extends JpaRepository<GodisnjiOdmor, Integer>{

	@Query("SELECT od AS odmor,l AS mo FROM Lekar l JOIN l.godisnjiOdmor od WHERE l.klinika.id = ?1 and od.odobren = false")
	public ArrayList<Object[]> zahteviLekari(Integer id);
	
	@Query("SELECT od FROM Lekar l JOIN l.godisnjiOdmor od WHERE l.id = ?1 and od.odobren = true")
	public ArrayList<GodisnjiOdmor> odobreniLekari(Integer id);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT l FROM Lekar l JOIN l.godisnjiOdmor od WHERE od.id = ?1")
	@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
	public Lekar getLekar(Integer id);
	
	@Query("SELECT s FROM MedicinskaSestra s JOIN s.godisnjiOdmor od WHERE od.id = ?1")
	public MedicinskaSestra getSestra(Integer id);

	
	@Query("SELECT od as odmor,l as mo FROM MedicinskaSestra l JOIN l.godisnjiOdmor od WHERE l.klinika.id = ?1 and od.odobren = false")
	public ArrayList<Object[]> zahteviSestre(Integer id);

}
