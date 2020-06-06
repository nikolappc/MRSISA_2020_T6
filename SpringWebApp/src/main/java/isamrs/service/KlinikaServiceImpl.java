package isamrs.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.OptBoolean;

import isamrs.domain.Klinika;
import isamrs.domain.Lekar;
import isamrs.domain.Ocena;
import isamrs.dto.KlinikaZaPacijentaDTO;
import isamrs.dto.PretragaKlinikeDTO;
import isamrs.exceptions.NotFoundException;
import isamrs.repository.KlinikaRepository;
import isamrs.repository.LekarRepository;
import isamrs.repository.OcenaRepository;

@org.springframework.stereotype.Service
public class KlinikaServiceImpl implements Service<Klinika, Integer>{

	@Autowired
	private KlinikaRepository repo;
	
	@Autowired
	private LekarRepository repoLekar;
	
	@Autowired
	private OcenaRepository repoOcena;
	
	@Override
	public Collection<Klinika> findAll() {
		return repo.findAll();
	}

	@Override
	public Klinika findOne(Integer id) throws NotFoundException {
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
	public Klinika update(Integer id, Klinika t) throws NotFoundException {
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
		return repo.pretragaZakazivanje(pretraga.getGrad(), pretraga.getDrzava(), pretraga.getOcjena(), pretraga.getNazivTipa());
	}
	
	public List<Lekar> lekariKlinikeTipa(int idKlinike, String nazivTipa) {
		return repoLekar.lekariKlinikeTipa(idKlinike, nazivTipa);
	}
	
	public List<Lekar> lekariKlinike(Integer idKlinike) {
		return repoLekar.lekariKlinike(idKlinike);
	}
	
	public Boolean pacijentOcijenioKliniku(int idPacijenta, int idKlinike) {
		if (repo.pacijentOcijenioKliniku(idPacijenta, idKlinike) == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public Boolean pacijentPosjetioKliniku(int idPacijenta, int idKlinike) {
		if (repo.pacijentPosjetioKliniku(idPacijenta, idKlinike) == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public Ocena getOcenaPacijenta(int idKlinike, int idPacijenta) {
		return repo.getOcenaPacijenta(idKlinike, idPacijenta);
	}
	
	public Ocena updateOcena(Integer id, Ocena o) {
		Ocena ocenaForUpdate = repoOcena.findById(id).orElseGet(null);
		ocenaForUpdate.setVrednost(o.getVrednost());
		return repoOcena.save(ocenaForUpdate);
	}
	
	public Ocena createOcena(Ocena o) {
		return repoOcena.save(o);
	}

	public Klinika findByOperacija(Integer id) {
		return repo.findByOperacija(id);
	}

	public Klinika findByLekar(Lekar lekar) {
		return repo.findByLekar(lekar.getId());
	}
}
