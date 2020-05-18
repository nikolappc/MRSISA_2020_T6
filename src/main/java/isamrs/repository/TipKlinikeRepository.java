package isamrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import isamrs.domain.TipKlinike;

@Repository
public interface TipKlinikeRepository extends JpaRepository<TipKlinike, Long> {

}
