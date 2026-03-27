package org.example;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.Serializable;
import java.util.List;

@Named
@ApplicationScoped
public class DotDao implements DotRepository, Serializable {

    private final EntityManagerFactory emf;

    public DotDao() {
        emf = Persistence.createEntityManagerFactory("dots");
    }

    private EntityManager em() {
        return emf.createEntityManager();
    }


    public void addDotToDB(Dot dot) {
        EntityManager entityManager = em();
        var tx = entityManager.getTransaction();

        try {
            tx.begin();
            entityManager.persist(dot);
            tx.commit();
        } catch (RuntimeException e) {

            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }


    public List<Dot> getDotsFromDB() {
        EntityManager entityManager = em();
        try {
            return entityManager
                    .createQuery("SELECT d FROM Dot d", Dot.class)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }


    public void clearDotsInBD() {
        EntityManager entityManager = em();
        var tx = entityManager.getTransaction();

        try {
            tx.begin();
            entityManager.createQuery("DELETE FROM Dot").executeUpdate();
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }



    @Override
    public void save(Dot dot) {
        addDotToDB(dot);
    }

    @Override
    public List<Dot> findAll() {
        return getDotsFromDB();
    }

    @Override
    public void deleteAll() {
        clearDotsInBD();
    }
}
