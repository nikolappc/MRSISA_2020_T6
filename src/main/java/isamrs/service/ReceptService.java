package isamrs.service;

import isamrs.domain.Recepti;
import isamrs.exceptions.NotFoundException;
import isamrs.repository.ReceptRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@org.springframework.stereotype.Service
public class ReceptService implements Service<Recepti, Integer> {

    @Autowired
    ReceptRepository repo;

    @Override
    public Collection<Recepti> findAll() {
        return repo.findAll();
    }

    @Override
    public Recepti findOne(Integer aLong) {
        return repo.getOne(aLong);
    }

    @Override
    public Recepti create(Recepti recepti) {
        return repo.save(recepti);
    }

    @Override
    public Recepti update(Integer aLong, Recepti recepti) {
        if(!repo.findById(aLong).isPresent()){
            throw new NotFoundException();
        }
        recepti.setId(aLong);
        return repo.save(recepti);
    }

    @Override
    public void delete(Integer aLong) {
        repo.deleteById(aLong);
    }

    public Collection<Recepti> getReceptsWithLek(Long lekId){
        return repo.getReceptsWithLek(lekId);
    }
}
