package application.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Map;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Pls fill this line")
    private Long code;
    @NotBlank(message = "Pls fill this line")
    private String name;
    @NotBlank(message = "Pls fill this line")
    private String description;
    private Double price;
    private String imagePath;
    private Double stock;
    private Boolean promotionalItem;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type", referencedColumnName = "type")
    private TypeProduct type;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "Product_priceFromSize", joinColumns = @JoinColumn(name = "Product_id"))
    private Map<String, Double> priceFromSize;

}
