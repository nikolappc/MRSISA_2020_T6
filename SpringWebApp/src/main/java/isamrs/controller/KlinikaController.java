package isamrs.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
import isamrs.domain.Operacija;
import isamrs.domain.Pacijent;
import isamrs.domain.Pregled;
import isamrs.dto.KlinikaDTO;
import isamrs.dto.KlinikaZaPacijentaDTO;
import isamrs.dto.OperacijaDTO;
import isamrs.dto.PosetaDTO;
import isamrs.dto.PregledDTO;
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
	public ResponseEntity<KlinikaDTO> getKlinika(@PathVariable("id") Long id){
		Klinika k = klinikaService.findOne(id);
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
	public ResponseEntity<KlinikaDTO> updateKlinika(@RequestBody KlinikaDTO k, @PathVariable("id") Integer id){
		Klinika klinika = klinikaService.update((long)id, mapToEntity(k));
		
		return new ResponseEntity<KlinikaDTO>(mapToDTO(klinika), HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<KlinikaDTO> deleteKlinika(@PathVariable Long id){
		klinikaService.delete(id);
		return new ResponseEntity<KlinikaDTO>(HttpStatus.NO_CONTENT);
	}
	
	
	private KlinikaDTO mapToDTO(Klinika k) {
		return new KlinikaDTO(k.getNaziv(), k.getAdresa(), k.getOpis(), k.getTipKlinike());
	}
	private Klinika mapToEntity(KlinikaDTO k) {
		return new Klinika(k.getNaziv(), k.getAdresa(), k.getOpis(), k.getTipKlinike());
	}
	
	
	@GetMapping(value = "/klinikeZaPacijenta", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<KlinikaZaPacijentaDTO>> getPregledi() {
		Collection<Klinika> klinike = klinikaService.findAll();
		
		List<KlinikaZaPacijentaDTO> klinikeDTO = new ArrayList<>();
		for (Klinika klinika : klinike) {
			klinikeDTO.add(new KlinikaZaPacijentaDTO(klinika));
		}
		return new ResponseEntity<List<KlinikaZaPacijentaDTO>>(klinikeDTO, HttpStatus.OK);
	}
	
}
