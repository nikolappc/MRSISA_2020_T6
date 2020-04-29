package isamrs.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isamrs.domain.Lekar;
import isamrs.domain.Osoba;
import isamrs.domain.Pacijent;
import isamrs.dto.OsobaDTO;
import isamrs.repository.LekarRepository;

@Service
public class LekarService {

	@Autowired
	private LekarRepository lekarRepo;
	
	
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
		lekar.setId((int)lekarRepo.count());
		return lekarRepo.save(lekar);
	}

	public Lekar update(Integer id,Lekar lekar) {
		Lekar lekarForUpdate = lekarRepo.findById(id).orElseGet(null);
		return lekarRepo.save(lekar);
	}

	public void delete(Integer id) throws Exception {
		lekarRepo.deleteById(id);
	}
	
	public Lekar findByEmail(String email) {
		return lekarRepo.findByEmail(email);
	}

	
}
