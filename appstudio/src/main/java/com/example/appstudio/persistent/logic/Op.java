package com.example.appstudio.persistent.logic;

import com.example.appstudio.data.Attr;

public enum Op {
    QUERY("QUERY"), INSERT("INSERT"), UPDATE("UPDATE"), DELETE("DELETE"),
    WHERE("WHERE"), AND("AND"), OR("OR"),
    EQUALTO("="), NOT_EQUALTO("><"), GREATER(">"), LESS("<"), GREATER_EQUAL(">="), LESS_EQUAL("<"),
    LIMIT("LIMIT"), SKIP("OFFSET"),
    ORDER_BY("ORDER BY");

    Op(String op) {
        this.op = op;
    }

    public String op;
    private Attr attr;

    public Attr getAttr() {
        return attr;
    }

    public void setAttr(Attr attr) {
        this.attr = attr;
    }
}
