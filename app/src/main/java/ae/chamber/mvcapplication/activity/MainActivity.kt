package ae.chamber.mvcapplication.activity

import ae.chamber.mvcapplication.R
import ae.chamber.mvcapplication.adapter.ArticleAdapter
import ae.chamber.mvcapplication.model.ModelResult
import ae.chamber.mvcapplication.model.ResponseModel
import ae.chamber.mvcapplication.network.Api
import ae.chamber.mvcapplication.network.NYAPI
import ae.chamber.mvcapplication.utils.GsonUtil
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener{
    var articleList = ArrayList<ModelResult>()
    var adapter : ArticleAdapter? = null

    var articleRcv: RecyclerView? = null
    var drawerLayout: DrawerLayout?= null
    val context: Context = this@MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        articleRcv = findViewById(R.id.articleRcv)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        adapter = ArticleAdapter(this,articleList)
        articleRcv?.let {
            articleRcv!!.adapter = adapter
        }

        getData(time = 7)

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout, toolbar,
            R.string.open,
            R.string.close)

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
        val id = item.itemId
        when(id){
            R.id.filterDay->{
                getData(time = 1)
            }
            R.id.filterWeek->{
                getData(time = 7)
            }
            R.id.filterMonth->{
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
        val temp = ArrayList<ModelResult>()
        for (item in articleList) {
            if (item.title.toLowerCase().contains(text.toLowerCase())) {
                temp.add(item)
            }
        }
        adapter?.updateList(temp)
    }



    private fun getData(time:Int) {
        try {
            showRcvProgress(true)
            val service = Api.urlApiService
            val call: Call<JsonObject> =
                service.getArticles("all-sections", time, getString(R.string.ny_value))
            call.enqueue(object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    try {
                        showRcvProgress(false)
                        if (response.code() == 200 || response.code()==201) {
                            val responseobj = response.body().toString()
                            val obj : JSONObject = JSONObject(responseobj)
                            val model: ResponseModel = GsonUtil.getInstance().gsonToResponseModel(obj)
                            var list = model.results as ArrayList<ModelResult>
                            for(item in list){
                                articleList.add(item)
                            }
                            adapter?.notifyDataSetChanged()

                        }else {
                            showToast(getString(R.string.error))
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        showToast(getString(R.string.error))
                    }
                }
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    showRcvProgress(false)
                    showToast(getString(R.string.error))
                }
            })
        } catch (e: Exception) {
            showRcvProgress(false)
            e.printStackTrace()
            showToast( getString(R.string.error))
        }
    }

    fun showToast(message :String){
        Toast.makeText(this,message, Toast.LENGTH_LONG).show()
    }

    fun showRcvProgress(isShow:Boolean){
        if(isShow){
            progrecc_rcv.visibility = View.VISIBLE
        }else{
            progrecc_rcv.visibility = View.GONE
        }
    }
}