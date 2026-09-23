package `in`.koreatech.business.feature.menu.form.mapper

import `in`.koreatech.business.domain.model.store.OwnerMenuCategoryOption
import `in`.koreatech.business.domain.model.store.OwnerMenuDetail
import `in`.koreatech.business.domain.model.store.OwnerMenuForm
import `in`.koreatech.business.domain.model.store.OwnerMenuPrice
import `in`.koreatech.business.feature.menu.form.EditableMenuPrice
import `in`.koreatech.business.feature.menu.form.MenuFormState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet

internal fun MenuFormState.withCategories(categories: List<OwnerMenuCategoryOption>) = copy(
    categories = categories.toImmutableList(),
    isLoading = false,
    error = null
)

internal fun MenuFormState.withMenuDetail(
    categories: List<OwnerMenuCategoryOption>,
    detail: OwnerMenuDetail
) = copy(
    categories = categories.toImmutableList(),
    selectedCategoryIds = detail.categoryIds.toImmutableSet(),
    name = detail.name,
    description = detail.description,
    isSinglePrice = detail.prices.singleOrNull()?.option == null,
    singlePrice = detail.prices
        .singleOrNull()
        ?.price
        ?.toString()
        .orEmpty(),
    optionPrices = detail.prices
        .takeIf { prices -> prices.any { it.option != null } }
        ?.map { EditableMenuPrice(it.option.orEmpty(), it.price.toString()) }
        ?.toImmutableList()
        ?: persistentListOf(EditableMenuPrice()),
    imageUrls = detail.imageUrls.toImmutableList(),
    isLoading = false,
    error = null
)

internal fun MenuFormState.toOwnerMenuPrices(): ImmutableList<OwnerMenuPrice>? = if (isSinglePrice) {
    singlePrice.toIntOrNull()?.let { persistentListOf(OwnerMenuPrice(option = null, price = it)) }
} else {
    optionPrices
        .mapNotNull { price ->
            price.price.toIntOrNull()?.let { OwnerMenuPrice(price.option.trim(), it) }
        }.takeIf { prices ->
            prices.size == optionPrices.size && prices.all { it.option?.isNotBlank() == true }
        }?.toImmutableList()
}

internal fun MenuFormState.toOwnerMenuForm(prices: ImmutableList<OwnerMenuPrice>) = OwnerMenuForm(
    categoryIds = selectedCategoryIds.toImmutableList(),
    name = name.trim(),
    description = description.trim(),
    prices = prices,
    imageUrls = imageUrls
)
