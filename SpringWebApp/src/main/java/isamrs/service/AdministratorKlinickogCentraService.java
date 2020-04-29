package isamrs.service;

import isamrs.domain.AdministratorKlinickogCentra;
import isamrs.repository.AdministratorKlinickogCentraRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorKlinickogCentraService {
	@Autowired
	private AdministratorKlinickogCentraRepository adminkcRepository;
	
	public AdministratorKlinickogCentra findByEmail(String email) {
		return adminkcRepository.findByEmail(email);
	}
}
