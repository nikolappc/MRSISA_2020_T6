package isamrs.service;

import isamrs.domain.AdministratorKlinickogCentra;
import isamrs.domain.Adresa;
import isamrs.exceptions.NotFoundException;
import isamrs.repository.AdministratorKlinickogCentraRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AdministratorKlinickogCentraService implements isamrs.service.Service<AdministratorKlinickogCentra, Integer> {
	@Autowired
	private AdministratorKlinickogCentraRepository adminkcRepository;

	@Autowired
	AdresaService adresaService;

	public AdministratorKlinickogCentra findByEmail(String email) {
		return adminkcRepository.findByEmail(email);
	}

	@Override
	public Collection<AdministratorKlinickogCentra> findAll() {
		return adminkcRepository.findAll();
	}

	@Override
	public AdministratorKlinickogCentra findOne(Integer integer) throws NotFoundException {
		return adminkcRepository.findById(integer).orElseThrow(NotFoundException::new);
	}

	@Override
	public AdministratorKlinickogCentra create(AdministratorKlinickogCentra administratorKlinickogCentra) {

		Adresa a = adresaService.createAdresa(administratorKlinickogCentra.getAdresa());
		administratorKlinickogCentra.setAdresa(a);
		return adminkcRepository.save(administratorKlinickogCentra);
	}

	@Override
	public AdministratorKlinickogCentra update(Integer integer, AdministratorKlinickogCentra administratorKlinickogCentra) {
		administratorKlinickogCentra.setId(integer);
		Adresa a = adresaService.createAdresa(administratorKlinickogCentra.getAdresa());
		administratorKlinickogCentra.setAdresa(a);
		return adminkcRepository.save(administratorKlinickogCentra);
	}

	@Override
	public void delete(Integer integer) {
		if (adminkcRepository.findAll().size()==1){
			return;
		}
		adminkcRepository.deleteById(integer);
	}
}
