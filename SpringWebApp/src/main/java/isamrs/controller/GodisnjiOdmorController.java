package isamrs.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import isamrs.domain.*;
import isamrs.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isamrs.dto.GodisnjiOdmorDTO;
import isamrs.dto.OdmorDTO;
import isamrs.service.OdmorService;

@RestController
@RequestMapping("/odmor")
public class GodisnjiOdmorController {
	
	@Autowired
	private OdmorService odmorService;
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<OdmorDTO>> getOdmors(HttpServletRequest req) {
		if (!(req.getSession().getAttribute("user") instanceof AdministratorKlinike)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		AdministratorKlinike ak = (AdministratorKlinike)req.getSession().getAttribute("user");
		Collection<OdmorDTO> odmori = odmorService.findAll(ak.getKlinika().getId());
		return new ResponseEntity<Collection<OdmorDTO>>(odmori, HttpStatus.OK);
	}

	
	
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GodisnjiOdmor> createZahtev(HttpServletRequest req,@RequestBody GodisnjiOdmor go) throws NotFoundException {
		if (!(req.getSession().getAttribute("user") instanceof MedicinskoOsoblje)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		MedicinskoOsoblje mo = (MedicinskoOsoblje)req.getSession().getAttribute("user");
		GodisnjiOdmor savedGO;
		if(mo instanceof Lekar){
			savedGO = odmorService.create(go, mo.getId());
		}else if(mo instanceof MedicinskaSestra){
			savedGO = odmorService.createSestra(go, mo.getId());
		}else{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<GodisnjiOdmor>(savedGO, HttpStatus.OK);
	}


	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GodisnjiOdmor> updateLekar(HttpServletRequest req,@RequestBody GodisnjiOdmorDTO odmor,@PathVariable Integer id)
			throws Exception, NotFoundException {
		if (!(req.getSession().getAttribute("user") instanceof AdministratorKlinike)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		GodisnjiOdmor updatedOdmor = odmorService.update(id,odmor.getOdmor());
		return new ResponseEntity<GodisnjiOdmor>(updatedOdmor, HttpStatus.OK);
	}
	
}
