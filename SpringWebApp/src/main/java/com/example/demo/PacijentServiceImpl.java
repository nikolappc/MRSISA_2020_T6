package com.example.demo;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PacijentServiceImpl implements PacijentService {
	@Autowired
	private InMemoryPacijentRepository pacijentRepository;

	@Override
	public Pacijent getPacijent() {
		Pacijent pacijent = pacijentRepository.getPacijent();
		return pacijent;
	}
}
