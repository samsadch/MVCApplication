package ae.chamber.mvcapplication.activity

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.test.rule.ActivityTestRule
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class MainActivityUITest {

    private var activeNetworkInfo: NetworkInfo? = null

    @get:Rule
    val activityTestRule: ActivityTestRule<MainActivity> = ActivityTestRule(
        MainActivity::class.java
    )

    @Before
    fun setUp() {
        val connectivityManager =
            activityTestRule.activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        activeNetworkInfo = connectivityManager.activeNetworkInfo
    }


}