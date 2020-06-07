package isamrs.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.OptBoolean;

import isamrs.controller.KlinikaController;
import isamrs.domain.GodisnjiOdmor;
import isamrs.domain.Klinika;
import isamrs.domain.Lekar;
import isamrs.domain.Ocena;
import isamrs.domain.Operacija;
import isamrs.domain.Pacijent;
import isamrs.domain.Pregled;
import isamrs.domain.RadnoVreme;
import isamrs.dto.GetOcenaDTO;
import isamrs.dto.KlinikaDTO;
import isamrs.dto.KlinikaZaPacijentaDTO;
import isamrs.dto.LekarZaPacijentaDTO;
import isamrs.dto.PretragaKlinikeDTO;
import isamrs.dto.SetOcenaDTO;
import isamrs.dto.SlobodniLekariKlinikeDTO;
import isamrs.exceptions.NotFoundException;
import isamrs.repository.KlinikaRepository;
import isamrs.repository.LekarRepository;
import isamrs.repository.OcenaRepository;
import isamrs.repository.PacijentRepository;

@org.springframework.stereotype.Service
public class KlinikaServiceImpl implements Service<Klinika, Integer>{

	@Autowired
	private KlinikaRepository repo;
	
	@Autowired
	private LekarRepository repoLekar;
	
	@Autowired
	private OcenaRepository repoOcena;
	
	@Autowired
	private PacijentRepository repoPacijent;
	
	
	@Override
	public Collection<Klinika> findAll() {
		return repo.findAll();
	}

	@Override
	public Klinika findOne(Integer id) throws NotFoundException {
		Optional<Klinika> klinika = repo.findById(id);
		if(klinika.isPresent()) {
			return  klinika.get();			
		}
		throw new NotFoundException();
	}

	@Override
	public Klinika create(Klinika t) {
		return repo.save(t);
	}

	@Override
	public Klinika update(Integer id, Klinika t) throws NotFoundException {
		Klinika k = repo.findById(id).orElseGet(null);
		if(k == null){
			throw new NotFoundException();
		}
		k.setAdresa(t.getAdresa());
		k.setTipKlinike(t.getTipKlinike());
		k.setOpis(t.getOpis());
		k.setNaziv(t.getNaziv());
		
		return repo.save(k);
	}

	@Override
	public void delete(Integer id) {
		repo.deleteById(id);
	}
	
	public Pacijent findOnePacijent(int id) {
		return repoPacijent.findById(id).orElseGet(null);
	}
	
	public List<Klinika> pretragaZakazivanje(PretragaKlinikeDTO pretraga) {
		return repo.pretragaZakazivanje(pretraga.getGrad(), pretraga.getDrzava(), pretraga.getOcjena(), pretraga.getNazivTipa());
	}
	
	public List<Lekar> lekariKlinikeTipa(int idKlinike, String nazivTipa) {
		return repoLekar.lekariKlinikeTipa(idKlinike, nazivTipa);
	}
	
	public List<Lekar> lekariKlinike(Integer idKlinike) {
		return repoLekar.lekariKlinike(idKlinike);
	}
	
