package isamrs.service;

import isamrs.domain.MedicinskoOsoblje;
import isamrs.domain.Pregled;
import isamrs.dto.PregledDTO;
import isamrs.repository.PregledRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PosetaService {

	@Autowired
	private PregledRepository pregledRepo;
	
	

	public Pregled create(Pregled pre) throws Exception{
		return pregledRepo.save(pre);
	}


}