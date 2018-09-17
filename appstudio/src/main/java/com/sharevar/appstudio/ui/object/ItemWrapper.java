package com.sharevar.appstudio.ui.object;

public class ItemWrapper<T> {
    private T object;
    private int index;
    private int depth;
    private ItemWrapper<T> parent;
    private boolean closed;

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

//    public int getIndex() {
//        return index;
//    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public ItemWrapper<T> getParent() {
        return parent;
    }

    public void setParent(ItemWrapper<T> parent) {
        this.parent = parent;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }
}
