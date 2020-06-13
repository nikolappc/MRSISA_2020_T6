package isamrs.service;

import isamrs.domain.Adresa;
import org.springframework.beans.factory.annotation.Autowired;

import isamrs.domain.MedicinskaSestra;
import isamrs.domain.Osoba;
import isamrs.repository.SestraRepository;
import org.springframework.stereotype.Service;


@Service
public class MedicinskaSestraService {
	
	@Autowired
	private SestraRepository sestraRepository;

	@Autowired
	private AdresaService adresaService;

	public MedicinskaSestra findByEmail(String email) {
		return sestraRepository.findByEmail(email);
	}

	public Osoba update(Integer id, MedicinskaSestra osoba) {
		MedicinskaSestra ms = sestraRepository.findById(id).orElseGet(null);
		if(ms == null)
			return null;
		Adresa a = adresaService.createAdresa(osoba.getAdresa());
		ms.setAdresa(a);

		ms.setBrojTelefona(osoba.getBrojTelefona());
		ms.setEmail(osoba.getEmail());
		ms.setIme(osoba.getIme());
		ms.setPrezime(osoba.getPrezime());
		ms.setJbo(osoba.getJbo());
		ms.setPassword(osoba.getPassword());
		
		return sestraRepository.save(ms);
	}
}
