package com.sharevar.appstudio.ui.object;

import android.support.v7.widget.RecyclerView;

import com.sharevar.appstudio.runtime.core.statement.Statement;
import com.sharevar.appstudio.ui.common.RecyclerViewAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerViewDataBinder {
    private Map<? extends Statement, ItemWrapper> statementMap = new HashMap<>();
    private List<Statement> statements;
    private List<ItemWrapper<Statement>> itemWrappers;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;

    public RecyclerViewDataBinder(RecyclerView recyclerView, RecyclerViewAdapter adapter, List<Statement> statements, List<ItemWrapper<Statement>> itemWrappers) {
        this.statements = statements;
        this.itemWrappers = itemWrappers;
        this.recyclerView = recyclerView;
        this.adapter = adapter;
    }

    public void close(Statement statement) {
        ItemWrapper wrapper = statementMap.get(statement);
        wrapper.setClosed(true);
    }

    public void open(Statement statement) {
        ItemWrapper wrapper = statementMap.get(statement);
        wrapper.setClosed(false);
    }

    public void onItemSelected(ItemWrapper<Statement> itemWrapper) {

    }

    public void removeItem(ItemWrapper<Statement> itemWrapper) {

    }

    public void insert(Statement statement) {
        ItemWrapper<Statement> itemWrapper = new ItemWrapper<>();
        itemWrapper.setObject(statement);
        itemWrappers.add(itemWrapper);
        statements.add(statement);
    }


}
