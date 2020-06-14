package isamrs.repository;

import isamrs.domain.Adresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdresaRepository extends JpaRepository<Adresa, Integer> {

    @Query("SELECT a FROM Adresa a WHERE a.adresa = ?1 and a.grad = ?2 and a.drzava = ?3")
    Adresa findByAdresa(String adresa, String grad, String drzava);
}
