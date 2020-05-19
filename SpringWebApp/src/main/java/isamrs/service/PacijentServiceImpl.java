package isamrs.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isamrs.domain.Lekar;
import isamrs.domain.Pacijent;
import isamrs.domain.Pregled;
import isamrs.domain.ZdravstveniKarton;
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
	public Collection<PosetaDTO> findBuduciPregled(Integer id) {
		Pacijent p = pacijentRepository.findById(id).orElseGet(null);
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

	
	

}
