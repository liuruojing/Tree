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
        return delete(root, true, key);
    }

    private V delete(Node<K, V> tree, Boolean isRoot, K key) {
        //是空树
        if (tree == null) {
            return null;
        } else {
            //如果匹配上
            if (tree.getKey().equals(key)) {
                //仅有左子树的情况或者右子树的情况或者是叶子节点的情况
                if (tree.getRchild() == null || tree.getLchild() == null) {
                    Node<K, V> childNode = tree.getRchild() == null ? tree.getLchild() : tree.getRchild();
                    //如果是根节点
                    if (isRoot) {
                        root = childNode;
                    } else { //如果不是根节点，找出他的父节点，并替换掉父节点的相应孩子子节点
                        ResetNode(tree, childNode);
                    }
                } else {
                    //左右子树都存在 那么找他的前继或者后继，这里找前继，将前继移至当前节点，并删除前继节点
                    Node<K, V> predecessorNode = findPredecessorNode(tree);
                    //删除前继节点
                    ResetNode(predecessorNode, null);
                    //如果是根节点
                    if (isRoot) {
                        predecessorNode.setLchild(root.getLchild());
                        predecessorNode.setRchild(root.getRchild());
                        root = predecessorNode;
                    } else {
                        predecessorNode.setLchild(tree.getLchild());
                        predecessorNode.setRchild(tree.getRchild());
                        ResetNode(tree, predecessorNode);
                    }

                }
                return tree.getValue();
            } else {
                //节点没有匹配上则开始遍历子树
                if (tree.getKey().compareTo(key) > 0) {
                    //遍历左子树
                    return delete(tree.getLchild(), false, key);
                } else {
                    //遍历右子树
                    return delete(tree.getRchild(), false, key);
                }
            }

        }
    }

    /**
     * replaceNode
     *
     * @param node        the node witch to replaced
     * @param replaceNode new node
     * @author liuruojing
     * @since ${PROJECT_NAME} 0.1.0
     */
    private void ResetNode(Node<K, V> node, Node<K, V> replaceNode) {
        Node<K, V> parentNode = findParentNode(root, node);
        if (parentNode.getLchild() == node) {
            parentNode.setLchild(replaceNode);
        } else {
            parentNode.setRchild(replaceNode);
        }
    }

    /**
     * 寻找某个节点的前继.
     *
     * @param
     * @return
     * @author liuruojing
     * @since ${PROJECT_NAME} 0.1.0
     */
    public Node<K, V> findPredecessorNode(Node<K, V> node) {
        if (node.getLchild() == null) {
            return node;
        } else {
            return findPredecessorNode(node.getLchild());
        }
    }

    /**
     * 找到某个节点的父节点.
     *
     * @param parent root
     * @param node   node
     * @return the parent of node
     * @author liuruojing
     * @since ${PROJECT_NAME} 0.1.0
     */
    private Node<K, V> findParentNode(Node<K, V> parent, Node<K, V> node) {
        if (parent == null || node == null) {
            throw new IllegalArgumentException("illege argument");
        } else {
            if (parent.getLchild() == node || parent.getRchild() == node) {
                return parent;
            } else {
                //如果只有右树
                if (parent.getLchild() == null) {
                    return findParentNode(parent.getRchild(), node);
                } else {
                    //如果只有左树
                    if (parent.getRchild() == null) {
                        return findParentNode(parent.getLchild(), node);
                    } else {
                        //如果两边都有子树，看看遍历哪边
                        return parent.getKey().compareTo(node.getKey()) > 0 ? findParentNode(parent.getLchild(), node) : findParentNode(parent.getRchild(), node);
                    }

                }


            }
        }


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
            StringBuilder keyStr = new StringBuilder();
            StringBuilder valueStr = new StringBuilder();
            Queue<Node<K, V>> queue = new LinkedList<>();
            queue.add(tree);
            while (!queue.isEmpty()) {
                Node<K, V> currentNode = queue.poll();
                if (currentNode != null) {
                    keyStr.append(currentNode.getKey().toString()).append(" ");
                    valueStr.append(currentNode.getValue().toString()).append(" ");
                    queue.add(currentNode.getLchild());
                    queue.add(currentNode.getRchild());
                }

            }
            return "key:["+keyStr.toString()+"]   value:["+valueStr.toString()+"]";
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