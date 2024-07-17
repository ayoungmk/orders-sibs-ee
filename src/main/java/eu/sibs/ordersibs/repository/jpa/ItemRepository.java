package eu.sibs.ordersibs.repository.jpa;


import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import eu.sibs.ordersibs.model.entity.Item;

@Stateless
public class ItemRepository extends JPAImpl {

	public Item findByName(String name){
		String query = "SELECT * FROM TB_ITEM item WHERE item.name = '" + name + "'";
		TypedQuery<Item> createQuery = em.createQuery(query, Item.class);
		Item item = createQuery.getSingleResult();
		return item;
	}

	public List<Item> findAll() {
		return getAll(Item.class);
	}

	public Optional<Item> findById(Long id) {
		String query = "SELECT * FROM TB_ITEM item WHERE item.id = " + id;
		TypedQuery<Item> createQuery = em.createQuery(query, Item.class);
		Item item = createQuery.getSingleResult();
		return Optional.of(item);
	}

	public void save(Item item) {
		update(item);
	}

	public void deleteById(Long id) {
		Optional<Item> item = this.findById(id);
		em.remove(item);
	}
}
