package com.example.miquan.ui.detail

import android.content.Context
import android.databinding.ObservableField
import android.util.Log
import bolts.Task.call
import com.example.miquan.base.ListItemViewModel
import com.example.miquan.ui.activitiy.DetailActivity
import com.example.miquan.ui.activitiy.ImageActivity
import com.kelin.mvvmlight.command.ReplyCommand
import org.jetbrains.anko.startActivity
import rx.functions.Action0


class ImageItemViewModel(val uri: String, val context: Context, val index: Int) : ListItemViewModel() {
    var itemClick = ReplyCommand<Any>(Action0 {
        if (context is DetailActivity) {
            Log.d("start Image", "" + context.resourceId)
            context.startActivity<ImageActivity>(ImageActivity.IMG_INDEX to index, ImageActivity.RESOURCE_ID to context.resourceId)
        }
    })
    var imageUrl = ObservableField<String>()

    init {
        imageUrl.set(uri)
    }

    override fun getViewType(): Int {
        return VIEW_TYPE_IMAGE
    }
}