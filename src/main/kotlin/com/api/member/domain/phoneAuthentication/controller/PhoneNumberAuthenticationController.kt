package com.api.member.domain.phoneAuthentication.controller

import com.api.member.domain.phoneAuthentication.domain.PhoneNumberAuthentication
import com.api.member.domain.phoneAuthentication.dto.AuthenticationCodeAuthenticateRequestDto
import com.api.member.domain.phoneAuthentication.dto.AuthenticationCodeRequestDto
import com.api.member.domain.phoneAuthentication.dto.AuthenticationCodeResponseDto
import com.api.member.domain.phoneAuthentication.service.PhoneNumberAuthenticationService
import com.api.member.domain.user.dto.UserSignInRequestDto
import com.api.member.domain.user.dto.UserSignInResponseDto
import com.api.member.domain.user.dto.UserSignUpRequestDto
import com.api.member.domain.user.dto.UserSignUpResponseDto
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@Api(tags = ["전화번호 인증 API"])
@RestController
@RequestMapping("/api/v1/phoneNumberAuthentication")
class PhoneNumberAuthenticationController(
    private val phoneNumberAuthenticationService: PhoneNumberAuthenticationService
) {
    @ApiOperation(value = "인증번호 요청")
    @PostMapping("/request")
    fun request(@RequestBody @Valid requestDto: AuthenticationCodeRequestDto): ResponseEntity<AuthenticationCodeResponseDto> {
        val result = phoneNumberAuthenticationService.request(requestDto)
        return ResponseEntity(result, HttpStatus.CREATED)
    }

    @ApiOperation(value = "인증번호 확인")
    @PostMapping("/authenticate")
    fun authenticate(@RequestBody @Valid requestDto: AuthenticationCodeAuthenticateRequestDto): ResponseEntity<HttpStatus> {
        phoneNumberAuthenticationService.authenticate(requestDto)
        return ResponseEntity(HttpStatus.OK)
    }
}