package com.sharevar.appstudio.ui.object;

import android.animation.ObjectAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.reflect.TypeToken;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.sharevar.appstudio.R;
import com.sharevar.appstudio.runtime.core.statement.Statement;
import com.sharevar.appstudio.runtime.core.var.Variable;
import com.sharevar.appstudio.runtime.core.function.CodeBlock;
import com.sharevar.appstudio.runtime.core.function.Function;
import com.sharevar.appstudio.runtime.core.function.Mode;
import com.sharevar.appstudio.runtime.core.function.Parameter;
import com.sharevar.appstudio.runtime.sdk.FunctionAdapter;
import com.sharevar.appstudio.runtime.sdk.script.IfAdapter;
import com.sharevar.appstudio.ui.base.BaseFragment;
import com.sharevar.appstudio.ui.common.ClassLinkRule;
import com.sharevar.appstudio.ui.common.RecyclerViewAdapter;
import com.sharevar.appstudio.ui.common.RecyclerViewBinder;

import java.util.ArrayList;
import java.util.List;

public class PlaygroundFragment extends BaseFragment {
    RecyclerView recyclerView;
    QMUITopBar mTopBar;
    List<ItemWrapper<Statement>> itemWrappers = new ArrayList<>();
    ClassLinkRule rule;
    RecyclerViewBinder[] recyclerViewBinders;

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_palyground, null);
        recyclerView = root.findViewById(R.id.recycler_view);
        mTopBar = root.findViewById(R.id.topbar);
        initRule();
        initBinders(DefaultRecyclerViewBinder.class,IfRecyclerViewBinder.class, ElseRecyclerViewBinder.class, EndRecyclerViewBinder.class);
        initTopBar();
        initRecyclerView();
        initRecyclerViewDrag();
        return root;
    }

    public void initRule() {
        rule = new ClassLinkRule() {
            @Override
            public Class keyType(Object o) {
                try {
                    ItemWrapper<Statement> itemWrapper = (ItemWrapper<Statement>) o;
                    return Class.forName(itemWrapper.getObject().getBinderClass());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return o.getClass();
            }
        };
        rule.add(DefaultRecyclerViewBinder.class, DefaultRecyclerViewBinder.class);
        rule.add(IfRecyclerViewBinder.class, IfRecyclerViewBinder.class);
        rule.add(ElseRecyclerViewBinder.class, ElseRecyclerViewBinder.class);
        rule.add(EndRecyclerViewBinder.class, EndRecyclerViewBinder.class);
    }


    public void initBinders(Class<? extends RecyclerViewBinder>... binderClasses) {
        recyclerViewBinders = new RecyclerViewBinder[binderClasses.length];
        for (int i = 0; i < binderClasses.length; i++) {
            try {
                recyclerViewBinders[i] = binderClasses[i].getConstructor(PlaygroundFragment.class).newInstance(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class DefaultRecyclerViewBinder extends RecyclerViewBinder<ItemWrapper<Statement>> {
        @Override
        public void bind(ItemWrapper<Statement> itemWrapper) {
            //                textView(R.id.index).setText(String.valueOf(itemWrappers.indexOf(itemWrapper)));
            ViewGroup.MarginLayoutParams marginLayoutParams = ((ViewGroup.MarginLayoutParams) viewHolder.itemView.getLayoutParams());
            marginLayoutParams.leftMargin = marginLayoutParams.leftMargin + getResources().getDimensionPixelOffset(R.dimen.child_left_margin) * itemWrapper.getDepth();
            Function function = itemWrapper.getObject().getFunction();
            Variable variable = itemWrapper.getObject().getRetVaule();
            textView(R.id.fun_name).setText(function.getName());
            final LinearLayout parameterLayout = linearLayout(R.id.fun_params);
            RadioGroup radioGroup = (RadioGroup) view(R.id.radio_group);
            radioGroup.removeAllViews();
            if (variable != null) {
                textView(R.id.fun_return).setText(variable.getName());
            }
            final List<Mode> modes = function.getModes();
            if (modes.size() > 1) {
                initRadioGroup(radioGroup, modes);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        initParams(parameterLayout, modes.get(checkedId).getParameters());
                    }
                });
            } else {
                initParams(parameterLayout, modes.get(0).getParameters());
            }
            radioGroup.check(0);
        }

        @Override
        public int layoutRes() {
            return R.layout.list_item_statement;
        }
    }

    public  class IfRecyclerViewBinder extends RecyclerViewBinder<ItemWrapper<Statement>> {

        @Override
        public void bind(ItemWrapper<Statement> itemWrapper) {

        }

        @Override
        public int layoutRes() {
            return 0;
        }
    }

    public  class ElseRecyclerViewBinder extends RecyclerViewBinder<ItemWrapper<Statement>> {

        @Override
        public void bind(ItemWrapper<Statement> itemWrapper) {

        }

        @Override
        public int layoutRes() {
            return R.layout.list_item_else;
        }
    }

    public  class EndRecyclerViewBinder extends RecyclerViewBinder<ItemWrapper<Statement>> {

        @Override
        public void bind(ItemWrapper<Statement> itemWrapper) {

        }

        @Override
        public int layoutRes() {
            return R.layout.list_item_end;
        }
    }

    private void initRecyclerView() {
        final RecyclerViewAdapter adapter = new RecyclerViewAdapter();
        adapter.register(new TypeToken<ItemWrapper<Statement>>() {
        }.getRawType(), rule, recyclerViewBinders);
        recyclerView.setAdapter(adapter);
        adapter.setItems(itemWrappers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }

    public void initParams(LinearLayout container, List<Parameter> parameters) {
        int num = 0;
        container.removeAllViews();
        for (Parameter parameter : parameters) {
            if (parameter.getType() != null) {
                ParameterAdapter parameterAdapter = ParameterAdapterManager.getInstance(getActivity()).get(parameter);
                if (parameterAdapter == null) {
                    parameterAdapter = ParameterAdapterManager.getInstance(getActivity()).getDefault(parameter);
                }
                View view = parameterAdapter.getView();
                container.addView(view);
                if (num < parameters.size()) {
                    View line = new View(getActivity());
                    line.setBackgroundColor(getActivity().getResources().getColor(R.color.play_frgment_card_background));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.list_divider_height));
                    params.leftMargin = getResources().getDimensionPixelSize(R.dimen.child_left_margin);
                    container.addView(line, params);
                }
            }
        }
    }

    public void initRadioGroup(RadioGroup radioGroup, List<Mode> modes) {
        for (int i = 0; i < modes.size(); i++) {
            RadioButton radioButton = new RadioButton(getActivity());
            radioButton.setButtonDrawable(null);
            radioButton.setBackground(getResources().getDrawable(R.drawable.s_radio_button_bg));
            int padding = 12;
            radioButton.setPadding(padding, padding, padding, padding);
            radioButton.setId(i);
            radioButton.setText(modes.get(i).getName());
            radioGroup.addView(radioButton);
        }
    }

    private void initTopBar() {
        mTopBar.setTitle("工作流");

        mTopBar.addRightImageButton(R.drawable.ic_add_light, R.id.topbar_right_about_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FunctionListFragment functionListFragment = new FunctionListFragment();
                startFragment(functionListFragment);
            }
        });
    }


    public void initRecyclerViewDrag() {
        final RecyclerView.Adapter adapter = recyclerView.getAdapter();
        new ItemTouchHelper(new ItemTouchHelper.Callback() {
            private RecyclerView.ViewHolder vh;

            @Override
            public boolean isItemViewSwipeEnabled() {
                return false;
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
                int srcPosition = viewHolder.getAdapterPosition();
                int position = target.getAdapterPosition();
                moveStatement(srcPosition, position);
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

            }
        }).attachToRecyclerView(recyclerView);
    }


    public void removeStatement(RecyclerView.ViewHolder viewHolder) {
        //todo
        ItemWrapper<Statement> itemWrapper = itemWrappers.remove(viewHolder.getAdapterPosition());
        if (itemWrapper.getDepth() > 0) {
            CodeBlock codeBlock = (CodeBlock) itemWrapper.getParent().getParent().getObject();
            codeBlock.getStatements().remove(itemWrapper.getObject());
        }
        // 刷新列表
        recyclerView.getAdapter().notifyItemRemoved(viewHolder.getAdapterPosition());
    }

    public void moveStatement(int srcPostion, int targetPosition) {
        ItemWrapper<Statement> targetItem = itemWrappers.get(targetPosition);
        ItemWrapper<Statement> srcItem = itemWrappers.get(srcPostion);
        int depth = srcItem.getDepth();
        srcItem.setDepth(targetItem.getDepth());
        srcItem.setParent(targetItem.getParent());
        itemWrappers.add(targetPosition, srcItem);

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

    public void insertStatement(Function function) {
        //todo
        FunctionAdapter adapter = FunctionAdapter.get(function);
        List<Statement> statements = adapter.generateStatement();
        for (Statement statement : statements) {
            statement.setFunctionAdapter(adapter);
            ItemWrapper<Statement> itemWrapper = new ItemWrapper<>();
            itemWrapper.setObject(statement);
            itemWrappers.add(itemWrapper);
        }
        recyclerView.getAdapter().notifyDataSetChanged();
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
        ((RecyclerViewAdapter) recyclerView.getAdapter()).setItems(itemWrappers);
        recyclerView.getAdapter().notifyDataSetChanged();
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
