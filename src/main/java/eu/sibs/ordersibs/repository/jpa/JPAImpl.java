package eu.sibs.ordersibs.repository.jpa;



import eu.sibs.ordersibs.repository.IJPA;
import eu.sibs.ordersibs.exceptions.DataNotFoundException;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * 
 * Implementation of the JPA persistence
 *
 */
@ApplicationScoped
@Stateless
public class JPAImpl implements IJPA {

	@Produces
	@PersistenceContext(unitName = "postg")
	protected EntityManager em;

	@Override
	public <T> List<T> getAll(Class<T> type) {
		String classType = type.getName();
		String query = "SELECT * FROM " + classType;
		TypedQuery<T> createQuery = em.createQuery(query, type);
		
		List<T> dataList = createQuery.getResultList();
		return dataList;
	}

	@Override
	public <T> T get(Class<T> type, long id){
		T t = em.find(type, id);
		
		if (t == null)
			throw new DataNotFoundException("Trying to get an object that does not exists");
		return t;
	}

	@Override
	public <T> T add(T entity) {
		em.persist(entity);
		
		return entity;
	}

	@Override
	public <T> T update(T entity){
		return em.merge(entity);
	}

	@Override
	public <T> T delete(T entity) {
		em.remove(entity);
		return entity;
	}

}
