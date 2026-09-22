package `in`.koreatech.business.domain.model.store

data class OwnerMenuCategory(
    val id: Int,
    val name: String,
    val menus: List<OwnerMenu>
)
