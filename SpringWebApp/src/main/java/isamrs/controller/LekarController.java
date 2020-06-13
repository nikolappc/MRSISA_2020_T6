package isamrs.controller;

import java.util.Collection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import isamrs.dto.*;
import isamrs.exceptions.NotFoundException;
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
import isamrs.domain.Pacijent;
import isamrs.service.LekarService;
@RestController
@RequestMapping("/lekar")
public class LekarController {
	
	@Autowired
	private LekarService lekarService;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Lekar>> getLekars(HttpServletRequest req) {
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
		Collection<Lekar> lekari = lekarService.findAll(ak.getId());
		return new ResponseEntity<Collection<Lekar>>(lekari, HttpStatus.OK);
	}


	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Lekar> getLekar(HttpServletRequest req ,@PathVariable("id") Integer id) {
		if (!(req.getSession().getAttribute("user") instanceof AdministratorKlinike)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		Lekar lekar = lekarService.findOne(id);

		if (lekar == null) {
			return new ResponseEntity<Lekar>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Lekar>(lekar, HttpStatus.OK);
	}
	
	@GetMapping(value = "/poseta/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<PosetaPacijentDTO>> getPosetaLekar(HttpServletRequest req,@PathVariable("id") Integer id) throws NotFoundException {
		if (!(req.getSession().getAttribute("user") instanceof Lekar)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		Collection<PosetaPacijentDTO> termini = lekarService.findPosete(id);
		
		if (termini == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(termini, HttpStatus.OK);
	}
	@PostMapping(value = "/pocetak/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HashMap<String, Boolean>> checkStart(HttpServletRequest req ,@RequestBody Lekar lekar, @PathVariable Integer id)
			throws Exception {
		if (!(req.getSession().getAttribute("user") instanceof Lekar)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		boolean check = lekarService.checkStart(id,lekar);
		
		HashMap<String, Boolean> mapa = new HashMap<String, Boolean>();	
		mapa.put("zapocni", check);
		return new ResponseEntity<HashMap<String,Boolean>>(mapa, HttpStatus.OK);
	}
	
	@PostMapping(value = "/pocetak/operacija/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HashMap<String, Boolean>> checkStartOperacija(HttpServletRequest req ,@RequestBody Lekar lekar, @PathVariable Integer id)
			throws Exception {
		if (!(req.getSession().getAttribute("user") instanceof Lekar)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		boolean check = lekarService.checkStartOperacija(id, lekar);
		HashMap<String, Boolean> mapa = new HashMap<String, Boolean>();	
		mapa.put("zapocni", check);
		return new ResponseEntity<HashMap<String,Boolean>>(mapa, HttpStatus.OK);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Lekar> createLekar(@RequestBody Lekar lekar, HttpServletRequest req) throws Exception {
		AdministratorKlinike ak = (AdministratorKlinike) req.getSession().getAttribute("user");
		Lekar savedLekar = lekarService.create(lekar,ak.getId());
		return new ResponseEntity<Lekar>(savedLekar, HttpStatus.CREATED);
	}


	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Lekar> updateLekar(HttpServletRequest req ,@RequestBody Lekar lekar, @PathVariable Integer id )
			throws Exception {
		if (!(req.getSession().getAttribute("user") instanceof AdministratorKlinike)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		Lekar updatedLekar = lekarService.update(id,lekar);
		return new ResponseEntity<Lekar>(updatedLekar, HttpStatus.OK);
	}

	/*
	 * url: /api/Lekars/1 DELETE
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Lekar> deleteLekar(HttpServletRequest req ,@PathVariable("id") Integer id) {
		if (!(req.getSession().getAttribute("user") instanceof AdministratorKlinike)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		try {
			lekarService.delete(id);
		} catch (Exception e) {
			return new ResponseEntity<Lekar>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<Lekar>(HttpStatus.NO_CONTENT);
	}
	
	
	@PostMapping(value = "/provjeriDaLiJeLjekarSlobodan", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> provjeriDaLiJeLjekarSlobodan(@RequestBody ProveraLekarSlobodanDTO provera, HttpServletRequest req) throws Exception {
		if (req.getSession().getAttribute("user") == null || !(req.getSession().getAttribute("user") instanceof Pacijent)) {
			return new ResponseEntity<Boolean>(HttpStatus.FORBIDDEN);
		}
		Boolean slobodan = lekarService.proverDaLiJeLekarSlobodan(provera);
		return new ResponseEntity<Boolean>(slobodan, HttpStatus.OK);
	}
	
	@GetMapping(value = "/pacijentPosjetio/{idPacijenta}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GetOcenaDTO> pacijentPosjetioLekara(@PathVariable("idPacijenta") Integer idPcijenta, @PathVariable("id") Integer id, HttpServletRequest req) throws Exception {
		if (req.getSession().getAttribute("user") == null || !(req.getSession().getAttribute("user") instanceof Pacijent)) {
			return new ResponseEntity<GetOcenaDTO>(HttpStatus.FORBIDDEN);
		}
		GetOcenaDTO getOcena = lekarService.pacijentPosjetioLekaraFunc(idPcijenta, id);
		return new ResponseEntity<GetOcenaDTO>(getOcena, HttpStatus.OK);
	}
	
	@PostMapping(value = "/ocijeni", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> ocijeniLekara(@RequestBody SetOcenaDTO novaOcena, HttpServletRequest req) throws Exception {
		if (req.getSession().getAttribute("user") == null || !(req.getSession().getAttribute("user") instanceof Pacijent)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		lekarService.ocijeniLekara(novaOcena);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value = "/vratiVremenaCijenu/{idLekara}/{nazivTipa}/{datum}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LekarSlobodanDTO> vratiVremenaCijenu(@PathVariable("idLekara") Integer idLekara, @PathVariable("nazivTipa") String nazivTipa,@PathVariable("datum") String datum, HttpServletRequest req) throws Exception {
		if (req.getSession().getAttribute("user") == null || !(req.getSession().getAttribute("user") instanceof Pacijent)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		LekarSlobodanDTO vremenaCijena = lekarService.vratiVremenaCijenu(idLekara, nazivTipa, datum);

		return new ResponseEntity<LekarSlobodanDTO>(vremenaCijena, HttpStatus.OK);
	}

}
