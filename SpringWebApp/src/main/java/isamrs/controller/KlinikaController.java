package isamrs.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

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
import isamrs.domain.Klinika;
import isamrs.domain.Lekar;
import isamrs.domain.Pacijent;
import isamrs.dto.GetOcenaDTO;
import isamrs.dto.SetOcenaDTO;
import isamrs.dto.KlinikaDTO;
import isamrs.dto.KlinikaZaPacijentaDTO;
import isamrs.dto.LekarZaPacijentaDTO;
import isamrs.dto.PretragaKlinikeDTO;
import isamrs.dto.SlobodniLekariKlinikeDTO;
import isamrs.service.KlinikaServiceImpl;

@RestController
@RequestMapping("/klinika")
public class KlinikaController {
	@Autowired
	private KlinikaServiceImpl klinikaService;

	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<KlinikaDTO>> getKlinike(){
		Collection<Klinika> klinike = klinikaService.findAll();
		Collection<KlinikaDTO> kdto = klinike.parallelStream().map(this::mapToDTO).collect(Collectors.toList());
		return new ResponseEntity<Collection<KlinikaDTO>>(kdto, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<KlinikaDTO> getKlinika(@PathVariable("id") Integer id) throws NotFoundException {
		Klinika k = klinikaService.findOne(id);
		if(k==null) {
			return new ResponseEntity<KlinikaDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<KlinikaDTO>(mapToDTO(k), HttpStatus.OK);
	}
	
	@GetMapping(value = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<KlinikaDTO> getKlinikaAdmin(HttpServletRequest req) throws NotFoundException {
		AdministratorKlinike ak = (AdministratorKlinike) req.getSession().getAttribute("user");
		
		Klinika k = klinikaService.findOne(ak.getKlinika().getId());
		if(k==null) {
			return new ResponseEntity<KlinikaDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<KlinikaDTO>(mapToDTO(k), HttpStatus.OK);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<KlinikaDTO> createKlinika(@RequestBody KlinikaDTO k){
		Klinika klinika = klinikaService.create(mapToEntity(k));
		
		return new ResponseEntity<KlinikaDTO>(mapToDTO(klinika), HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<KlinikaDTO> updateKlinika(@RequestBody KlinikaDTO k, @PathVariable("id") Integer id) throws NotFoundException {
		Klinika klinika = klinikaService.update(id, mapToEntity(k));
		
		return new ResponseEntity<KlinikaDTO>(mapToDTO(klinika), HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<KlinikaDTO> deleteKlinika(@PathVariable Integer id){
		try {

			klinikaService.delete(id);
		} catch (Exception e) {
			return new ResponseEntity<KlinikaDTO>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<KlinikaDTO>(HttpStatus.NO_CONTENT);
	}


	@GetMapping(value = "/klinikeZaPacijenta", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<KlinikaZaPacijentaDTO>> getPregledi(HttpServletRequest req) {
		if (req.getSession().getAttribute("user") == null || !(req.getSession().getAttribute("user") instanceof Pacijent)) {
			return new ResponseEntity<List<KlinikaZaPacijentaDTO>>(HttpStatus.FORBIDDEN);
		}
		Collection<Klinika> klinike = klinikaService.findAll();

		List<KlinikaZaPacijentaDTO> klinikeDTO = new ArrayList<>();
		for (Klinika klinika : klinike) {
			klinikeDTO.add(new KlinikaZaPacijentaDTO(klinika));
		}
		return new ResponseEntity<List<KlinikaZaPacijentaDTO>>(klinikeDTO, HttpStatus.OK);
	}

	@PostMapping(value = "/pretragaKlinikaZakazivanje",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<KlinikaZaPacijentaDTO>> pretragaKlinikaZakazivanje(@RequestBody PretragaKlinikeDTO pretraga, HttpServletRequest req){
		if (req.getSession().getAttribute("user") == null || !(req.getSession().getAttribute("user") instanceof Pacijent)) {
			return new ResponseEntity<List<KlinikaZaPacijentaDTO>>(HttpStatus.FORBIDDEN);
		}
		ArrayList<KlinikaZaPacijentaDTO> klinikeDTO = klinikaService.pretragaKlinikaZakazivanje(pretraga);

		return new ResponseEntity<List<KlinikaZaPacijentaDTO>>(klinikeDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/getSviLekariKlinike/{idKlinike}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<LekarZaPacijentaDTO>> getSviLekariKlinike(@PathVariable("idKlinike") Integer idKlinike, HttpServletRequest req) throws Exception {
		if (req.getSession().getAttribute("user") == null || !(req.getSession().getAttribute("user") instanceof Pacijent)) {
			return new ResponseEntity<ArrayList<LekarZaPacijentaDTO>>(HttpStatus.FORBIDDEN);
		}
		List<Lekar> lekari = klinikaService.lekariKlinike(idKlinike);
		ArrayList<LekarZaPacijentaDTO> sviLekari = new ArrayList<LekarZaPacijentaDTO>();
		for (Lekar l : lekari) {
			sviLekari.add(new LekarZaPacijentaDTO(l));
		}
		return new ResponseEntity<ArrayList<LekarZaPacijentaDTO>>(sviLekari, HttpStatus.OK);
	}

	@GetMapping(value = "/pacijentPosjetio/{idPacijenta}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GetOcenaDTO> pacijentPosjetioKliniku(@PathVariable("idPacijenta") Integer idPcijenta, @PathVariable("id") Integer id, HttpServletRequest req) throws Exception {
		if (req.getSession().getAttribute("user") == null || !(req.getSession().getAttribute("user") instanceof Pacijent)) {
			return new ResponseEntity<GetOcenaDTO>(HttpStatus.FORBIDDEN);
		}
		GetOcenaDTO getOcena = klinikaService.pacijentPosjetio(idPcijenta, id);
		return new ResponseEntity<GetOcenaDTO>(getOcena, HttpStatus.OK);
	}

	@PostMapping(value = "/ocijeni", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> ocijeniKliniku(@RequestBody SetOcenaDTO novaOcena, HttpServletRequest req) throws Exception, NotFoundException {
		if (req.getSession().getAttribute("user") == null || !(req.getSession().getAttribute("user") instanceof Pacijent)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		klinikaService.ocijeniKliniku(novaOcena);

		return new ResponseEntity<>(HttpStatus.OK);
	}


	@PostMapping(value = "/getSlobodniLekariKlinike", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<LekarZaPacijentaDTO>> getSlobodniLekariKlinike(@RequestBody SlobodniLekariKlinikeDTO slk, HttpServletRequest req) throws Exception, NotFoundException {
		if (req.getSession().getAttribute("user") == null || !(req.getSession().getAttribute("user") instanceof Pacijent)) {
			return new ResponseEntity<ArrayList<LekarZaPacijentaDTO>>(HttpStatus.FORBIDDEN);
		}
		ArrayList<LekarZaPacijentaDTO> lekari = klinikaService.getSlobodniLekariKlinike(slk);

		return new ResponseEntity<ArrayList<LekarZaPacijentaDTO>>(lekari, HttpStatus.OK);
	}


	private KlinikaDTO mapToDTO(Klinika k) {
		return new KlinikaDTO(k.getId(), k.getNaziv(), k.getAdresa(), k.getOpis(), k.getTipKlinike());
	}
	private Klinika mapToEntity(KlinikaDTO k) {
		return new Klinika(k.getNaziv(), k.getAdresa(), k.getOpis(), k.getTipKlinike());
	}
}
