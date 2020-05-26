package isamrs.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

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

import isamrs.domain.Klinika;
import isamrs.domain.Lekar;
import isamrs.domain.Ocena;
import isamrs.domain.Pacijent;
import isamrs.domain.TipPosete;
import isamrs.dto.GetOcenaDTO;
import isamrs.dto.LekarSlobodanDTO;
import isamrs.dto.LekarZaPacijentaDTO;
import isamrs.dto.OsobaDTO;
import isamrs.dto.ProveraLekarSlobodanDTO;
import isamrs.dto.SetOcenaDTO;
import isamrs.dto.SlobodniLekariKlinikeDTO;
import isamrs.dto.TerminDTO;
import isamrs.service.KlinikaServiceImpl;
import isamrs.service.LekarService;
import isamrs.service.PacijentServiceImpl;
@RestController
@RequestMapping("/lekar")
public class LekarController {
	
	@Autowired
	private LekarService lekarService;
	
	@Autowired
	private PacijentServiceImpl pacijentService;
	
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
	
	@GetMapping(value = "/pregledi/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<TerminDTO>> getPreglediLekar(@PathVariable("id") Integer id) {
		Collection<TerminDTO> termini = lekarService.findTermini(id);

		if (termini == null) {
			return new ResponseEntity<Collection<TerminDTO>>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Collection<TerminDTO>>(termini, HttpStatus.OK);
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
	
	
	@PostMapping(value = "/provjeriDaLiJeLjekarSlobodan", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> provjeriDaLiJeLjekarSlobodan(@RequestBody ProveraLekarSlobodanDTO provera, HttpServletRequest req) throws Exception {
		System.out.println(provera.getIdLekara()+"************************************************");
		if (req.getSession().getAttribute("user") == null || !(req.getSession().getAttribute("user") instanceof Pacijent)) {
			return new ResponseEntity<Boolean>(HttpStatus.FORBIDDEN);
		}
		System.out.println(provera.getIdLekara());
		Lekar l = lekarService.findOne(provera.getIdLekara());
		System.out.println(l);
		if (KlinikaController.lekarImaSlobodanTermin(l, provera.getDatum())) {
			for (TipPosete tp : l.getTipoviPoseta()) {
				if (tp.getNaziv().equals(provera.getNazivTipa())) {
					return new ResponseEntity<Boolean>(true, HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<Boolean>(false, HttpStatus.OK);
	}
//	
//	@GetMapping(value = "/pacijentPosjetio/{idPacijenta}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<GetOcenaDTO> pacijentPosjetioLekara(@PathVariable("idPacijenta") Integer idPcijenta, @PathVariable("id") Integer id, HttpServletRequest req) throws Exception {
//		if (req.getSession().getAttribute("user") == null || !(req.getSession().getAttribute("user") instanceof Pacijent)) {
//			return new ResponseEntity<GetOcenaDTO>(HttpStatus.FORBIDDEN);
//		}
//		Boolean ocijenio = lekarService.pacijentOcijenioLekara(idPcijenta, id);
//		Boolean posjetio = lekarService.pacijentPosjetioLekara(idPcijenta, id);
//		GetOcenaDTO getOcena = new GetOcenaDTO();
//		if (posjetio && ocijenio) {
//			getOcena.setMozeOcjenjivati(true);
//			getOcena.setOcjena(lekarService.getOcenaPacijenta(id, idPcijenta).getVrednost());
//		} else if (posjetio) {
//			getOcena.setMozeOcjenjivati(true);
//			getOcena.setOcjena(0);
//		} else {
//			getOcena.setMozeOcjenjivati(false);
//			getOcena.setOcjena(0);
//		}
//		return new ResponseEntity<GetOcenaDTO>(getOcena, HttpStatus.OK);
//	}
//	
//	@PostMapping(value = "/ocijeni", produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Boolean> ocijeniLekara(@RequestBody SetOcenaDTO novaOcena, HttpServletRequest req) throws Exception {
//		if (req.getSession().getAttribute("user") == null || !(req.getSession().getAttribute("user") instanceof Pacijent)) {
//			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//		}
//		System.out.println(novaOcena.getIdPacijenta() + novaOcena.getId());
//		if (lekarService.pacijentOcijenioLekara(novaOcena.getIdPacijenta(), novaOcena.getId())) {
//			System.out.println("USLOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
//			Ocena stara = lekarService.getOcenaPacijenta(novaOcena.getId(), novaOcena.getIdPacijenta());
//			stara.setVrednost(novaOcena.getOcjena());
//			Ocena updated = lekarService.updateOcena(stara.getId(), stara);
//		} else {
//			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//			Ocena nova = new Ocena();
//			nova.setVrednost(novaOcena.getOcjena());
//			Pacijent p = pacijentService.findOne(novaOcena.getIdPacijenta());
//			Lekar l = lekarService.findOne(novaOcena.getId());
//			nova.setPacijent(p);
//			l.getOcena().add(nova);
//			
//			Ocena created = lekarService.createOcena(nova);
//			//Lekar ll = lekarService.update(novaOcena.getId(), k);
//		}
//		return new ResponseEntity<>(HttpStatus.OK);
//	}
//	
	@GetMapping(value = "/vratiVremenaCijenu/{idLekara}/{nazivTipa}/{datum}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LekarSlobodanDTO> vratiVremenaCijenu(@PathVariable("idLekara") Integer idLekara, @PathVariable("nazivTipa") String nazivTipa,@PathVariable("datum") String datum, HttpServletRequest req) throws Exception {
		if (req.getSession().getAttribute("user") == null || !(req.getSession().getAttribute("user") instanceof Pacijent)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
		Date d = sdf.parse(datum);
		Lekar l = lekarService.findOne(idLekara);
		LekarSlobodanDTO vremenaCijena = new LekarSlobodanDTO();
		vremenaCijena.setListaVremena(KlinikaController.getSlobodniTermini(l, d));
		String naziv = nazivTipa.replace("%20", " ");
		vremenaCijena.setCijena(naziv, l.getKlinika());
		
		System.out.println(vremenaCijena.getCijenaTipaOpciono());
		for (String s : vremenaCijena.getListaVremena()) {
			System.out.println(s);
		}
		return new ResponseEntity<LekarSlobodanDTO>(vremenaCijena, HttpStatus.OK);
	}

}
