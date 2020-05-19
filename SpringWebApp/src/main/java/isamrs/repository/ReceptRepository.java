package isamrs.repository;

import isamrs.domain.Recepti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ReceptRepository extends JpaRepository<Recepti,Integer> {

    @Query("SELECT r FROM Recepti r WHERE r.lek.sifraLeka = :sifraLeka")
    Collection<Recepti> getReceptsWithLek(Long sifraLeka);
}
