package isamrs.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.OptBoolean;

import isamrs.domain.Klinika;
import isamrs.dto.KlinikaZaPacijentaDTO;
import isamrs.dto.PretragaKlinikeDTO;
import isamrs.exceptions.NotFoundException;
import isamrs.repository.KlinikaRepository;

@org.springframework.stereotype.Service
public class KlinikaServiceImpl implements Service<Klinika, Integer>{

	@Autowired
	private KlinikaRepository repo;
	
	@Override
	public Collection<Klinika> findAll() {
		return repo.findAll();
	}

	@Override
	public Klinika findOne(Integer id) {
		Optional<Klinika> klinika = repo.findById(id);
		if(klinika.isPresent()) {
			return  klinika.get();			
		}
		throw new NotFoundException();
	}

	@Override
	public Klinika create(Klinika t) {
		return repo.save(t);
	}

	@Override
	public Klinika update(Integer id, Klinika t) {
		Klinika k = repo.findById(id).orElseGet(null);
		if(k == null){
			throw new NotFoundException();
		}
		k.setAdresa(t.getAdresa());
		k.setTipKlinike(t.getTipKlinike());
		k.setOpis(t.getOpis());
		k.setNaziv(t.getNaziv());
		
		return repo.save(k);
	}

	@Override
	public void delete(Integer id) {
		repo.deleteById(id);
	}
	
	public List<Klinika> pretragaZakazivanje(PretragaKlinikeDTO pretraga) {
		return repo.pretragaZakazivanje(pretraga.getDatum(), pretraga.getNazivTipa(), pretraga.getOcjena(), pretraga.getGrad(), pretraga.getDrzava());
	}
	
}
