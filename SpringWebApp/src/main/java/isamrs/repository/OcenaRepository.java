package isamrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import isamrs.domain.Ocena;

@Repository
public interface OcenaRepository extends JpaRepository<Ocena, Integer> {

}
