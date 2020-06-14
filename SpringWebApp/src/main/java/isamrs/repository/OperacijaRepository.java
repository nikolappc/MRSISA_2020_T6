package isamrs.repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import isamrs.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OperacijaRepository extends JpaRepository<Operacija, Integer> {
	
	@Query("SELECT o FROM Operacija o WHERE o.zdravstveniKarton.id = ?1")
	public List<Operacija> findByIdKarton(Integer id);
	
	@Query("SELECT o FROM Operacija o JOIN o.zdravstveniKarton zk JOIN zk.pacijent p WHERE p.id = ?1 and o.sala != null")
	public List<Operacija> findByPacijent(Integer id);

	@Query("SELECT o FROM Operacija o WHERE o.sala = ?1")
	public List<Operacija> findBySala(Sala s);
	
	@Query("SELECT o FROM Operacija o WHERE o.tipPosete = ?1")
	public List<Operacija> findByTip(TipPosete tp);

	@Query("SELECT o FROM Operacija o JOIN o.lekari l WHERE l = ?1 and o.potvrdjen = true")
	public List<Operacija> findByLekar(Lekar l);

	@Query("SELECT o FROM Klinika k JOIN k.operacije o WHERE o.sala = null and k.id = ?1")
	public List<Operacija> findZahteve(Integer id);

	@Query("SELECT o FROM Operacija o WHERE o.sala = ?1 and o.termin.kraj >= ?2")
	public List<Operacija> findBySala(Sala sala, Date today);

	@Query("SELECT o FROM Operacija o JOIN o.lekari l WHERE l.id  = ?2 and o.zdravstveniKarton.id = ?1")
	public Collection<Operacija> checkKarton(Integer idKarton, Integer idLekar);
}
