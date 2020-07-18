package com.sam.nyarticle.adapter

import ae.chamber.mvcapplication.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ae.chamber.mvcapplication.model.MediaMetadata
import com.squareup.picasso.Picasso

class DetailImagesAdapter(val context: Context, val list: List<MediaMetadata>) :
    RecyclerView.Adapter<DetailImagesAdapter.VieHolder>() {

    var inflator: LayoutInflater? = null

    init {
        inflator = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VieHolder {
        val view = inflator?.inflate(R.layout.list_item_images, parent, false)
        return VieHolder(view!!)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: VieHolder, position: Int) {
        val item = list[position]

        Picasso.get()
            .load(item.url)
            .into(holder.imageView)

    }

    class VieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)

    }

}