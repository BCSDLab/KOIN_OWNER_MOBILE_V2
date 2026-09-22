package `in`.koreatech.business.core.navigation

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

sealed interface Screen : NavKey {
    @Serializable data object SignIn : Screen

    @Serializable data object SignUp : Screen

    @Serializable data object PasswordReset : Screen

    @Serializable data object Home : Screen

    @Serializable data object RegisterStore : Screen

    @Serializable data object Menu : Screen

    @Serializable data class MenuCreate(
        val shopId: Int
    ) : Screen

    @Serializable data class MenuEdit(
        val shopId: Int,
        val menuId: Int
    ) : Screen

    @Serializable data object Event : Screen

    @Serializable data class EventCreate(
        val shopId: Int
    ) : Screen

    @Serializable data class EventEdit(
        val shopId: Int,
        val eventId: Int
    ) : Screen

    @Serializable data object Settings : Screen

    @Serializable data object ManageShops : Screen

    @Serializable data class StoreEdit(
        val shopId: Int
    ) : Screen

    @Serializable data object Terms : Screen

    @Serializable data object OpenSourceLicenses : Screen
}

val screenSavedStateConfiguration =
    SavedStateConfiguration {
        serializersModule =
            SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(Screen.SignIn::class)
                    subclass(Screen.SignUp::class)
                    subclass(Screen.PasswordReset::class)
                    subclass(Screen.Home::class)
                    subclass(Screen.RegisterStore::class)
                    subclass(Screen.Menu::class)
                    subclass(Screen.MenuCreate::class)
                    subclass(Screen.MenuEdit::class)
                    subclass(Screen.Event::class)
                    subclass(Screen.EventCreate::class)
                    subclass(Screen.EventEdit::class)
                    subclass(Screen.Settings::class)
                    subclass(Screen.ManageShops::class)
                    subclass(Screen.StoreEdit::class)
                    subclass(Screen.Terms::class)
                    subclass(Screen.OpenSourceLicenses::class)
                }
            }
    }
