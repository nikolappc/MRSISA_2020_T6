package isamrs.registracija;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import isamrs.registracija.VerificationToken;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Integer> {

	@Query("SELECT vt FROM VerificationToken vt WHERE vt.token = ?1")
	public VerificationToken findByToken(String token);

}
