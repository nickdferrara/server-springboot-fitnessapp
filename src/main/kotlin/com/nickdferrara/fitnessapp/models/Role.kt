package com.nickdferrara.fitnessapp.models

import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import java.util.UUID

@Entity
@Table(name = "Roles")
class Role(
    var name: String = "",
    @Id @UuidGenerator
    var id: UUID? = null,
)
