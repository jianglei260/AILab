package com.example.miquan.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.support.design.widget.BottomNavigationView
import android.support.v7.graphics.Palette
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.example.miquan.R
import com.example.miquan.ui.adapter.MainListItem
import com.example.miquan.ui.widget.CustomDraweeView
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.backends.pipeline.PipelineDraweeController
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.request.BasePostprocessor
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.relativeLayout

inline fun ViewManager.bottomNavigationView(theme: Int = 0, init: BottomNavigationView.() -> Unit): BottomNavigationView {
    return ankoView({ BottomNavigationView(it) }, theme, init)
}
inline fun ViewManager.appcompatImageView(theme: Int = 0, init: AppCompatImageView.() -> Unit): AppCompatImageView {
    return ankoView({ AppCompatImageView(it) }, theme, init)
}

inline fun ViewManager.smartRefreshLayout(): SmartRefreshLayout = smartRefreshLayout() {}
inline fun ViewManager.smartRefreshLayout(theme: Int = 0, init: SmartRefreshLayout.() -> Unit): SmartRefreshLayout {
    return ankoView({ SmartRefreshLayout(it) }, theme, init)
}

inline fun ViewManager.classicHeader(theme: Int = 0, init: ClassicsHeader.() -> Unit): ClassicsHeader {
    return ankoView({ ClassicsHeader(it) }, theme, init)
}

inline fun ViewManager.classicFooter(theme: Int = 0, init: ClassicsFooter.() -> Unit): ClassicsFooter {
    return ankoView({ ClassicsFooter(it) }, theme, init)
}

inline fun ViewManager.simpleDraweeView(theme: Int = 0, init: SimpleDraweeView.() -> Unit): SimpleDraweeView {
    return ankoView({ SimpleDraweeView(it) }, theme, init)
}

inline fun ViewManager.customDraweeView(theme: Int = 0, init: CustomDraweeView.() -> Unit): CustomDraweeView {
    return ankoView({ CustomDraweeView(it) }, theme, init)
}
inline fun showImageAndTintBg(simpleDraweeView: SimpleDraweeView, uri: Uri, tintView: View) {
    with(simpleDraweeView) {
        val processor = object : BasePostprocessor() {
            override fun process(bitmap: Bitmap?) {
                super.process(bitmap)
                if (bitmap != null) {
                    val builder = Palette.from(bitmap)
                    builder.generate {
                        val swatch: Palette.Swatch? = it.lightVibrantSwatch
                        var color = swatch?.rgb
                        if (color != null) {
                            if (color > (Color.WHITE - 256)) {
                                color = resources.getColor(R.color.colorPrimary)
                            }
                            tintView.setBackgroundColor(color)
                        }
                    }
//                    val height = (bitmap.height / bitmap.width) * width
//                    Log.d("load bitmap width", "$width $height")
//                    layoutParams = ViewGroup.LayoutParams(width, height)
                }


            }
        }
        val request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setPostprocessor(processor)
                .build()

        val controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(getController())
                // other setters as you need
                .build()
        setController(controller)
    }
}

open class _SmartRefreshLayout(ctx: Context) : SmartRefreshLayout(ctx) {
    inline fun <T : View> T.lparams(
            width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
            height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT
    ): T {
        val layoutParams = SmartRefreshLayout.LayoutParams(width, height)
        this@lparams.layoutParams = layoutParams
        return this
    }


}