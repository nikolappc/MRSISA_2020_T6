package com.example.demo.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Pacijent;
import com.example.demo.repository.InMemoryPacijentRepository;


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
