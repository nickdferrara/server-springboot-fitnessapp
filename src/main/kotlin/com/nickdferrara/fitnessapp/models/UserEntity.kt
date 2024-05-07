package com.nickdferrara.fitnessapp.models

import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import java.util.*

@Entity
@Table(name = "Users")
class UserEntity(
    var email: String = "",
    var password: String = "",

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinTable(
        name = "UserRoles",
        joinColumns = [JoinColumn(name = "userId", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "roleId", referencedColumnName = "id")]
    )
    var roles: MutableList<Role> = mutableListOf(),

    @Id @UuidGenerator
    var id: UUID? = null,
)
