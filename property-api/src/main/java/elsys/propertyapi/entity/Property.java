package elsys.propertyapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;

import java.util.List;

@Entity
@Table(name = "properties")
@Data
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    @Column(nullable = false, updatable = false)
    private String ownerUuid;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Type(ListArrayType.class)
    @Column(columnDefinition = "TEXT[]")
    private List<String> images;

    @OneToOne(mappedBy = "property", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Location location;

    @ElementCollection(targetClass = PropertyFacility.class, fetch = FetchType.LAZY)
    @JoinTable(name = "property_facilities", joinColumns = @JoinColumn(name = "id"))
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private List<PropertyFacility> facilities;

    @JsonIgnore
    @OneToMany(mappedBy = "property", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Room> rooms;

    public void addImage(String imageUrl) {
        images.add(imageUrl);
    }
}
