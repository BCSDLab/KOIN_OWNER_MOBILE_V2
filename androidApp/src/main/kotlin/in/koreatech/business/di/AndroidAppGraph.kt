package `in`.koreatech.business.di

import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.createGraph
import `in`.koreatech.business.core.di.AppScope

@DependencyGraph(AppScope::class)
interface AndroidAppGraph : AppGraph

fun createAndroidAppGraph(): AndroidAppGraph = createGraph<AndroidAppGraph>()
