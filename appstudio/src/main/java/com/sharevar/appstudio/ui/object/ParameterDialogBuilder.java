package com.sharevar.appstudio.ui.object;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import com.sharevar.appstudio.R;
import com.sharevar.appstudio.context.ProjectContext;
import com.sharevar.appstudio.runtime.core.RuntimeContext;
import com.sharevar.appstudio.runtime.core.function.Option;
import com.sharevar.appstudio.runtime.core.function.Parameter;
import com.sharevar.appstudio.runtime.core.var.Variable;

import java.util.List;

public class ParameterDialogBuilder extends QMUIDialog.AutoResizeDialogBuilder {
    private Context mContext;
    private EditText mEditText;
    private Parameter parameter;
    private RuntimeContext runtimeContext;

    public ParameterDialogBuilder(Context context) {
        super(context);
        this.mContext = context;
    }

    public ParameterDialogBuilder runtimeContext(RuntimeContext runtimeContext) {
        this.runtimeContext = runtimeContext;
        return this;
    }

    public ParameterDialogBuilder parameter(Parameter parameter) {
        this.parameter = parameter;
        return this;
    }

    @Override
    public View onBuildContent(QMUIDialog dialog, ScrollView parent) {
        QMUIGroupListView layout = new QMUIGroupListView(mContext);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        int padding = QMUIDisplayHelper.dp2px(mContext, 20);
        layout.setPadding(padding, padding, padding, padding);
        mEditText = new EditText(mContext);
        QMUIViewHelper.setBackgroundKeepingPadding(mEditText, QMUIResHelper.getAttrDrawable(mContext, R.attr.qmui_list_item_bg_with_border_bottom));
        mEditText.setHint("输入框");
        LinearLayout.LayoutParams editTextLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, QMUIDisplayHelper.dpToPx(50));
        editTextLP.bottomMargin = QMUIDisplayHelper.dp2px(mContext, 15);
        mEditText.setLayoutParams(editTextLP);
        layout.addView(mEditText);
        buildFromOptions(layout);
        buildFromVars(layout);
        return layout;
    }

    public void buildFromOptions(QMUIGroupListView parent) {
        QMUIGroupListView.Section section = QMUIGroupListView.newSection(mContext).setTitle("可选项");
        List<Option> options = parameter.getOptions();
        for (Option option : options) {
//            TextView textView = new TextView(mContext);
//            textView.setLineSpacing(QMUIDisplayHelper.dp2px(mContext, 4), 1.0f);
//            textView.setText(option.getName());
//            textView.setTextColor(ContextCompat.getColor(mContext, R.color.app_color_description));
            QMUICommonListItemView normalItem = parent.createItemView(option.getName());
            normalItem.setOrientation(QMUICommonListItemView.VERTICAL);
            section.addItemView(normalItem, new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        section.addTo(parent);
    }

    public void buildFromVars(QMUIGroupListView parent) {
        List<Variable> variables = runtimeContext.getAvailbleVars(parameter);
        QMUIGroupListView.Section section = QMUIGroupListView.newSection(mContext).setTitle("可选项");
        if (variables == null)
            return;
        for (Variable variable : variables) {
//            TextView textView = new TextView(mContext);
//            textView.setLineSpacing(QMUIDisplayHelper.dp2px(mContext, 4), 1.0f);
//            textView.setText(variable.getName());
//            textView.setTextColor(ContextCompat.getColor(mContext, R.color.app_color_description));
//            textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            QMUICommonListItemView normalItem = parent.createItemView(variable.getName());
            normalItem.setOrientation(QMUICommonListItemView.VERTICAL);
            section.addItemView(normalItem, new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        section.addTo(parent);
    }
}
