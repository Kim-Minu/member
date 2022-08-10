package com.api.member.domain.user.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(length = 50, nullable = false)
    var email: String,

    @Column(length = 20, nullable = false)
    var name: String,

    @Column(length = 20, nullable = false)
    var nickname: String,

    @Column(length = 20, nullable = false)
    var phoneNumber: String,

    @Column(length = 100, nullable = false)
    var password: String,

    @Enumerated(EnumType.STRING)
    @Column(length = 5, nullable = false)
    var role: Role,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    var updatedAt: LocalDateTime = LocalDateTime.now()

) {

    fun resetPassword(password: String){
        this.password = password
        this.updatedAt = LocalDateTime.now()
    }

}