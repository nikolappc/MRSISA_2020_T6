package isamrs.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isamrs.domain.Lekar;
import isamrs.domain.Osoba;
import isamrs.domain.Pacijent;
import isamrs.domain.Pregled;
import isamrs.dto.OsobaDTO;
import isamrs.repository.LekarRepository;
import isamrs.repository.PregledRepository;

@Service
public class LekarService {

	@Autowired
	private LekarRepository lekarRepo;
	
	@Autowired
	private PregledRepository pregledRepo;
	
	
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
		return lekarRepo.save(lekar);
	}

	public void delete(Integer id) throws Exception {
		lekarRepo.deleteById(id);
	}
	
	public Lekar findByEmail(String email) {
		return lekarRepo.findByEmail(email);
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
	
	/*public ArrayList<Lekar> findLekariKlinike(int idKlinike){
		return lekarRepo.findLekariKlinike(idKlinike);
	}
*/

}
