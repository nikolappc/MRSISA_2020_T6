package isamrs.service;

import org.springframework.beans.factory.annotation.Autowired;

import isamrs.domain.MedicinskaSestra;
import isamrs.repository.SestraRepository;
import org.springframework.stereotype.Service;


@Service
public class MedicinskaSestraService {
	
	@Autowired
	private SestraRepository sestraRepository;
	
	public MedicinskaSestra findByEmail(String email) {
		return sestraRepository.findByEmail(email);
	}
}
