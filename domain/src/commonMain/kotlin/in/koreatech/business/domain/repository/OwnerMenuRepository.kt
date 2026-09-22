package `in`.koreatech.business.domain.repository

import `in`.koreatech.business.domain.model.store.OwnerMenuCategory
import `in`.koreatech.business.domain.model.store.OwnerMenuCategoryOption
import `in`.koreatech.business.domain.model.store.OwnerMenuDetail
import `in`.koreatech.business.domain.model.store.OwnerMenuForm

interface OwnerMenuRepository {
    suspend fun getOwnerMenus(shopId: Int): Result<List<OwnerMenuCategory>>

    suspend fun getOwnerMenu(menuId: Int): Result<OwnerMenuDetail>

    suspend fun getOwnerMenuCategories(shopId: Int): Result<List<OwnerMenuCategoryOption>>

    suspend fun createOwnerMenu(
        shopId: Int,
        menu: OwnerMenuForm
    ): Result<Unit>

    suspend fun updateOwnerMenu(
        menuId: Int,
        menu: OwnerMenuForm
    ): Result<Unit>

    suspend fun deleteOwnerMenu(menuId: Int): Result<Unit>

    suspend fun createOwnerMenuCategory(
        shopId: Int,
        name: String
    ): Result<Unit>

    suspend fun updateOwnerMenuCategory(
        categoryId: Int,
        name: String
    ): Result<Unit>

    suspend fun deleteOwnerMenuCategory(categoryId: Int): Result<Unit>
}
