package com.sharevar.miquan.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by jianglei on 16/8/14.
 */

public class CustomDraweeView extends SimpleDraweeView {
    public CustomDraweeView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public CustomDraweeView(Context context) {
        super(context);
    }

    public CustomDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private static final String TAG = "CustomDraweeView";

    private boolean loadding;
    private boolean loadfailed;

    @Override
    protected void onDraw(Canvas canvas) {
        if (getDrawable() instanceof BitmapDrawable) {
            BitmapDrawable drawable = (BitmapDrawable) getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            if (bitmap.isRecycled()) {
                setImageURI(uri);
                onAttach();
                return;
            }
        }
        super.onDraw(canvas);
    }

    protected Uri uri;

    public void setImageURI(String uri) {
        this.setImageURI(uri,null);
    }

    public boolean isLoadding() {
        return loadding;
    }

    public void setLoadding(boolean loadding) {
        this.loadding = loadding;
    }

    public boolean isLoadfailed() {
        return loadfailed;
    }

    public void setLoadfailed(boolean loadfailed) {
        this.loadfailed = loadfailed;
    }

    public void setImageURI(@Nullable String uriString, @Nullable Object callerContext) {
        loadfailed = false;
        loadding = true;
        ControllerListener listener = new BaseControllerListener() {
            @Override
            public void onFailure(String id, Throwable throwable) {
                super.onFailure(id, throwable);
                loadfailed = true;
                loadding = false;
            }

            @Override
            public void onFinalImageSet(String id, Object imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                loadding = false;
            }
        };
        this.uri = Uri.parse(uriString);
        DraweeController controller = Fresco.newDraweeControllerBuilder().setControllerListener(listener).setUri(Uri.parse(uriString)).build();
        setController(controller);
    }
}
