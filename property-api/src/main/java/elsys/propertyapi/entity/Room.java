package elsys.propertyapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "rooms")
@Data
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private int guests;

    @Column(nullable = false, columnDefinition = "DECIMAL(10, 2)")
    private float price;

    @Column(nullable = false)
    private int area;

    @ElementCollection(targetClass = RoomFacility.class, fetch = FetchType.LAZY)
    @JoinTable(name = "room_facilities", joinColumns = @JoinColumn(name = "id"))
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private List<RoomFacility> facilities;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "property_id", referencedColumnName = "uuid")
    private Property property;
}
