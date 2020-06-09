package isamrs.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import isamrs.domain.AdministratorKlinike;
import isamrs.domain.Dijagnoza;
import isamrs.domain.Klinika;
import isamrs.domain.Lekar;
import isamrs.domain.Pacijent;
import isamrs.domain.Pregled;
import isamrs.domain.Recepti;
import isamrs.domain.Sala;
import isamrs.domain.Termin;
import isamrs.domain.TipPosete;
import isamrs.domain.ZdravstveniKarton;
import isamrs.dto.PredefinisaniPregledDTO;
import isamrs.dto.ZakazivanjePregledaDTO;
import isamrs.repository.KlinikaRepository;
import isamrs.repository.LekarRepository;
import isamrs.exceptions.NotFoundException;
import isamrs.repository.PacijentRepository;
import isamrs.repository.PregledRepository;
import isamrs.repository.TipPoseteRepository;
import isamrs.repository.ZdravstveniKartonRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;


@Service
public class PregledServiceImpl implements PregledService {
	
	@Autowired
	private PregledRepository pregledRepository;
	
	@Autowired
	private KlinikaRepository klinikaRepo;
	
	@Autowired
	private PacijentRepository pacijentRepo;
	
	@Autowired
	private TipPoseteRepository tipRepo;
	
	@Autowired
	private LekarRepository lekarRepo;
	
	@Autowired
	private ZdravstveniKartonRepository kartonRepo;
	
	@Autowired
	private MailSender mailSender;

	@Autowired
	private ZdravstveniKartonRepository zdrrepo;


	//@Override
	public Collection<Pregled> findAll() {

		return null;
	}

	//@Override
	public Pregled findOne(Integer id) throws NotFoundException {
		return pregledRepository.findById(id).orElseThrow(NotFoundException::new);
	}

	//@Override
	public Pregled create(Pregled t) {
		return pregledRepository.save(t);
	}

	


	//@Override
	public Pregled update(Integer id, Pregled t) throws NotFoundException {
		Pregled pregledForUpdate = pregledRepository.findById(id).orElseThrow(NotFoundException::new);
		t.setZdravstveniKarton(zdrrepo.findById(t.getZdravstveniKarton().getId()).get());
		return pregledRepository.save(t);
	}

	//@Override
	public void delete(Integer id) {
		pregledRepository.deleteById(id);
		
	}

	//@Override
	public List<Pregled> findByKartonId(Integer id) {
		return pregledRepository.findByIdKarton(id);
	}
	
	public List<Pregled> findByKartonIdOdradjen(Integer id) {
		return pregledRepository.findByIdKartonOdradjen(id);
	}


	//@Override
	public Collection<Pregled> findPreglediKlinike(Integer idKlinike) {
		return pregledRepository.getPreglediKlinike(idKlinike);
	}
	
	//@Override
	public Collection<Pregled> findPredefinisaniPreglediKlinike(Integer idKlinike) {
		return pregledRepository.getPredefinisaniPreglediKlinike(idKlinike);
	}
	
	//@Override
	public List<Pregled> getBuduciPotvrdjeniPregledi(Integer id){
		Date now = new Date();
		return pregledRepository.getBuduciPotvrdjeniPregledi(id, now);
	}

	//@Override
	public Collection<Pregled> findByLekar(Lekar lekar) {
		return pregledRepository.findByLekar(lekar);
	}

	//@Override
	public Iterable<? extends Pregled> findBySala(Sala s) {
		return pregledRepository.findBySala(s);
	}
	
