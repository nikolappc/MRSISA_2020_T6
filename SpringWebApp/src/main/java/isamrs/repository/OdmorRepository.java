package isamrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import isamrs.domain.GodisnjiOdmor;

@Repository
public interface OdmorRepository extends JpaRepository<GodisnjiOdmor, Integer>{

}
