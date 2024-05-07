package com.nickdferrara.fitnessapp.models

import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import java.util.*

@Entity
@Table(name = "Carts")
class Cart (

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var products: MutableList<Product> = mutableListOf(),

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "userId", referencedColumnName = "id")
    var user: UserEntity? = null,

    @Id @UuidGenerator
    var id: UUID? = null,
)