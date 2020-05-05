package isamrs.repository;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import isamrs.domain.Klinika;
import isamrs.domain.Lekar;
@Repository
public interface KlinikaRepository extends JpaRepository<Klinika, Integer>{

}
