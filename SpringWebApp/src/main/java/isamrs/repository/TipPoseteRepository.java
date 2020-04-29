package isamrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import isamrs.domain.TipPosete;

@Repository
public interface TipPoseteRepository extends JpaRepository<TipPosete, Integer> {

}
