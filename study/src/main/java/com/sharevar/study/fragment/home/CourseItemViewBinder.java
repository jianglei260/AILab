package com.sharevar.study.fragment.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.sharevar.study.R;
import com.sharevar.study.entity.Course;
import com.sharevar.study.fragment.learn.CourseContentFragment;

import me.drakeet.multitype.ItemViewBinder;

public class CourseItemViewBinder extends ItemViewBinder<Course,CourseItemViewBinder.ViewHoler> {
    private HomeLearnController controller;

    public CourseItemViewBinder(HomeLearnController controller) {
        this.controller = controller;
    }

    @NonNull
    @Override
    protected ViewHoler onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root=inflater.inflate(R.layout.learn_course_item,parent,false);
        return new ViewHoler(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHoler holder, @NonNull final Course item) {
        holder.courseImage.setImageURI(item.getImageURL());
        holder.nameText.setText(item.getName());
        holder.descText.setText(item.getDesc());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (controller.getHomeControlListener()!=null){
                    CourseContentFragment courseContentFragment=new CourseContentFragment();
                    courseContentFragment.setCourse(item);
                    controller.getHomeControlListener().startFragment(courseContentFragment);
                }
            }
        });
    }

    static class ViewHoler extends RecyclerView.ViewHolder {

        private @NonNull final TextView nameText;
        private @NonNull final TextView descText;
        private @NonNull final SimpleDraweeView courseImage;

        ViewHoler(@NonNull View itemView) {
            super(itemView);
            this.nameText = (TextView) itemView.findViewById(R.id.course_name);
            this.descText=(TextView) itemView.findViewById(R.id.course_desc);
            this.courseImage=(SimpleDraweeView) itemView.findViewById(R.id.course_image);
        }
    }
}
