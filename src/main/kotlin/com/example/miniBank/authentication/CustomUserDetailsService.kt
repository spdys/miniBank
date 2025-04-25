package com.example.miniBank.authentication

import com.example.miniBank.repository.UserRepository
import org.springframework.security.core.userdetails.*
import org.springframework.stereotype.Service
import org.springframework.security.core.userdetails.User

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository): UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found")

        return User.builder()
            .username(user.username)
            .password(user.password)
            .authorities(emptyList())
            .build()
    }
}
