package isamrs.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import isamrs.domain.AdministratorKlinike;
import isamrs.domain.Operacija;
import isamrs.domain.Pregled;
import isamrs.domain.StavkaCenovnika;
import isamrs.domain.TipPosete;
import isamrs.repository.AdministratorKlinikeRepository;
import isamrs.repository.LekarRepository;
import isamrs.repository.OperacijaRepository;
import isamrs.repository.PregledRepository;
import isamrs.repository.TipPoseteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
	
	@Autowired
	private LekarRepository lekarRepo;
	
	
	public Collection<TipPosete> findAll(Integer idAdmina) {
		AdministratorKlinike ak;
		try {
			ak = adminRepo.findById(idAdmina).get();			
		} catch (Exception e) {
			return null;
		}
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

	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public TipPosete create(TipPosete t,Integer idAdmina) throws Exception{
		AdministratorKlinike ak;
		try {
			ak = adminRepo.findById(idAdmina).get();			
		} catch (Exception e) {
			return null;
		}
		ak.getKlinika().getTipPosete().add(t);
		ArrayList<StavkaCenovnika> lista = (ArrayList<StavkaCenovnika>) t.getStavkeCenovnika();
		StavkaCenovnika sc = lista.get(lista.size() - 1);
		sc.setTipPosete(t);
		sc.setCenovnik(ak.getKlinika().getCenovnik());
		ak.getKlinika().getCenovnik().getStavkaCenovnika().add(sc);
		TipPosete tp = tipRepo.save(t); 
		adminRepo.save(ak);
		return tp;
	}

	@Transactional(readOnly = false)
	public TipPosete update(Integer id, TipPosete t) throws Exception {
		
		TipPosete tp = tipRepo.findOneById(t.getId());
		if(tp == null)
			return null;
		
		List<Pregled> pregledi = pregledRepo.findByTip(tp);
		if(pregledi.isEmpty()) {
			List<Operacija> operacije = operacijaRepo.findByTip(tp);
			if(operacije.isEmpty()) {
				StavkaCenovnika sc =  tp.getStavkeCenovnika().iterator().next();
				sc.setCena( ((ArrayList<StavkaCenovnika>) t.getStavkeCenovnika()).get(0).getCena());
				tp.setTip(t.getTip());
				tp.setNaziv(t.getNaziv());
				return tipRepo.save(tp);
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

	public Collection<TipPosete> findAll() {
		return tipRepo.findAll();
	}

	@Transactional(readOnly = false)
	public Collection<TipPosete> findAllLekar(Integer id) {
		return lekarRepo.findOneById(id).getTipoviPoseta();
	}

}
