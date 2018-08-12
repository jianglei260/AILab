package com.sharevar.appstudio.ui.object;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sharevar.appstudio.R;
import com.sharevar.appstudio.data.Entity;
import com.sharevar.appstudio.object.Statement;
import com.sharevar.appstudio.object.Variable;
import com.sharevar.appstudio.object.function.Function;
import com.sharevar.appstudio.object.function.Parameter;
import com.sharevar.appstudio.object.function.builtin.CodeBlock;
import com.sharevar.appstudio.ui.base.BaseActivity;
import com.sharevar.appstudio.ui.common.RecyclerViewAdapter;
import com.sharevar.appstudio.ui.common.RecyclerViewBinder;

import java.util.ArrayList;
import java.util.List;

public class PlaygroundActivity extends BaseActivity {
    RecyclerView recyclerView;
    TextView title;
    List<Statement> statements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palyground);
        recyclerView = findViewById(R.id.recycler_view);
        title = findViewById(R.id.title);
    }

    private void initRecyclerView() {
        final RecyclerViewAdapter adapter = new RecyclerViewAdapter();
        adapter.register((Class<? extends ItemWrapper<Statement>>) ItemWrapper.class, R.layout.list_item_statement, new RecyclerViewBinder<ItemWrapper<Statement>>() {
            @Override
            public void bind(ItemWrapper<Statement> itemWrapper) {
                textView(R.id.index).setText(itemWrapper.getIndex());
                ViewGroup.MarginLayoutParams marginLayoutParams = ((ViewGroup.MarginLayoutParams) viewHolder.itemView.getLayoutParams());
                marginLayoutParams.leftMargin = getResources().getDimensionPixelOffset(R.dimen.child_left_margin) * itemWrapper.getDepth();
                Function function = itemWrapper.getObject().getFunction();
                Variable variable = itemWrapper.getObject().getRetVaule();
                textView(R.id.fun_name).setText(function.getName());
                textView(R.id.fun_return).setText(variable.getName());
                LinearLayout parameterLayout = linearLayout(R.id.fun_params);
                for (Parameter parameter : function.getParameters()) {
                    if (parameter.getType() != null) {
                        String typeName = parameter.getType().getName();
                        ParameterAdapter parameterAdapter = ParameterAdapterManager.getInstance().get(typeName);
                        if (parameterAdapter!=null){
                            parameterAdapter.nameText().setText(parameter.getName());
                            parameterLayout.addView(parameterAdapter.getView());
                        }
                    }
                }
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    public List<ItemWrapper<Statement>> toItemWrapper(List<Statement> statements) {
        List<ItemWrapper<Statement>> itemWrappers = new ArrayList<>();
        int index = 0;
        int depth = 0;
        for (Statement statement : statements) {
            addToWrapperList(itemWrappers, statement, index, depth, null);
        }
        return itemWrappers;
    }

    public void addToWrapperList(List<ItemWrapper<Statement>> wrappers, Statement statement, int index, int depth, ItemWrapper<Statement> parent) {
        ItemWrapper<Statement> wrapper = new ItemWrapper<>();
        wrapper.setObject(statement);
        wrapper.setIndex(index);
        wrapper.setDepth(depth);
        wrapper.setParent(parent);
        wrappers.add(wrapper);
        if (statement instanceof CodeBlock) {
            List<Statement> statements = ((CodeBlock) statement).getStatements();
            for (Statement child : statements) {
                addToWrapperList(wrappers, child, ++index, ++depth, wrapper);
            }

        }
    }

    public List<Statement> getStatements() {
        return statements;
    }
}
