package com.example.miquan.ui.adapter

import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.support.v7.graphics.Palette
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.miquan.R
import com.example.miquan.extensions.customDraweeView
import com.example.miquan.extensions.showImageAndTintBg
import com.example.miquan.extensions.simpleDraweeView
import com.example.miquan.model.Resource
import com.facebook.drawee.controller.BaseControllerListener
import com.facebook.drawee.controller.ControllerListener
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.interfaces.DraweeController
import com.facebook.imagepipeline.request.BasePostprocessor
import com.facebook.drawee.backends.pipeline.PipelineDraweeController
import com.facebook.drawee.generic.RoundingParams
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.facebook.imagepipeline.request.ImageRequest
import org.jetbrains.anko.sdk25.coroutines.onClick


class MainListAdapter(val resources: ArrayList<Resource>, val itemClick: (Resource) -> Unit) : RecyclerView.Adapter<MainListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = MainListItem().createView(AnkoContext.Companion.create(parent.context, this, false))
        return ViewHolder(view, itemClick)
    }

    override fun getItemCount(): Int = resources.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindResource(resource = resources[position])
    }

    class ViewHolder(view: View, private val itemClick: (Resource) -> Unit)
        : RecyclerView.ViewHolder(view) {
        val headImage: ImageView = view.find(MainListItem.head_image)
        val previewImage: SimpleDraweeView = view.find(MainListItem.preview_iamge)
        val titleText: TextView = view.find(MainListItem.title_text)
        val bottomLayout: RelativeLayout = view.find(MainListItem.bottom_layout)
        fun bindResource(resource: Resource) {
            with(resource) {
                showImageAndTintBg(previewImage, Uri.parse(img), bottomLayout)
                headImage.imageURI = (Uri.parse(img))
                titleText.text = title
                itemView.onClick { itemClick(resource) }
            }
        }

    }
}

class MainListItem : AnkoComponent<MainListAdapter> {
    companion object {
        val bottom_layout = 1
        val preview_iamge = 2
        val head_image = 3
        val title_text = 4
    }

    override fun createView(ui: AnkoContext<MainListAdapter>): View = with(ui) {
        frameLayout {
            cardView {
                radius = dip(8).toFloat()
                relativeLayout {
                    relativeLayout {
                        id = bottom_layout
                        backgroundResource = R.color.colorPrimary
                        customDraweeView {
                            id = head_image
                            hierarchy.roundingParams = RoundingParams.asCircle()
                        }.lparams(width = dip(40), height = dip(40)) {
                            centerVertically()
                            margin = dip(10)
                        }
                        textView {
                            id = title_text
                            textColor = Color.WHITE
                            maxLines = 2
                            ellipsize = TextUtils.TruncateAt.END
                        }.lparams {
                            centerVertically()
                            rightOf(head_image)
                        }
                    }.lparams(width = matchParent, height = dip(49)) {
                        below(preview_iamge)
                    }
                    customDraweeView {
                        id = preview_iamge
                    }.lparams(width = matchParent, height = dip(240)) {
                    }
                }.lparams(width = matchParent, height = wrapContent)
            }.lparams(width = matchParent, height = wrapContent) {
                padding = dip(2)
            }
        }
    }
}