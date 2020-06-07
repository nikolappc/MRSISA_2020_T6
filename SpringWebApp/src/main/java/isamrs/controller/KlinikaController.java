package isamrs.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
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
import isamrs.domain.GodisnjiOdmor;
import isamrs.domain.Klinika;
import isamrs.domain.Lekar;
import isamrs.domain.Ocena;
import isamrs.domain.Operacija;
import isamrs.domain.Pacijent;
import isamrs.domain.Pregled;
import isamrs.domain.RadnoVreme;
import isamrs.domain.Sala;
import isamrs.domain.StavkaCenovnika;
import isamrs.domain.TipPosete;
import isamrs.dto.GetOcenaDTO;
import isamrs.dto.SetOcenaDTO;
import isamrs.dto.KlinikaDTO;
import isamrs.dto.KlinikaZaPacijentaDTO;
import isamrs.dto.LekarZaPacijentaDTO;
import isamrs.dto.OperacijaDTO;
import isamrs.dto.PosetaDTO;
import isamrs.dto.PregledDTO;
import isamrs.dto.PretragaKlinikeDTO;
import isamrs.dto.ProveraLekarSlobodanDTO;
import isamrs.dto.SlobodniLekariKlinikeDTO;
import isamrs.service.KlinikaServiceImpl;
import isamrs.service.PacijentServiceImpl;

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

	/*
	public static boolean terminZauzetF(Lekar l, Calendar cal1, Calendar c) {
		Calendar cal2 = Calendar.getInstance();
		for (Pregled p : l.getPregled()) {
			cal2.setTime(p.getTermin().getPocetak());
			if ((cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)) &&
					(cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR))) { //pregled je tog datuma
				if ((cal2.get(Calendar.HOUR_OF_DAY) == c.get(Calendar.HOUR_OF_DAY)) &&
						(cal2.get(Calendar.MINUTE) == c.get(Calendar.MINUTE))) {
					return true;
				}
			}
		}
		for (Operacija o : l.getOperacije()) {
			cal2.setTime(o.getTermin().getPocetak());
			if ((cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)) && (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR))) { //pregled je tog datuma
				Calendar cckraj = Calendar.getInstance();
				cckraj.setTime(c.getTime());
				cckraj.add(Calendar.MINUTE, 30);
				if (!(o.getTermin().getPocetak().after(cckraj.getTime()) || o.getTermin().getKraj().before(c.getTime()) || o.getTermin().getPocetak().equals(cckraj.getTime()) || o.getTermin().getKraj().equals(c.getTime()))) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean lekarNaGodisnjem(Lekar l, Date datum) {
		for (GodisnjiOdmor go : l.getGodisnjiOdmor()) {
			if ((datum.after(go.getPocetak()) || datum.equals(go.getPocetak())) && (datum.before(go.getKraj()) || datum.equals(go.getKraj()))) {
				return true;
			}
		}
		return false;
	}

	public static boolean lekarImaSlobodanTermin(Lekar l, Date date1) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		if (lekarNaGodisnjem(l, cal1.getTime())) {
			return false;
		}
		if (cal1.get(Calendar.DAY_OF_WEEK)==0 || cal1.get(Calendar.DAY_OF_WEEK)==1) {
			return false;
		}
		if (l.getRadnoVreme() == null || l.getRadnoVreme().isEmpty()) {
			return false;
		}
		ArrayList<RadnoVreme> radnaVremena = new ArrayList<RadnoVreme>(l.getRadnoVreme());
		Calendar cal3 = Calendar.getInstance();
		Calendar cal4 = Calendar.getInstance();
		Date pocetak = radnaVremena.get(l.getRadnoVreme().size()-1).getPocetak();
		Date kraj = radnaVremena.get(l.getRadnoVreme().size()-1).getKraj();
		cal3.setTime(pocetak);
		cal4.setTime(kraj);
		Calendar c = Calendar.getInstance();
		for (c = cal3; c.before(cal4); c.add(Calendar.MINUTE, 30)) {
			boolean terminZauzet = terminZauzetF(l, cal1, c);
			if (!terminZauzet) {
				return true;
			}
		}
		return false;
	}

	public static ArrayList<String> getSlobodniTermini(Lekar l, Date date1){
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		ArrayList<Date> vremena = new ArrayList<Date>();
		ArrayList<RadnoVreme> radnaVremena = new ArrayList<RadnoVreme>(l.getRadnoVreme());
		Calendar cal3 = Calendar.getInstance();
		Calendar cal4 = Calendar.getInstance();
		Date pocetak = radnaVremena.get(l.getRadnoVreme().size()-1).getPocetak();
		Date kraj = radnaVremena.get(l.getRadnoVreme().size()-1).getKraj();
		cal3.setTime(pocetak);
		cal4.setTime(kraj);
		cal3.set(Calendar.YEAR, cal1.get(Calendar.YEAR));
		cal3.set(Calendar.MONTH, cal1.get(Calendar.MONTH));
		cal3.set(Calendar.DAY_OF_MONTH, cal1.get(Calendar.DAY_OF_MONTH));
		cal4.set(Calendar.YEAR, cal1.get(Calendar.YEAR));
		cal4.set(Calendar.MONTH, cal1.get(Calendar.MONTH));
		cal4.set(Calendar.DAY_OF_MONTH, cal1.get(Calendar.DAY_OF_MONTH));
		Calendar c = Calendar.getInstance();
		for (c = cal3; c.before(cal4); c.add(Calendar.MINUTE, 30)) {
			boolean terminZauzet = terminZauzetF(l, cal1, c);

			if (!terminZauzet) {
				vremena.add(c.getTime());
			}
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		ArrayList<String> vremenaStr = new ArrayList<String>();
		for (Date ddd : vremena) {
			vremenaStr.add(sdf.format(ddd));
		}

		return vremenaStr;
	}
	*/
}
