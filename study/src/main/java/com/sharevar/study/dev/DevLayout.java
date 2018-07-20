package com.sharevar.study.dev;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zzhoujay.richtext.RichText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.lang.reflect.Method;

public class DevLayout extends FrameLayout {
    String md="---\n" +
            "type: doc\n" +
            "layout: reference\n" +
            "category: \"Syntax\"\n" +
            "title: \"返回与跳转：break 与 continue\"\n" +
            "---\n" +
            "\n" +
            "# 返回和跳转\n" +
            "\n" +
            "Kotlin 有三种结构化跳转表达式：\n" +
            "\n" +
            "* *return*{: .keyword }。默认从最直接包围它的函数或者[匿名函数](lambdas.html#匿名函数)返回。\n" +
            "* *break*{: .keyword }。终止最直接包围它的循环。\n" +
            "* *continue*{: .keyword }。继续下一次最直接包围它的循环。\n" +
            "\n" +
            "所有这些表达式都可以用作更大表达式的一部分：\n" +
            "\n" +
            "``` kotlin\n" +
            "val s = person.name ?: return\n" +
            "```\n" +
            "\n" +
            "这些表达式的类型是 [Nothing 类型](exceptions.html#nothing-类型)。\n" +
            "\n" +
            "## Break 与 Continue 标签\n" +
            "\n" +
            "在 Kotlin 中任何表达式都可以用标签（*label*{: .keyword }）来标记。\n" +
            "标签的格式为标识符后跟 `@` 符号，例如：`abc@`、`fooBar@`都是有效的标签（参见[语法](grammar.html#labelReference)）。\n" +
            "要为一个表达式加标签，我们只要在其前加标签即可。\n" +
            "\n" +
            "``` kotlin\n" +
            "loop@ for (i in 1..100) {\n" +
            "    // ……\n" +
            "}\n" +
            "```\n" +
            "\n" +
            "现在，我们可以用标签限制 *break*{: .keyword } 或者*continue*{: .keyword }：\n" +
            "\n" +
            "``` kotlin\n" +
            "loop@ for (i in 1..100) {\n" +
            "    for (j in 1..100) {\n" +
            "        if (……) break@loop\n" +
            "    }\n" +
            "}\n" +
            "```\n" +
            "\n" +
            "标签限制的 break 跳转到刚好位于该标签指定的循环后面的执行点。\n" +
            "*continue*{: .keyword } 继续标签指定的循环的下一次迭代。\n" +
            "\n" +
            "\n" +
            "## 标签处返回\n" +
            "\n" +
            "Kotlin 有函数字面量、局部函数和对象表达式。因此 Kotlin 的函数可以被嵌套。\n" +
            "标签限制的 *return*{: .keyword } 允许我们从外层函数返回。\n" +
            "最重要的一个用途就是从 lambda 表达式中返回。回想一下我们这么写的时候：\n" +
            "\n" +
            "<div class=\"sample\" markdown=\"1\" data-min-compiler-version=\"1.2\">\n" +
            "\n" +
            "``` kotlin\n" +
            "//sampleStart\n" +
            "fun foo() {\n" +
            "    listOf(1, 2, 3, 4, 5).forEach {\n" +
            "        if (it == 3) return // 非局部直接返回到 foo() 的调用者\n" +
            "        print(it)\n" +
            "    }\n" +
            "    println(\"this point is unreachable\")\n" +
            "}\n" +
            "//sampleEnd\n" +
            "\n" +
            "fun main(args: Array<String>) {\n" +
            "    foo()\n" +
            "}\n" +
            "```\n" +
            "</div>\n" +
            "\n" +
            "这个 *return*{: .keyword } 表达式从最直接包围它的函数即 `foo` 中返回。\n" +
            "（注意，这种非局部的返回只支持传给[内联函数](inline-functions.html)的 lambda 表达式。）\n" +
            "如果我们需要从 lambda 表达式中返回，我们必须给它加标签并用以限制 *return*{: .keyword }。\n" +
            "\n" +
            "<div class=\"sample\" markdown=\"1\" data-min-compiler-version=\"1.2\">\n" +
            "\n" +
            "``` kotlin\n" +
            "//sampleStart\n" +
            "fun foo() {\n" +
            "    listOf(1, 2, 3, 4, 5).forEach lit@{\n" +
            "        if (it == 3) return@lit // 局部返回到该 lambda 表达式的调用者，即 forEach 循环\n" +
            "        print(it)\n" +
            "    }\n" +
            "    print(\" done with explicit label\")\n" +
            "}\n" +
            "//sampleEnd\n" +
            "\n" +
            "fun main(args: Array<String>) {\n" +
            "    foo()\n" +
            "}\n" +
            "```\n" +
            "</div>\n" +
            "\n" +
            "现在，它只会从 lambda 表达式中返回。通常情况下使用隐式标签更方便。\n" +
            "该标签与接受该 lambda 的函数同名。\n" +
            "\n" +
            "<div class=\"sample\" markdown=\"1\" data-min-compiler-version=\"1.2\">\n" +
            "\n" +
            "``` kotlin\n" +
            "//sampleStart\n" +
            "fun foo() {\n" +
            "    listOf(1, 2, 3, 4, 5).forEach {\n" +
            "        if (it == 3) return@forEach // 局部返回到该 lambda 表达式的调用者，即 forEach 循环\n" +
            "        print(it)\n" +
            "    }\n" +
            "    print(\" done with implicit label\")\n" +
            "}\n" +
            "//sampleEnd\n" +
            "\n" +
            "fun main(args: Array<String>) {\n" +
            "    foo()\n" +
            "}\n" +
            "```\n" +
            "</div>\n" +
            "\n" +
            "或者，我们用一个[匿名函数](lambdas.html#匿名函数)替代 lambda 表达式。\n" +
            "匿名函数内部的 *return*{: .keyword } 语句将从该匿名函数自身返回\n" +
            "\n" +
            "<div class=\"sample\" markdown=\"1\" data-min-compiler-version=\"1.2\">\n" +
            "\n" +
            "``` kotlin\n" +
            "//sampleStart\n" +
            "fun foo() {\n" +
            "    listOf(1, 2, 3, 4, 5).forEach(fun(value: Int) {\n" +
            "        if (value == 3) return  // 局部返回到匿名函数的调用者，即 forEach 循环\n" +
            "        print(value)\n" +
            "    })\n" +
            "    print(\" done with anonymous function\")\n" +
            "}\n" +
            "//sampleEnd\n" +
            "\n" +
            "fun main(args: Array<String>) {\n" +
            "    foo()\n" +
            "}\n" +
            "```\n" +
            "</div>\n" +
            "\n" +
            "请注意，前文三个示例中使用的局部返回类似于在常规循环中使用 *continue*{: .keyword }。并没有 *break*{: .keyword } 的直接等价形式，不过可以通过增加另一层嵌套 lambda 表达式并从其中非局部返回来模拟：\n" +
            "\n" +
            "<div class=\"sample\" markdown=\"1\" data-min-compiler-version=\"1.2\">\n" +
            "\n" +
            "``` kotlin\n" +
            "//sampleStart\n" +
            "fun foo() {\n" +
            "    run loop@{\n" +
            "        listOf(1, 2, 3, 4, 5).forEach {\n" +
            "            if (it == 3) return@loop // 从传入 run 的 lambda 表达式非局部返回\n" +
            "            print(it)\n" +
            "        }\n" +
            "    }\n" +
            "    print(\" done with nested loop\")\n" +
            "}\n" +
            "//sampleEnd\n" +
            "\n" +
            "fun main(args: Array<String>) {\n" +
            "    foo()\n" +
            "}\n" +
            "```\n" +
            "</div>\n" +
            "\n" +
            "当要返一个回值的时候，解析器优先选用标签限制的 return，即\n" +
            "\n" +
            "``` kotlin\n" +
            "return@a 1\n" +
            "```\n" +
            "\n" +
            "意为“从标签 `@a` 返回 1”，而不是“返回一个标签标注的表达式 `(@a 1)`”。\n";
    public DevLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public DevLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public DevLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public DevLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        String path = "/Users/jianglei/kotlin-web-site-cn/pages/docs/reference/basic-types.md";
        try {
//            FileInputStream inputStream = new FileInputStream(path);
//            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//            BufferedReader reader = new BufferedReader(inputStreamReader);
//            StringBuilder txtBuilder=new StringBuilder();
//            String line="";
//            while ((line=reader.readLine())!=null){
//                txtBuilder.append(line+"\n");
//            }
            TextView textView = new TextView(getContext());


            addView(textView);
            RichText.fromMarkdown(md).into(textView);
            Class proxy = Class.forName("com.sharevar.study.dev.Proxy");
            Method mainMethod = proxy.getMethod("main", String[].class);
           mainMethod.invoke(null,null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
