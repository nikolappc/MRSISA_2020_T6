package isamrs.service;

import isamrs.exceptions.NotFoundException;

import java.util.Collection;


public interface Service<T, ID> {
	Collection<T> findAll();

	T findOne(ID id) throws NotFoundException;

	T create(T t) throws NotFoundException;

	T update(ID id,T t) throws NotFoundException;

	void delete(ID id);
}
