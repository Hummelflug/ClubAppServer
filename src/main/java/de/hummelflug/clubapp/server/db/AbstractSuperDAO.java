package de.hummelflug.clubapp.server.db;

import org.hibernate.SessionFactory;

import io.dropwizard.hibernate.AbstractDAO;

public class AbstractSuperDAO <E> extends AbstractDAO<E> {

	public AbstractSuperDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	public void commit() {
		currentSession().getTransaction().commit();
	}
	
	public E insert(E e) {
		return persist(e);
	}
	
	public void refresh(E e) {
		currentSession().refresh(e);
	}
	
	public E update(E e) {
        currentSession().merge(e);
        return e;
    }

}
