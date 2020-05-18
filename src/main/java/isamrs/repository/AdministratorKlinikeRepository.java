package isamrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import isamrs.domain.AdministratorKlinike;

@Repository
public interface AdministratorKlinikeRepository extends JpaRepository<AdministratorKlinike, Integer>{
	@Query("SELECT ak FROM AdministratorKlinike ak WHERE ak.email = ?1")
	public AdministratorKlinike findByEmail(String email);
}
