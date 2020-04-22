package isamrs.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import isamrs.domain.TipKlinike;
import isamrs.exceptions.NotFoundException;
import isamrs.service.TipKlinikeService;

@Controller
@RequestMapping("/tipKlinike")
public class TipKlinikeController {
	@Autowired
	private TipKlinikeService service;
	
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TipKlinike> getTipKlinike(@PathVariable("id")Long id){
		try {
			TipKlinike t = service.findOne(id);
			return new ResponseEntity<TipKlinike>(t, HttpStatus.FOUND);
		}catch (NotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tip Klinike sa id-jem: "+id+ " nije pronadjen.", e);
		}
	}

	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<TipKlinike>> getTipoviKlinike(){
		Collection<TipKlinike> tipovi = service.findAll();
		return new ResponseEntity<Collection<TipKlinike>>(tipovi, HttpStatus.OK);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TipKlinike> createTipKlinike(@RequestBody TipKlinike t){
		TipKlinike t2 = service.create(t);
		return new ResponseEntity<TipKlinike>(t2, HttpStatus.OK);
	}
	
	@PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TipKlinike> updateTipKlinike(@PathVariable("id") Long id,@RequestBody TipKlinike t){
		TipKlinike t2 = service.update(id, t);
		return new ResponseEntity<TipKlinike>(t2, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<TipKlinike> deleteTipKlinike(@PathVariable("id") Long id){
		service.delete(id);
		return new ResponseEntity<TipKlinike>(HttpStatus.OK);
	}
}
