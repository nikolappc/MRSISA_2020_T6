package isamrs.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.PessimisticLockException;

import isamrs.domain.AdministratorKlinike;
import isamrs.domain.Cenovnik;
import isamrs.domain.Dijagnoza;
import isamrs.domain.GodisnjiOdmor;
import isamrs.domain.Klinika;
import isamrs.domain.Lekar;
import isamrs.domain.Operacija;
import isamrs.domain.Pacijent;
import isamrs.domain.Pregled;
import isamrs.domain.RadnoVreme;
import isamrs.domain.Recepti;
import isamrs.domain.Sala;
import isamrs.domain.Termin;
import isamrs.domain.TipPosete;
import isamrs.domain.ZdravstveniKarton;
import isamrs.dto.PredefinisaniPregledDTO;
import isamrs.dto.ZakazaniPregledDTO;
import isamrs.dto.ZakazivanjePregledaDTO;
import isamrs.dto.ZakazaniPregledDTO;
import isamrs.repository.KlinikaRepository;
import isamrs.repository.LekarRepository;
import isamrs.exceptions.NotFoundException;
import isamrs.operacije.zakazivanje.OperacijaRunnable;
import isamrs.operacije.zakazivanje.PregledRunnable;
import isamrs.repository.PacijentRepository;
import isamrs.repository.PregledRepository;
import isamrs.repository.SalaRepository;
import isamrs.repository.TipPoseteRepository;
import isamrs.repository.ZdravstveniKartonRepository;
import isamrs.tasks.ThreadPoolTaskSchedulerConfig;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;


@Service
public class PregledServiceImpl implements PregledService {
	
	@Autowired
	ThreadPoolTaskSchedulerConfig threadPoolTaskSchedulerConfig;
	
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

	@Autowired
	private SalaRepository salaRepo;

	@Autowired
	PregledRunnable pregledRunnable;

	//@Override
	public Collection<Pregled> findAll() {

		return null;
	}

	//@Override
	public Pregled findOne(Integer id) throws NotFoundException {
		return pregledRepository.findById(id).orElseThrow(NotFoundException::new);
	}

	//@Override
	public Pregled create(Pregled t, Lekar lekar) {

		Lekar l = lekarRepo.findById(t.getLekar().getId()).get();
		TipPosete tp = tipRepo.findById(t.getTipPosete().getId()).get();
		t.setLekar(l);
		t.setTipPosete(tp);
		
		Pregled p = pregledRepository.save(t);
		l.getKlinika().getPregledi().add(p);
		l.getPregled().add(p);
		pregledRepository.save(p);
		ThreadPoolTaskScheduler threadPoolTaskScheduler = threadPoolTaskSchedulerConfig.threadPoolTaskScheduler();
		pregledRunnable.setId(p.getId());
		LocalDate date = LocalDate.now().plusDays(1);
		LocalDateTime datetime = date.atTime(LocalTime.of(0,0));
		threadPoolTaskScheduler.schedule(pregledRunnable, Date.from(datetime.atZone(ZoneId.systemDefault()).toInstant()));
		
		return p;
	}

	


	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Pregled update(Integer id, Pregled t) throws NotFoundException {
		Pregled pregledForUpdate = pregledRepository.findById(id).orElseThrow(NotFoundException::new);
		try {
			pregledForUpdate.setPotvrdjen(t.isPotvrdjen());
			pregledForUpdate.setTipPosete(t.tipPosete);
			pregledForUpdate.setOdradjen(t.isOdradjen());
			pregledForUpdate.setTermin(t.getTermin());
			try{
				pregledForUpdate.setTipPosete(tipRepo.findById(t.getTipPosete().getId()).orElseThrow(NotFoundException::new));
			}catch (Exception e){
				e.printStackTrace();
			}
			try{
				pregledForUpdate.setLekar(lekarRepo.findById(t.getLekar().getId()).orElseThrow(NotFoundException::new));
			}catch (Exception e){
				e.printStackTrace();
			}
			try{
				pregledForUpdate.setZdravstveniKarton(zdrrepo.findById(t.getZdravstveniKarton().getId()).orElseThrow(NotFoundException::new));
			}catch (Exception e){
				e.printStackTrace();
			}
			pregledForUpdate.setRecepti(t.getRecepti());
			pregledForUpdate.setDijagnoza(t.getDijagnoza());
			pregledForUpdate.setOpis(t.getOpis());
			pregledForUpdate.setSala(t.getSala());
		}
		catch (Exception e) {
			throw new NotFoundException();
		}
		return pregledRepository.save(pregledForUpdate);
	}
	@Override
	public void delete(Integer id) {
		pregledRepository.deleteById(id);
		
	}

