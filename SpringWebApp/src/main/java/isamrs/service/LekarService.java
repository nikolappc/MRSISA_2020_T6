package isamrs.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import isamrs.domain.*;
import isamrs.dto.PosetaPacijentDTO;
import isamrs.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import isamrs.dto.GetOcenaDTO;
import isamrs.dto.LekarDTO;
import isamrs.dto.LekarSlobodanDTO;
import isamrs.dto.SetOcenaDTO;
import isamrs.repository.AdministratorKlinikeRepository;
import isamrs.repository.LekarRepository;
import isamrs.repository.OcenaRepository;
import isamrs.repository.OperacijaRepository;
import isamrs.repository.PacijentRepository;
import isamrs.repository.PregledRepository;
import isamrs.repository.TipPoseteRepository;
import isamrs.dto.ProveraLekarSlobodanDTO;

@Service
@Transactional(readOnly = true)
public class LekarService {

	@Autowired
	private LekarRepository lekarRepo;
	
	@Autowired
	private PregledRepository pregledRepo;
	
	@Autowired
	private OperacijaRepository operacijaRepo;

	@Autowired
	private PacijentRepository pacijentRepository;

	@Autowired
	private OcenaRepository repoOcena;
	
	@Autowired
	private AdministratorKlinikeRepository adminRepository;
	
	@Autowired
	private TipPoseteRepository tipRepo;

	@Autowired
	private AdresaService adresaService;


	public Collection<Lekar> findAll(Integer idAdmina) {
		AdministratorKlinike ak;
		try {
			ak = adminRepository.findById(idAdmina).get();			
		} catch (Exception e) {
			return null;
		}
		return lekarRepo.findByKlinika(ak.getKlinika().getId());
	}

	@Transactional(readOnly = false)
	public Lekar findOne(Integer id) {
		Lekar lekar = lekarRepo.findOneById(id);
		return lekar;
	}

