package `in`.koreatech.business.data.service

import kotlin.test.Test
import kotlin.test.assertEquals

class Sha256PasswordHasherTest {
    private val passwordHasher = Sha256PasswordHasher()

    @Test
    fun hashReturnsSha256Hex() {
        assertEquals(
            "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8",
            passwordHasher.hash("password")
        )
    }
}
