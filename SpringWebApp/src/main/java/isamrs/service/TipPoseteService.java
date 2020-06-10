package isamrs.service;

import java.util.Collection;
import java.util.List;

import isamrs.domain.AdministratorKlinike;
import isamrs.domain.Operacija;
import isamrs.domain.Pregled;
import isamrs.domain.TipPosete;
import isamrs.repository.AdministratorKlinikeRepository;
import isamrs.repository.OperacijaRepository;
import isamrs.repository.PregledRepository;
import isamrs.repository.TipPoseteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipPoseteService {

	@Autowired
	private TipPoseteRepository tipRepo;
	
	@Autowired
	private PregledRepository pregledRepo;
	
	@Autowired
	private OperacijaRepository operacijaRepo;
	
	@Autowired
	private AdministratorKlinikeRepository adminRepo;
	
	
	public Collection<TipPosete> findAll(Integer idAdmina) {
		AdministratorKlinike ak = adminRepo.findById(idAdmina).get();
		return tipRepo.findByKlinika(ak.getKlinika().getId());
	}
	
	public Collection<TipPosete> findPregledi() {
		return tipRepo.findPregledi();
	}

	public TipPosete findOne(Integer id) {
		return tipRepo.findById(id).orElseGet(null);
	}
	
	public TipPosete findByNaziv(String naziv) {
		return tipRepo.findByNaziv(naziv);
	}

	public TipPosete create(TipPosete t,Integer idAdmina) throws Exception{
		AdministratorKlinike ak = adminRepo.findById(idAdmina).get();
		TipPosete tp = tipRepo.save(t); 
		return tp;
	}

	public TipPosete update(Integer id, TipPosete t) throws Exception {
		
		TipPosete tp = tipRepo.findById(t.getId()).orElseGet(null);

		
		List<Pregled> pregledi = pregledRepo.findByTip(tp);
		if(pregledi.isEmpty()) {
			List<Operacija> operacije = operacijaRepo.findByTip(tp);
			if(operacije.isEmpty()) {

				return tipRepo.save(t);
			}
			else
				throw new Exception();
		}
		else
			throw new Exception();
		
		
	}

	public void delete(Integer id) throws Exception {
		tipRepo.deleteById(id);
	}

}
