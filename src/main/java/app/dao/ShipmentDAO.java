package app.dao;

import app.entities.Shipment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class ShipmentDAO implements IDAO<Shipment, Integer> {

    private static EntityManagerFactory emf;
    private static ShipmentDAO instance;
    private ShipmentDAO(EntityManagerFactory _emf) {
        emf = _emf;
    }

    public static ShipmentDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null){
            instance = new ShipmentDAO(_emf);
        }
        return instance;
    }

    @Override
    public Shipment create(Shipment shipment) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(shipment);
            em.getTransaction().commit();
            return shipment;
        }
    }

    @Override
    public List<Shipment> getAll() {
        try(EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT s FROM Shipment s", Shipment.class)
                    .getResultList();
        }

    }

    @Override
    public Shipment getById(Integer id) {
        try(EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT s FROM Shipment s WHERE s.id = :id", Shipment.class)
                    .setParameter("id",id)
                    .getSingleResult();
        }
    }

    @Override
    public Shipment update(Shipment shipment) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.merge(shipment);
            em.getTransaction().commit();
            return shipment;
        }

    }

    @Override
    public boolean delete(Shipment shipment) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
           int rowsAffected =  em.createQuery("DELETE FROM Shipment s WHERE s.id = :id")
                    .setParameter("id", shipment.getId())
                    .executeUpdate();
           em.getTransaction().commit();
           return rowsAffected > 0;
        }
    }


}
