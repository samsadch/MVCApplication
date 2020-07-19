package ae.chamber.mvcapplication.activity

import ae.chamber.mvcapplication.R
import ae.chamber.mvcapplication.adapter.ArticleAdapter
import ae.chamber.mvcapplication.model.ResponseAPI
import ae.chamber.mvcapplication.model.Result
import ae.chamber.mvcapplication.network.Api
import ae.chamber.mvcapplication.utils.getFilteredData
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    var articleList = ArrayList<Result>()
    var adapter: ArticleAdapter? = null

    var articleRcv: RecyclerView? = null
    var drawerLayout: DrawerLayout? = null
    val context: Context = this@MainActivity

    companion object {
        const val URL = "URL"
        const val DETAIL = "DETAIL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        articleRcv = findViewById(R.id.articleRcv)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        adapter = ArticleAdapter(this, articleList)
        articleRcv?.let {
            articleRcv!!.adapter = adapter
        }

        getData(time = 7)

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout, toolbar,
            R.string.open,
            R.string.close
        )

        drawerLayout?.addDrawerListener(toggle)
        toggle.isDrawerIndicatorEnabled = true
        toggle.syncState()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_item, menu)

        val searchItem = menu!!.findItem(R.id.search)
        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.filterDay -> {
                getData(time = 1)
            }
            R.id.filterWeek -> {
                getData(time = 7)
            }
            R.id.filterMonth -> {
                getData(time = 30)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        filter(query.toString())
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        filter(newText.toString())
        return false
    }

    private fun filter(text: String) {
        adapter?.updateList(
            getFilteredData(
                text,
                articleList
            )
        )
    }


    private fun getData(time: Int) {
        try {
            showRcvProgress(true)
            val service = Api.urlApiService
            val call: Call<ResponseAPI> =
                service.getArticles("all-sections", time, getString(R.string.ny_value))
            call.enqueue(object : Callback<ResponseAPI> {
                override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
                    try {
                        showRcvProgress(false)
                        if (response.code() == 200 || response.code() == 201) {
                            val result = response.body()
                            var list = result?.results
                            list?.let {
                                setAadapter(list)
                            }
                        } else {
                            showToast(getString(R.string.error))
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        showToast(getString(R.string.error))
                    }
                }

                override fun onFailure(call: Call<ResponseAPI>, t: Throwable) {
                    showRcvProgress(false)
                    showToast(getString(R.string.error))
                }
            })
        } catch (e: Exception) {
            showRcvProgress(false)
            e.printStackTrace()
            showToast(getString(R.string.error))
        }
    }

    private fun setAadapter(list: List<Result>) {
        for (item in list) {
            articleList.add(item)
        }
        adapter?.notifyDataSetChanged()
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun showRcvProgress(isShow: Boolean) {
        if (isShow) {
            progrecc_rcv.visibility = View.VISIBLE
        } else {
            progrecc_rcv.visibility = View.GONE
        }
    }
}