package ae.chamber.mvcapplication.activity

import ae.chamber.mvcapplication.R
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ArticleDetailActivity : AppCompatActivity() {

    private var detail: String? = null
    private var itemDetail: TextView? = null
    private var fab: FloatingActionButton? = null
    private var mToolbar: Toolbar? = null
    private var url = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)

        mToolbar = findViewById(R.id.toolbar)
        fab = findViewById(R.id.fab)
        itemDetail = findViewById(R.id.article_detail)

        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mToolbar?.setNavigationOnClickListener { finish() }

        getIntentData()
        itemDetail?.text = detail

    }

    fun getIntentData(){
        val i = intent
        if (i != null) {
            detail = i.getStringExtra(MainActivity.DETAIL)
            url = i.getStringExtra(MainActivity.URL)
        }
    }

    fun fabClick(view: View?) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }
}