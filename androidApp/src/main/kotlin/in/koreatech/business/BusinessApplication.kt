package `in`.koreatech.business

import android.app.Application
import `in`.koreatech.business.di.AndroidAppGraph
import `in`.koreatech.business.di.createAndroidAppGraph

class BusinessApplication : Application() {
    val appGraph: AndroidAppGraph by lazy {
        createAndroidAppGraph()
    }
}
