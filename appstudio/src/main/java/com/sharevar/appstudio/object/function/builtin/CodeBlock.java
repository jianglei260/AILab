package com.sharevar.appstudio.object.function.builtin;

import com.sharevar.appstudio.common.ds.CollectionOP;
import com.sharevar.appstudio.object.Statement;
import com.sharevar.appstudio.object.Variable;
import com.sharevar.appstudio.object.function.Function;
import com.sharevar.appstudio.object.function.Parameter;

import java.util.ArrayList;
import java.util.List;

public abstract class CodeBlock extends Function {
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
