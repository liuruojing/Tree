package cn.jarvan;

import cn.jarvan.tree.impl.BinarySortTree;

public class Main {

    public static void main(String[] args) {
        BinarySortTree tree = new BinarySortTree();
        tree.append(14, "I");
        tree.append(17, "o");
        tree.append(8, "l");
        tree.append(9, "e");
        tree.append(4, "v");
        tree.append(18, "o");
        tree.append(15, "y");
        tree.append(1, "u");
        System.out.println(tree.toString());
        System.out.print(tree.search(9));
    }
}
