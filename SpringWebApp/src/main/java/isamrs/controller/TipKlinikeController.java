package isamrs.controller;

import java.util.Collection;
import java.util.stream.Collectors;

import isamrs.domain.AdministratorKlinickogCentra;
import isamrs.dto.TipKlinikeDTO;
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

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/tipKlinike")
public class TipKlinikeController {
	@Autowired
	private TipKlinikeService service;
	
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TipKlinikeDTO> getTipKlinike(@PathVariable("id") Long id, HttpServletRequest httpServletRequest){
		if (!(httpServletRequest.getSession().getAttribute("user") instanceof AdministratorKlinickogCentra)){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		try {
			TipKlinike t = service.findOne(id);
			return new ResponseEntity<TipKlinikeDTO>(new TipKlinikeDTO(t), HttpStatus.OK);
		}catch (NotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tip Klinike sa id-jem: "+id+ " nije pronadjen.", e);
		}
	}

	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<TipKlinikeDTO>> getTipoviKlinike(HttpServletRequest httpServletRequest){
		if (!(httpServletRequest.getSession().getAttribute("user") instanceof AdministratorKlinickogCentra)){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		Collection<TipKlinikeDTO> tipovi = service.findAll().stream().map(TipKlinikeDTO::new).collect(Collectors.toList());
		return new ResponseEntity<Collection<TipKlinikeDTO>>(tipovi, HttpStatus.OK);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TipKlinikeDTO> createTipKlinike(@RequestBody TipKlinike t, HttpServletRequest httpServletRequest){
		if (!(httpServletRequest.getSession().getAttribute("user") instanceof AdministratorKlinickogCentra)){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		TipKlinike t2 = service.create(t);
		return new ResponseEntity<TipKlinikeDTO>(new TipKlinikeDTO(t2), HttpStatus.OK);
	}
	
	@PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TipKlinikeDTO> updateTipKlinike(@PathVariable("id") Long id, @RequestBody TipKlinike t, HttpServletRequest httpServletRequest) throws NotFoundException {
		if (!(httpServletRequest.getSession().getAttribute("user") instanceof AdministratorKlinickogCentra)){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		TipKlinike t2 = service.update(id, t);
		return new ResponseEntity<TipKlinikeDTO>(new TipKlinikeDTO(t2), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<TipKlinikeDTO> deleteTipKlinike(@PathVariable("id") Long id, HttpServletRequest httpServletRequest){
		if (!(httpServletRequest.getSession().getAttribute("user") instanceof AdministratorKlinickogCentra)){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		service.delete(id);
		return new ResponseEntity<TipKlinikeDTO>(HttpStatus.OK);
	}
}
