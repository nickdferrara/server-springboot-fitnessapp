package com.nickdferrara.fitnessapp.models

import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import java.util.*

@Entity
@Table(name = "People")
class Person(
    val lastName: String? = null,
    val firstName: String? = null,
    val profilePicture: String? = null,

    @OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], optional = true)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    var address: Address? = null,

    @Id @UuidGenerator
    var id: UUID? = null,
)

