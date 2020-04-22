package isamrs.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isamrs.domain.Klinika;
import isamrs.service.KlinikaServiceImpl;

@RestController
@RequestMapping("/klinika")
public class KlinikaController {
	@Autowired
	private KlinikaServiceImpl klinikaService;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Klinika>> getKlinike(){
		Collection<Klinika> klinike = klinikaService.findAll();
		return new ResponseEntity<Collection<Klinika>>(klinike, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Klinika> getKlinika(@PathVariable("id") Long id){
		Klinika k = klinikaService.findOne(id);
		if(k==null) {
			return new ResponseEntity<Klinika>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Klinika>(k, HttpStatus.OK);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Klinika> createKlinika(@RequestBody Klinika k){
		Klinika klinika = klinikaService.create(k);
		
		return new ResponseEntity<Klinika>(klinika, HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Klinika> updateKlinika(@RequestBody Klinika k, @PathVariable("id") Integer id){
		Klinika klinika = klinikaService.update((long)id, k);
		
		return new ResponseEntity<Klinika>(klinika, HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Klinika> deleteKlinika(@PathVariable Long id){
		klinikaService.delete(id);
		return new ResponseEntity<Klinika>(HttpStatus.NO_CONTENT);
	}
}
