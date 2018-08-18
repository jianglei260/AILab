package com.sharevar.appstudio.runtime.core;

import com.sharevar.appstudio.object.Statement;

import java.util.List;

public class VM {
    private List<Statement> statements;

    public static VM load(List<Statement> statements) {
        VM vm = new VM();
        vm.statements = statements;
        return vm;
    }

    public Statement nextStep() {
        return null;
    }

    public void start() {

    }
}