	public Boolean pacijentOcijenioKliniku(int idPacijenta, int idKlinike) {
		if (repo.pacijentOcijenioKliniku(idPacijenta, idKlinike) == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public Boolean pacijentPosjetioKliniku(int idPacijenta, int idKlinike) {
		if (repo.pacijentPosjetioKliniku(idPacijenta, idKlinike) == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public Ocena getOcenaPacijenta(int idKlinike, int idPacijenta) {
		return repo.getOcenaPacijenta(idKlinike, idPacijenta);
	}
	
	public Ocena updateOcena(Integer id, Ocena o) {
		Ocena ocenaForUpdate = repoOcena.findById(id).orElseGet(null);
		ocenaForUpdate.setVrednost(o.getVrednost());
		return repoOcena.save(ocenaForUpdate);
	}
	
	public Ocena createOcena(Ocena o) {
		return repoOcena.save(o);
	}

	public Klinika findByOperacija(Integer id) {
		return repo.findByOperacija(id);
	}

	public Klinika findByLekar(Lekar lekar) {
		return repo.findByLekar(lekar.getId());
	}
	
	
	
	public ArrayList<KlinikaZaPacijentaDTO> pretragaKlinikaZakazivanje(PretragaKlinikeDTO pretraga) {
		Collection<Klinika> kliniks = pretragaZakazivanje(pretraga);
		ArrayList<Klinika> klinike = new ArrayList<Klinika>();
		for (Klinika kk : kliniks) {
			boolean datumOk = false;
			for (Lekar l : kk.getLekari()) {
				if (lekarImaSlobodanTermin(l, pretraga.getDatum())) {
					datumOk = true;
					break;
				}
			}
			klinike.add(kk);
		}
		ArrayList<KlinikaZaPacijentaDTO> klinikeDTO = new ArrayList<>();
		for (Klinika klinika : klinike) {
			KlinikaZaPacijentaDTO klinikaDTO = new KlinikaZaPacijentaDTO(klinika);
			klinikaDTO.postaviCijenuPregleda(klinika, pretraga.getNazivTipa());
			klinikeDTO.add(klinikaDTO);
		}
		return klinikeDTO;
	}
	
	public GetOcenaDTO pacijentPosjetio(int idPcijenta, int id) {
		Boolean ocijenio = pacijentOcijenioKliniku(idPcijenta, id);
		Boolean posjetio = pacijentPosjetioKliniku(idPcijenta, id);
		GetOcenaDTO getOcena = new GetOcenaDTO();
		if (posjetio && ocijenio) {
			getOcena.setMozeOcjenjivati(true);
			getOcena.setOcjena(getOcenaPacijenta(id, idPcijenta).getVrednost());
		} else if (posjetio) {
			getOcena.setMozeOcjenjivati(true);
			getOcena.setOcjena(0);
		} else {
			getOcena.setMozeOcjenjivati(false);
			getOcena.setOcjena(0);
		}
		return getOcena;
	}
	
	public void ocijeniKliniku(SetOcenaDTO novaOcena) {
		if (pacijentOcijenioKliniku(novaOcena.getIdPacijenta(), novaOcena.getId())) {
			Ocena stara = getOcenaPacijenta(novaOcena.getId(), novaOcena.getIdPacijenta());
			stara.setVrednost(novaOcena.getOcjena());
			Ocena updated = updateOcena(stara.getId(), stara);
		} else {
			Ocena nova = new Ocena();
			nova.setVrednost(novaOcena.getOcjena());
			Klinika k = null;
			try {
				k = findOne(novaOcena.getId());
			} catch (NotFoundException e) {
				System.out.println("greska 195 klinika service");
				e.printStackTrace();
			}
			Pacijent p = findOnePacijent(novaOcena.getIdPacijenta());
			nova.setPacijent(p);
			k.getOcena().add(nova);
			
			Ocena created = createOcena(nova);
			//Klinika kk = klinikaService.update(novaOcena.getId(), k);
		}
	}
	
	public ArrayList<LekarZaPacijentaDTO> getSlobodniLekariKlinike(SlobodniLekariKlinikeDTO slk){
		Klinika k = null;
		try {
			k = findOne(slk.getIdKlinike());
		} catch (NotFoundException e) {
			System.out.println("greska 215 klinika service");
			e.printStackTrace();
		}
		List<Lekar> lekars = lekariKlinikeTipa(slk.getIdKlinike(), slk.getNazivTipa());
		ArrayList<LekarZaPacijentaDTO> lekari = new ArrayList<LekarZaPacijentaDTO>();
		for (Lekar l : lekars) {
			if (lekarImaSlobodanTermin(l, slk.getDatum())/* && ljekarImaTip(l, slk.getNazivTipa())*/) {
				LekarZaPacijentaDTO lzp = new LekarZaPacijentaDTO(l);
				lzp.setListaVremena(getSlobodniTermini(l, slk.getDatum()));
				lzp.setCijena(slk.getNazivTipa(), k);
				lekari.add(lzp);
			}
		}
		return lekari;
	}
	
	public static boolean terminZauzetF(Lekar l, Calendar cal1 /*datum iz pretrage*/, Calendar c /*termin koji se provjerava*/) {
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
	
	public static boolean lekarImaSlobodanTermin(Lekar l, Date date1 /*datum iz pretrage*/) {
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
}
