package com.sharevar.appstudio.runtime.sdk.script;

import com.sharevar.appstudio.object.function.CodeBlock;
import com.sharevar.appstudio.object.function.Function;
import com.sharevar.appstudio.object.function.Mode;
import com.sharevar.appstudio.object.function.Parameter;
import com.sharevar.appstudio.object.function.Void;
import com.sharevar.appstudio.runtime.sdk.FunctionAdapter;

import java.util.Collection;
import java.util.Comparator;

public class IfAdapter extends FunctionAdapter {
    public IfAdapter(Function function) {
        super(function);
    }

    @Override
    public Object invoke() {
        CodeBlock codeBlock = function.getRuntimeContext().getCodeBlock();
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
            return codeBlock.invoke();
        } else {
            return null;
        }
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
