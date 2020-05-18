package isamrs.service;

import java.util.Collection;


public interface Service<T, ID> {
	Collection<T> findAll();

	T findOne(ID id);

	T create(T t);

	T update(ID id,T t);

	void delete(ID id);
}
