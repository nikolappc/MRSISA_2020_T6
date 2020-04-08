package com.example.demo.service;

import java.util.Collection;

import com.example.demo.domain.Lekar;

public interface LekarService {
	Collection<Lekar> findAll();

	Lekar findOne(Long id);

	Lekar create(Lekar lekar);

	Lekar update(Long id,Lekar lekar);

	void delete(Long id);
}
