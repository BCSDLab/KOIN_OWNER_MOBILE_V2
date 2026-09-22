package `in`.koreatech.business.domain.usecase.store

import dev.zacsweers.metro.Inject
import `in`.koreatech.business.domain.model.store.StoreSearchResult
import `in`.koreatech.business.domain.repository.SignupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Inject
class SearchStoresUseCase(
    private val repository: SignupRepository
) {
    operator fun invoke(query: String): Flow<Result<List<StoreSearchResult>>> =
        flow {
            emit(repository.searchStores(query))
        }
}
