package isamrs.service;

import java.util.Collection;

import isamrs.domain.TipPosete;
import isamrs.repository.TipPoseteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipPoseteService {

	@Autowired
	private TipPoseteRepository tipRepo;
	
	public Collection<TipPosete> findAll() {
		return tipRepo.findAll();
	}

	public TipPosete findOne(Integer id) {
		return tipRepo.findById(id).orElseGet(null);
	}

	public TipPosete create(TipPosete t) throws Exception{
		return tipRepo.save(t);
	}

	public TipPosete update(Integer id, TipPosete t) throws Exception {
		TipPosete tp = tipRepo.findById(t.getId()).orElseGet(null);
		if(tp == null) 
			throw new Exception();
		return tipRepo.save(t);
	}

	public void delete(Integer id) throws Exception {
		tipRepo.deleteById(id);
	}

}
