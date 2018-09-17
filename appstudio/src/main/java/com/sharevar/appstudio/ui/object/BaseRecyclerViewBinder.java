package com.sharevar.appstudio.ui.object;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.Switch;

import com.sharevar.appstudio.R;
import com.sharevar.appstudio.runtime.core.function.Function;
import com.sharevar.appstudio.runtime.core.statement.Statement;
import com.sharevar.appstudio.runtime.core.var.Variable;
import com.sharevar.appstudio.ui.common.RecyclerViewBinder;

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
        expandSwitch= (Switch) view(R.id.expand_switch);
    }
    public void closeCard(){

    }

    public void expandCard(){

    }

}
