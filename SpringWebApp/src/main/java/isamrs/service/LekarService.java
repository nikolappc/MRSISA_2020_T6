package isamrs.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import isamrs.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isamrs.dto.GetOcenaDTO;
import isamrs.dto.LekarSlobodanDTO;
import isamrs.dto.OsobaDTO;
import isamrs.dto.SetOcenaDTO;
import isamrs.dto.TerminDTO;
import isamrs.repository.LekarRepository;
import isamrs.repository.OcenaRepository;
import isamrs.repository.OperacijaRepository;
import isamrs.repository.PacijentRepository;
import isamrs.repository.PregledRepository;
import isamrs.dto.ProveraLekarSlobodanDTO;

@Service
public class LekarService {

	@Autowired
	private LekarRepository lekarRepo;
	
	@Autowired
	private PregledRepository pregledRepo;
	
	@Autowired
	private OperacijaRepository operacijaRepo;
	
	@Autowired
	private OcenaRepository repoOcena;
	
	@Autowired
	private PacijentRepository repoPacijent;
	
	
	public Collection<OsobaDTO> findAll() {
		ArrayList<OsobaDTO> lekari = new ArrayList<OsobaDTO>();
		for(Lekar l : lekarRepo.findAll()) {
			lekari.add(new OsobaDTO(l));
		}
		
		return lekari;
	}

	public Lekar findOne(Integer id) {
		return lekarRepo.findById(id).orElseGet(null);
	}
	
	public Lekar create(Lekar lekar) {
		//lekar.setId((int)lekarRepo.count());
		return lekarRepo.save(lekar);
	}

	public Lekar update(Integer id,Lekar lekar) {
		Lekar lekarForUpdate = lekarRepo.findById(id).orElseGet(null);
		
		lekarForUpdate.setAdresa(lekar.getAdresa());
		lekarForUpdate.setBrojTelefona(lekar.getBrojTelefona());
		lekarForUpdate.setEmail(lekar.getEmail());
		lekarForUpdate.setIme(lekar.getIme());
		lekarForUpdate.setPrezime(lekar.getPrezime());
		lekarForUpdate.setJbo(lekar.getJbo());
		lekarForUpdate.setPassword(lekar.getPassword());
		
		return lekarRepo.save(lekarForUpdate);
	}

	public void delete(Integer id) throws Exception {
		lekarRepo.deleteById(id);
	}
	
	public Lekar findByEmail(String email) {
		return lekarRepo.findByEmail(email);
	}

	public Collection<Lekar> findByKlinika(Klinika k){
		return lekarRepo.findByKlinika(k.getId());
	}

	public boolean checkStart(Integer idPregleda, Lekar lekar) {
		Pregled p = pregledRepo.findById(idPregleda).orElseGet(null);
		// Ukoliko je taj lekar zaduzen za pregled i ukoliko je sada vreme
		// za zapocinjnaje pregleda vrati true
		if(p.getLekar().getId().equals(lekar.getId())) {
			Date d = new Date(System.currentTimeMillis());
			
			if(p.getTermin().getPocetak().before(d) &&
					p.getTermin().getKraj().after(d)) {
				return true;
			}
			
		}
		
		return false;
	}

	public Collection<TerminDTO> findTermini(Integer id) {
		ArrayList<TerminDTO> pregledi = new ArrayList<TerminDTO>();
		
		Lekar l = lekarRepo.findById(id).orElseGet(null);
		
		for(Pregled p : pregledRepo.findByLekar(l)) {
			pregledi.add(new TerminDTO(p)); 
		}
		
		for(Operacija o : operacijaRepo.findByLekar(l)) {
			pregledi.add(new TerminDTO(o));
		}
		
		return pregledi;
	}

	
	/*public ArrayList<Lekar> findLekariKlinike(int idKlinike){
		return lekarRepo.findLekariKlinike(idKlinike);
	}
*/
	
