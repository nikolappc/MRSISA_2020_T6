package isamrs.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import isamrs.domain.GodisnjiOdmor;
import isamrs.dto.OdmorDTO;

@Repository
public interface OdmorRepository extends JpaRepository<GodisnjiOdmor, Integer>{

	@Query("SELECT od AS odmor,l AS mo FROM Lekar l JOIN l.godisnjiOdmor od WHERE l.klinika.id = ?1 and od.odobren = false")
	public ArrayList<Object[]> zahteviLekari(Integer id);
	
	@Query("SELECT od as odmor,l as mo FROM MedicinskaSestra l JOIN l.godisnjiOdmor od WHERE l.klinika.id = ?1 and od.odobren = false")
	public ArrayList<Object[]> zahteviSestre(Integer id);
}