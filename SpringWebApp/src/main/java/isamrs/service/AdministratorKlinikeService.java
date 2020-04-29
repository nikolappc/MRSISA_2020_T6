package isamrs.service;

import isamrs.domain.AdministratorKlinike;
import isamrs.repository.AdministratorKlinikeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorKlinikeService {
	
	@Autowired
	private AdministratorKlinikeRepository adminklinikeRepository;
	
	public AdministratorKlinike findByEmail(String email) {
		return adminklinikeRepository.findByEmail(email);
	}
}
