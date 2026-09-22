package `in`.koreatech.business.feature.settings.terms

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class TermsState(
    val terms: ImmutableList<TermItem> = persistentListOf(),
    val selectedIndex: Int = 0,
    val isLoading: Boolean = true
)
