package com.nickdferrara.fitnessapp.models

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.UuidGenerator
import java.util.*

@Entity
@Table(name = "Roles")
class Role(
    var name: String = "",
    @Id @UuidGenerator
    var id: UUID? = null,
)
