package isamrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import isamrs.domain.Pacijent;

@Repository
public interface PacijentRepository extends JpaRepository<Pacijent, Integer>, PacijentRepositoryCustom{

}
