package com.nickdferrara.fitnessapp.models

import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "Products")
class Product (
    var name: String = "",
    var description: String = "",
    var quantity: Int = 0,
    var price: BigDecimal = BigDecimal.ZERO,
    var category: String = "",
    var brand: String = "",
    var image: String? = null,

    @Id @UuidGenerator
    var id: UUID? = null,
)