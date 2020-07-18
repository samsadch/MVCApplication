package ae.chamber.mvcapplication.activity

import ae.chamber.mvcapplication.R
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.sam.nyarticle.adapter.DetailImagesAdapter
import ae.chamber.mvcapplication.model.ModelResult
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar

    lateinit var resultData: ModelResult
    lateinit var adapter:DetailImagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        resultData = intent.getParcelableExtra("DATA") as ModelResult
        initViews()
    }

    private fun initViews() {
        try {
            Picasso.get()
                .load(resultData.media[0].mediaMetadata[0].url)
                .into(detail_image)

            toolbar = findViewById(R.id.toolbar)
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = resultData.title
            detail_header.text = resultData.title
            att_text.text = resultData.abstract

            /*ic_share.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(resultData.url));
                startActivity(browserIntent)
            }*/

            toolbar.setNavigationOnClickListener {
                finish()
                overridePendingTransition(0, 0)
            }

            adapter = DetailImagesAdapter(this,resultData.media[0].mediaMetadata)
            val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            images_rcv.layoutManager = layoutManager
            images_rcv.adapter =adapter

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}