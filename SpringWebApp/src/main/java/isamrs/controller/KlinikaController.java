package isamrs.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
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

import isamrs.domain.GodisnjiOdmor;
import isamrs.domain.Klinika;
import isamrs.domain.Lekar;
import isamrs.domain.Operacija;
import isamrs.domain.Pacijent;
import isamrs.domain.Pregled;
import isamrs.domain.RadnoVreme;
import isamrs.domain.Sala;
import isamrs.domain.StavkaCenovnika;
import isamrs.domain.TipPosete;
import isamrs.dto.KlinikaDTO;
import isamrs.dto.KlinikaZaPacijentaDTO;
import isamrs.dto.LekarZaPacijentaDTO;
import isamrs.dto.OperacijaDTO;
import isamrs.dto.PosetaDTO;
import isamrs.dto.PregledDTO;
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
	public ResponseEntity<KlinikaDTO> getKlinika(@PathVariable("id") Integer id){
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
	
	
	private KlinikaDTO mapToDTO(Klinika k) {
		return new KlinikaDTO(k.getId(), k.getNaziv(), k.getAdresa(), k.getOpis(), k.getTipKlinike());
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
	
	
	
	@PostMapping(value = "/pretragaKlinikaZakazivanje",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<KlinikaZaPacijentaDTO>> pretragaKlinikaZakazivanje(@RequestBody PretragaKlinikeDTO pretraga){
		Collection<Klinika> kliniks = klinikaService.findAll();
		System.out.println(pretraga.getDatum().toString());
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(pretraga.getDatum());
		List<Klinika> klinike = new ArrayList<Klinika>();
		for (Klinika kk : kliniks) {
			if (!klinikaImaTip(kk, pretraga.getNazivTipa())) {
				continue;
			}
			boolean datumOk = false;
			for (Lekar l : kk.getLekari()) {
				if (lekarImaSlobodanTermin(l, cal1)) {
					System.out.println("SLOBODAN " + l.getIme());
					datumOk = true;
					break;
				}
			}
			String grad = kk.getAdresa().split(", ")[1];
			String drzava = kk.getAdresa().split(", ")[2];
			
			if (datumOk && grad.contains(pretraga.getGrad()) && drzava.contains(pretraga.getDrzava()) && kk.getProsjek() >= pretraga.getOcjena()) {
				klinike.add(kk);
			}
		}
		
		List<KlinikaZaPacijentaDTO> klinikeDTO = new ArrayList<>();
		for (Klinika klinika : klinike) {
			KlinikaZaPacijentaDTO klinikaDTO = new KlinikaZaPacijentaDTO(klinika);
			klinikaDTO.postaviCijenuPregleda(klinika, pretraga.getNazivTipa());
			klinikeDTO.add(klinikaDTO);
		}
		return new ResponseEntity<List<KlinikaZaPacijentaDTO>>(klinikeDTO, HttpStatus.OK);
	}
	
	@PostMapping(value = "/getSlobodniLekariKlinike", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<LekarZaPacijentaDTO>> getSlobodniLekariKlinike(@RequestBody SlobodniLekariKlinikeDTO slk) throws Exception {
		//ArrayList<Lekar> lekars = lekarService.findLekariKlinike(slk.getIdKlinike());
		Klinika k = klinikaService.findOne(slk.getIdKlinike());
		Collection<Lekar> lekars = k.getLekari();
		ArrayList<LekarZaPacijentaDTO> lekari = new ArrayList<LekarZaPacijentaDTO>();
		Calendar cal1 = Calendar.getInstance();
		System.out.println(slk.getDatum().toString() + "***");
		cal1.setTime(slk.getDatum());
		for (Lekar l : lekars) {
			if (lekarImaSlobodanTermin(l, cal1) && ljekarImaTip(l, slk.getNazivTipa())) {
				LekarZaPacijentaDTO lzp = new LekarZaPacijentaDTO(l);
				lzp.setListaVremena(KlinikaController.getSlobodniTermini(l, cal1));
				lekari.add(lzp);
			}
		}
		System.out.println("OVOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
		for (LekarZaPacijentaDTO ll : lekari) {
			for (String dd : ll.getListaVremena()) {
				System.out.println(dd);
			}
		}
		return new ResponseEntity<ArrayList<LekarZaPacijentaDTO>>(lekari, HttpStatus.OK);
	}
	
	
	public static boolean klinikaImaTip(Klinika k, String naziv) {
		for (StavkaCenovnika sc : k.getCenovnik().getStavkaCenovnika()) {
			if (sc.getTipPosete().getNaziv().equals(naziv)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean ljekarImaTip(Lekar l, String naziv) {
		for (TipPosete tp : l.getTipoviPoseta()) {
			if (tp.getNaziv().equals(naziv)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean terminZauzetF(Lekar l, Calendar cal1 /*datum iz pretrage*/, Calendar c /*termin koji se provjerava*/) {
		Calendar cal2 = Calendar.getInstance();
		for (Pregled p : l.getPregled()) {
			System.out.println("pregled " + p.getTermin().getPocetak());
			System.out.println("datum za koji se trazi " + cal1.getTime());
			System.out.println("termin koji se provjerava " + c.getTime());
			cal2.setTime(p.getTermin().getPocetak());
			if ((cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)) && (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR))) { //pregled je tog datuma
				System.out.println("jednaki godina i dan");
				//if (cal2.getTime() == c.getTime()) {
				if ((cal2.get(Calendar.HOUR_OF_DAY) == c.get(Calendar.HOUR_OF_DAY)) && (cal2.get(Calendar.MINUTE) == c.get(Calendar.MINUTE))) {
					System.out.println("zauzet");
					return true;
				}
				System.out.println("nije zauzet");
			}
		}
		for (Operacija o : l.getOperacije()) {
			System.out.println("operacija " + o.getTermin().getPocetak());
			System.out.println("datum za koji se trazi " + cal1.getTime());
			System.out.println("termin koji se provjerava " + c.getTime());
			cal2.setTime(o.getTermin().getPocetak());
			if ((cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)) && (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR))) { //pregled je tog datuma
				Calendar cckraj = Calendar.getInstance();
				cckraj.setTime(c.getTime());
				cckraj.add(Calendar.MINUTE, 30);
				if (!(o.getTermin().getPocetak().after(cckraj.getTime()) || o.getTermin().getKraj().before(c.getTime()) || o.getTermin().getPocetak().equals(cckraj.getTime()) || o.getTermin().getKraj().equals(c.getTime()))) {
					//ovdje <=
					System.out.println("zauzet");
					return true;
				}
				System.out.println("nije zauzet");
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
	
	public static boolean lekarImaSlobodanTermin(Lekar l, Calendar cal1 /*datum iz pretrage*/) {
		//ima isto radno vrijeme 5 dana i ne radi vikendom?
		if (lekarNaGodisnjem(l, cal1.getTime())) {
			System.out.println("NA GODISNJEM " + l.getIme());
			return false;
		}
		if (cal1.get(Calendar.DAY_OF_WEEK)==0 || cal1.get(Calendar.DAY_OF_WEEK)==6) {
			System.out.println("VIKEND " + l.getIme());
			return false;
		}
		if (l.getRadnoVreme() == null || l.getRadnoVreme().isEmpty()) {
			System.out.println("NEMA RADNO VRIJEME " + l.getIme());
			return false;
		}
		ArrayList<RadnoVreme> radnaVremena = new ArrayList<RadnoVreme>(l.getRadnoVreme());
		Calendar cal3 = Calendar.getInstance();
		Calendar cal4 = Calendar.getInstance();
		Date pocetak = radnaVremena.get(l.getRadnoVreme().size()-1).getPocetak();
		Date kraj = radnaVremena.get(l.getRadnoVreme().size()-1).getKraj();
		System.out.println(l.getIme() + " radno vrijeme: " + pocetak + "  -  " + kraj);
		cal3.setTime(pocetak);
		cal4.setTime(kraj);
		Calendar c = Calendar.getInstance();
		for (c = cal3; c.before(cal4); c.add(Calendar.MINUTE, 30)) {
			System.out.println("TERMIN" + c.getTime());
			boolean terminZauzet = terminZauzetF(l, cal1, c);
			System.out.println("zauzet " + terminZauzet);
			if (!terminZauzet) {
				return true;
			}
		}
		return false;
		/*if (slobodan && !lekarNaGodisnjem(l, pretraga.getDatum())) {
			datumOk = true;
		}*/
	}
	
	public static ArrayList<String> getSlobodniTermini(Lekar l, Calendar cal1){
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
		for (Date v : vremena) {
			System.out.println(v);
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		ArrayList<String> vremenaStr = new ArrayList<String>();
		for (Date ddd : vremena) {
			vremenaStr.add(sdf.format(ddd));
		}
		
		return vremenaStr;
	}
	
}
