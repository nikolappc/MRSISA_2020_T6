package isamrs.repository;

import java.util.Collection;
import java.util.HashMap;

import org.springframework.stereotype.Repository;

import isamrs.domain.Lekar;

@Repository
public class LekarRepository implements LekarRepositoryInterface{
	private HashMap<Long,Lekar> lekari = new HashMap<Long, Lekar>();

	@Override
	public Collection<Lekar> findAll() {
		return lekari.values();
	}

	@Override
	public Lekar findOne(Long id) {
		return lekari.get(id);
	}

	@Override
	public Lekar create(Lekar lekar) {
		return lekari.put(lekar.getId(), lekar);
	}

	@Override
	public Lekar update(Lekar lekar) {
		return lekari.put(lekar.getId(), lekar);
	}

	@Override
	public void delete(Long id) {
		lekari.remove(id);
	}

	
}
