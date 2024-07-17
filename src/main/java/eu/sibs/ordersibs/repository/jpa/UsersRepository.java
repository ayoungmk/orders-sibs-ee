package eu.sibs.ordersibs.repository.jpa;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import eu.sibs.ordersibs.model.entity.User;

@Stateless
public class UsersRepository extends JPAImpl{

    @PersistenceContext(unitName = "postg")
    protected EntityManager em;

    public User findByName(String userName) {
        String query = "SELECT * FROM TB_USER user WHERE user.name = " + userName;
        TypedQuery<User> createQuery = em.createQuery(query, User.class);
        User user = createQuery.getSingleResult();
        return user;
    }

    public List<User> findAll() {
        return getAll(User.class);
    }

    public Optional<User> findById(Long id) {
        String query = "SELECT * FROM TB_USER user WHERE user.id = " + id;
        TypedQuery<User> createQuery = em.createQuery(query, User.class);
        User user = createQuery.getSingleResult();
        return Optional.of(user);
    }

    public void save(User user) {
        update(user);
    }
}
