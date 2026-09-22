package `in`.koreatech.business.data.repository

import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.data.mapper.toOwnerMenuCategory
import `in`.koreatech.business.data.mapper.toOwnerMenuCategoryOptions
import `in`.koreatech.business.data.mapper.toOwnerMenuDetail
import `in`.koreatech.business.data.mapper.toOwnerMenuRequest
import `in`.koreatech.business.data.request.store.CreateOwnerMenuCategoryRequest
import `in`.koreatech.business.data.request.store.ModifyOwnerMenuCategoryRequest
import `in`.koreatech.business.data.source.remote.OwnerMenuRemoteDataSource
import `in`.koreatech.business.data.util.suspendRunCatching
import `in`.koreatech.business.domain.model.store.OwnerMenuCategory
import `in`.koreatech.business.domain.model.store.OwnerMenuCategoryOption
import `in`.koreatech.business.domain.model.store.OwnerMenuDetail
import `in`.koreatech.business.domain.model.store.OwnerMenuForm
import `in`.koreatech.business.domain.repository.OwnerMenuRepository

@ContributesBinding(AppScope::class)
@Inject
@SingleIn(AppScope::class)
class OwnerMenuRepositoryImpl(
    private val ownerMenuRemoteDataSource: OwnerMenuRemoteDataSource
) : OwnerMenuRepository {
    override suspend fun getOwnerMenus(shopId: Int): Result<List<OwnerMenuCategory>> =
        suspendRunCatching {
            ownerMenuRemoteDataSource.getOwnerMenus(shopId).menuCategories.map {
                it.toOwnerMenuCategory()
            }
        }

    override suspend fun getOwnerMenu(menuId: Int): Result<OwnerMenuDetail> =
        suspendRunCatching {
            ownerMenuRemoteDataSource.getOwnerMenu(menuId).toOwnerMenuDetail()
        }

    override suspend fun getOwnerMenuCategories(shopId: Int): Result<List<OwnerMenuCategoryOption>> =
        suspendRunCatching {
            ownerMenuRemoteDataSource.getOwnerMenuCategories(shopId).toOwnerMenuCategoryOptions()
        }

    override suspend fun createOwnerMenu(
        shopId: Int,
        menu: OwnerMenuForm
    ): Result<Unit> =
        suspendRunCatching {
            ownerMenuRemoteDataSource.createOwnerMenu(shopId, menu.toOwnerMenuRequest())
        }

    override suspend fun updateOwnerMenu(
        menuId: Int,
        menu: OwnerMenuForm
    ): Result<Unit> =
        suspendRunCatching {
            ownerMenuRemoteDataSource.updateOwnerMenu(menuId, menu.toOwnerMenuRequest())
        }

    override suspend fun deleteOwnerMenu(menuId: Int): Result<Unit> =
        suspendRunCatching {
            ownerMenuRemoteDataSource.deleteOwnerMenu(menuId)
        }

    override suspend fun createOwnerMenuCategory(
        shopId: Int,
        name: String
    ): Result<Unit> =
        suspendRunCatching {
            ownerMenuRemoteDataSource.createOwnerMenuCategory(
                shopId,
                CreateOwnerMenuCategoryRequest(name)
            )
        }

    override suspend fun updateOwnerMenuCategory(
        categoryId: Int,
        name: String
    ): Result<Unit> =
        suspendRunCatching {
            ownerMenuRemoteDataSource.updateOwnerMenuCategory(
                categoryId,
                ModifyOwnerMenuCategoryRequest(categoryId, name)
            )
        }

    override suspend fun deleteOwnerMenuCategory(categoryId: Int): Result<Unit> =
        suspendRunCatching {
            ownerMenuRemoteDataSource.deleteOwnerMenuCategory(categoryId)
        }
}
