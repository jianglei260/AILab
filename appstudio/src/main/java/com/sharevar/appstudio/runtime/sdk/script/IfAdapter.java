package com.sharevar.appstudio.runtime.sdk.script;

import com.sharevar.appstudio.runtime.core.statement.EndStatement;
import com.sharevar.appstudio.runtime.core.statement.Statement;
import com.sharevar.appstudio.runtime.core.function.CodeBlock;
import com.sharevar.appstudio.runtime.core.function.Function;
import com.sharevar.appstudio.runtime.core.function.Mode;
import com.sharevar.appstudio.runtime.core.function.Parameter;
import com.sharevar.appstudio.runtime.core.function.Void;
import com.sharevar.appstudio.runtime.sdk.FunctionAdapter;
import com.sharevar.appstudio.ui.object.PlaygroundFragment;

import java.util.Collection;
import java.util.List;

public class IfAdapter extends FunctionAdapter {
    public IfAdapter(Function function) {
        super(function);
    }

    @Override
    public Object invoke() {

        Mode mode = function.getModes().get(function.getModeIndex());
        boolean result = false;
        switch (mode.getName()) {
            case "greatThan":
                result = greatThan(mode.getParameters().get(0), mode.getParameters().get(1));
                break;
            case "equal":
                result = equal(mode.getParameters().get(0), mode.getParameters().get(1));
                break;
            case "smallThan":
                result = smallThan(mode.getParameters().get(0), mode.getParameters().get(1));
                break;
            case "notNull":
                result = notNull(mode.getParameters().get(0));
                break;
            case "notEmpty":
                result = notEmpty(mode.getParameters().get(0));
                break;
        }
        if (result) {
            return getContext().getIfCodeBlock(function).invoke();
        } else {
            return getContext().getElseCodeBlock(function).invoke();
        }
    }

    @Override
    public List<Statement> generateStatement() {
        List<Statement> statements=super.generateStatement();
        Statement elseStatement=new Statement();
        elseStatement.setBinderClass(PlaygroundFragment.ElseRecyclerViewBinder.class.getName());
        EndStatement statement=new EndStatement();
        statement.setBinderClass(PlaygroundFragment.EndRecyclerViewBinder.class.getName());
        statements.add(elseStatement);
        statements.add(statement);
        return statements;
    }

    public boolean greatThan(Parameter parameter1, Parameter parameter2) {
        Object o1 = parameter1.getValue();
        Object o2 = parameter2.getValue();
        if (o1 instanceof Comparable && o2 instanceof Comparable) {
            int result = ((Comparable) o1).compareTo(o2);
            if (result > 0)
                return true;
            else
                return false;
        }
        return false;
    }

    public boolean equal(Parameter parameter1, Parameter parameter2) {
        Object o1 = parameter1.getValue();
        Object o2 = parameter2.getValue();
        if (o1 instanceof Comparable && o2 instanceof Comparable) {
            int result = ((Comparable) o1).compareTo(o2);
            if (result == 0)
                return true;
            else
                return false;
        }
        return false;
    }

    public boolean notNull(Parameter parameter) {
        return parameter.getValue() != null;
    }

    public boolean notEmpty(Parameter parameter) {
        Object value = parameter.getValue();
        if (value instanceof CharSequence) {
            return ((CharSequence) value).length() > 0;
        } else if (value instanceof Collection) {
            return !((Collection) value).isEmpty();
        }
        return false;
    }

    public boolean smallThan(Parameter parameter1, Parameter parameter2) {
        Object o1 = parameter1.getValue();
        Object o2 = parameter2.getValue();
        if (o1 instanceof Comparable && o2 instanceof Comparable) {
            int result = ((Comparable) o1).compareTo(o2);
            if (result < 0)
                return true;
            else
                return false;
        }
        return false;
    }
}
