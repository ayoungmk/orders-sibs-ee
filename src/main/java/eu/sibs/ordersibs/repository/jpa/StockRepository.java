package eu.sibs.ordersibs.repository.jpa;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import eu.sibs.ordersibs.model.entity.Item;
import eu.sibs.ordersibs.model.entity.Stock;

@Stateless
public class StockRepository extends JPAImpl{

	@PersistenceContext(unitName = "postg")
	protected EntityManager em;

	public Stock findByItem(Item item){
		String query = "SELECT * FROM TB_STOCK stock WHERE stock.item = " + item;
		TypedQuery<Stock> createQuery = em.createQuery(query, Stock.class);
		Stock stock = createQuery.getSingleResult();
		return stock;
	}

    public void save(Stock stock) {
		update(stock);
    }

	public List<Stock> findAll() {
		return getAll(Stock.class);
	}

	public Optional<Stock> findById(Long id) {
		String query = "SELECT * FROM TB_STOCK stock WHERE stock.id = " + id;
		TypedQuery<Stock> createQuery = em.createQuery(query, Stock.class);
		Stock stock = createQuery.getSingleResult();
		return Optional.of(stock);
	}
}
