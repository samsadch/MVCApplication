package ae.chamber.mvcapplication.adapter

import ae.chamber.mvcapplication.R
import ae.chamber.mvcapplication.activity.ArticleDetailActivity
import ae.chamber.mvcapplication.activity.MainActivity
import ae.chamber.mvcapplication.model.Result
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import org.jetbrains.annotations.NotNull


class ArticleAdapter(private val context: Context, var list: ArrayList<Result>) :
    RecyclerView.Adapter<ArticleAdapter.VieHolder>() {
    var inflator: LayoutInflater? = null

    init {
        inflator = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VieHolder {
        val view = inflator?.inflate(R.layout.list_item_article, parent, false)
        return VieHolder(view!!)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: VieHolder, position: Int) {
        val item: Result = list[position]
        holder.headerTxv.text = item.title
        holder.byTxv.text = item.byline
        holder.dateTxv.text = item.published_date
        holder.sourceTxv.text = item.source

        setThumbImage(item,holder)

        holder.bodyCons.setOnClickListener {
            val intent = Intent(context, ArticleDetailActivity::class.java)
            intent.putExtra(MainActivity.URL,item.url)
            intent.putExtra(MainActivity.DETAIL,item.abstract)
            context.startActivity(intent)
        }
    }

    private fun setThumbImage(
        item: Result,
        holder: VieHolder
    ) {
        try {
            val imageUrl = item.media[0]
            Picasso.get()
                .load(item.media[0].mediaMetadata[1].url)
                .into(holder.profileImage)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    class VieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val headerTxv: TextView = itemView.findViewById(R.id.headline_text)
        val byTxv: TextView = itemView.findViewById(R.id.byTxv)
        val sourceTxv: TextView = itemView.findViewById(R.id.sourceTxv)
        val dateTxv: TextView = itemView.findViewById(R.id.dateTxv)
        val bodyCons: ConstraintLayout = itemView.findViewById(R.id.layout_item_body)
        val profileImage: CircleImageView = itemView.findViewById(R.id.thumb_image)
    }

    fun updateList(@NotNull temp: ArrayList<Result>) {
        list = temp
        notifyDataSetChanged()
    }

}