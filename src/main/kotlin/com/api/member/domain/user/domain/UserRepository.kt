package com.api.member.domain.user.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Long> {
    fun findByEmail(email:String): User?
    fun findByPhoneNumberOrEmail(phoneNumber: String, email: String): User?
    fun findByPhoneNumber(phoneNumber: String): User?
    fun existsByEmail(email:String): Boolean
    fun existsByPhoneNumber(phoneNumber: String): Boolean
}