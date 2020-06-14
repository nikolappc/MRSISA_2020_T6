package isamrs.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

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

import isamrs.domain.AdministratorKlinike;
import isamrs.domain.Lekar;
import isamrs.domain.TipPosete;
import isamrs.service.TipPoseteService;
@RestController
@RequestMapping("/tip")
public class TipPoseteController {
	
	@Autowired
	private TipPoseteService tipService;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<TipPosete>> getTipovi(HttpServletRequest req) {
		if ((req.getSession().getAttribute("user") instanceof AdministratorKlinike)) {
			AdministratorKlinike ak= (AdministratorKlinike) req.getSession().getAttribute("user");
			
			Collection<TipPosete> tipovi = tipService.findAll(ak.getId());
			
			return new ResponseEntity<Collection<TipPosete>>(tipovi, HttpStatus.OK);
		}
		else if ((req.getSession().getAttribute("user") instanceof Lekar)) {
			Lekar l= (Lekar) req.getSession().getAttribute("user");
			
			Collection<TipPosete> tipovi = tipService.findAllLekar(l.getId());
			
			return new ResponseEntity<Collection<TipPosete>>(tipovi, HttpStatus.OK);
		}
		else {
			Collection<TipPosete> tipovi = tipService.findAll();
			return new ResponseEntity<Collection<TipPosete>>(tipovi, HttpStatus.OK);
		}
	}
	
	@GetMapping(value = "/tipoviPregleda", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<TipPosete>> getTipoviPregleda() {
		//autorizacija
		Collection<TipPosete> tipovi = tipService.findPregledi();
		return new ResponseEntity<Collection<TipPosete>>(tipovi, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TipPosete> getTip(@PathVariable("id") Integer id) {
		//autorizacija
		TipPosete tip = tipService.findOne(id);

		if (tip == null) {
			return new ResponseEntity<TipPosete>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<TipPosete>(tip, HttpStatus.OK);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TipPosete> createTipPosete(@RequestBody TipPosete tip, HttpServletRequest req) throws Exception {
		if (!(req.getSession().getAttribute("user") instanceof AdministratorKlinike)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		AdministratorKlinike ak;
		try {
			ak = (AdministratorKlinike) req.getSession().getAttribute("user");
		}
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		TipPosete savedTipPosete = tipService.create(tip,ak.getId());
		return new ResponseEntity<TipPosete>(savedTipPosete, HttpStatus.CREATED);
	}


	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TipPosete> updateTipPosete(HttpServletRequest req,@RequestBody TipPosete tip, @PathVariable Integer id) {
		if (!(req.getSession().getAttribute("user") instanceof AdministratorKlinike)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		TipPosete updatedTipPosete = null;
		
		try {
			updatedTipPosete = tipService.update(id,tip);
		} catch (Exception e) {
			return new ResponseEntity<TipPosete>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<TipPosete>(updatedTipPosete, HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<TipPosete> deleteTipPosete(HttpServletRequest req,@PathVariable("id") Integer id) {
		if (!(req.getSession().getAttribute("user") instanceof AdministratorKlinike)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		try {
			tipService.delete(id);
		} catch (Exception e) {
			return new ResponseEntity<TipPosete>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<TipPosete>(HttpStatus.NO_CONTENT);
	}
	
}
