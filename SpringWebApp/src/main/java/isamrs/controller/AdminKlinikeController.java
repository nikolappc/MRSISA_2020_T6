package isamrs.controller;

import isamrs.domain.*;
import isamrs.dto.AdminKlinikeDTO;
import isamrs.exceptions.LekarZauzetException;
import isamrs.exceptions.NotFoundException;
import isamrs.exceptions.SalaZauzetaException;
import isamrs.service.AdministratorKlinikeService;
import isamrs.service.AdresaService;
import isamrs.service.KlinikaServiceImpl;
import isamrs.service.OperacijaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

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
    private AdministratorKlinikeService adminService;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AdministratorKlinike>> getAdministratoriKlinike(){
        Collection<AdministratorKlinike> col = administratorKlinikeService.findAll();
        return new ResponseEntity<>(col, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdministratorKlinike> getOne(@PathVariable("id") Integer id){
        try{
            AdministratorKlinike administratorKlinike = administratorKlinikeService.findOne(id);
            return new ResponseEntity<>(administratorKlinike, HttpStatus.OK);
        }catch (NotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdministratorKlinike> createAdminKlinike(@RequestBody AdminKlinikeDTO admin){

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
    public ResponseEntity<AdministratorKlinike> updateAdminKlinike(@PathVariable("id") Integer id,@RequestBody AdminKlinikeDTO admin){
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
    public ResponseEntity<String> delete(@PathVariable("id") Integer id){
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
	public ResponseEntity<Collection<Integer>> getSalas() {
		Collection<Integer> pregledi = adminService.findAllZahteviPregleda();
		return new ResponseEntity<Collection<Integer>>(pregledi, HttpStatus.OK);
	}

    @GetMapping(value = "/operacija",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Integer>> getOperacije() {
        Collection<Integer> operacije = adminService.findAllZahteviOperacija();
        return new ResponseEntity<Collection<Integer>>(operacije, HttpStatus.OK);
    }


	@GetMapping(value = "/pregled/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pregled> getPregled(@PathVariable("id") Integer id) {
		Pregled pregled = adminService.findPregled(id);

		if (pregled == null)
			return new ResponseEntity<Pregled>(HttpStatus.NOT_FOUND);

		if (pregled.getSala() != null)
			return new ResponseEntity<Pregled>(HttpStatus.BAD_REQUEST);

		return new ResponseEntity<Pregled>(pregled, HttpStatus.OK);
	}

	@PutMapping(value = "/pregled/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pregled> updateSala(@RequestBody Pregled pregled, @PathVariable Integer id) throws NotFoundException {


		Pregled updatePregled = null;

		try {
			updatePregled = adminService.update(id,pregled);
		}
		catch (Exception e) {
			return new ResponseEntity<Pregled>(HttpStatus.BAD_REQUEST);
		}


		return new ResponseEntity<Pregled>(updatePregled, HttpStatus.OK);
	}


    @GetMapping(value = "/operacija/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Operacija> getOperacija(@PathVariable("id") Integer id) {
        Operacija operacija = adminService.findOperacija(id);

        if (operacija == null)
            return new ResponseEntity<Operacija>(HttpStatus.NOT_FOUND);

        if (operacija.getSala() != null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(operacija, HttpStatus.OK);
    }

    @PutMapping(value = "/operacija/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Operacija> updateSalaOperacija(@RequestBody Operacija operacija, @PathVariable Integer id) throws Exception, SalaZauzetaException, LekarZauzetException {


        Operacija updateOperacija = null;

        updateOperacija = adminService.update(id,operacija);
        return new ResponseEntity<Operacija>(updateOperacija, HttpStatus.OK);
    }

    @PostMapping(value = "/lekar", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Lekar>> getSlobodniLekari(@RequestBody Termin termin){
        Collection<Lekar> lekari = adminService.getSlobodniLekari(termin);
        return new ResponseEntity<>(lekari, HttpStatus.OK);
    }
}

