package isamrs.service;

import isamrs.domain.Recepti;
import isamrs.exceptions.NotFoundException;
import isamrs.repository.ReceptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class ReceptServiceImpl implements ReceptService {

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
    public Recepti update(Integer aLong, Recepti recepti) throws NotFoundException {
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

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void overiRecept(Integer id, Recepti toEntity) throws NotFoundException {
        Recepti r = repo.findById(id).orElseThrow(NotFoundException::new);
        System.out.println(r.getVersion()+"----------------------------------------------------------------------------------1");
        r.setOveren(!r.isOveren());
        this.repo.save(r);
        System.out.println(r.getVersion()+"----------------------------------------------------------------------------------1");
    }
}
