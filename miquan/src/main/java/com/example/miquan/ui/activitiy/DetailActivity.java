package com.example.miquan.ui.activitiy;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.miquan.R;
import com.example.miquan.base.BaseActivity;
import com.example.miquan.base.ListItemViewModel;
import com.example.miquan.data.server.RetrofitProvider;
import com.example.miquan.databinding.ActivityDetailBinding;
import com.example.miquan.databinding.DialogBuyBinding;
import com.example.miquan.ui.detail.BuyHintViewModel;
import com.example.miquan.ui.detail.DetailViewModel;
import com.example.miquan.ui.detail.ImageItemViewModel;
import com.example.miquan.utils.IOTask;
import com.example.miquan.utils.RxUtil;
import com.example.miquan.utils.UIAction;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.File;
import java.io.FileOutputStream;

import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import me.tatarka.bindingcollectionadapter.BindingRecyclerViewAdapters;
import me.tatarka.bindingcollectionadapter.ItemViewArg;

public class DetailActivity extends BaseActivity {
    public static final String ID = "DetailActivity:ID";
    public int resourceId = 0;
    ActivityDetailBinding binding;
    DialogBuyBinding buyBinding;
    Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        resourceId = getIntent().getIntExtra(ID, 147);
        binding.setDetailViewModel(new DetailViewModel(resourceId, this));
        BindingRecyclerViewAdapters.setAdapter(binding.recyclerView, ItemViewArg.of(binding.getDetailViewModel().itemView), binding.getDetailViewModel().viewModels, null, null);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final int headerHeight = getResources().getDimensionPixelSize(R.dimen.header_height);
        final int headerBarHeight = getResources().getDimensionPixelSize(R.dimen.header_bar_height);
        binding.headerBar.setVisibility(View.VISIBLE);
//        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                int verticalOffset = binding.recyclerView.computeVerticalScrollOffset();
//                if (verticalOffset > (headerHeight - headerBarHeight)) {
//                    binding.headerBack.setVisibility(View.GONE);
//                    binding.headerBar.setVisibility(View.VISIBLE);
//                } else {
//                    binding.headerBar.setVisibility(View.GONE);
//                    binding.headerBack.setVisibility(View.VISIBLE);
//                }
//            }
//        });

    }

    int drawNum = 0;

    public void generateBitmap(final int num) {
//        Bitmap bitmap;
//        Canvas canvas=new Canvas(bitmap);
        drawNum = 0;
        int width = binding.recyclerView.getMeasuredWidth();
        final int height = binding.recyclerView.getMeasuredHeight();
        final Bitmap outBitmap = Bitmap.createBitmap(width, height * (num + 1), Bitmap.Config.ARGB_8888);
        final Paint paint = new Paint();
        final Canvas canvas = new Canvas(outBitmap);
        canvas.drawColor(Color.WHITE);
        for (int i = 0; i < num; i++) {
            ListItemViewModel itemViewModel = binding.getDetailViewModel().viewModels.get(i + 1);
            if (itemViewModel instanceof ImageItemViewModel) {
                String uri = ((ImageItemViewModel) itemViewModel).getUri();
                ImageRequest request = ImageRequest.fromUri(uri);
                final Rect dst = new Rect(0, i * height, width, height * (i + 1));
                DataSource<CloseableReference<CloseableImage>> dataSource = Fresco.getImagePipeline().fetchDecodedImage(request, this);
                dataSource.subscribe(new BaseBitmapDataSubscriber() {


                    @Override
                    protected void onNewResultImpl(Bitmap bitmap) {
                        Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                        canvas.drawBitmap(bitmap, src, dst, paint);
                        drawNum++;
                        if (drawNum == num) {
                            RxUtil.execute(new IOTask<Void>() {
                                @Override
                                public Void run() {
                                    drawQRCode(canvas, num, height, paint);
                                    save(outBitmap);
                                    return null;
                                }
                            }, new UIAction<Void>() {
                                @Override
                                public void onComplete(Void aVoid) {
                                    showToast(" 已保存到手机相册");
                                }
                            });
                        }
                    }

                    @Override
                    protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {

                    }
                }, CallerThreadExecutor.getInstance());
            }
        }
    }

    public void drawQRCode(Canvas canvas, int num, int height, Paint paint) {
        String content = RetrofitProvider.BASE_URl + "/v1/resource/" + resourceId;
        int codeHeight = 640;
        Bitmap qrBitmap = QRCodeEncoder.syncEncodeQRCode(content, codeHeight, Color.BLACK, Color.WHITE, BitmapFactory.decodeResource(getResources(), R.drawable.ic_logo_small));
        int topMargin = (height * num) + (height - codeHeight) / 2;
        int leftMargin = (canvas.getWidth() - codeHeight) / 2;
        Rect dst = new Rect(leftMargin, topMargin, leftMargin + codeHeight, topMargin + codeHeight);
        Rect src = new Rect(0, 0, qrBitmap.getWidth(), qrBitmap.getHeight());
        canvas.drawBitmap(qrBitmap, src, dst, paint);
        paint.setColor(getResources().getColor(R.color.colorPrimary));
        paint.setTextSize(60);
        int textTopMargin = (height * num) + height - codeHeight + 20;
        canvas.drawText("识别二维码查看更多图片", leftMargin, textTopMargin, paint);
    }

    public void save(Bitmap bitmap) {
        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "miquan_share_"+System.currentTimeMillis(), "miquan");
//        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "share.jpg";
//        try {
//            FileOutputStream fos = new FileOutputStream(path);
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, fos);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public void showBuyHint(int num) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.dialog);
//        buyBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.dialog_buy, null, false);
//        buyBinding.setBuyViewModel(new BuyHintViewModel());
//        builder.setView(buyBinding.getRoot());
//        dialog = builder.show();
    }

}
