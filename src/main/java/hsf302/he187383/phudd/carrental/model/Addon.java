package hsf302.he187383.phudd.carrental.model;


import hsf302.he187383.phudd.carrental.model.enums.PriceType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "addons")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Addon {
    @Id @GeneratedValue
    @Column(name = "addon_id")
    UUID addonId;

    @Column(nullable = false, unique = true)
    String name; // driver, insurance, gps ...

    @Enumerated(EnumType.STRING)
    @Column(name = "price_type", length = 16)
    PriceType priceType;

    @Column(precision = 12, scale = 2)
    BigDecimal price;

    String description;
}
