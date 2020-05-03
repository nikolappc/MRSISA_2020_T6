package isamrs.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import isamrs.domain.AdministratorKlinickogCentra;
import isamrs.domain.AdministratorKlinike;
import isamrs.domain.Lekar;
import isamrs.domain.MedicinskaSestra;
import isamrs.domain.Osoba;
import isamrs.domain.Pacijent;
import isamrs.domain.Pregled;
import isamrs.domain.ZdravstveniKarton;
import isamrs.dto.PregledDTO;
import isamrs.dto.RegDTO;
import isamrs.dto.UserDTO;
import isamrs.dto.ZdravstveniKartonDTO;
import isamrs.registracija.OnRegistrationFailEvent;
import isamrs.registracija.OnRegistrationSuccessEvent;
import isamrs.registracija.VerificationToken;
import isamrs.service.AdministratorKlinickogCentraService;
import isamrs.service.AdministratorKlinikeService;
import isamrs.service.LekarService;
import isamrs.service.MedicinskaSestraService;
import isamrs.service.PacijentService;
import isamrs.service.PregledService;
import isamrs.service.ZdravstveniKartonServiceImpl;



	
@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private PacijentService pacijentService;
	@Autowired
	private LekarService lekarService;
	@Autowired
	private MedicinskaSestraService sestraService;
	@Autowired
	private AdministratorKlinikeService adminKlinikeService;
	@Autowired 
	private AdministratorKlinickogCentraService adminKCService;

	
	@Autowired
	ApplicationEventPublisher eventPublisher;
	
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Osoba> loginUser(HttpServletRequest req, @RequestBody UserDTO user) {
    	if (req.getSession().getAttribute("user") != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vec je ulogovan korisnik.");
    	}
    	
    	AdministratorKlinickogCentra akc = adminKCService.findByEmail(user.getUsername());
    	if (akc != null) {
        	if (akc.getPassword().equals(user.getPassword())) {
        		System.out.println("naslo akc");
            	req.getSession().setAttribute("user", akc);
        		return new ResponseEntity<Osoba>(akc, HttpStatus.OK);
        	}
    	}
    	AdministratorKlinike ak = adminKlinikeService.findByEmail(user.getUsername());
    	if (ak != null) {
        	if (ak.getPassword().equals(user.getPassword())) {
        		System.out.println("naslo ak");
            	req.getSession().setAttribute("user", ak);
        		return new ResponseEntity<Osoba>(ak, HttpStatus.OK);
        	}
    	}
    	MedicinskaSestra ms = sestraService.findByEmail(user.getUsername());
    	if (ms != null) {
        	if (ms.getPassword().equals(user.getPassword())) {
        		System.out.println("naslo sestru");
            	req.getSession().setAttribute("user", ms);
        		return new ResponseEntity<Osoba>(ms, HttpStatus.OK);
        	}
    	}
    	Lekar l = lekarService.findByEmail(user.getUsername());
    	if (l != null) {
        	if (l.getPassword().equals(user.getPassword())) {
        		System.out.println("naslo ljekara");
            	req.getSession().setAttribute("user", l);
        		return new ResponseEntity<Osoba>(l, HttpStatus.OK);
        	}
    	}
    	Pacijent p = pacijentService.findByEmail(user.getUsername());
    	if (p != null) {
        	if (p.getPassword().equals(user.getPassword()) && p.isEnabled()) {
        		System.out.println("naslo pacijenta");
            	req.getSession().setAttribute("user", p);
        		return new ResponseEntity<Osoba>(p, HttpStatus.OK);
        	}
    	}

    	
    	System.out.println("loggggginfejl" + p.getId());
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Korisnik ne postoji.");

    }
	

	@GetMapping(value = "/ulogovan", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Osoba> getUlogovani(HttpServletRequest req) {
		
		System.out.println("GET");
		Osoba osoba = (Osoba)req.getSession().getAttribute("user");
		System.out.println("GETok");
		System.out.println(osoba.getIme());
		System.out.println(osoba.getId());
		System.out.println(osoba.getPassword());
		
		if (osoba instanceof Pacijent) {
			return new ResponseEntity<Osoba>((Pacijent)osoba, HttpStatus.OK);
		}
		else if (osoba instanceof Lekar) {
			return new ResponseEntity<Osoba>((Lekar)osoba, HttpStatus.OK);
		}
		else if (osoba instanceof MedicinskaSestra) {
			return new ResponseEntity<Osoba>((MedicinskaSestra)osoba, HttpStatus.OK);
		}
		else if (osoba instanceof AdministratorKlinike) {
			return new ResponseEntity<Osoba>((AdministratorKlinike)osoba, HttpStatus.OK);
		}
		else if (osoba instanceof AdministratorKlinickogCentra) {
			return new ResponseEntity<Osoba>((AdministratorKlinickogCentra)osoba, HttpStatus.OK);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nema ulogovanog korisnika.");
		}
	}
	
	@GetMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> logout(HttpServletRequest req) {
		if (req.getSession().getAttribute("user") != null) {
			req.getSession().invalidate();
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		}
		return new ResponseEntity<Boolean>(false, HttpStatus.OK);
	}

	
	@PostMapping(value = "/registracija", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registracija(HttpServletRequest req, HttpServletResponse res, @RequestBody RegDTO reg) {
    	if (req.getSession().getAttribute("user") != null) {
    		System.out.println("sesija");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vec je ulogovan korisnik.");
    	}
    	
    	AdministratorKlinickogCentra akc = adminKCService.findByEmail(reg.getEmail());
    	if (akc != null) {
    		System.out.println("naslo akc");
    		return new ResponseEntity<String>("Vec postoji registrovan nalog sa ovom email adresom.", HttpStatus.BAD_REQUEST);
    	}
    	AdministratorKlinike ak = adminKlinikeService.findByEmail(reg.getEmail());
    	if (ak != null) {
    		System.out.println("naslo ak");
    		return new ResponseEntity<String>("Vec postoji registrovan nalog sa ovom email adresom.", HttpStatus.BAD_REQUEST);
    	}
    	MedicinskaSestra ms = sestraService.findByEmail(reg.getEmail());
    	if (ms != null) {
    		System.out.println("naslo sestru");
    		return new ResponseEntity<String>("Vec postoji registrovan nalog sa ovom email adresom.", HttpStatus.BAD_REQUEST);
    	}
    	Lekar l = lekarService.findByEmail(reg.getEmail());
    	if (l != null) {
    		System.out.println("naslo ljekara");
    		return new ResponseEntity<String>("Vec postoji registrovan nalog sa ovom email adresom.", HttpStatus.BAD_REQUEST);
    	}
    	Pacijent p = pacijentService.findByEmail(reg.getEmail());
    	if (p != null) {
    		System.out.println("naslo pacijenta");
    		return new ResponseEntity<String>("Vec postoji registrovan nalog sa ovom email adresom.", HttpStatus.BAD_REQUEST);
    	}
    	
    	Pacijent pac = new Pacijent();
    	pac.setEmail(reg.getEmail());
    	pac.setPassword(reg.getPassword());
    	pac.setIme(reg.getIme());
    	pac.setPrezime(reg.getPrezime());
    	pac.setBrojTelefona(reg.getBrojTelefona());
    	pac.setEnabled(false);
    	pac.setJbo(reg.getJbo());
    	pac.setAdresa(reg.getAdresa().toUpperCase() + ", " + reg.getGrad().toUpperCase() + ", " + reg.getDrzava().toUpperCase());

    	System.out.println("create");
    	pacijentService.create(pac);
    	
    	System.out.println("uspjeloooooo" + pac.getId() + " " + pac.getAdresa());
		return new ResponseEntity<String>("Uspjesno poslat zahtjev.", HttpStatus.OK);
    }
	
	@GetMapping(value = "/approveRegistration/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> approveRegistration(@PathVariable("email") String email, WebRequest request){
		try {
			System.out.println("email:"+email);
			Pacijent pacijent = pacijentService.findByEmail(email);
			eventPublisher.publishEvent(new OnRegistrationSuccessEvent(pacijent, request.getContextPath()));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("Uspešno odobrena registracija.", HttpStatus.OK);
	}
	
	@GetMapping(value = "/denyRegistration/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> denyRegistration(@PathVariable("email") String email , WebRequest request){
		try {
			Pacijent pacijent = pacijentService.findByEmail(email);
			eventPublisher.publishEvent(new OnRegistrationFailEvent(pacijent, request.getContextPath()));
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("Uspešno odbijena registracija.", HttpStatus.OK);
	}
	
	@GetMapping(value = "/confirmRegistration", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<String> confirmRegistration(WebRequest request, @RequestParam("token") String token) {  
	    VerificationToken verificationToken = pacijentService.getVerificationToken(token);  
	    if (verificationToken == null) {   
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nevalidan token.");
	    }  
	    Pacijent user = verificationToken.getUser();  
	    Calendar calendar = Calendar.getInstance();       
	    if((verificationToken.getExpiryDate().getTime()-calendar.getTime().getTime())<=0) {   
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Isteklo je vrijeme za potvrdu registracije."); 
	    }    
	    user.setEnabled(true);  
	    pacijentService.save(user);      
		return new ResponseEntity<String>("Uspjesna registracija.", HttpStatus.OK);
	}
	
	// Sluzi za dobavljanje nepotvrdjenih registrovanih korisnika
	@GetMapping(value = "/nepotvrdjeni", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Pacijent>> getNepotvrdjeni(){
		Collection<Pacijent> pac = pacijentService.findNotConfirmed();
		return new ResponseEntity<Collection<Pacijent>>(pac, HttpStatus.OK);
	}

	

}
