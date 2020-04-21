package isamrs.repository;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import isamrs.domain.Klinika;
import isamrs.domain.Lekar;

public interface KlinikaRepository extends JpaRepository<Klinika, Long>{

}
