package isamrs.controller;

import java.util.Calendar;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import isamrs.dto.*;
import isamrs.dto.post.RazlogOdbijanja;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import isamrs.domain.ZdravstveniKarton;
import isamrs.registracija.OnRegistrationFailEvent;
import isamrs.registracija.OnRegistrationSuccessEvent;
import isamrs.registracija.VerificationToken;
import isamrs.service.AdministratorKlinickogCentraService;
import isamrs.service.AdministratorKlinikeService;
import isamrs.service.LekarService;
import isamrs.service.MedicinskaSestraService;
import isamrs.service.PacijentServiceImpl;
import isamrs.service.ZdravstveniKartonServiceImpl;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private PacijentServiceImpl pacijentService;
	@Autowired
	private LekarService lekarService;
	@Autowired
	private MedicinskaSestraService sestraService;
	@Autowired
	private AdministratorKlinikeService adminKlinikeService;
	@Autowired
	private ZdravstveniKartonServiceImpl kartonService;
	@Autowired
	private AdministratorKlinickogCentraService adminKCService;

	@Autowired
	ApplicationEventPublisher eventPublisher;
	
	public static String adresa = "";

	@PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Osoba> loginUser(HttpServletRequest req, @RequestBody UserDTO user) {
		if (req.getSession().getAttribute("user") != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vec je ulogovan korisnik.");
		}
		adresa = req.getRequestURL().toString().split("://")[0] + "://" + req.getRequestURL().toString().split("://")[1].split("/")[0];
		AdministratorKlinickogCentra akc = adminKCService.findByEmail(user.getUsername());
		if (akc != null) {
			if (akc.getPassword().equals(user.getPassword())) {
				req.getSession().setAttribute("user", akc);
				return new ResponseEntity<Osoba>(akc, HttpStatus.OK);
			}
		}
		AdministratorKlinike ak = adminKlinikeService.findByEmail(user.getUsername());
		if (ak != null) {
			if (ak.getPassword().equals(user.getPassword())) {
				req.getSession().setAttribute("user", ak);
				return new ResponseEntity<Osoba>(ak, HttpStatus.OK);
			}
		}
		MedicinskaSestra ms = sestraService.findByEmail(user.getUsername());
		if (ms != null) {
			if (ms.getPassword().equals(user.getPassword())) {
				req.getSession().setAttribute("user", ms);
				return new ResponseEntity<Osoba>(ms, HttpStatus.OK);
			}
		}
		Lekar l = lekarService.findByEmail(user.getUsername());
		if (l != null) {
			if (l.getPassword().equals(user.getPassword())) {
				req.getSession().setAttribute("user", l);
				return new ResponseEntity<Osoba>(l, HttpStatus.OK);
			}
		}
		Pacijent p = pacijentService.findByEmail(user.getUsername());
		if (p != null) {
			if (p.getPassword().equals(user.getPassword()) && p.isAllowed()) {
				req.getSession().setAttribute("user", p);
				return new ResponseEntity<Osoba>(p, HttpStatus.OK);
			}
		}
		adresa = "";
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Korisnik ne postoji.");

	}

	@GetMapping(value = "/ulogovan", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Osoba> getUlogovani(HttpServletRequest req) {

		Osoba osoba = (Osoba) req.getSession().getAttribute("user");

		if (osoba instanceof Pacijent) {
			return new ResponseEntity<Osoba>((Pacijent) osoba, HttpStatus.OK);
		} else if (osoba instanceof Lekar) {
			return new ResponseEntity<Osoba>((Lekar) osoba, HttpStatus.OK);
		} else if (osoba instanceof MedicinskaSestra) {
			return new ResponseEntity<Osoba>((MedicinskaSestra) osoba, HttpStatus.OK);
		} else if (osoba instanceof AdministratorKlinike) {
			return new ResponseEntity<Osoba>((AdministratorKlinike) osoba, HttpStatus.OK);
		} else if (osoba instanceof AdministratorKlinickogCentra) {
			return new ResponseEntity<Osoba>((AdministratorKlinickogCentra) osoba, HttpStatus.OK);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nema ulogovanog korisnika.");
		}
	}

	@GetMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> logout(HttpServletRequest req) {
		req.getSession().invalidate();
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}

	@PostMapping(value = "/registracija", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> registracija(HttpServletRequest req, HttpServletResponse res,
			@RequestBody RegDTO reg) {
		if (req.getSession().getAttribute("user") != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vec je ulogovan korisnik.");
		}

		AdministratorKlinickogCentra akc = adminKCService.findByEmail(reg.getEmail());
		if (akc != null) {
			return new ResponseEntity<String>("Već postoji registrovan nalog sa ovom email adresom.",
					HttpStatus.BAD_REQUEST);
		}
		AdministratorKlinike ak = adminKlinikeService.findByEmail(reg.getEmail());
		if (ak != null) {
			return new ResponseEntity<String>("Već postoji registrovan nalog sa ovom email adresom.",
					HttpStatus.BAD_REQUEST);
		}
		MedicinskaSestra ms = sestraService.findByEmail(reg.getEmail());
		if (ms != null) {
			return new ResponseEntity<String>("Već postoji registrovan nalog sa ovom email adresom.",
					HttpStatus.BAD_REQUEST);
		}
		Lekar l = lekarService.findByEmail(reg.getEmail());
		if (l != null) {
			return new ResponseEntity<String>("Već postoji registrovan nalog sa ovom email adresom.",
					HttpStatus.BAD_REQUEST);
		}
		Pacijent p = pacijentService.findByEmail(reg.getEmail());
		if (p != null) {
			return new ResponseEntity<String>("Već postoji registrovan nalog sa ovom email adresom.",
					HttpStatus.BAD_REQUEST);
		}

		Pacijent pac = new Pacijent();
		pac.setEmail(reg.getEmail());
		pac.setPassword(reg.getPassword());
		pac.setIme(reg.getIme());
		pac.setPrezime(reg.getPrezime());
		pac.setBrojTelefona(reg.getBrojTelefona());
		pac.setJbo(reg.getJbo());
		pac.setAdresa(reg.getAdresa());
		pac.setAllowed(false);
		pac.setResponded(false);
		pacijentService.create(pac);

		return new ResponseEntity<String>("Uspjesno poslat zahtjev.", HttpStatus.OK);
	}

	@GetMapping(value = "/approveRegistration/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> approveRegistration(@PathVariable("email") String email, WebRequest request, HttpServletRequest httpServletRequest) {
		if (!(httpServletRequest.getSession().getAttribute("user") instanceof AdministratorKlinickogCentra)){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		try {
			System.out.println("email:" + email);
			Pacijent pacijent = pacijentService.respond(email);

			eventPublisher.publishEvent(new OnRegistrationSuccessEvent(pacijent, request.getContextPath()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("Uspešno odobrena registracija.", HttpStatus.OK);
	}

	@PostMapping(value = "/denyRegistration/{email}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> denyRegistration(@PathVariable("email") String email, @RequestBody RazlogOdbijanja razlg, WebRequest request, HttpServletRequest httpServletRequest) {
		if (!(httpServletRequest.getSession().getAttribute("user") instanceof AdministratorKlinickogCentra)){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		try {
			Pacijent pacijent = pacijentService.findByEmail(email);
			pacijentService.setOdobren(email, false);
			eventPublisher.publishEvent(new OnRegistrationFailEvent(pacijent, request.getContextPath(), razlg.getRazlog()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("Uspešno odbijena registracija.", HttpStatus.OK);
	}

	@GetMapping(value = "/confirmRegistration", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> confirmRegistration(WebRequest request, @RequestParam("token") String token) {
		VerificationToken verificationToken = pacijentService.getVerificationToken(token);
		if (verificationToken == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nevalidan token.");
		}
		Pacijent user = verificationToken.getUser();
		Calendar calendar = Calendar.getInstance();
		if ((verificationToken.getExpiryDate().getTime() - calendar.getTime().getTime()) <= 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Isteklo je vrijeme za potvrdu registracije.");
		}
		pacijentService.setOdobren(user.getEmail(), true);
		ZdravstveniKarton zk = new ZdravstveniKarton();
		kartonService.create(zk);
		user.setZdravstveniKarton(zk);
		pacijentService.save(user);
		return new ResponseEntity<String>("Uspešna registracija!", HttpStatus.OK);
	}

	// Sluzi za dobavljanje nepotvrdjenih registrovanih korisnika
	@GetMapping(value = "/nepotvrdjeni", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<PacijentDTO>> getNepotvrdjeni(HttpServletRequest httpServletRequest) {
		if (!(httpServletRequest.getSession().getAttribute("user") instanceof AdministratorKlinickogCentra)){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		Collection<PacijentDTO> pac = pacijentService.findNotConfirmed().stream().map(PacijentDTO::new).collect(Collectors.toList());
		return new ResponseEntity<>(pac, HttpStatus.OK);
	}
	
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Osoba> updateUser(HttpServletRequest req, @RequestBody Osoba osoba, @PathVariable Integer id, HttpServletRequest httpServletRequest){
		if (httpServletRequest.getSession().getAttribute("user")==null){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		
		Osoba updatedOsoba = null;
		try {
			switch (osoba.getTip()) {
			case "ADMIN_KC":
				updatedOsoba = adminKCService.update(id, (AdministratorKlinickogCentra)osoba);
				break;
			case "ADMIN_K":
				updatedOsoba = adminKlinikeService.update(id, (AdministratorKlinike)osoba);
				break;
			case "LEKAR":
				updatedOsoba = lekarService.updateUser(id, (Lekar)osoba);
				break;
			case "SESTRA":
				updatedOsoba = sestraService.update(id, (MedicinskaSestra)osoba);
				break;
			case "PACIJENT":
				updatedOsoba = pacijentService.update(id, (Pacijent)osoba);
				break;

			default:
				return new ResponseEntity<Osoba>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<Osoba>(HttpStatus.FORBIDDEN);
		}
		req.getSession().setAttribute("user", updatedOsoba);
		return new ResponseEntity<Osoba>(updatedOsoba, HttpStatus.OK);
	}
	
	
}
