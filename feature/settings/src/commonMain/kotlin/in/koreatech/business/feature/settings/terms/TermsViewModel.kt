package `in`.koreatech.business.feature.settings.terms

import androidx.lifecycle.ViewModel
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.binding
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import `in`.koreatech.business.core.di.AppScope
import `in`.koreatech.business.core.term.TermsContentProvider
import kotlinx.collections.immutable.toImmutableList
import org.orbitmvi.orbit.OrbitContainerHost
import org.orbitmvi.orbit.blockingIntent
import org.orbitmvi.orbit.viewmodel.orbitContainer

@Inject
@ViewModelKey
@ContributesIntoMap(AppScope::class, binding<ViewModel>())
class TermsViewModel(
    private val termsContentProvider: TermsContentProvider
) : ViewModel(), OrbitContainerHost<TermsState, TermsState, Nothing> {
    override val container = orbitContainer<TermsState, Nothing>(
        initialState = TermsState(),
        onCreate = { loadTerms() }
    )

    fun selectTerm(index: Int) = blockingIntent {
        if (index in state.terms.indices) reduce { state.copy(selectedIndex = index) }
    }

    private suspend fun loadTerms() = subIntent {
        val content = termsContentProvider.getTerms()
        val terms = listOf(
            TermItem(
                type = TermType.Service,
                content = content.service
            ),
            TermItem(
                type = TermType.Privacy,
                content = content.privacy
            ),
            TermItem(
                type = TermType.Marketing,
                content = content.marketing
            )
        )
        reduce { state.copy(terms = terms.toImmutableList(), isLoading = false) }
    }
}
