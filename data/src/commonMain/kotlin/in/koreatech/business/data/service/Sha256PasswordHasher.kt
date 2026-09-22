package `in`.koreatech.business.data.service

import dev.zacsweers.metro.ContributesBinding
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.domain.service.PasswordHasher
import okio.ByteString.Companion.encodeUtf8

@ContributesBinding(AppScope::class)
class Sha256PasswordHasher : PasswordHasher {
    override fun hash(password: String): String = password.encodeUtf8().sha256().hex()
}
