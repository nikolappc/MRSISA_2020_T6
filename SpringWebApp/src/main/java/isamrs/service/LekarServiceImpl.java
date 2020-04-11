package isamrs.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isamrs.domain.Lekar;
import isamrs.repository.LekarRepositoryInterface;

@Service
public class LekarServiceImpl implements LekarService {

	@Autowired
	private LekarRepositoryInterface lekarRepo;
	
	
	@Override
	public Collection<Lekar> findAll() {
		return lekarRepo.findAll();
	}

	@Override
	public Lekar findOne(Long id) {
		return lekarRepo.findOne(id);
	}

	@Override
	public Lekar create(Lekar lekar) {
		Integer id = (int) this.findAll().size();
		lekar.setId(id);
		return lekarRepo.create(lekar);
	}

	@Override
	public Lekar update(Long id,Lekar lekar) {
		Lekar lekarForUpdate = lekarRepo.findOne(id);
		//Ovde ce biti neka logika za update
		return lekarRepo.update(lekar);
	}

	@Override
	public void delete(Long id) {
		lekarRepo.delete(id);
	}

	
}
