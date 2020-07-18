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


        // get data from previous activity
        val i = intent
        if (i != null) {
            val detail = i.getStringExtra(MainActivity.DETAIL)
            url = i.getStringExtra(MainActivity.URL)
            itemDetail?.text = detail
        }

    }

    fun fabClick(view: View?) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }
}