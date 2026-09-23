package `in`.koreatech.business.domain.usecase.address

import dev.zacsweers.metro.Inject
import `in`.koreatech.business.domain.model.address.AddressSearchResult
import `in`.koreatech.business.domain.repository.OwnerShopRepository

@Inject
class SearchAddressUseCase(
    private val ownerShopRepository: OwnerShopRepository
) {
    suspend operator fun invoke(keyword: String): Result<List<AddressSearchResult>> =
        ownerShopRepository.searchAddress(keyword.trim())
}
