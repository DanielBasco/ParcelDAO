package app;

import app.config.HibernateConfig;
import app.entities.DeliveryStatus;
import app.entities.Parcel;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ParcelDAOTest {

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static final ParcelDAO parcelDAO = ParcelDAO.getInstance(emf);
    private static Parcel p1;
    private static Parcel p2;

    @BeforeEach
    void setUp() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Parcel ").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE parcel_seq RESTART WITH 1");
            em.getTransaction().commit();
            Parcel[] parcels = ParcelPopulator.populate(parcelDAO);
            p1 = parcels[0];
            p2 = parcels[1];
        } catch (Exception e){
            e.printStackTrace();
        }
    }



    @AfterAll
    void tearDown() {
        if (emf != null && emf.isOpen()) {
            emf.close();
            System.out.println("EntityManagerFactory closed");
        }
    }

    @Test
    void getInstance(){assertNotNull(emf);}

    @Test
    void createParcel() {

        Parcel p3 = Parcel.builder()
                .trackingNumber("E222")
                .receiverName("Jannik")
                .senderName("Sinner")
                .deliveryStatus(DeliveryStatus.DELIVERED)
                .build();
        parcelDAO.createParcel(p3);

        assertNotNull(p3.getID());

        assertEquals(3, parcelDAO.findAll().size());

    }

    @Test
    void getByTrackingNumber() {
        assertEquals("K123", p2.getTrackingNumber());
    }

    @Test
    void findAll() {
        assertEquals(2, parcelDAO.findAll().size());

    }

    @Test
    void updateStatus() {
        parcelDAO.updateStatus(p1.getTrackingNumber(), DeliveryStatus.PENDING);
        assertEquals(DeliveryStatus.PENDING, p1.getDeliveryStatus());
    }

    @Test
    void deleteByTrackingNumber() {
    }

    @Test
    void findByStatus() {
    }
}