package eu.sibs.ordersibs.repository.jpa;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import eu.sibs.ordersibs.model.entity.StockMovement;

@Stateless
public class StockMovementsRepository extends JPAImpl{

    @PersistenceContext(unitName = "postg")
    protected EntityManager em;


    public void save(StockMovement stockMovement) {
        update(stockMovement);
    }

    public List<StockMovement> findAll() {
        return  getAll(StockMovement.class);
    }

    public Optional<StockMovement> findById(Long id) {
        String query = "SELECT * FROM TB_STOCK_MOVEMENT sm WHERE sm.id = " + id;
        TypedQuery<StockMovement> createQuery = em.createQuery(query, StockMovement.class);
        StockMovement stockMovement = createQuery.getSingleResult();
        return Optional.of(stockMovement);
    }
}
