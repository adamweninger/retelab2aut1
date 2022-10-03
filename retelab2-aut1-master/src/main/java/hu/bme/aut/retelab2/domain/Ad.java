package hu.bme.aut.retelab2.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Ad {
    @Id
    @GeneratedValue
    private Long id;
    
    private String title;

    private String description;

    private Integer price;

    @CreationTimestamp
    private LocalDateTime creationTime;

    private String secretCode;

    @ElementCollection
    private List<String> tags = new ArrayList<>();

    private LocalDateTime expiry;
}
