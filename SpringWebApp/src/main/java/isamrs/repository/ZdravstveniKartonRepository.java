package isamrs.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import isamrs.domain.ZdravstveniKarton;

@Repository
public interface ZdravstveniKartonRepository extends JpaRepository<ZdravstveniKarton, Integer> {


    @Query("SELECT z FROM ZdravstveniKarton z JOIN z.pacijent p WHERE p.id = ?1")
    ZdravstveniKarton findByPacijent(Integer pacijentId);

}
