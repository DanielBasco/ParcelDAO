package app;

import app.config.HibernateConfig;

import app.entities.DeliveryStatus;
import app.entities.Parcel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;

import java.util.List;

public class ParcelDAO {
    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private static ParcelDAO instance;

    private ParcelDAO(EntityManagerFactory _emf) {
        emf = _emf;
    }

    public static ParcelDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null){
            instance = new ParcelDAO(_emf);
        }
        return instance;
    }




    public Parcel createParcel(Parcel parcel) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(parcel);
            em.getTransaction().commit();
            return parcel;
        }
    }

    public Parcel getByTrackingNumber(String trackingNumber){
        try(EntityManager em = emf.createEntityManager()) {
           return em.createQuery("SELECT p FROM Parcel p WHERE p.trackingNumber = ?1", Parcel.class)
                   .setParameter(1, trackingNumber)
                   .getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

     public List<Parcel> findAll(){
        try(EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT p FROM Parcel p", Parcel.class).getResultList();
        }
    }

    public void updateStatus(String trackingNumber, DeliveryStatus newStatus){
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.createQuery("UPDATE Parcel p SET p.deliveryStatus =?1 WHERE p.trackingNumber = ?2")
                    .setParameter(1, newStatus)
                    .setParameter(2, trackingNumber)
                    .executeUpdate();
            em.getTransaction().commit();
        }
    }

    public boolean deleteByTrackingNumber(String trackingNumber){
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
             int rowsAffected = em.createQuery("DELETE FROM Parcel p WHERE p.trackingNumber = ?1")
                    .setParameter(1, trackingNumber)
                            .executeUpdate();
            em.getTransaction().commit();
             if (rowsAffected == 1){
                 return true;
             } else {
                 return false;
             }
        }
    }

    public List<Parcel> findByStatus(DeliveryStatus status){
        try(EntityManager em = emf.createEntityManager()){
           return em.createQuery("SELECT p FROM Parcel p WHERE p.deliveryStatus = ?1")
                   .setParameter(1, status)
                   .getResultList();


        }
    }

}
