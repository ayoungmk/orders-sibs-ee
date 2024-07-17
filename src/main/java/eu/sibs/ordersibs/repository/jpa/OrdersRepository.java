package eu.sibs.ordersibs.repository.jpa;


import eu.sibs.ordersibs.model.entity.Item;
import eu.sibs.ordersibs.model.entity.Order;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Stateless
public class OrdersRepository extends JPAImpl {

	@PersistenceContext(unitName = "postg")
	protected EntityManager em;

	public List<Order> findByStatusAndItemIdOrderByCreationDateAsc(String status, Item item){
		String query = "SELECT * FROM TB_ORDER order WHERE order.status = " + status +
						" AND order.itemId = " + item +
						" ORDER BY order.creationDate asc";
		TypedQuery<Order> createQuery = em.createQuery(query, Order.class);
		List<Order> order = createQuery.getResultList();
		return order;
	}

	public List<Order> findAllByStatus(String status){
		String query = "SELECT * FROM TB_ORDER order WHERE order.status = " + status;
		TypedQuery<Order> createQuery = em.createQuery(query, Order.class);
		List<Order> order = createQuery.getResultList();
		return order;
	}


    public List<Order> findAll() {
		return getAll(Order.class);
    }

	public Optional<Order> findById(Long id) {
		String query = "SELECT * FROM TB_ORDER order WHERE order.id = " + id;
		TypedQuery<Order> createQuery = em.createQuery(query, Order.class);
		Order order = createQuery.getSingleResult();
		return Optional.of(order);
	}

	public void deleteById(Long id) {
		Optional<Order> order = this.findById(id);
		em.remove(order);
	}

	public void save(Order order) {
		update(order);
	}
}

