package app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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

    @PrePersist
    public void prePersist() {
        updated = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updated = LocalDateTime.now();
    }

}
