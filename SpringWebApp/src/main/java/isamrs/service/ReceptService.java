package isamrs.service;

import isamrs.domain.Recepti;
import isamrs.exceptions.NotFoundException;
import isamrs.repository.ReceptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;

import javax.transaction.Transactional;
import java.util.Collection;

@org.springframework.stereotype.Service
public interface ReceptService extends Service<Recepti, Integer> {



    public Collection<Recepti> getReceptsWithLek(Long lekId);

    public void overiRecept(Integer id, Recepti toEntity) throws NotFoundException;
}
