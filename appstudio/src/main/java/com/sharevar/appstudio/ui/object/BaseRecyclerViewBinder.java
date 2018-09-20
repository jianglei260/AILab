package com.sharevar.appstudio.ui.object;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.sharevar.appstudio.R;
import com.sharevar.appstudio.runtime.core.function.Function;
import com.sharevar.appstudio.runtime.core.function.Mode;
import com.sharevar.appstudio.runtime.core.function.Parameter;
import com.sharevar.appstudio.runtime.core.statement.Statement;
import com.sharevar.appstudio.runtime.core.var.Variable;
import com.sharevar.appstudio.ui.common.RecyclerViewBinder;

import java.util.List;

public abstract class BaseRecyclerViewBinder extends RecyclerViewBinder<ItemWrapper<Statement>> {
    private RecyclerView recyclerView;
    protected Function function;
    protected Variable variable;
    protected Switch expandSwitch;

    public BaseRecyclerViewBinder(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Override
    public void bind(ItemWrapper<Statement> itemWrapper) {
        //                textView(R.id.index).setText(String.valueOf(itemWrappers.indexOf(itemWrapper)));
        ViewGroup.MarginLayoutParams marginLayoutParams = ((ViewGroup.MarginLayoutParams) viewHolder.itemView.getLayoutParams());
        marginLayoutParams.leftMargin = marginLayoutParams.leftMargin + recyclerView.getResources().getDimensionPixelOffset(R.dimen.child_left_margin) * itemWrapper.getDepth();
        function = itemWrapper.getObject().getFunction();
        variable = itemWrapper.getObject().getRetVaule();
        textView(R.id.fun_name).setText(function.getName());
        expandSwitch = (Switch) view(R.id.expand_switch);
    }

    public void initMode(RelativeLayout layout, final LinearLayout parameterLayout, final List<Mode> modes) {
        final TextView modeValue= textView(R.id.mode_value);
        modeValue.setText(modes.get(0).getName());
        textView(R.id.mode_name).setText("选项");
        final QMUIBottomSheet.BottomListSheetBuilder builder = new QMUIBottomSheet.BottomListSheetBuilder(recyclerView.getContext());
        for (Mode mode : modes) {
            builder.addItem(mode.getName());
        }
        builder.setOnSheetItemClickListener(new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
            @Override
            public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                dialog.dismiss();
                modeValue.setText(modes.get(position).getName());
                initParams(parameterLayout, modes.get(position).getParameters());
            }
        });
        final QMUIBottomSheet qmuiBottomSheet= builder.build();
        modeValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!qmuiBottomSheet.isShowing()){
                    qmuiBottomSheet.show();
                }
            }
        });
        initParams(parameterLayout, modes.get(0).getParameters());
    }

    public Context getContext(){
        return recyclerView.getContext();
    }

    public void initParams(LinearLayout container, List<Parameter> parameters) {
        int num = 0;
        container.removeAllViews();
        for (Parameter parameter : parameters) {
            if (parameter.getType() != null) {
                ParameterAdapter parameterAdapter = ParameterAdapterManager.getInstance(getContext()).get(parameter);
                if (parameterAdapter == null) {
                    parameterAdapter = ParameterAdapterManager.getInstance(getContext()).getDefault(parameter);
                }
                View view = parameterAdapter.getView();
                container.addView(view);
                if (num < parameters.size()) {
                    View line = new View(getContext());
                    line.setBackgroundColor(getContext().getResources().getColor(R.color.play_frgment_card_background));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getContext().getResources().getDimensionPixelSize(R.dimen.list_divider_height));
                    params.leftMargin = getContext().getResources().getDimensionPixelSize(R.dimen.child_left_margin);
                    container.addView(line, params);
                }
            }
        }
    }

    public void closeCard() {

    }

    public void expandCard() {

    }

}
