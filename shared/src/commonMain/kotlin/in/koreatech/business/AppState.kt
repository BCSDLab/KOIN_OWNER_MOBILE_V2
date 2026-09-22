package `in`.koreatech.business

sealed interface AppState {
    data object Loading : AppState

    data class Ready(
        val accessToken: String?
    ) : AppState
}
