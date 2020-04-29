package isamrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import isamrs.domain.MedicinskaSestra;

@Repository
public interface SestraRepository extends JpaRepository<MedicinskaSestra, Integer>{
	@Query("SELECT ms FROM MedicinskaSestra ms WHERE ms.email = ?1")
	public MedicinskaSestra findByEmail(String email);
}
