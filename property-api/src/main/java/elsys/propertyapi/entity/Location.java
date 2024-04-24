package elsys.propertyapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "locations")
@Data
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    @Column(nullable = false)
    private String googlePlaceId;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private int postalCode;

    @Column(columnDefinition = "DECIMAL(8, 6)", nullable = false)
    private double latitude;

    @Column(columnDefinition = "DECIMAL(9, 6)", nullable = false)
    private double longitude;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "property_uuid", referencedColumnName = "uuid")
    private Property property;
}
