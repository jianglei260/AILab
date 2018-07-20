package com.example.appstudio.stand.type;

import android.util.Log;

import com.example.appstudio.stand.type.graph.DirectedGraph;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Stack;

public class TypeInference {
    private static final String TAG = "TypeInference";
    private static TypeInference inference;
    private DirectedGraph<Type> graph;

    public static TypeInference get() {
        if (inference == null)
            inference = new TypeInference();
        return inference;
    }

    public TypeInference() {
        graph = new DirectedGraph<>();
        Method[] methods = ConvertFactory.class.getMethods();
        for (Method method : methods) {
            if (method.getParameterTypes().length <= 0)
                continue;
            if (!method.getName().startsWith("to"))
                continue;
            Type src = Type.of(method.getParameterTypes()[0]);
            Type target = Type.of(method.getReturnType());
            graph.addVertex(src);
            graph.addVertex(target);

        }
        for (Method method : methods) {
            if (method.getParameterTypes().length <= 0)
                continue;
            if (!method.getName().startsWith("to"))
                continue;
            Type src = Type.of(method.getParameterTypes()[0]);
            Type target = Type.of(method.getReturnType());
            boolean result = graph.addEdge(src, target);
            Log.d(TAG, "add edge:" + result + " " + method.getName() + ": " + src.getType() + " " + target.getType());
        }

    }

    public boolean convertable(Type src, Type target) {
        if (src.equals(target))
            return true;
        Stack<Type> path = new Stack<>();
        return graph.getShortestPath(src, target, path) > 0;
    }

    public Object convertTo(Object src, Type targetType) {
        Type srcType = Type.of(src);
        if (srcType.equals(targetType))
            return src;
        Stack<Type> path = new Stack<>();
        int len = graph.getShortestPath(srcType, targetType, path);
        Log.d(TAG, "len=" + len);
        if (len <= 0) {
            Log.e(TAG, "len=" + len + "not found coverter for type:" + srcType.getType() + "----" + targetType.getType());
            return src;
        }
        while (!path.empty()) {
            Type current = path.pop();
            Object result = convert(src, current);
            src = result;
        }
        return src;
    }

    private Object convert(Object src, Type outType) {
        Type srcType = Type.of(src);
        if (srcType.equals(outType))
            return src;
        Method[] methods = ConvertFactory.class.getMethods();
        try {
            for (Method method : methods) {
                if (method.getParameterTypes().length <= 0)
                    continue;
                if (!method.getName().startsWith("to"))
                    continue;
                Type methodOutputType = Type.of(method.getReturnType());
                Type methodInputType = Type.of(method.getParameterTypes()[0]);
                if (method.getName().startsWith("to") && srcType.equals(methodInputType) && outType.equals(methodOutputType)) {
                    return method.invoke(null, src);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return src;
    }

}
