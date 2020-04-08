package com.example.demo.controller;

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

import com.example.demo.domain.Lekar;
import com.example.demo.service.LekarServiceImpl;
@RestController
@RequestMapping("/lekar")
public class LekarController {
	
	@Autowired
	private LekarServiceImpl lekarService;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Lekar>> getLekars() {
		Collection<Lekar> lekari = lekarService.findAll();
		return new ResponseEntity<Collection<Lekar>>(lekari, HttpStatus.OK);
	}


	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Lekar> getLekar(@PathVariable("id") Long id) {
		Lekar lekar = lekarService.findOne(id);

		if (lekar == null) {
			return new ResponseEntity<Lekar>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Lekar>(lekar, HttpStatus.OK);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Lekar> createLekar(@RequestBody Lekar lekar) throws Exception {
		Lekar savedLekar = lekarService.create(lekar);
		return new ResponseEntity<Lekar>(savedLekar, HttpStatus.CREATED);
	}


	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Lekar> updateLekar(@RequestBody Lekar lekar, @PathVariable Long id)
			throws Exception {
		
		Lekar updatedLekar = lekarService.update(id,lekar);
		return new ResponseEntity<Lekar>(updatedLekar, HttpStatus.OK);
	}

	/*
	 * url: /api/Lekars/1 DELETE
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Lekar> deleteLekar(@PathVariable("id") Long id) {
		lekarService.delete(id);
		return new ResponseEntity<Lekar>(HttpStatus.NO_CONTENT);
	}
	
}
