package isamrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import isamrs.domain.AdministratorKlinickogCentra;

@Repository
public interface AdministratorKlinickogCentraRepository extends JpaRepository<AdministratorKlinickogCentra, Integer>{
	@Query("SELECT akc FROM AdministratorKlinickogCentra akc WHERE akc.email = ?1")
	public AdministratorKlinickogCentra findByEmail(String email);
}
