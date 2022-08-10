package com.api.member.domain.phoneAuthentication.domain

import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
class PhoneNumberAuthentication(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    val authenticationCode: String,

    val phoneNumber: String,

    val expiredDate: LocalDateTime? = null,

    @Column(nullable = false)
    var isAuthenticated: Boolean = false,

    var authenticatedAt: LocalDateTime? = null,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    var updatedAt: LocalDateTime = LocalDateTime.now()
) {

    fun complete(){
        this.isAuthenticated = true
        this.updatedAt = LocalDateTime.now()
        this.authenticatedAt = LocalDateTime.now()
    }
}