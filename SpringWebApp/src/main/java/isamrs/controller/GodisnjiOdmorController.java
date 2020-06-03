package isamrs.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

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

import isamrs.domain.AdministratorKlinike;
import isamrs.domain.GodisnjiOdmor;
import isamrs.domain.Lekar;
import isamrs.domain.MedicinskoOsoblje;
import isamrs.domain.TipKlinike;
import isamrs.dto.OdmorDTO;
import isamrs.dto.OsobaDTO;
import isamrs.service.OdmorService;
import isamrs.service.TipPoseteService;

@RestController
@RequestMapping("/odmor")
public class GodisnjiOdmorController {
	
	@Autowired
	private OdmorService odmorService;
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<OdmorDTO>> getOdmors(HttpServletRequest req) {
		AdministratorKlinike ak = (AdministratorKlinike)req.getSession().getAttribute("user");
		Collection<OdmorDTO> odmori = odmorService.findAll(ak.getKlinika().getId());
		return new ResponseEntity<Collection<OdmorDTO>>(odmori, HttpStatus.OK);
	}

	
	
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GodisnjiOdmor> createZahtev(HttpServletRequest req,@RequestBody GodisnjiOdmor go){
		Lekar l = (Lekar)req.getSession().getAttribute("user");
		GodisnjiOdmor savedGO = odmorService.create(go, l.getId());
		return new ResponseEntity<GodisnjiOdmor>(savedGO, HttpStatus.OK);
	}
	
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GodisnjiOdmor> updateLekar(@RequestBody GodisnjiOdmor odmor, @PathVariable Integer id)
			throws Exception {
		
		GodisnjiOdmor updatedOdmor = odmorService.update(id,odmor);
		return new ResponseEntity<GodisnjiOdmor>(updatedOdmor, HttpStatus.OK);
	}
	
}
