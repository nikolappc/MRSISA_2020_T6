package isamrs.service;

import isamrs.domain.*;
import isamrs.dto.PosetaPacijentDTO;
import isamrs.dto.PregledDTO;
import isamrs.exceptions.LekarZauzetException;
import isamrs.exceptions.NotFoundException;
import isamrs.exceptions.SalaZauzetaException;
import isamrs.operacije.doktori.OnDoktorDodatEvent;
import isamrs.repository.*;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class AdministratorKlinikeService implements isamrs.service.Service<AdministratorKlinike, Integer> {

	@Autowired 
    KlinikaRepository klinikaRepo;
	
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

    @Autowired
    private PacijentRepository pacijentRepository;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    public AdministratorKlinike findByEmail(String email) {
        return adminklinikeRepository.findByEmail(email);
    }

    @Override
    public Collection<AdministratorKlinike> findAll() {
        return adminklinikeRepository.findAll();
    }

    @Override
    public AdministratorKlinike findOne(Integer integer) throws NotFoundException {
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
        if (administratorKlinickogCentra.getKlinika() != null)
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

    public Operacija findOperacija(Integer id) {
        return operacijaRepo.findById(id).orElseGet(null);
    }

    public Collection<PosetaPacijentDTO> findAllZahteviPregleda() {
		return pregledRepo.findZahteve().stream().map(p -> {
			Pacijent pac = pacijentRepository.findByKarton(p.getZdravstveniKarton().getId());
			return new PosetaPacijentDTO(p, pac);
		})
				.collect(Collectors.toList());    }

    public Collection<PosetaPacijentDTO> findAllZahteviOperacija() {
        return operacijaRepo.findZahteve().stream().map(p -> {
				Pacijent pac = pacijentRepository.findByKarton(p.getZdravstveniKarton().getId());
				return new PosetaPacijentDTO(p,  pac);
        	})
			.collect(Collectors.toList());
    }


    public Operacija update(Integer id, Operacija operacija) throws Exception, SalaZauzetaException, LekarZauzetException {

        Operacija operacijaBaza = operacijaRepo.findById(id).orElseGet(null);
        if (operacijaBaza != null) {
            Sala s = salaRepo.findById(operacija.getSala().getId()).orElseGet(null);

            if (!proveriTerminSala(s, operacija.getTermin())) {
                throw new SalaZauzetaException("Zauzeta sala", "Izvinjavamo se sala je zauzeta u odabranom terminu");
            }
            if (!proveriTerminLekara(operacija.getLekari(), operacija.getTermin())) {
                throw new LekarZauzetException("Zauzet lekar", "Izvinjavamo se lekar je zauzet u odabranom terminu");
            }
            Set<Integer> ids = new HashSet<>();
            for (Lekar l : operacijaBaza.getLekari()) {
                ids.add(l.getId());
            }
            for (Lekar l : operacija.getLekari()) {
                if (ids.contains(l.getId())) {
                    continue;
                }
                eventPublisher.publishEvent(new OnDoktorDodatEvent(l, operacijaBaza, operacija.getTermin()));
                l.getOperacije().add(operacijaBaza);
                lekarRepo.save(l);
            }
            operacijaBaza.setSala(s);


            operacijaBaza.setLekar(operacija.getLekari());
            operacijaRepo.save(operacijaBaza);
        }
        return operacijaBaza;
    }

    public Boolean proveriTerminLekara(Collection<Lekar> lekar, Termin termin) {
        for (Lekar l : lekar) {
            if (!proveriTerminLekara(l.getId(), termin)) {
                return false;
            }
        }
        return true;
    }

    public Pregled update(Integer id, Pregled pregled) throws Exception, NotFoundException {
        Pregled pregledBaza = pregledRepo.findById(id).orElseThrow(NotFoundException::new);
        Sala s = salaRepo.findById(pregled.getSala().getId()).orElseGet(null);

        if (!proveriTerminSala(s, pregled.getTermin())) {
            throw new Exception();
        }

        if (!proveriTerminLekara(pregled.getLekar().getId(), pregled.getTermin())) {
            throw new Exception();
        }

        pregledBaza.setSala(s);

        pregledBaza.setLekar(pregled.getLekar());
        pregledRepo.save(pregledBaza);
        //Dodaj pacijenta u klinici
        
        System.out.println("servis");
        Klinika kk = klinikaRepo.findById(1).orElseGet(null);
		for (Lekar llll : kk.getLekari()) {
			System.out.println(llll.getIme() + llll.getId());
		}

        return pregledBaza;
    }

    private boolean proveriTerminLekara(Integer id, Termin termin) {
        Lekar lekar = lekarRepo.findById(id).orElseGet(null);
        return proveriTerminLekara(lekar, termin);
    }

    public boolean proveriTerminLekara(Lekar lekar, Termin termin) {
        Collection<Pregled> pregledi = pregledRepo.findByLekar(lekar);
        for (Pregled p : pregledi) {
            //Ako je termin posle kraja nekog pregleda to je ok
            if (p.getSala() == null)
                continue;

            if (p.getTermin().getKraj().before(termin.getPocetak())) {
                continue;
            }
            //Ako je termin pre pocetka nekog pregleda to je ok
            if (p.getTermin().getPocetak().after(termin.getKraj())) {
                continue;
            }

            //Ako je bilo koji drugi slucaj onda je zauzeta sala
            return false;
        }
        Collection<Operacija> operacije = operacijaRepo.findByLekar(lekar);

        for (Operacija p : operacije) {

            if (p.getSala() == null)
                continue;

            //Ako je termin posle kraja nekog pregleda to je ok
            if (p.getTermin().getKraj().before(termin.getPocetak())) {
                continue;
            }
            //Ako je termin pre pocetka nekog pregleda to je ok
            if (p.getTermin().getPocetak().after(termin.getKraj())) {
                continue;
            }

            //Ako je bilo koji drugi slucaj onda je zauzeta sala
            return false;
        }


        return true;
    }

    public boolean proveriTerminSala(Sala s, Termin termin) {
        for (Pregled p : pregledRepo.findBySala(s)) {
            //Ako je termin posle kraja nekog pregleda to je ok
            if (p.getTermin().getKraj().before(termin.getPocetak())) {
                continue;
            }
            //Ako je termin pre pocetka nekog pregleda to je ok
            if (p.getTermin().getPocetak().after(termin.getKraj())) {
                continue;
            }

            //Ako je bilo koji drugi slucaj onda je zauzeta sala
            return false;
        }

        for (Operacija p : operacijaRepo.findBySala(s)) {
            //Ako je termin posle kraja nekog pregleda to je ok
            if (p.getTermin().getKraj().before(termin.getPocetak())) {
                continue;
            }
            //Ako je termin pre pocetka nekog pregleda to je ok
            if (p.getTermin().getPocetak().after(termin.getKraj())) {
                continue;
            }

            //Ako je bilo koji drugi slucaj onda je zauzeta sala
            return false;
        }

        //Vrati true ako je sve ok
        return true;
    }


    public Collection<Lekar> getSlobodniLekari(Termin t) {
        Collection<Lekar> lekari = lekarRepo.findAll();
        lekari = lekari.stream().filter(lekar -> proveriTerminLekara(lekar, t)).collect(Collectors.toList());
        return lekari;
    }
}
