package com.api.member.global.jwt

import com.api.member.domain.user.domain.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenProvider(
    @Qualifier("customUserDetailsService")
    private val userDetailsService: UserDetailsService,
) {
    private val secretKey: String = "jsonwebtokensecretkeysignwithmemberapitesttokenkey"
    private val signingKey: SecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))

    @Value("\${jwt.token-validity-in-seconds}")
    private val validityInMilliseconds: Long = 0L

    fun createToken(user: User): String {

        val claims: Claims = Jwts.claims().setSubject(user.email)

        claims["role"] = user.role
        claims["id"] = user.id

        val validity = Date(Date().time + validityInMilliseconds)

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(Date())
            .setExpiration(validity)
            .signWith(signingKey)
            .compact()
    }

    fun getAuthentication(token: String): Authentication {
        val userDetails : UserDetails = userDetailsService.loadUserByUsername(getUsername(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun getUsername(token: String): String {
        return Jwts.parserBuilder()
            .setSigningKey(signingKey.encoded)
            .build()
            .parseClaimsJws(token)
            .body.subject
    }

    fun getUserId(token: String): Long {

        val body = token.substring(7, token.length)

        val claims: Jws<Claims> = Jwts.parserBuilder()
            .setSigningKey(signingKey.encoded)
            .build()
            .parseClaimsJws(body)

        return claims.body.getValue("id").toString().toLong()
    }

    fun resolveToken(req : HttpServletRequest): String? {
        val bearerToken: String? = req.getHeader("Authorization")
        bearerToken?: return null

        if(!bearerToken.startsWith("Bearer ")) return null

        return bearerToken.substring(7, bearerToken.length)
    }

    fun validateToken(token: String): Boolean {
        try{
            val claims: Jws<Claims> = Jwts.parserBuilder().setSigningKey(signingKey.encoded).build().parseClaimsJws(token)
            if(claims.body.expiration.before(Date())) return false
            return true
        } catch (e: Exception) {
            return false
        }
    }

    fun expireToken(token: String){
        val claims: Jws<Claims> = Jwts.parserBuilder().setSigningKey(signingKey.encoded).build().parseClaimsJws(token)
        claims.body.expiration.time = Date().time
    }
}