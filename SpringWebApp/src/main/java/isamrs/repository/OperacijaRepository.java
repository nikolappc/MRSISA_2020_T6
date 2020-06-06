package isamrs.repository;

import java.util.List;

import isamrs.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OperacijaRepository extends JpaRepository<Operacija, Integer> {
	
	@Query("SELECT o FROM Operacija o WHERE o.zdravstveniKarton.id = ?1")
	public List<Operacija> findByIdKarton(Integer id);

	@Query("SELECT o FROM Operacija o WHERE o.sala = ?1")
	public List<Operacija> findBySala(Sala s);
	
	@Query("SELECT o FROM Operacija o WHERE o.tipPosete = ?1")
	public List<Operacija> findByTip(TipPosete tp);

	@Query("SELECT o FROM Operacija o JOIN o.lekari l WHERE l = ?1")
	public List<Operacija> findByLekar(Lekar l);

	@Query("SELECT o FROM Operacija o WHERE o.sala = null")
	public List<Operacija> findZahteve();}
