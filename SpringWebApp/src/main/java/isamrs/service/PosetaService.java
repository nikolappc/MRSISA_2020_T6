package isamrs.service;

import isamrs.domain.*;
import isamrs.dto.PregledDTO;
import isamrs.exceptions.NotFoundException;
import isamrs.repository.AdministratorKlinikeRepository;
import isamrs.repository.LekarRepository;
import isamrs.repository.OdmorRepository;
import isamrs.repository.OperacijaRepository;
import isamrs.repository.PregledRepository;
import isamrs.repository.SalaRepository;
import isamrs.repository.TipPoseteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.Optional;

@Service
public class PosetaService {

	@Autowired
	private PregledRepository pregledRepo;
	
	@Autowired
	private OperacijaRepository operacijaRepository;
	
	@Autowired
	private TipPoseteRepository tipRepo;
	
	@Autowired
	private LekarRepository lekarRepo;
	
	@Autowired
	private SalaRepository salaRepo;
	
	@Autowired
	private AdministratorKlinikeRepository adminRepo;
	
	@Autowired
	private OdmorRepository odmorRepo;

	public Pregled create(Pregled pre, Integer idAdmina) throws Exception, NotFoundException{
		Lekar l = lekarRepo.findById(pre.getLekar().getId()).get();
		Sala s = salaRepo.findById(pre.getSala().getId()).get();
		TipPosete t = tipRepo.findById(pre.getTipPosete().getId()).get();
		Optional<AdministratorKlinike> o = adminRepo.findById(idAdmina);
		AdministratorKlinike ak = null;
		if(o.isPresent()) {
			ak = o.get();
		}
		else {
			throw new NotFoundException();
		}
		
		if(s != null)
			if (!proveriTerminSala(s, pre.getTermin())) {
	            throw new Exception();
	        }
		if(l == null)
			throw new Exception();
        if (!proveriTerminLekara(l, pre.getTermin())) {
            throw new Exception();
        }
        pre.setSala(s);
        pre.setLekar(l);
        pre.setTipPosete(t);
        
        Pregled p = pregledRepo.save(pre);
        ak.getKlinika().getPregledi().add(p);
        pregledRepo.save(p);
		return p;
	}
	

    public boolean proveriTerminLekara(Lekar lekar, Termin termin) {
        Collection<Pregled> pregledi = pregledRepo.findByLekar(lekar);
        for (Pregled p : pregledi) {
            //Ako je termin posle kraja nekog pregleda to je ok
            if (p.getSala() == null)
                continue;

            if (p.getTermin().getKraj().before(termin.getPocetak()) || 
            		p.getTermin().getKraj().equals(termin.getPocetak())) {
                continue;
            }
            //Ako je termin pre pocetka nekog pregleda to je ok
            if (p.getTermin().getPocetak().after(termin.getKraj())  || 
            		p.getTermin().getPocetak().equals(termin.getKraj())) {
                continue;
            }

            //Ako je bilo koji drugi slucaj onda je zauzeta sala
            return false;
        }
        Collection<Operacija> operacije = operacijaRepository.findByLekar(lekar);

        for (Operacija p : operacije) {

            if (p.getSala() == null)
                continue;

            //Ako je termin posle kraja nekog pregleda to je ok
            if (p.getTermin().getKraj().before(termin.getPocetak()) || 
            		p.getTermin().getKraj().equals(termin.getPocetak())) {
                continue;
            }
            //Ako je termin pre pocetka nekog pregleda to je ok
            if (p.getTermin().getPocetak().after(termin.getKraj())  || 
            		p.getTermin().getPocetak().equals(termin.getKraj())) {
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
        
        System.out.println(terminPocetak.getTime());
        System.out.println(terminKraj.getTime());
        System.out.println(radnoVremePocetak.getTime());
        System.out.println(radnoVremeKraj.getTime());
        
        if(!(radnoVremePocetak.before(terminPocetak) && radnoVremeKraj.after(terminKraj) ))  {
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
            if (p.getTermin().getKraj().before(termin.getPocetak()) || 
            		p.getTermin().getKraj().equals(termin.getPocetak())) {
                continue;
            }
            //Ako je termin pre pocetka nekog pregleda to je ok
            if (p.getTermin().getPocetak().after(termin.getKraj()) || 
            		p.getTermin().getPocetak().equals(termin.getKraj())) {
                continue;
            }

            //Ako je bilo koji drugi slucaj onda je zauzeta sala
            return false;
        }

        for (Operacija p : operacijaRepository.findBySala(s)) {
            //Ako je termin posle kraja nekog pregleda to je ok
            if (p.getTermin().getKraj().before(termin.getPocetak()) || 
            		p.getTermin().getKraj().equals(termin.getPocetak())) {
                continue;
            }
            //Ako je termin pre pocetka nekog pregleda to je ok
            if (p.getTermin().getPocetak().after(termin.getKraj()) || 
            		p.getTermin().getPocetak().equals(termin.getKraj())) {
                continue;
            }

            //Ako je bilo koji drugi slucaj onda je zauzeta sala
            return false;
        }

        //Vrati true ako je sve ok
        return true;
    }
	
	
	public Lekar findOneLekar(Integer id) {
		return lekarRepo.findById(id).orElseGet(null);
	}
	
	public TipPosete findTipByNaziv(String naziv) {
		return tipRepo.findByNaziv(naziv);
	}

	public Pacijent findPacijent(Integer idPregled) {
		Pregled pregled = pregledRepo.findById(idPregled).orElseGet(null);
		if(pregled == null)
			return null;
		return pregled.getZdravstveniKarton().getPacijent();
	}
	
	public Pacijent findPacijentOperacija(Integer idPregled) {
		Operacija pregled = operacijaRepository.findById(idPregled).orElseGet(null);
		if(pregled == null)
			return null;
		return pregled.getZdravstveniKarton().getPacijent();
	}

	public Collection<Poseta> findByLekar(Lekar lekar){
		Collection<Pregled> pregled = pregledRepo.findByLekar(lekar);
		Collection<Operacija> operacija = operacijaRepository.findByLekar(lekar);
		Collection<Poseta> posete = new LinkedList<>();
		posete.addAll(pregled);
		posete.addAll(operacija);
		return posete;
	}

}
