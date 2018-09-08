package com.sharevar.appstudio.runtime.sdk;

import com.sharevar.appstudio.runtime.core.statement.Statement;
import com.sharevar.appstudio.runtime.core.function.Function;
import com.sharevar.appstudio.runtime.core.RuntimeContext;
import com.sharevar.appstudio.ui.object.PlaygroundFragment;

import java.util.ArrayList;
import java.util.List;

public abstract class FunctionAdapter {
    protected Function function;
    protected List<Statement> statements;

    public FunctionAdapter(Function function) {
        this.function = function;
    }

    public Function getFunction() {
        return function;
    }


    public RuntimeContext getContext() {
        return function.getRuntimeContext();
    }

    public static FunctionAdapter get(Function function) {
        String adapterClass = function.getAdapter();
        try {
            FunctionAdapter adapter = (FunctionAdapter) Class.forName(adapterClass).getConstructor(Function.class).newInstance(function);
            return adapter;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DefaultFunctionAdapter(function);
    }

    public abstract Object invoke();

    public List<Statement> generateStatement() {
        List<Statement> statements=new ArrayList<>();
        this.statements=statements;
        Statement statement=new Statement();
        statement.setFunction(function);
        statement.setBinderClass(PlaygroundFragment.DefaultRecyclerViewBinder.class.getName());
        statements.add(statement);
        return statements;
    }
}
