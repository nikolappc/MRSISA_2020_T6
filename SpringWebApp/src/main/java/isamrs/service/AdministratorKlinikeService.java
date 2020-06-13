package isamrs.service;

import isamrs.domain.*;
import isamrs.dto.GrafDTO;
import isamrs.dto.IzvestajDTO;
import isamrs.dto.LekarOcena;
import isamrs.dto.Podatak;
import isamrs.dto.PosetaPacijentDTO;
import isamrs.dto.PregledDTO;
import isamrs.exceptions.LekarZauzetException;
import isamrs.exceptions.NotFoundException;
import isamrs.exceptions.SalaZauzetaException;
import isamrs.operacije.doktori.OnDoktorDodatEvent;
import isamrs.operacije.doktori.OnDoktorDodatEventPregled;
import isamrs.repository.*;

import java.sql.Timestamp;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class AdministratorKlinikeService implements isamrs.service.Service<AdministratorKlinike, Integer> {

	
    @Autowired
    private AdministratorKlinikeRepository adminklinikeRepository;
    
    @Autowired
    private KlinikaRepository klinikaRepo;
    
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
    private OdmorRepository odmorRepo;

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
        if(ak == null)
			return null;
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

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public Operacija update(Integer id, Operacija operacija) throws Exception, SalaZauzetaException, LekarZauzetException {

        Operacija operacijaBaza = operacijaRepo.findById(id).orElseGet(null);
        if (operacijaBaza != null) {
            Sala s = salaRepo.findById(operacija.getSala().getId()).orElseGet(null);

            while(!proveriTerminSala(s, operacija.getTermin())) {
            	
            	
            	Date pocetak = operacija.getTermin().getPocetak();
            	Date kraj = operacija.getTermin().getKraj();
            	
            	Calendar pocetakK = Calendar.getInstance();
            	pocetakK.setTime(pocetak);
                
                Calendar krajK = Calendar.getInstance();
                krajK.setTime(kraj);
                
                long ONE_MINUTE_IN_MILLIS=60000;//millisecs

            	long t= pocetakK.getTimeInMillis();
            	operacija.getTermin().setPocetak(new Date(t + (30 * ONE_MINUTE_IN_MILLIS)));
            	
            	t= krajK.getTimeInMillis();
            	operacija.getTermin().setKraj(new Date(t + (30 * ONE_MINUTE_IN_MILLIS)));
                
            }
            if (!proveriTerminLekara(operacija.getLekari(), operacija.getTermin())) {
                throw new LekarZauzetException("Zauzet lekar", "Izvinjavamo se lekar je zauzet u odabranom terminu");
            }
            Set<Integer> ids = new HashSet<>();
            for (Lekar l : operacijaBaza.getLekari()) {
                ids.add(l.getId());
            }
            Lekar bazaLekar = null;
            for (Lekar l : operacija.getLekari()) {
                if (ids.contains(l.getId())) {
                    continue;
                }
                Lekar l1 = lekarRepo.findById(l.getId()).orElse(null);
                l1.getOperacije().add(operacijaBaza);
                operacijaBaza.addLekar(l1);
                bazaLekar = lekarRepo.save(l1);
                eventPublisher.publishEvent(new OnDoktorDodatEvent(l, operacijaBaza, operacijaBaza.getTermin()));
                
            }
            bazaLekar.getKlinika().getOperacije().add(operacijaBaza);
            operacijaBaza.setSala(s);
            operacijaBaza.setPotvrdjen(true);
            operacijaBaza.setTermin(operacija.getTermin());
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

    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public Pregled update(Integer id, Pregled pregled) throws Exception, NotFoundException {
        Pregled pregledBaza = pregledRepo.findById(id).orElseThrow(NotFoundException::new);
        if(pregledBaza == null)
        	return pregledBaza;
        Sala s = salaRepo.findById(pregled.getSala().getId()).orElseGet(null);
        if(s == null)
        	return pregledBaza;
        if (!proveriTerminLekara(pregled.getLekar().getId(), pregled.getTermin())) {
        	throw new Exception();
        }
        
        while(!proveriTerminSala(s, pregled.getTermin())) {
        	
        	
        	Date pocetak = pregled.getTermin().getPocetak();
        	Date kraj = pregled.getTermin().getKraj();
        	
        	Calendar pocetakK = Calendar.getInstance();
        	pocetakK.setTime(pocetak);
            
            Calendar krajK = Calendar.getInstance();
            krajK.setTime(kraj);
            
            long ONE_MINUTE_IN_MILLIS=60000;//millisecs

        	long t= pocetakK.getTimeInMillis();
        	pregled.getTermin().setPocetak(new Date(t + (30 * ONE_MINUTE_IN_MILLIS)));
        	
        	t= krajK.getTimeInMillis();
        	pregled.getTermin().setKraj(new Date(t + (30 * ONE_MINUTE_IN_MILLIS)));
            
        }


        Lekar l = lekarRepo.findById(pregled.getLekar().getId()).orElse(null);
        eventPublisher.publishEvent(new OnDoktorDodatEventPregled(l, pregledBaza, pregled.getTermin()));
        l.getPregled().add(pregledBaza);
        lekarRepo.save(l);
        l.getKlinika().getPregledi().add(pregledBaza);
        pregledBaza.setPotvrdjen(true);
        pregledBaza.setSala(s);
        pregledBaza.setLekar(l);
        pregledBaza.setTermin(pregled.getTermin());
        pregledRepo.save(pregledBaza);

        return pregledBaza;
    }

    private boolean proveriTerminLekara(Integer id, Termin termin) {
        Lekar lekar = lekarRepo.findById(id).orElseGet(null);
        return proveriTerminLekara(lekar, termin);
    }

    public boolean proveriTerminLekara(Lekar lekar, Termin termin) {
    	if(lekar == null)
    		return false;
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
        
        RadnoVreme r = lekar.getRadnoVreme().iterator().next();
        
        
        Calendar terminPocetak = Calendar.getInstance();
        terminPocetak.setTime(termin.getPocetak());
        
        Calendar terminKraj = Calendar.getInstance();
        terminKraj.setTime(termin.getKraj());
        
        Calendar radnoVremePocetak = Calendar.getInstance();
        radnoVremePocetak.setTime(r.getPocetak());
        radnoVremePocetak.set(Calendar.DAY_OF_MONTH, terminPocetak.get(Calendar.DAY_OF_MONTH));
        radnoVremePocetak.set(Calendar.MONTH, terminPocetak.get(Calendar.MONTH));
        radnoVremePocetak.set(Calendar.YEAR, terminPocetak.get(Calendar.YEAR));
        
        Calendar radnoVremeKraj = Calendar.getInstance();
        radnoVremeKraj.setTime(r.getKraj());
        radnoVremeKraj.set(Calendar.DAY_OF_MONTH, terminPocetak.get(Calendar.DAY_OF_MONTH));
        radnoVremeKraj.set(Calendar.MONTH, terminPocetak.get(Calendar.MONTH));
        radnoVremeKraj.set(Calendar.YEAR, terminPocetak.get(Calendar.YEAR));
        
        
        if(!((radnoVremePocetak.before(terminPocetak) || radnoVremePocetak.equals(terminPocetak)) && (radnoVremeKraj.after(terminKraj) || radnoVremeKraj.equals(terminKraj))))  {
        	return false;
        }
        
        //Provera da li je godisnji odmor tad
        for(GodisnjiOdmor go : odmorRepo.odobreniLekari(lekar.getId())) {
        	 
             
             //Ako je termin posle kraja nekog odmora to je ok
             if (go.getKraj().before(termin.getPocetak()) || 
            		 go.getKraj().equals(termin.getPocetak())) {
                 continue;
             }
             //Ako je termin pre pocetka nekog odmora to je ok
             if (go.getPocetak().after(termin.getKraj())  || 
            		 go.getPocetak().equals(termin.getKraj())) {
                 continue;
             }
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

	public IzvestajDTO izvestaj(Integer id) {
		IzvestajDTO izvestaj = new IzvestajDTO();
		AdministratorKlinike ak = adminklinikeRepository.findById(id).orElseGet(null);
		if(ak == null)
			return null;
		Double prosekKlinike = klinikaRepo.prosekKlinike(ak.getKlinika().getId());
		if(prosekKlinike == null) {
			prosekKlinike = 0.0;
		}
		for(Lekar l : ak.getKlinika().getLekari()) {
			Double prosekLekara = lekarRepo.prosekLekara(l.getId());
			if(prosekLekara == null) {
				prosekLekara = 0.0;
			}
			LekarOcena lo = new LekarOcena(l, prosekLekara);
			izvestaj.getOceneLekara().add(lo);
		}
		
		izvestaj.setProsekKlinike(prosekKlinike);
		return izvestaj;
	}

	public GrafDTO izvestajTip(Integer id,String tip) {
		GrafDTO graf = new GrafDTO();
		AdministratorKlinike ak = adminklinikeRepository.findById(id).orElseGet(null);
		if(ak == null)
			return null;
		ArrayList<Object[]> nesto = klinikaRepo.dnevniIzvestaj(ak.getKlinika().getId(),tip);
		for(Object[] o : nesto) {
			Podatak p = new Podatak();
			p.podatak = ((Timestamp) o[0]).toString();
			p.vrednost = (int)((long) o[1]);
			graf.getPodaci().add(p);
		}
		nesto = klinikaRepo.dnevniIzvestajOperacije(ak.getKlinika().getId(),tip);
		spoljna :for(Object[] o : nesto) {
			
			Podatak p = new Podatak();
			p.podatak = ((Timestamp) o[0]).toString();
			p.vrednost = (int)((long) o[1]);
			for(Podatak p1 : graf.getPodaci()) {
				if(p1.podatak.equals(p.podatak)) {
					p1.vrednost += p.vrednost;
					continue spoljna;
				}
			}
			
			graf.getPodaci().add(p);
		}
		return graf;
	}
	
	
	public double troskovi(Integer id, Date pocetak, Date kraj) {
		double suma = 0;
		AdministratorKlinike ak = adminklinikeRepository.findById(id).orElseGet(null);
		if(ak == null)
			return 0;
		Double pregledi = klinikaRepo.preglediUIntervalu(ak.getKlinika().getId(), pocetak, kraj);
		suma += pregledi == null? 0 : pregledi;
		
		Double operacije = klinikaRepo.operacijeUIntervalu(ak.getKlinika().getId(), pocetak, kraj);
		suma += operacije == null? 0 : operacije;
		
		return suma;
	}
}
