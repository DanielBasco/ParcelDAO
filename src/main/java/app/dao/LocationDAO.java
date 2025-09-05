package app.dao;

import app.entities.Location;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class LocationDAO implements IDAO<Location, Integer> {

    private static EntityManagerFactory emf;
    private static LocationDAO instance;
    private LocationDAO(EntityManagerFactory _emf) {
        emf = _emf;
    }

    public static LocationDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null){
            instance = new LocationDAO(_emf);
        }
        return instance;
    }

    @Override
    public Location create(Location location) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(location);
            em.getTransaction().commit();
            return location;
        }
    }

    @Override
    public List<Location> getAll() {
        try(EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT l FROM Location l", Location.class)
                    .getResultList();
        }
    }

    @Override
    public Location getById(Integer id) {
        try(EntityManager em = emf.createEntityManager()) {
         return em.createQuery("SELECT l FROM Location l WHERE l.id = :id", Location.class)
                 .setParameter("id", id)
                 .getSingleResult();
        }
    }

    @Override
    public Location update(Location location) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.merge(location);
            em.getTransaction().commit();
            return location;
        }
    }

    @Override
    public boolean delete(Location location) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            int rowsAffected = em.createQuery("DELETE FROM Location l WHERE l.id = :id")
                    .setParameter("id", location.getId())
                    .executeUpdate();
            em.getTransaction().commit();
            return rowsAffected > 0;
        }
    }
}
