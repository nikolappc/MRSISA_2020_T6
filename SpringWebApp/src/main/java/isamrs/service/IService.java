package isamrs.service;

import java.util.Collection;

import isamrs.domain.Lekar;

public interface IService<T> {
	Collection<T> findAll();

	T findOne(Long id);

	T create(T t);

	T update(Long id,T t);

	void delete(Long id);
}