	public Boolean otkaziPregled(int idPregleda, int idUlogovanog) throws NotFoundException {
		Pregled p = findOne(idPregleda);
		if (p.getZdravstveniKarton().getPacijent().getId() != idUlogovanog) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, 1);
		if (c.getTime().after(p.getTermin().getPocetak())) {
			return false;
		}
		p.setPotvrdjen(false);  //nije vise potvrdjen jer svi nezakazani su nepotvrdjeni
		p.setZdravstveniKarton(null);   //uklanja se pacijent
		//ako je sala rezervisana ne uklanja se, nego pregled postaje predefinisani
		if (p.getSala() != null) {
			Pregled pr = update(idPregleda, p);
		} else {
			//ako sala nije rezervisana
			//OVO NE MOZE?? JER JE POTVRDJEN=FALSE
			/*p.setTermin(null);
			p.setTipPosete(null);
			p.setRecepti(null);
			p.setDijagnoza(null);
			p.setLekar(null);
			Pregled pr = pregledService.update(idPregleda, p);
			pregledService.delete(idPregleda);*/
		}
		return true;
	}
	
	public Boolean zakaziPregled(ZakazivanjePregledaDTO zahtjev) {
		//Klinika k = klinikaService.findOne(zahtjev.getIdKlinike());
		Klinika k = klinikaRepo.findById(zahtjev.getIdKlinike()).orElseGet(null);
		Pregled pregled = new Pregled();
		pregled.setPotvrdjen(false);
		Termin termin = new Termin();
		pregled.setRecepti(new ArrayList<Recepti>());
		pregled.setDijagnoza(new ArrayList<Dijagnoza>());
		//Lekar l = posetaService.findOneLekar(zahtjev.getIdLekara());
		Lekar l = lekarRepo.findById(zahtjev.getIdLekara()).orElseGet(null);
		//Pacijent p = pacijentService.findOne(zahtjev.getIdPacijenta());
		Pacijent p = pacijentRepo.findById(zahtjev.getIdPacijenta()).orElseGet(null);
		pregled.setZdravstveniKarton(p.getZdravstveniKarton());
		pregled.setLekar(l);
		//TipPosete tp = posetaService.findTipByNaziv(zahtjev.getNazivTipa());
		TipPosete tp = tipRepo.findByNaziv(zahtjev.getNazivTipa());
		pregled.setTipPosete(tp);   //falice sala
		termin.setPocetak(zahtjev.getTerminPocetak());
		Calendar c = Calendar.getInstance();
		c.setTime(zahtjev.getTerminPocetak());
		c.add(Calendar.MINUTE, 30);
		termin.setKraj(c.getTime());
		//Termin ter = terminService.create(termin);
		pregled.setTermin(termin);
		k.getPregledi().add(pregled);
		l.getPregled().add(pregled);
		p.getZdravstveniKarton().getPregledi().add(pregled);
		Pregled pr = create(pregled);

		//send mail
		String subject = "Zahtjev za zakazivanje pregleda";
		String message = "Dobili ste zahtjev za zakazivanje pregleda od strane pacijenta.";

		SimpleMailMessage email = new SimpleMailMessage();
		email.setSubject(subject);
		email.setText(message);
		for (AdministratorKlinike ak : k.getAdministratorKlinike()) {
			String recipient = ak.getEmail();
			email.setTo(recipient);
			mailSender.send(email);
		}
		return true;
	}
	
	public Boolean zakaziPredefinisaniPregled(ZakazivanjePregledaDTO zahtjev, int idZk, String email) throws NotFoundException {
		Klinika k = klinikaRepo.findById(zahtjev.getIdKlinike()).orElseGet(null);
		Pregled prDef = findOne(zahtjev.getIdPredefinisanogTermina());
		//ZdravstveniKarton zk = kartonService.findOne(idZk);
		ZdravstveniKarton zk = kartonRepo.findById(idZk).orElseGet(null);
		prDef.setZdravstveniKarton(zk);
		prDef.setPotvrdjen(true);
		Pregled prDef1 = update(zahtjev.getIdPredefinisanogTermina(), prDef);

		//send mail
		String subject1 = "Potvrda o zakazivanju pregleda";
		String message1 = "Vas pregled je zakazan.";

		SimpleMailMessage email1 = new SimpleMailMessage();
		email1.setSubject(subject1);
		email1.setText(message1);
		//String recipient1 = ((Pacijent)req.getSession().getAttribute("user")).getEmail();
		email1.setTo(email);
		mailSender.send(email1);
		return true;
	}
	
	public Collection<PredefinisaniPregledDTO> getPredefinisaniPregledi(int idKlinike){
		Collection<Pregled> pregledi = findPredefinisaniPreglediKlinike(idKlinike);
		Collection<PredefinisaniPregledDTO> posete = new ArrayList<PredefinisaniPregledDTO>();
		Klinika k = klinikaRepo.findById(idKlinike).orElseGet(null);
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c2.setTime(new Date());
		c2.set(Calendar.HOUR_OF_DAY, 0); c2.set(Calendar.MINUTE, 0); c2.set(Calendar.SECOND, 0); c2.set(Calendar.MILLISECOND, 0);
		for (Pregled p : pregledi) {
			c1.setTime(p.getTermin().getPocetak());
			c1.set(Calendar.HOUR_OF_DAY, 0); c1.set(Calendar.MINUTE, 0); c1.set(Calendar.SECOND, 0); c1.set(Calendar.MILLISECOND, 0);
			if (c1.getTime().after(c2.getTime())) {
				posete.add(new PredefinisaniPregledDTO(p, k));
			}
		}
		return posete;
	}
	
}



