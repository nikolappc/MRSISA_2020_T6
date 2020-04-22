package isamrs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import isamrs.domain.Lekar;
import isamrs.domain.Pregled;
import isamrs.domain.Sala;
import isamrs.dto.SalaTerminiDTO;

public interface PregledRepository extends JpaRepository<Pregled, Integer> {
	
	
	
//	
//	@Query("SELECT sala,lekar FROM Pregled p where p.sala = ?1")
//	List<SalaTerminiDTO> nadjiPregledeSala(Sala idSale);
}
