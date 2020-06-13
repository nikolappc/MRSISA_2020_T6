
package isamrs.controller;

import isamrs.domain.*;
import isamrs.dto.AdminKlinikeDTO;
import isamrs.dto.GrafDTO;
import isamrs.dto.IzvestajDTO;
import isamrs.dto.PosetaPacijentDTO;
import isamrs.exceptions.LekarZauzetException;
import isamrs.exceptions.NotFoundException;
import isamrs.exceptions.SalaZauzetaException;
import isamrs.registracija.VerificationToken;
import isamrs.repository.KlinikaRepository;
import isamrs.service.AdministratorKlinikeService;
import isamrs.service.KlinikaServiceImpl;
import isamrs.service.OperacijaService;
import isamrs.service.PregledServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/adminKlinike")
public class AdminKlinikeController {

    @Autowired
    AdministratorKlinikeService administratorKlinikeService;

    @Autowired
    KlinikaServiceImpl klinikaService;

    @Autowired
    OperacijaService operacijaService;
    
    @Autowired
    PregledServiceImpl pregledService;

    @Autowired
    private AdministratorKlinikeService adminService;
    
    @Autowired 
    KlinikaRepository klinikaRepo;
    
    @Autowired
	private MailSender mailSender;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AdministratorKlinike>> getAdministratoriKlinike(HttpServletRequest req){
    	if (!(req.getSession().getAttribute("user") instanceof AdministratorKlinickogCentra)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
        Collection<AdministratorKlinike> col = administratorKlinikeService.findAll();
        return new ResponseEntity<>(col, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdministratorKlinike> getOne(@PathVariable("id") Integer id, HttpServletRequest req){
    	if (!(req.getSession().getAttribute("user") instanceof AdministratorKlinike || 
    			req.getSession().getAttribute("user") instanceof AdministratorKlinickogCentra)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
        try{
            AdministratorKlinike administratorKlinike = administratorKlinikeService.findOne(id);
            return new ResponseEntity<>(administratorKlinike, HttpStatus.OK);
        }catch (NotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdministratorKlinike> createAdminKlinike(@RequestBody AdminKlinikeDTO admin, HttpServletRequest req){
    	if (!(req.getSession().getAttribute("user") instanceof AdministratorKlinickogCentra)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
        AdministratorKlinike ak = toEntity(admin);
        if(admin.getKlinika()!=null){
            try{
                 Klinika k = klinikaService.findOne(admin.getKlinika());
                 ak.setKlinika(k);
//                 Adresa a = adresaService.createAdresa(ak.getAdresa());
//                 ak.setAdresa(a);
            }catch (NotFoundException nfe){
                nfe.printStackTrace();
            }
        }
        AdministratorKlinike a = administratorKlinikeService.create(ak);
        return new ResponseEntity<>(a, HttpStatus.OK);
    }
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdministratorKlinike> updateAdminKlinike(HttpServletRequest req,@PathVariable("id") Integer id,@RequestBody AdminKlinikeDTO admin){
    	if (!(req.getSession().getAttribute("user") instanceof AdministratorKlinike || 
    			req.getSession().getAttribute("user") instanceof AdministratorKlinickogCentra)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
    	
    	AdministratorKlinike ak = toEntity(admin);
        if(admin.getKlinika()!=null){
            try{
                Klinika k = klinikaService.findOne(admin.getKlinika());
                ak.setKlinika(k);
            }catch (NotFoundException nfe){
                nfe.printStackTrace();
            }
        }
        AdministratorKlinike a = administratorKlinikeService.update(id,ak);
        return new ResponseEntity<>(a, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(HttpServletRequest req, @PathVariable("id") Integer id){
    	if (!(req.getSession().getAttribute("user") instanceof AdministratorKlinickogCentra)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
        administratorKlinikeService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    AdministratorKlinike toEntity(AdminKlinikeDTO adminKlinikeDTO){

        return new AdministratorKlinike(adminKlinikeDTO.getIme()
                , adminKlinikeDTO.getPrezime()
                , adminKlinikeDTO.getPassword()
                , adminKlinikeDTO.getBrojTelefona()
                , adminKlinikeDTO.getJbo()
                , adminKlinikeDTO.getAdresa()
                , adminKlinikeDTO.getEmail());
    }
	@GetMapping(value = "/pregled",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<PosetaPacijentDTO>> getSalas(HttpServletRequest req) {
		if (!(req.getSession().getAttribute("user") instanceof AdministratorKlinike)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		Collection<PosetaPacijentDTO> pregledi = adminService.findAllZahteviPregleda();
		return new ResponseEntity<>(pregledi, HttpStatus.OK);
	}

    @GetMapping(value = "/operacija",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<PosetaPacijentDTO>> getOperacije(HttpServletRequest req) {
    	if (!(req.getSession().getAttribute("user") instanceof AdministratorKlinike)){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
        Collection<PosetaPacijentDTO> operacije = adminService.findAllZahteviOperacija();
        return new ResponseEntity<>(operacije, HttpStatus.OK);
    }


	@GetMapping(value = "/pregled/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pregled> getPregled(HttpServletRequest req,@PathVariable("id") Integer id) {
		if (!(req.getSession().getAttribute("user") instanceof AdministratorKlinike)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		Pregled pregled = adminService.findPregled(id);

		if (pregled == null)
			return new ResponseEntity<Pregled>(HttpStatus.NOT_FOUND);

		if (pregled.getSala() != null)
			return new ResponseEntity<Pregled>(HttpStatus.BAD_REQUEST);

		return new ResponseEntity<Pregled>(pregled, HttpStatus.OK);
	}

	@PutMapping(value = "/pregled/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pregled> updateSala(HttpServletRequest req,@RequestBody Pregled pregled, @PathVariable Integer id) throws NotFoundException {
		if (!(req.getSession().getAttribute("user") instanceof AdministratorKlinike)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		Pregled updatePregled = null;

		try {
			
			updatePregled = adminService.update(id,pregled);
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
			String subject1 = "Zakazivanje pregleda";
			String link1 = "http://localhost:8080/api/pacijent/potvrdiPregled/" + updatePregled.getId();
			String link2 = "http://localhost:8080/api/pacijent/odbijPregled/" + updatePregled.getId();
			String message1 = "Zakazali ste pregled kod lekara "+updatePregled.getLekar().getIme()+""
					+updatePregled.getLekar().getPrezime()+", u vreme "+sdf.format(updatePregled.getTermin().getPocetak())
					+".\nMolimo Vas da potvrdite zakazivanje pregleda klikom na link "+link1
					+" ili da odbijete klikom na link "+link2;
			SimpleMailMessage email1 = new SimpleMailMessage();
			email1.setSubject(subject1);
			email1.setText(message1);
			String recipient = updatePregled.getZdravstveniKarton().getPacijent().getEmail();
			email1.setTo(recipient);
			mailSender.send(email1);

			
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Pregled>(HttpStatus.BAD_REQUEST);
		}


		return new ResponseEntity<Pregled>(updatePregled, HttpStatus.OK);
	}
	
	
	


    @GetMapping(value = "/operacija/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Operacija> getOperacija(HttpServletRequest req,@PathVariable("id") Integer id) {
    	if (!(req.getSession().getAttribute("user") instanceof AdministratorKlinike)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
        Operacija operacija = adminService.findOperacija(id);

        if (operacija == null)
            return new ResponseEntity<Operacija>(HttpStatus.NOT_FOUND);

        if (operacija.getSala() != null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(operacija, HttpStatus.OK);
    }

    @PutMapping(value = "/operacija/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Operacija> updateSalaOperacija(HttpServletRequest req,@RequestBody Operacija operacija, @PathVariable Integer id) throws Exception, SalaZauzetaException, LekarZauzetException {
    	if (!(req.getSession().getAttribute("user") instanceof AdministratorKlinike)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

        Operacija updateOperacija = null;

        updateOperacija = adminService.update(id,operacija);
        return new ResponseEntity<Operacija>(updateOperacija, HttpStatus.OK);
    }

    @PostMapping(value = "/lekar", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Lekar>> getSlobodniLekari(HttpServletRequest req, @RequestBody Termin termin){
    	if (!(req.getSession().getAttribute("user") instanceof AdministratorKlinike)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
        Collection<Lekar> lekari = adminService.getSlobodniLekari(termin);
        return new ResponseEntity<>(lekari, HttpStatus.OK);
    }
    
    
    @GetMapping(value = "/izvestaj", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IzvestajDTO> getIzvestaj(HttpServletRequest req){
    	if (!(req.getSession().getAttribute("user") instanceof AdministratorKlinike)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
    	AdministratorKlinike ak = (AdministratorKlinike)req.getSession().getAttribute("user");
    	IzvestajDTO izvestaj = adminService.izvestaj(ak.getId());
        return new ResponseEntity<>(izvestaj, HttpStatus.OK);
    }
    
    @GetMapping(value = "/izvestaj/{tip}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GrafDTO> getIzvestajDnevni(HttpServletRequest req, @PathVariable String tip){
    	if (!(req.getSession().getAttribute("user") instanceof AdministratorKlinike)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
    	AdministratorKlinike ak = (AdministratorKlinike)req.getSession().getAttribute("user");
    	GrafDTO izvestaj = adminService.izvestajTip(ak.getId(),tip);
        return new ResponseEntity<>(izvestaj, HttpStatus.OK);
    }
    
    @PostMapping(value = "/troskovi", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HashMap<String,Double>> getTroskovi(HttpServletRequest req, @RequestBody HashMap<String,Date> mapa){
    	if (!(req.getSession().getAttribute("user") instanceof AdministratorKlinike)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
    	AdministratorKlinike ak = (AdministratorKlinike)req.getSession().getAttribute("user");
    	
        Double troskovi = adminService.troskovi(ak.getId(), mapa.get("pocetak"), mapa.get("kraj"));
        HashMap<String,Double> a = new HashMap<String,Double>();
        a.put("trosak", troskovi);
        return new ResponseEntity<HashMap<String,Double>>(a, HttpStatus.OK);
    }
    
}

