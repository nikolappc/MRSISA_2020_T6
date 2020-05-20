package isamrs.service;

import isamrs.domain.AdministratorKlinickogCentra;
import isamrs.domain.AdministratorKlinike;
import isamrs.domain.Lekar;
import isamrs.domain.Operacija;
import isamrs.domain.Pregled;
import isamrs.domain.Sala;
import isamrs.domain.Termin;
import isamrs.exceptions.NotFoundException;
import isamrs.repository.AdministratorKlinikeRepository;
import isamrs.repository.LekarRepository;
import isamrs.repository.OperacijaRepository;
import isamrs.repository.PregledRepository;
import isamrs.repository.SalaRepository;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AdministratorKlinikeService implements isamrs.service.Service<AdministratorKlinike, Integer> {
	
	@Autowired
	private AdministratorKlinikeRepository adminklinikeRepository;
	
	@Autowired
	private PregledRepository pregledRepo;

	@Autowired
	private SalaRepository salaRepo;

	@Autowired
	private OperacijaRepository operacijaRepo;

	@Autowired
	private LekarRepository lekarRepo;

	public AdministratorKlinike findByEmail(String email) {
		return adminklinikeRepository.findByEmail(email);
	}

	@Override
	public Collection<AdministratorKlinike> findAll() {
		return adminklinikeRepository.findAll();
	}

	@Override
	public AdministratorKlinike findOne(Integer integer) {
		return adminklinikeRepository.findById(integer).orElseThrow(NotFoundException::new);
	}

	@Override
	public AdministratorKlinike create(AdministratorKlinike administratorKlinickogCentra) {
		return adminklinikeRepository.save(administratorKlinickogCentra);
	}

	@Override
	public AdministratorKlinike update(Integer integer, AdministratorKlinike administratorKlinickogCentra) {
		
		AdministratorKlinike ak = adminklinikeRepository.findById(integer).orElseGet(null);
		
		ak.setAdresa(administratorKlinickogCentra.getAdresa());
		ak.setBrojTelefona(administratorKlinickogCentra.getBrojTelefona());
		ak.setEmail(administratorKlinickogCentra.getEmail());
		ak.setIme(administratorKlinickogCentra.getIme());
		ak.setPrezime(administratorKlinickogCentra.getPrezime());
		ak.setJbo(administratorKlinickogCentra.getJbo());
		ak.setPassword(administratorKlinickogCentra.getPassword());
		if(administratorKlinickogCentra.getKlinika() != null)
			ak.setKlinika(administratorKlinickogCentra.getKlinika());
		
		return adminklinikeRepository.save(ak);
	}

	@Override
	public void delete(Integer integer) {
		adminklinikeRepository.deleteById(integer);
	}

	public Pregled findPregled(Integer id) {
		return pregledRepo.findById(id).orElseGet(null);
	}

	public Collection<Integer> findAllZahtevi() {
		ArrayList<Integer> lista = new ArrayList<Integer>();
		for(Pregled p : pregledRepo.findZahteve()) {
			if (p.getSala() == null)
				lista.add(p.getId());
		}
		return lista;
	}

	public Pregled update(Integer id, Pregled pregled) throws Exception {
		Pregled pregledBaza = pregledRepo.findById(id).orElseGet(null);
		if (pregledBaza != null) {
			Sala s = salaRepo.findById(pregled.getSala().getId()).orElseGet(null);

			if(!proveriTerminSala(s,pregled.getTermin())) {
				throw new Exception();
			}

			if(!proveriTerminLekara(pregled.getLekar().getId(), pregled.getTermin())) {
				throw new Exception();
			}

			pregledBaza.setSala(s);

			pregledBaza.setLekar(pregled.getLekar());
		}
		pregledRepo.save(pregledBaza);
		//Dodaj pacijenta u klinici

		return pregledBaza;
	}

	private boolean proveriTerminLekara(Integer id, Termin termin) {
		Lekar lekar = lekarRepo.findById(id).orElseGet(null);

		for(Pregled p: lekar.getPregled()) {
			//Ako je termin posle kraja nekog pregleda to je ok
			if(p.getSala() == null)
				continue;

			if(p.getTermin().getKraj().before(termin.getPocetak())) {
				continue;
			}
			//Ako je termin pre pocetka nekog pregleda to je ok
			if(p.getTermin().getPocetak().after(termin.getKraj())) {
				continue;
			}

			//Ako je bilo koji drugi slucaj onda je zauzeta sala
			return false;
		}

		for(Operacija p: lekar.getOperacije() ) {

			if(p.getSala() == null)
				continue;

			//Ako je termin posle kraja nekog pregleda to je ok
			if(p.getTermin().getKraj().before(termin.getPocetak())) {
				continue;
			}
			//Ako je termin pre pocetka nekog pregleda to je ok
			if(p.getTermin().getPocetak().after(termin.getKraj())) {
				continue;
			}

			//Ako je bilo koji drugi slucaj onda je zauzeta sala
			return false;
		}


		return true;
	}

	private boolean proveriTerminSala(Sala s, Termin termin) {
		for(Pregled p: pregledRepo.findBySala(s) ) {
			//Ako je termin posle kraja nekog pregleda to je ok
			if(p.getTermin().getKraj().before(termin.getPocetak())) {
				continue;
			}
			//Ako je termin pre pocetka nekog pregleda to je ok
			if(p.getTermin().getPocetak().after(termin.getKraj())) {
				continue;
			}

			//Ako je bilo koji drugi slucaj onda je zauzeta sala
			return false;
		}

		for(Operacija p: operacijaRepo.findBySala(s) ) {
			//Ako je termin posle kraja nekog pregleda to je ok
			if(p.getTermin().getKraj().before(termin.getPocetak())) {
				continue;
			}
			//Ako je termin pre pocetka nekog pregleda to je ok
			if(p.getTermin().getPocetak().after(termin.getKraj())) {
				continue;
			}

			//Ako je bilo koji drugi slucaj onda je zauzeta sala
			return false;
		}

		//Vrati true ako je sve ok
		return true;
	}






}
