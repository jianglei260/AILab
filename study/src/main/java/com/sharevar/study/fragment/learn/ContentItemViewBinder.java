package com.sharevar.study.fragment.learn;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sharevar.study.R;
import com.sharevar.study.entity.Chapter;
import com.sharevar.study.entity.Fragment;
import com.sharevar.study.entity.Unit;

import java.util.List;

import me.drakeet.multitype.ItemViewBinder;

public class ContentItemViewBinder extends ItemViewBinder<Unit,ContentItemViewBinder.ViewHoler> {
    private CourseContentFragment fragment;

    public ContentItemViewBinder(CourseContentFragment fragment) {
        this.fragment = fragment;
    }

    @NonNull
    @Override
    protected ViewHoler onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root=inflater.inflate(R.layout.layout_content_card,parent,false);
        return new ViewHoler(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHoler holder, @NonNull final Unit item) {
        holder.titleText.setText(item.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(item,item);
            }
        });
        List<Chapter> chapters= item.getChapters();
        if (chapters!=null&&chapters.size()>0){
            LayoutInflater inflater=LayoutInflater.from(holder.itemView.getContext());
            for (final Chapter chapter : chapters) {
                View chapterItemView=inflater.inflate(R.layout.course_content_item,holder.container,false);
                TextView titleText=chapterItemView.findViewById(R.id.chapter_name);
                titleText.setText(chapter.getName());
                holder.container.addView(chapterItemView);
                chapterItemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startFragment(chapter,item);
                    }
                });
            }
        }else {

        }
    }

    private void startFragment(Fragment courseFragment,Unit unit){
        LearnFragment learnFragment=new LearnFragment();
        learnFragment.setCurrent(courseFragment);
        learnFragment.setUnit(unit);
        this.fragment.startFragment(learnFragment);
    }

    static class ViewHoler extends RecyclerView.ViewHolder {

        private @NonNull
        final TextView titleText;
        private @NonNull final LinearLayout container;

        ViewHoler(@NonNull View itemView) {
            super(itemView);
            this.titleText = (TextView) itemView.findViewById(R.id.content_title);
            this.container=(LinearLayout) itemView.findViewById(R.id.content_container);
        }
    }
}
