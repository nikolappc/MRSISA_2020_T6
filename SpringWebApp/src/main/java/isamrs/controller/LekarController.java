package isamrs.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;

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
import isamrs.domain.Lekar;
import isamrs.dto.LekarZaPacijentaDTO;
import isamrs.dto.OsobaDTO;
import isamrs.dto.SlobodniLekariKlinikeDTO;
import isamrs.service.KlinikaServiceImpl;
import isamrs.service.LekarService;
@RestController
@RequestMapping("/lekar")
public class LekarController {
	
	@Autowired
	private LekarService lekarService;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<OsobaDTO>> getLekars() {
		Collection<OsobaDTO> lekari = lekarService.findAll();
		return new ResponseEntity<Collection<OsobaDTO>>(lekari, HttpStatus.OK);
	}


	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Lekar> getLekar(@PathVariable("id") Integer id) {
		Lekar lekar = lekarService.findOne(id);

		if (lekar == null) {
			return new ResponseEntity<Lekar>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Lekar>(lekar, HttpStatus.OK);
	}
	
	
	
	@PostMapping(value = "/pocetak/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HashMap<String, Boolean>> checkStart(@RequestBody Lekar lekar, @PathVariable Integer id)
			throws Exception {
		
		boolean check = lekarService.checkStart(id,lekar);
		HashMap<String, Boolean> mapa = new HashMap<String, Boolean>();	
		mapa.put("zapocni", check);
		return new ResponseEntity<HashMap<String,Boolean>>(mapa, HttpStatus.OK);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Lekar> createLekar(@RequestBody Lekar lekar) throws Exception {
		Lekar savedLekar = lekarService.create(lekar);
		return new ResponseEntity<Lekar>(savedLekar, HttpStatus.CREATED);
	}


	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Lekar> updateLekar(@RequestBody Lekar lekar, @PathVariable Integer id)
			throws Exception {
		
		Lekar updatedLekar = lekarService.update(id,lekar);
		return new ResponseEntity<Lekar>(updatedLekar, HttpStatus.OK);
	}

	/*
	 * url: /api/Lekars/1 DELETE
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Lekar> deleteLekar(@PathVariable("id") Integer id) {
		try {
			lekarService.delete(id);
		} catch (Exception e) {
			return new ResponseEntity<Lekar>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<Lekar>(HttpStatus.NO_CONTENT);
	}
	
	
	
	
}
