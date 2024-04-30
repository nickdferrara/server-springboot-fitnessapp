package com.nickdferrara.fitnessapp.models

import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import java.util.*

@Entity
@Table(name = "Address")
class Address(
    val street: String? = null,
    val city: String? = null,
    val state: String? = null,
    val zip: Int?  = null,
    @Id @UuidGenerator
    var id: UUID? = null,
)
