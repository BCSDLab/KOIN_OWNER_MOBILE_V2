package `in`.koreatech.business.domain.repository

import `in`.koreatech.business.domain.model.signup.OwnerRegistration
import `in`.koreatech.business.domain.model.store.StoreSearchResult

interface SignupRepository {
    suspend fun checkAccount(phoneNumber: String): Result<Unit>

    suspend fun requestSmsVerification(phoneNumber: String): Result<Unit>

    suspend fun verifySmsCode(
        phoneNumber: String,
        verificationCode: String
    ): Result<String>

    suspend fun checkCompanyNumber(companyNumber: String): Result<Unit>

    suspend fun registerOwner(
        registration: OwnerRegistration,
        verificationToken: String
    ): Result<Unit>

    suspend fun searchStores(query: String): Result<List<StoreSearchResult>>
}
