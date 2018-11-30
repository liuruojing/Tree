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
       //测试删除的各种情形(懒得引jar包就不用junit了 见谅)
//1、非根节点
        //测试删除节点仅有左子树
        tree.delete(4);
        System.out.println(tree.toString());
        //测试删除节点为叶子节点
        tree.delete(1);
        System.out.println(tree.toString());
        //测试删除节点仅有右子树
        tree.delete(8);
        System.out.println(tree.toString());
        tree.delete(17);
        System.out.println(tree.toString());
        //测试删除节点既有左子树，又有右子树

    }
}
