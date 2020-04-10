package isamrs.controller;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

}
