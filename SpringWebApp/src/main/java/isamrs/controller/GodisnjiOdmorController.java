package isamrs.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isamrs.domain.GodisnjiOdmor;
import isamrs.domain.Lekar;
import isamrs.domain.MedicinskoOsoblje;
import isamrs.domain.TipKlinike;
import isamrs.service.OdmorService;
import isamrs.service.TipPoseteService;

@RestController
@RequestMapping("/odmor")
public class GodisnjiOdmorController {
	
	@Autowired
	private OdmorService odmorService;
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GodisnjiOdmor> createZahtev(HttpServletRequest req,@RequestBody GodisnjiOdmor go){
		Lekar l = (Lekar)req.getSession().getAttribute("user");
		GodisnjiOdmor savedGO = odmorService.create(go, l.getId());
		return new ResponseEntity<GodisnjiOdmor>(savedGO, HttpStatus.OK);
	}
	
}
