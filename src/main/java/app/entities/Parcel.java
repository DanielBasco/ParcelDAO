package app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString

@Entity
public class Parcel {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private int ID;
    @Column(unique = true)
    private String trackingNumber;
    private String senderName;
    private String receiverName;
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;
    LocalDateTime updated;

    @OneToMany (mappedBy = "parcel", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    @Builder.Default
    private Set<Shipment> shipments = new HashSet<>();


    @PrePersist
    public void prePersist() {
        updated = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updated = LocalDateTime.now();
    }

    // Uni-directional add

    public void addShipment(Location sourceLocation, Location destinationLocation, LocalDateTime dateTime) {
        Shipment shipment = new Shipment(this, sourceLocation, destinationLocation, dateTime);
        this.shipments.add(shipment);

    }

}
