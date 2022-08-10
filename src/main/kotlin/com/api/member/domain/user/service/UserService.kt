package com.api.member.domain.user.service

import com.api.member.domain.phoneAuthentication.domain.PhoneNumberAuthenticationRepository
import com.api.member.domain.phoneAuthentication.exception.AuthenticationInvalidCodeException
import com.api.member.domain.user.domain.UserRepository
import com.api.member.domain.user.dto.*
import com.api.member.domain.user.exception.*
import com.api.member.global.jwt.JwtTokenProvider
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
class UserService (
    private val userRepository: UserRepository,
    private val authenticationManager: AuthenticationManager,
    private val phoneNumberAuthenticationRepository: PhoneNumberAuthenticationRepository,
    private val jwtTokenProvider: JwtTokenProvider,
    )
{
    private val bCryptPasswordEncoder = BCryptPasswordEncoder()

    // 회원 정보 조회
    fun find(jwtToken: String): UserResponseDto {
        val id = jwtTokenProvider.getUserId(jwtToken)
        val user = userRepository.findByIdOrNull(id) ?: throw UserNotFoundException()
        return UserResponseDto(user)
    }

    // 회원 가입
    @Transactional
    fun signUp(requestDto: UserSignUpRequestDto): UserSignUpResponseDto{

        // 전화 번호 인증 여부 확인
        phoneNumberAuthenticationRepository
            .findFirstByPhoneNumberAndAuthenticationCodeAndIsAuthenticatedOrderByIdDesc(
            requestDto.phoneNumber,
            requestDto.authenticationCode,
            true
        ) ?: throw UserNotAuthPhoneNumberException(requestDto.phoneNumber)

        // 전화번호 중복 체크
        val phoneNumberExist = userRepository.existsByPhoneNumber(requestDto.phoneNumber)

        if(phoneNumberExist){
            throw PhoneNumberDuplicateException(requestDto.phoneNumber)
        }

        // 이메일 중복체크
        val emailExist = userRepository.existsByEmail(requestDto.email)

        if(emailExist){
            throw EmailDuplicateException(requestDto.email)
        }


        val user = userRepository.save(requestDto.toEntity(bCryptPasswordEncoder.encode(requestDto.password)))

        val token = jwtTokenProvider.createToken(user)

        return UserSignUpResponseDto(user, token)
    }

    /**
     * 로그인
     */
    fun signIn(requestDto: UserSignInRequestDto): UserSignInResponseDto {

        val user = userRepository.findByPhoneNumberOrEmail(requestDto.id, requestDto.id) ?: throw UserNotFoundException()

        try {
            //아이디 비밀번호 인증
            val authenticator = UsernamePasswordAuthenticationToken(user.email, requestDto.password)

            if(!authenticationManager.authenticate(authenticator).isAuthenticated){
                throw UserSignInException("아이디 또는 비밀번호를 확인해주세요.")
            }

        }catch (e: DisabledException) {
            throw UserSignInException(e.message!!)
        }catch (e: BadCredentialsException){
            throw UserSignInException(e.message!!)
        }catch (e: LockedException){
            throw UserSignInException(e.message!!)
        }

        val token = jwtTokenProvider.createToken(user)

        return UserSignInResponseDto(user, token)
    }

    // 비밀번호 찾기
    @Transactional
    fun findPassword(requestDto: UserFindPasswordRequestDto): UserFindPasswordResponseDto{

        val phoneNumber = requestDto.phoneNumber
        val authenticationCode = requestDto.authenticationCode

        // 인증 여부 확인
        val phoneNumberAuthentication = phoneNumberAuthenticationRepository
        .findFirstByPhoneNumberAndAuthenticationCodeAndIsAuthenticatedOrderByIdDesc(
            phoneNumber,
            authenticationCode,
            true
        ) ?: throw UserNotAuthPhoneNumberException(phoneNumber)

        val user = userRepository.findByPhoneNumber(phoneNumber) ?: throw UserNotFoundException()

        val newPassword = randomPasswordGenerator(8)
        val encNewPassword = bCryptPasswordEncoder.encode(newPassword)

        user.resetPassword(encNewPassword)

        return UserFindPasswordResponseDto(newPassword!!)
    }

    // 랜덤 문자열 생성
    fun randomPasswordGenerator(length: Int): String? {
        val randomString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val random = Random()
        val stringBuilder = StringBuilder(length)

        for (i in 0 until length) {
            stringBuilder.append(randomString[random.nextInt(randomString.length)])
        }

        return stringBuilder.toString()
    }
}