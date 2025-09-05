package app;

import app.config.HibernateConfig;
import app.dao.LocationDAO;
import app.dao.ParcelDAO;
import app.dao.ShipmentDAO;
import app.entities.DeliveryStatus;
import app.entities.Location;
import app.entities.Parcel;
import app.entities.Shipment;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDateTime;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private static final ParcelDAO parcelDAO = ParcelDAO.getInstance(emf);
    private static final LocationDAO locationDAO = LocationDAO.getInstance(emf);
    private static final ShipmentDAO shipmentDAO = ShipmentDAO.getInstance(emf);

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

        Location l1 = Location.builder()
                .address("Skovshovedále 75")
                .latitude(56.10)
                .longitude(66.12)
                .build();

        Location l2 = Location.builder()
                .address("Sæbjørnsvej 22")
                .latitude(142.44)
                .longitude(132.56)
                .build();

        //Persister først location
        locationDAO.create(l1);
        locationDAO.create(l2);


        //Sætter dem i databasen
        parcelDAO.createParcel(p1);
        parcelDAO.createParcel(p2);
        parcelDAO.createParcel(p3);
        parcelDAO.createParcel(p4);

        //Inden persist tilføjer jeg shipment til parcel med location i
        p1.addShipment(l1, l2, LocalDateTime.now().plusDays(1));
        p1.addShipment(l2, l1, LocalDateTime.now().plusDays(2));

        parcelDAO.update(p1);
        parcelDAO.update(p2);

        //getByTrackingNumber
        System.out.println("getByTrackingNumber: "+parcelDAO.getByTrackingNumber("A123"));

        //findAll
        parcelDAO.findAll();
        System.out.println("findAll: "+parcelDAO.findAll());

        //updateStatus ud fra trackingNumber
        parcelDAO.updateStatus("B456", DeliveryStatus.PENDING);

        //deleteByTrackingNumber
        parcelDAO.deleteByTrackingNumber("C789");

        //findByStatus
        System.out.println("Find by DeliveryStatus: "+parcelDAO.findByStatus(DeliveryStatus.PENDING));


        // NY ------------------------------------------------------------------------------------------------|

        //Henter alle shipments
        System.out.println(shipmentDAO.getAll());


        //Henter location ud fra id
        System.out.println(locationDAO.getById(l1.getId()));


    }




}