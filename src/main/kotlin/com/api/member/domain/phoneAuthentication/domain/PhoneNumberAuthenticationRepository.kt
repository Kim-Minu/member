package com.api.member.domain.phoneAuthentication.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PhoneNumberAuthenticationRepository: JpaRepository<PhoneNumberAuthentication, Long> {

    fun findFirstByPhoneNumberAndAuthenticationCodeAndIsAuthenticatedOrderByIdDesc(
        phoneNumber: String,
        authenticationCode: String,
        isAuthenticated: Boolean
    ): PhoneNumberAuthentication?

    fun findFirstByPhoneNumberAndIsAuthenticatedOrderByIdDesc(
        phoneNumber: String,
        isAuthenticated: Boolean
    ): PhoneNumberAuthentication?
}