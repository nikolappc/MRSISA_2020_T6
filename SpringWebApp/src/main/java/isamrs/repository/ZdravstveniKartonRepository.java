package isamrs.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import isamrs.domain.ZdravstveniKarton;

@Repository
public interface ZdravstveniKartonRepository extends JpaRepository<ZdravstveniKarton, Integer> {

}