	@Transactional(readOnly = false)
	public Lekar create(LekarDTO lekar, Integer idAdmina) {
		Lekar l = lekarRepo.save(lekar.getLekar());
		AdministratorKlinike ak;
		try {
			ak = adminRepository.findById(idAdmina).get();			
		} catch (Exception e) {
			return null;
		}
		l.setKlinika(ak.getKlinika());
		for(TipPosete tp : lekar.getTipovi()) {
			TipPosete tp1 = tipRepo.findById(tp.getId()).orElseGet(null);
			l.getTipoviPoseta().add(tp1);
			tp1.lekari.add(l);
		}
		ak.getKlinika().getLekari().add(l);
		//Adresa a = adresaService.createAdresa(l.getAdresa());
		//l.setAdresa(a);
		l = lekarRepo.save(l);
		return l;
	}
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Lekar updateUser(Integer id,Lekar lekar) throws Exception {
		
		
		Lekar lekarForUpdate = lekarRepo.findOneById(id);
		
		Adresa a = adresaService.createAdresa(lekar.getAdresa());
		lekarForUpdate.setAdresa(a);
		lekarForUpdate.setBrojTelefona(lekar.getBrojTelefona());
		lekarForUpdate.setEmail(lekar.getEmail());
		lekarForUpdate.setIme(lekar.getIme());
		lekarForUpdate.setPrezime(lekar.getPrezime());
		lekarForUpdate.setJbo(lekar.getJbo());
		lekarForUpdate.setPassword(lekar.getPassword());
		lekarForUpdate.setPrviPut(false);
		
		return lekarRepo.save(lekarForUpdate);
	}
	

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Lekar update(Integer id,Lekar lekar) throws Exception {
		
		
		Lekar lekarForUpdate = lekarRepo.findOneById(id);
		Date today = new Date();
		if(!lekarForUpdate.isPrviPut()) {
			
			for(Pregled p : lekarForUpdate.getPregled()) {
				if(p.getTermin().getKraj().after(today))
					throw new Exception();
			}
			
			for(Operacija o : lekarForUpdate.getOperacije()) {
				if(o.getTermin().getKraj().after(today))
					throw new Exception();
			}

		}
		Adresa a = adresaService.createAdresa(lekar.getAdresa());
		lekarForUpdate.setAdresa(a);
		lekarForUpdate.setBrojTelefona(lekar.getBrojTelefona());
		lekarForUpdate.setEmail(lekar.getEmail());
		lekarForUpdate.setIme(lekar.getIme());
		lekarForUpdate.setPrezime(lekar.getPrezime());
		lekarForUpdate.setJbo(lekar.getJbo());
		lekarForUpdate.setPassword(lekar.getPassword());
		lekarForUpdate.setPrviPut(false);
		
		return lekarRepo.save(lekarForUpdate);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
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
		if(p == null)
			return false;
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
	
	public boolean checkStartOperacija(Integer idOperacije, Lekar lekar) {
		Operacija o = operacijaRepo.findById(idOperacije).orElseGet(null);
		if(o == null)
			return false;
		for(Lekar l : o.getLekari()) {
			if(l.getId() != lekar.getId())
				continue;
			Date d = new Date(System.currentTimeMillis());
				
			if(o.getTermin().getPocetak().before(d) &&
					o.getTermin().getKraj().after(d)) {
				return true;
				
				
			}
		}
		
		return false;
	}

	public Collection<PosetaPacijentDTO> findPosete(Integer id) throws NotFoundException {
		ArrayList<PosetaPacijentDTO> pregledi = new ArrayList<>();
		
		Lekar l = lekarRepo.findById(id).orElseThrow(NotFoundException::new);
		
		for(Pregled p : pregledRepo.findByLekar(l)) {
			Pacijent pacijent = pacijentRepository.findByKarton(p.getZdravstveniKarton().getId());
			pregledi.add(new PosetaPacijentDTO(p,  pacijent));
		}
		
		for(Operacija o : operacijaRepo.findByLekar(l)) {
			Pacijent pacijent = pacijentRepository.findByKarton(o.getZdravstveniKarton().getId());
			pregledi.add(new PosetaPacijentDTO(o,  pacijent));		}
		
		return pregledi;
	}

	

	
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
		if(ocenaForUpdate == null)
			return null;
		ocenaForUpdate.setVrednost(o.getVrednost());
		return repoOcena.save(ocenaForUpdate);
	}
	
	public Ocena createOcena(Ocena o) {
		return repoOcena.save(o);
	}

	@Transactional(readOnly = false)
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
	
	@Transactional(readOnly = false)
	public void ocijeniLekara(SetOcenaDTO novaOcena) {
		if (pacijentOcijenioLekara(novaOcena.getIdPacijenta(), novaOcena.getId())) {
			Ocena stara = getOcenaPacijenta(novaOcena.getId(), novaOcena.getIdPacijenta());
			stara.setVrednost(novaOcena.getOcjena());
			Ocena updated = updateOcena(stara.getId(), stara);
		} else {
			Ocena nova = new Ocena();
			nova.setVrednost(novaOcena.getOcjena());
			Pacijent p = pacijentRepository.findById(novaOcena.getIdPacijenta()).orElseGet(null);
			if(p == null)
				return;
			Lekar l = findOne(novaOcena.getId());
			nova.setPacijent(p);
			l.getOcena().add(nova);

			Ocena created = createOcena(nova);
			//Lekar ll = lekarService.update(novaOcena.getId(), k);
		}
	}

	@Transactional(readOnly = false)
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

	public Boolean checkKarton(Integer idKarton, Integer idLekar) {
		if(pregledRepo.checkKarton(idKarton,idLekar).size() > 0) {
			return new Boolean(true);
		}
		else if(operacijaRepo.checkKarton(idKarton,idLekar).size() > 0){
			return new Boolean(true);
		}
		return new Boolean(false);
	}

}
