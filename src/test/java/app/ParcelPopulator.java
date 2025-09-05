package app;

import app.dao.ParcelDAO;
import app.entities.DeliveryStatus;
import app.entities.Parcel;

public class ParcelPopulator {

    public static Parcel[] populate(ParcelDAO parcelDAO) {
        Parcel p1 = Parcel.builder()
                .trackingNumber("D321")
                .senderName("Daniel")
                .receiverName("Jesper")
                .deliveryStatus(DeliveryStatus.DELIVERED)
                .build();
        p1 = parcelDAO.createParcel(p1);


        Parcel p2 = Parcel.builder()
                .trackingNumber("K123")
                .senderName("Nanna")
                .receiverName("Skov")
                .deliveryStatus(DeliveryStatus.PENDING)
                .build();
        p2 = parcelDAO.createParcel(p2);

        return new Parcel[]{p1, p2};
    }

}
