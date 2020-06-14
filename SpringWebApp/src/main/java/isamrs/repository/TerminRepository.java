package isamrs.repository;

import org.hibernate.exception.DataException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import isamrs.domain.Termin;

import java.util.Date;

@Repository
public interface TerminRepository extends JpaRepository<Termin, Integer>{



    @Query("SELECT t FROM Termin t WHERE t.pocetak = ?1 and t.kraj = ?2")
    public Termin findByTermin(Date d1, Date d2);
}
