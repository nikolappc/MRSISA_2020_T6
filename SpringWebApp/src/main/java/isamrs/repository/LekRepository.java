package isamrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import isamrs.domain.Lek;
@Repository
public interface LekRepository extends JpaRepository<Lek, Long> {

}
