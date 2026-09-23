package `in`.koreatech.business.core.designsystem.resource

import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import `in`.koreatech.business.core.designsystem.generated.resources.Res
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.core.term.TermsContent
import `in`.koreatech.business.core.term.TermsContentProvider

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class ComposeTermsContentProvider : TermsContentProvider {
    override suspend fun getTerms(): TermsContent = TermsContent(
        service = Res.readBytes("files/Terms_koin_sign_up.txt").decodeToString(),
        privacy = Res.readBytes("files/Terms_personal_information.txt").decodeToString(),
        marketing = Res.readBytes("files/Terms_marketing.txt").decodeToString()
    )
}
