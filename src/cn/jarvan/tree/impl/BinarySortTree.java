package cn.jarvan.tree.impl;

import cn.jarvan.tree.Tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <b><code>BinarySortTree</code></b>
 * <p>
 * 二分查找树(二叉排序树).
 * 定义：
 * 它或者是一棵空树；或者是具有下列性质的二叉树：
 * （1）若左子树不空，则左子树上所有结点的值均小于它的根结点的值；
 * （2）若右子树不空，则右子树上所有结点的值均大于它的根结点的值；
 * （3）左、右子树也分别为二分查找树；
 * <p>
 * <b>Creation Time:</b> 2018/11/19 18:55.
 *
 * @author liuruojing
 * @since DesignPatterns 0.1.0
 */
public class BinarySortTree<K extends Comparable, V> implements Tree<K, V> {

    Node<K, V> root;

    @Override
    public V append(K key, V value) {
        return append(root, key, value);

    }

    private V append(Node<K, V> tree, K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("no null key,please");
        }
        //如果是空树,第一次调用初始化时会出现该情况
        if (tree == null) {
            this.root = new Node<K, V>();
            this.root.setKey(key);
            this.root.setValue(value);
            return null;
        } else {
            K oldKey = tree.getKey();
            //如果key相对 替换掉value，并将旧值返回
            if (oldKey.equals(key)) {
                V oldValue = tree.getValue();
                tree.setValue(value);
                return oldValue;
            } else {
                //如果key小于根节点
                if (oldKey.compareTo(key) > 0) {
                    //直接挂到左孩子节点
                    if (tree.getLchild() == null) {
                        Node<K, V> child = new Node<K, V>();
                        child.setKey(key);
                        child.setValue(value);
                        tree.setLchild(child);
                        return null;
                    } else {  //如果不为空，进行递归
                        return append(tree.getLchild(), key, value);
                    }
                } else { //如果key大于根节点
                    if (tree.getRchild() == null) {
                        Node<K, V> child = new Node<K, V>();
                        child.setKey(key);
                        child.setValue(value);
                        tree.setRchild(child);
                        return null;
                    } else {  //如果不为空，进行递归
                        return append(tree.getRchild(), key, value);
                    }

                }
            }

        }
    }

    @Override
    public V search(K key) {
        if (key == null) {
            throw new IllegalArgumentException("not null,please");
        } else {
            return search(root, key);
        }
    }

    private V search(Node<K, V> tree, K key) {
        if (tree == null) {
            return null;
        } else {
            //如果相等 返回value
            if (tree.getKey().equals(key)) {
                return tree.getValue();
            } else {
                //如果比key大，则遍历左子树
                if (tree.getKey().compareTo(key) > 0) {
                    return search(tree.getLchild(), key);
                } else {   //否则遍历右子树
                    return search(tree.getRchild(), key);
                }
            }

        }

    }

    @Override
    public V delete(K key) {
        return deleteFromRoot(root, key);
    }

    private V deleteFromRoot(Node<K, V> tree, K key) {
        //是空树
        if (tree == null) {
            return null;
        } else {
            //如果根节点匹配上
            if (tree.getKey().equals(key)) {
                //如果是叶子节点 直接删除
                if (tree.getLchild() == null && tree.getLchild() == null) {
                   root = null;
                }else {
                    //仅有左子树的情况
                    if(tree.getRchild() == null){
                        root = tree.getLchild();
                    }else{
                        //仅有右子树的情况
                        if(tree.getLchild() == null){
                            root = tree.getRchild();
                        }else{ //左右子树都存在 那么找他的前继后者后继，这里找前继
                          return null;
                        }
                    }
                }
            } else {  //根节点没有匹配上则开始遍历子树
                return null;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return showTree(root);

    }

    /**
     * 广度优先遍历.
     *
     * @param tree
     * @return 遍历后的字符串
     * @author liuruojing
     * @since ${PROJECT_NAME} 0.1.0
     */
    private String showTree(Node<K, V> tree) {

        if (tree == null) {
            return null;
        } else {
            StringBuilder str = new StringBuilder();
            Queue<Node<K, V>> queue = new LinkedList<>();
            queue.add(tree);
            while (!queue.isEmpty()) {
                Node<K, V> currentNode = queue.poll();
                if (currentNode != null) {
                    str.append(currentNode.getValue().toString()).append(" ");
                    queue.add(currentNode.getLchild());
                    queue.add(currentNode.getRchild());
                }

            }
            return str.toString();
        }

    }

    private static class Node<K, V> implements Tree.Node<K, V> {
        private K key;
        private V value;
        private Node<K, V> lchild = null;
        private Node<K, V> rchild = null;

        @Override
        public void setKey(K key) {
            this.key = key;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public void setValue(V value) {
            this.value = value;
        }

        @Override
        public V getValue() {
            return value;
        }

        public Node<K, V> getLchild() {
            return lchild;
        }

        public void setLchild(Node<K, V> lchild) {
            this.lchild = lchild;
        }

        public Node<K, V> getRchild() {
            return rchild;
        }

        public void setRchild(Node<K, V> rchild) {
            this.rchild = rchild;
        }
    }

}