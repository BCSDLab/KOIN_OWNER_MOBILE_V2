package `in`.koreatech.business.core.term

interface TermsContentProvider {
    suspend fun getTerms(): TermsContent
}
