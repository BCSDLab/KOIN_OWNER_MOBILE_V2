package `in`.koreatech.business.feature.menu.form.model

import `in`.koreatech.business.domain.model.store.OwnerMenuCategoryOption

data class MenuFormCategory(
    val id: Int,
    val name: String
)

internal fun OwnerMenuCategoryOption.toMenuFormCategory() = MenuFormCategory(
    id = id,
    name = name
)
