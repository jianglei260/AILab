package com.sharevar.appstudio.ui.object;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
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
import java.util.Collections;
import java.util.List;

public class PlaygroundActivity extends BaseActivity {
    RecyclerView recyclerView;
    TextView title;
    List<ItemWrapper<Statement>> itemWrappers;

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
                        if (parameterAdapter != null) {
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

    public void initRecyclerViewDrag() {
        final RecyclerView.Adapter adapter = recyclerView.getAdapter();
        new ItemTouchHelper(new ItemTouchHelper.Callback() {
            private RecyclerView.ViewHolder vh;

            @Override
            public boolean isItemViewSwipeEnabled() {
                return true;
            }

            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder
                    viewHolder) {
                // 拖拽的标记，这里允许上下左右四个方向
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT |
                        ItemTouchHelper.RIGHT;
                // 滑动的标记，这里允许左右滑动
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            /*
                这个方法会在某个Item被拖动和移动的时候回调，这里我们用来播放动画，当viewHolder不为空时为选中状态
                否则为释放状态
             */
            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if (viewHolder != null) {
                    vh = viewHolder;
                    pickUpAnimation(viewHolder.itemView);
                } else {
                    if (vh != null) {
                        putDownAnimation(vh.itemView);
                    }
                }
            }


            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                // 移动时更改列表中对应的位置并返回true
//                Collections.swap(itemWrappers, viewHolder.getAdapterPosition(), target
////                        .getAdapterPosition());
                ItemWrapper<Statement> itemWrapper = itemWrappers.remove(viewHolder.getAdapterPosition());
                itemWrappers.add(target.getAdapterPosition(), itemWrapper);
                //todo 实现itemwrapper 层级修改，可能需要修改Codeblock插入逻辑
                
                return true;
            }

            /*
                当onMove返回true时调用
             */
            @Override
            public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int
                    fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
                super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
                // 移动完成后刷新列表
                adapter.notifyItemMoved(viewHolder.getAdapterPosition(), target
                        .getAdapterPosition());
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // 将数据集中的数据移除
                ItemWrapper<Statement> itemWrapper = itemWrappers.remove(viewHolder.getAdapterPosition());
                if (itemWrapper.getDepth() > 0) {
                    CodeBlock codeBlock = (CodeBlock) itemWrapper.getParent().getParent().getObject();
                    codeBlock.getStatements().remove(itemWrapper.getObject());
                }
                // 刷新列表
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void pickUpAnimation(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationZ", 1f, 10f);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(300);
        animator.start();
    }

    private void putDownAnimation(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationZ", 10f, 1f);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(300);
        animator.start();
    }

    public void insertStatement(Statement statement) {
        //todo
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

    public void setStatements(List<Statement> statements) {
        itemWrappers = toItemWrapper(statements);
    }

    public List<Statement> getStatements() {
        List<Statement> statements = new ArrayList<>();
        for (ItemWrapper<Statement> itemWrapper : itemWrappers) {
            Statement statement = itemWrapper.getObject();
            if (itemWrapper.getDepth() > 0) {
                continue;
            } else {
                statements.add(statement);
            }
        }
        return statements;
    }
}