	@Override
	public List<Pregled> findByKartonId(Integer id) {
		return pregledRepository.findByIdKarton(id);
	}
	
	public List<Pregled> findByKartonIdOdradjen(Integer id) {
		return pregledRepository.findByIdKartonOdradjen(id);
	}


	@Override
	public Collection<Pregled> findPreglediKlinike(Integer idKlinike) {
		return pregledRepository.getPreglediKlinike(idKlinike);
	}
	
	@Override
	public Collection<Pregled> findPredefinisaniPreglediKlinike(Integer idKlinike) {
		return pregledRepository.getPredefinisaniPreglediKlinike(idKlinike);
	}
	
	@Override
	public List<ZakazaniPregledDTO> getBuduciPotvrdjeniPregledi(Integer id){
		Date now = new Date();
		List<Pregled> pregledi = pregledRepository.getBuduciPotvrdjeniPregledi(id, now);
		List<ZakazaniPregledDTO> zakazani = new ArrayList<ZakazaniPregledDTO>();
		for (Pregled p : pregledi) {
			Pregled pr = pregledRepository.getOne(p.getId());
			Klinika k = klinikaRepo.findByLekar(pr.getLekar().getId());
			//Lekar ll = lekarRepo.getOne(pr.getLekar().getId());
			//System.out.println(ll.getKlinika());
			Cenovnik c = k.getCenovnik();
			zakazani.add(new ZakazaniPregledDTO(p, c));
		}
		return zakazani;
	}

	@Override
	public Collection<Pregled> findByLekar(Lekar lekar) {
		return pregledRepository.findByLekar(lekar);
	}

