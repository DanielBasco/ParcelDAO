package app;

import app.config.HibernateConfig;
import app.entities.DeliveryStatus;
import app.entities.Parcel;
import jakarta.persistence.EntityManagerFactory;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private static final ParcelDAO dao = ParcelDAO.getInstance(emf);
    public static void main(String[] args) {






      //Bygger objekterne
      Parcel p1 = Parcel.builder()
                .trackingNumber("D321")
                .senderName("Daniel")
                .receiverName("Jesper")
                .deliveryStatus(DeliveryStatus.DELIVERED)
                        .build();
        Parcel p2 = Parcel.builder()
                .trackingNumber("A123")
                .senderName("Anna")
                .receiverName("Lars")
                .deliveryStatus(DeliveryStatus.PENDING)
                .build();

        Parcel p3 = Parcel.builder()
                .trackingNumber("B456")
                .senderName("Mikkel")
                .receiverName("Sofie")
                .deliveryStatus(DeliveryStatus.IN_TRANSIT)
                .build();

        Parcel p4 = Parcel.builder()
                .trackingNumber("C789")
                .senderName("Karsten")
                .receiverName("Maria")
                .deliveryStatus(DeliveryStatus.PENDING)
                .build();

        //Sætter dem i databasen
        dao.createParcel(p1);
        dao.createParcel(p2);
        dao.createParcel(p3);
        dao.createParcel(p4);

        //getByTrackingNumber
        System.out.println("getByTrackingNumber: "+dao.getByTrackingNumber("A123"));

        //findAll
        dao.findAll();
        System.out.println("findAll: "+dao.findAll());

        //updateStatus ud fra trackingNumber
        dao.updateStatus("B456", DeliveryStatus.PENDING);

        //deleteByTrackingNumber
        dao.deleteByTrackingNumber("C789");

        //findByStatus
        System.out.println("Find by DeliveryStatus: "+dao.findByStatus(DeliveryStatus.PENDING));
    }


}