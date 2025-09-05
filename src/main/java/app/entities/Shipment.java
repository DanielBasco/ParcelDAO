package app.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString

@Entity
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    LocalDateTime dateTime;


    @ManyToOne
    private Parcel parcel;

    @ManyToOne (cascade = CascadeType.PERSIST)
    private Location sourceLocation;

    @ManyToOne (cascade = CascadeType.PERSIST)
    private Location destinationLocation;

    public Shipment(Parcel parcel, Location sourceLocation, Location destinationLocation, LocalDateTime dateTime ) {
        this.dateTime = dateTime;
        this.parcel = parcel;
        this.sourceLocation = sourceLocation;
        this.destinationLocation = destinationLocation;
    }
}