	@Override
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
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Boolean zakaziPregled(ZakazivanjePregledaDTO zahtjev) {
		Klinika k = klinikaRepo.findById(zahtjev.getIdKlinike()).orElseGet(null);
		if(k == null)
			return false;
		Pregled pregled = new Pregled();
		pregled.setPotvrdjen(false);
		pregled.setOdradjen(false);
		pregled.setRecepti(new ArrayList<Recepti>());
		pregled.setDijagnoza(new ArrayList<Dijagnoza>());
		Pacijent p = pacijentRepo.findById(zahtjev.getIdPacijenta()).orElseGet(null);
		pregled.setZdravstveniKarton(p.getZdravstveniKarton());
		TipPosete tp = tipRepo.findByNaziv(zahtjev.getNazivTipa());
		pregled.setTipPosete(tp);   //falice sala
		Termin termin = new Termin();
		termin.setPocetak(zahtjev.getTerminPocetak());
		Calendar c = Calendar.getInstance();
		c.setTime(zahtjev.getTerminPocetak());
		c.add(Calendar.MINUTE, 30);
		termin.setKraj(c.getTime());
		Lekar l = null;
		try {
			l = lekarRepo.findOneById(zahtjev.getIdLekara());		
		} catch (/*PessimisticLockingFailureException*/Exception e) {
			return false;
		}
		if (lekarZauzetZaTermin(l, termin)) {
			return false;
		}
		pregled.setLekar(l);
		pregled.setTermin(termin);
		k.getPregledi().add(pregled);
		l.getPregled().add(pregled);
		p.getZdravstveniKarton().getPregled().add(pregled);
		
		//Pregled pr = create(pregled);
		Pregled pr = create(pregled,l);
		System.out.println(pr.getId() + "PREGLED");

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
	
	
	Boolean lekarZauzetZaTermin(Lekar l, Termin t) {
		if (l.getGodisnjiOdmor() != null) {
			for (GodisnjiOdmor go : l.getGodisnjiOdmor()) {
				if ((t.getPocetak().after(go.getPocetak()) || t.getPocetak().equals(go.getPocetak())) && (t.getPocetak().before(go.getKraj()) || t.getPocetak().equals(go.getKraj()))) {
					return true;
				}
			}
		}
		ArrayList<RadnoVreme> radnaVremena = new ArrayList<RadnoVreme>(l.getRadnoVreme());
		Date pocetak = radnaVremena.get(l.getRadnoVreme().size()-1).getPocetak();
		Date kraj = radnaVremena.get(l.getRadnoVreme().size()-1).getKraj();
		Calendar pocetakRadnogVremena = Calendar.getInstance();
		pocetakRadnogVremena.setTime(pocetak);
		Calendar krajRadnogVremena = Calendar.getInstance();
		krajRadnogVremena.setTime(kraj);
		Calendar pocetakTermina = Calendar.getInstance();
		pocetakTermina.setTime(t.getPocetak());
		Calendar krajTermina = Calendar.getInstance();
		krajTermina.setTime(t.getKraj());
		if (pocetakTermina.get(Calendar.DAY_OF_WEEK) == 0 || pocetakTermina.get(Calendar.DAY_OF_WEEK) == 1) {
			return true;
		}
		pocetakRadnogVremena.set(Calendar.YEAR, 0); pocetakRadnogVremena.set(Calendar.MONTH, 0); pocetakRadnogVremena.set(Calendar.DAY_OF_MONTH, 0);
		krajRadnogVremena.set(Calendar.YEAR, 0); krajRadnogVremena.set(Calendar.MONTH, 0); krajRadnogVremena.set(Calendar.DAY_OF_MONTH, 0);
		pocetakTermina.set(Calendar.YEAR, 0); pocetakTermina.set(Calendar.MONTH, 0); pocetakTermina.set(Calendar.DAY_OF_MONTH, 0);
		krajTermina.set(Calendar.YEAR, 0); krajTermina.set(Calendar.MONTH, 0); krajTermina.set(Calendar.DAY_OF_MONTH, 0);
		if (pocetakTermina.getTime().before(pocetakRadnogVremena.getTime()) || krajTermina.getTime().after(krajRadnogVremena.getTime())) {
			return true;
		}
		for (Pregled p : l.getPregled()) {
			if ( ( !p.getTermin().getPocetak().after(t.getPocetak() )
				&& p.getTermin().getKraj().after(t.getPocetak()) )
			|| ( p.getTermin().getPocetak().after(t.getPocetak()) && p.getTermin().getPocetak().before(t.getKraj()) ) ){
				return true;
			}
		}
		for (Operacija p : l.getOperacije()) {
			if (((p.getTermin().getPocetak().before(t.getPocetak()) || p.getTermin().getPocetak().equals(t.getPocetak()))
				&& (p.getTermin().getKraj().after(t.getPocetak())))
			|| (p.getTermin().getPocetak().after(t.getPocetak()) && (p.getTermin().getPocetak().before(t.getKraj())))){
				return true;
			}
		}
		return false;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Boolean zakaziPredefinisaniPregled(ZakazivanjePregledaDTO zahtjev, int idZk, String email) throws NotFoundException {
		Klinika k = klinikaRepo.findById(zahtjev.getIdKlinike()).orElseGet(null);
		if(k == null)
			return null;
		Pregled prDef = findOne(zahtjev.getIdPredefinisanogTermina());
		if (prDef == null) {
			return false;
		}
		if (prDef.getZdravstveniKarton() != null) {
			return false;
		}
		//ZdravstveniKarton zk = kartonService.findOne(idZk);
		ZdravstveniKarton zk = kartonRepo.findById(idZk).orElseGet(null);
		prDef.setZdravstveniKarton(zk);
		prDef.setPotvrdjen(true);
		try {
			Pregled prDef1 = update(zahtjev.getIdPredefinisanogTermina(), prDef);
		} catch (Exception e) {
			return false;
		}


		//send mail
		String subject1 = "Potvrda o zakazivanju pregleda";
		String message1 = "Vas pregled je zakazan.";

		SimpleMailMessage email1 = new SimpleMailMessage();
		email1.setSubject(subject1);
		email1.setText(message1);
		email1.setTo(email);
		mailSender.send(email1);
		return true;
	}
	
	public Collection<PredefinisaniPregledDTO> getPredefinisaniPregledi(int idKlinike){
		Collection<Pregled> pregledi = findPredefinisaniPreglediKlinike(idKlinike);
		Collection<PredefinisaniPregledDTO> posete = new ArrayList<PredefinisaniPregledDTO>();
		Klinika k = klinikaRepo.findById(idKlinike).orElseGet(null);
		if(k == null)
			return null;
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

	@Override
	public Pregled create(Pregled t) throws NotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
	
}