	public Boolean pacijentOcijenioLekara(int idPacijenta, int idLekara) {
		//System.out.println(lekarRepo.pacijentOcijenioLekara(idPacijenta, idLekara));
		System.out.println("hhhhhhhhhhhh"+idPacijenta + " " + idLekara);
		if (lekarRepo.pacijentOcijenioLekara(idPacijenta, idLekara).isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	public Boolean pacijentPosjetioLekara(int idPacijenta, int idLekara) {
		//System.out.println(lekarRepo.pacijentPosjetioLekara(idPacijenta, idLekara));
		if (lekarRepo.pacijentPosjetioLekara(idPacijenta, idLekara).isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	public Ocena getOcenaPacijenta(int idLekara, int idPacijenta) {
		return lekarRepo.getOcenaPacijenta(idLekara, idPacijenta);
	}
	
	public Ocena updateOcena(Integer id, Ocena o) {
		Ocena ocenaForUpdate = repoOcena.findById(id).orElseGet(null);
		ocenaForUpdate.setVrednost(o.getVrednost());
		return repoOcena.save(ocenaForUpdate);
	}
	
	public Ocena createOcena(Ocena o) {
		return repoOcena.save(o);
	}
	
	public Boolean proverDaLiJeLekarSlobodan(ProveraLekarSlobodanDTO provera) {
		Lekar l = findOne(provera.getIdLekara());
		if (KlinikaServiceImpl.lekarImaSlobodanTermin(l, provera.getDatum())) {
			for (TipPosete tp : l.getTipoviPoseta()) {
				if (tp.getNaziv().equals(provera.getNazivTipa())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public GetOcenaDTO pacijentPosjetioLekaraFunc(int idPacijenta, int id) {
		Boolean ocijenio = pacijentOcijenioLekara(idPacijenta, id);
		Boolean posjetio = pacijentPosjetioLekara(idPacijenta, id);
		GetOcenaDTO getOcena = new GetOcenaDTO();
		if (posjetio && ocijenio) {
			getOcena.setMozeOcjenjivati(true);
			getOcena.setOcjena(getOcenaPacijenta(id, idPacijenta).getVrednost());
		} else if (posjetio) {
			getOcena.setMozeOcjenjivati(true);
			getOcena.setOcjena(0);
		} else {
			getOcena.setMozeOcjenjivati(false);
			getOcena.setOcjena(0);
		}
		return getOcena;
	}
	
	public void ocijeniLekara(SetOcenaDTO novaOcena) {
		if (pacijentOcijenioLekara(novaOcena.getIdPacijenta(), novaOcena.getId())) {
			Ocena stara = getOcenaPacijenta(novaOcena.getId(), novaOcena.getIdPacijenta());
			stara.setVrednost(novaOcena.getOcjena());
			Ocena updated = updateOcena(stara.getId(), stara);
		} else {
			Ocena nova = new Ocena();
			nova.setVrednost(novaOcena.getOcjena());
			Pacijent p = repoPacijent.findById(novaOcena.getIdPacijenta()).orElseGet(null);
			Lekar l = findOne(novaOcena.getId());
			nova.setPacijent(p);
			l.getOcena().add(nova);
			
			Ocena created = createOcena(nova);
			//Lekar ll = lekarService.update(novaOcena.getId(), k);
		}
	}
	
	public LekarSlobodanDTO vratiVremenaCijenu(int idLekara, String nazivTipa, String datum) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
		Date d = null;
		try {
			d = sdf.parse(datum);
		} catch (ParseException e) {
			System.out.println("greska 217 lekar service");
			e.printStackTrace();
		}
		Lekar l = findOne(idLekara);
		LekarSlobodanDTO vremenaCijena = new LekarSlobodanDTO();
		vremenaCijena.setListaVremena(KlinikaServiceImpl.getSlobodniTermini(l, d));
		String naziv = nazivTipa.replace("%20", " ");
		vremenaCijena.setCijena(naziv, l.getKlinika());
		return vremenaCijena;
	}

}
