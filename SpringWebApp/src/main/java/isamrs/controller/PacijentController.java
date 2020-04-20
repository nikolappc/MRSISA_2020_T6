package isamrs.controller;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import isamrs.domain.Pacijent;
import isamrs.service.PacijentService;


	
@RestController
@RequestMapping("/api/pacijent")
public class PacijentController {
	
	@Autowired
	private PacijentService pacijentService;

	@GetMapping(value = "/ulogovan", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pacijent> getPacijent() {
		Pacijent pacijent = pacijentService.getPacijent();
		return new ResponseEntity<Pacijent>(pacijent, HttpStatus.OK);
	}
	
	@PostMapping(value = "/izmjena", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pacijent> updatePacijent(@RequestBody Pacijent pacijent) {
		Pacijent p = pacijentService.findOne(pacijent.getId());

		if (p == null) {
			return new ResponseEntity<Pacijent>(p, HttpStatus.BAD_REQUEST);
		}

		p.setIme(pacijent.getIme());
		p.setPrezime(pacijent.getPrezime());
		p.setPassword(pacijent.getPassword());
		p.setBrojTelefona(pacijent.getBrojTelefona());
		p.setJbo(pacijent.getJbo());
		p.setAdresa(pacijent.getAdresa());

		p = pacijentService.save(p);
		
		pacijentService.izmijeniUlogovanog(pacijent);
		
		return new ResponseEntity<Pacijent>(p, HttpStatus.OK);
	
	}

}
