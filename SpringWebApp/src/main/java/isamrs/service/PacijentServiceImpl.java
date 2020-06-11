package isamrs.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import isamrs.domain.Pacijent;
import isamrs.domain.Pregled;
import isamrs.domain.ZdravstveniKarton;
import isamrs.dto.IzmjenaOsobeDTO;
import isamrs.dto.PosetaDTO;
import isamrs.exceptions.NotFoundException;
import isamrs.registracija.VerificationToken;
import isamrs.registracija.VerificationTokenRepository;
import isamrs.repository.PacijentRepository;


@Service
public class PacijentServiceImpl implements PacijentService {
	@Autowired
	private PacijentRepository pacijentRepository;
	
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
	public Collection<PosetaDTO> findBuduciPregled(Integer id) throws NotFoundException {
		Pacijent p = pacijentRepository.findById(id).orElseThrow(NotFoundException::new);
		ArrayList<PosetaDTO> pregledi = new ArrayList<PosetaDTO>();
		ZdravstveniKarton zk = p.getZdravstveniKarton();
		if(zk != null) {			
			for(Pregled pregled : zk.getPregledi()) {
				Date d = new Date(System.currentTimeMillis());
				
				if(pregled.getTermin().getKraj().after(d)) {
					pregledi.add(new PosetaDTO(pregled));
				}
			}
		}
		return pregledi;
	}

	@Override
	public Pacijent findByKarton(Integer idKartona) {
		return pacijentRepository.findByKarton(idKartona);
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
	
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
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

		System.out.println(p.getVersion()+"----------------------------------------------------------------------------------1");
		Pacijent updated = pacijentRepository.save(p);
		//pacijentRepository.flush();
		System.out.println(p.getVersion()+"----------------------------------------------------------------------------------2");

		return updated;
		
	}
	
	
	

}
