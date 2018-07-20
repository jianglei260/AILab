package com.sharevar.study.fragment.learn;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.sharevar.study.R;
import com.sharevar.study.entity.FlatFragment;
import com.sharevar.study.entity.Fragment;
import com.zzhoujay.richtext.RichText;

import me.drakeet.multitype.ItemViewBinder;

public class LearnItemViewBinder extends ItemViewBinder<FlatFragment,LearnItemViewBinder.ViewHolder> {
    LearnFragment learnFragment;

    public LearnItemViewBinder(LearnFragment learnFragment) {
        this.learnFragment = learnFragment;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view=inflater.inflate(R.layout.learn_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull FlatFragment item) {
        RichText.fromMarkdown(item.getContent()).into(holder.contentText);
        holder.continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (learnFragment.next());else {
                    learnFragment.back();
                }
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private @NonNull
        final TextView contentText;
        private @NonNull final QMUIRoundButton continueButton;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.contentText = (TextView) itemView.findViewById(R.id.content_text);
            this.continueButton=(QMUIRoundButton) itemView.findViewById(R.id.continue_button);
        }
    }
}
