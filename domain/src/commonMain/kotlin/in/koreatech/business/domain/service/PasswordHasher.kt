package `in`.koreatech.business.domain.service

interface PasswordHasher {
    fun hash(password: String): String
}
