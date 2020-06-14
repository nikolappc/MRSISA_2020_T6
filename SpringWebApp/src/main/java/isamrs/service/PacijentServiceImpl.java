package isamrs.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import isamrs.registracija.OnRegistrationFailEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import isamrs.domain.Operacija;
import isamrs.domain.Pacijent;
import isamrs.domain.Poseta;
import isamrs.domain.Pregled;
import isamrs.domain.ZdravstveniKarton;
import isamrs.dto.IzmjenaOsobeDTO;
import isamrs.dto.PosetaDTO;
import isamrs.exceptions.NotFoundException;
import isamrs.registracija.VerificationToken;
import isamrs.registracija.VerificationTokenRepository;
import isamrs.repository.OperacijaRepository;
import isamrs.repository.PacijentRepository;


@Service
public class PacijentServiceImpl implements PacijentService {
	@Autowired
	private PacijentRepository pacijentRepository;
	
	@Autowired
	private OperacijaRepository operacijaRepo;
	
	@Autowired
	private VerificationTokenRepository verificationTokenRepo;
	
	@Override
	public Pacijent findOne(int id) {
		return pacijentRepository.findById(id).orElse(null);
	}
	
	@Override
	public Pacijent save(Pacijent pacijent) {
		return pacijentRepository.save(pacijent);
	}
	
	@Override
	public Pacijent findByEmail(String email) {
		return pacijentRepository.findByEmail(email);
	}
	
	@Override
	public Pacijent create(Pacijent pacijent) {
		//pacijent.setId((int)pacijentRepository.count());
		return pacijentRepository.save(pacijent);
	}
	
	@Override
	public void createVerificationToken(Pacijent user, String token) {  
		VerificationToken newUserToken = new VerificationToken(token, user);  
		verificationTokenRepo.save(newUserToken); 
	}
	
	@Override
	public VerificationToken getVerificationToken(String token) {
		return verificationTokenRepo.findByToken(token);
	}

	@Override
	public Collection<Pacijent> findNotConfirmed() {
		return pacijentRepository.findNotConfirmed();
	}

	@Override
	public Collection<Pacijent> findAll() {
		return pacijentRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<PosetaDTO> findBuduciPregled(Integer id) throws NotFoundException {
		//Pacijent p = pacijentRepository.findById(id).orElseThrow(NotFoundException::new);
		ArrayList<PosetaDTO> pregledi = new ArrayList<PosetaDTO>();		
		for(Pregled pregled : pacijentRepository.findPregled(id)) {
			Date d = new Date(System.currentTimeMillis());
			if(pregled.getSala() == null)
				continue;
			if(pregled.getTermin().getKraj().after(d)) {
				pregledi.add(new PosetaDTO(pregled));
			}
		}
		List<Operacija> operacije = operacijaRepo.findByPacijent(id);
		for(Operacija o : operacije) {
			Date d = new Date(System.currentTimeMillis());
			
			if(o.getTermin().getKraj().after(d)) {
				pregledi.add(new PosetaDTO(o));
			}
		}
		return pregledi;
	}

	@Override
	public Pacijent findByKarton(Integer idKartona) {
		return pacijentRepository.findByKarton(idKartona);
	}

    @Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
    public void setOdobren(String email, boolean b) {
		Pacijent pacijent = pacijentRepository.findByEmail(email);
		pacijent.setResponded(true);
		pacijent.setAllowed(b);
		pacijentRepository.save(pacijent);
    }

	@Override
	public Pacijent respond(String email) {
		Pacijent p = pacijentRepository.findByEmail(email);
		p.setResponded(true);
		pacijentRepository.save(p);
		return p;
	}

	public Pacijent update(Integer id,Pacijent lekar) {
		Pacijent pForUpdate = pacijentRepository.findById(id).orElseGet(null);
		if(pForUpdate == null)
			return null;
		pForUpdate.setAdresa(lekar.getAdresa());
		pForUpdate.setBrojTelefona(lekar.getBrojTelefona());
		pForUpdate.setEmail(lekar.getEmail());
		pForUpdate.setIme(lekar.getIme());
		pForUpdate.setPrezime(lekar.getPrezime());
		pForUpdate.setJbo(lekar.getJbo());
		pForUpdate.setPassword(lekar.getPassword());
		
		return pacijentRepository.save(pForUpdate);
	}
	
	
	public Pacijent izmijeni(Integer id, IzmjenaOsobeDTO pacijent) {
		Pacijent p = findOne(pacijent.getId());

		if (p == null) {
			return null;
			//return new ResponseEntity<Pacijent>(p, HttpStatus.BAD_REQUEST);
		}

		p.setIme(pacijent.getIme());
		p.setPrezime(pacijent.getPrezime());
		p.setPassword(pacijent.getPassword());
		p.setBrojTelefona(pacijent.getBrojTelefona());
		p.setJbo(pacijent.getJbo());
		p.getAdresa().setAdresa(pacijent.getAdresa().getAdresa());
		p.getAdresa().setGrad(pacijent.getAdresa().getGrad());
		p.getAdresa().setDrzava(pacijent.getAdresa().getDrzava());

		Pacijent updated = pacijentRepository.save(p);

		return updated;
		
	}
	
	
	

}
