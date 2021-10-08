package com.maitienhai.icommerce.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_color")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class ProductColor {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @OneToMany(mappedBy = "color", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

}
