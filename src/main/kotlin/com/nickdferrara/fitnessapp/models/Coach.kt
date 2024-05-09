package com.nickdferrara.fitnessapp.models

import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import java.util.*

@Entity
@Table(name = "Coaches")
class Coach(

    val isActive:  Boolean = false,

    @OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], optional = true)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    var user: UserEntity? = null,

    @OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], optional = true)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    var person: Person? = null,

    @OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], optional = true)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    var address: Address? = null,

    @Id @UuidGenerator
    var id: UUID? = null,
)