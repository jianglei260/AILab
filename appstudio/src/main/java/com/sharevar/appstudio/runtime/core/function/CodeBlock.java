package com.sharevar.appstudio.runtime.core.function;

import com.sharevar.appstudio.common.ds.CollectionOP;
import com.sharevar.appstudio.runtime.core.statement.Statement;
import com.sharevar.appstudio.runtime.core.var.Variable;
import com.sharevar.appstudio.runtime.core.function.Function;
import com.sharevar.appstudio.runtime.core.function.Parameter;

import java.util.ArrayList;
import java.util.List;

public abstract class CodeBlock extends Statement {
    protected List<Statement> statements = new ArrayList<>();
    protected List<Variable> variables = new ArrayList<>();

    public List<Statement> getStatements() {
        return statements;
    }

    public void setStatements(List<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public Object invoke() {
        Object value = null;
        for (Statement statement : statements) {
            Function function = statement.getFunction();
            for (Parameter parameter : function.getParameters()) {
                Variable variable = CollectionOP.findByAttr(variables, "name", parameter.getName());
                if (variable != null && variable.getType().equals(parameter.getType())) {
                    parameter.setValue(variable.getValue());
                }
            }
            value = function.invoke();
            statement.getRetVaule().setValue(value);
            variables.add(statement.getRetVaule());
        }
        return value;
    }
}
