package cn.jarvan.tree;

/**
 * <b><code>Tree</code></b>
 * <p>
 * Description.
 * <p>
 * <b>Creation Time:</b> 2018/11/19 19:08.
 *
 * @author liuruojing
 * @since DesignPatterns 0.1.0
 */
public interface Tree<K extends Comparable, V> {
    /**
     * 添加元素.
     * 如果覆盖了原值则返回old value 若没有则返回null
     * @param
     * @return
     * @author liuruojing
     * @since ${PROJECT_NAME} 0.1.0
     */
    V append(K key, V value);

    /**
     * 搜索元素.
     *
     * @param
     * @return
     * @author liuruojing
     * @since ${PROJECT_NAME} 0.1.0
     */
    V search(K key);

    /**
     * 删除元素,如果没有匹配到元素 则返回null 否则返回删除的oldValue.
     *
     * @param
     * @return
     * @author liuruojing
     * @since ${PROJECT_NAME} 0.1.0
     */
    V delete(K key);

    interface Node<K, V> {
        /**
         * set key.
         *
         * @param key the key
         * @author liuruojing
         * @since ${PROJECT_NAME} 0.1.0
         */
        void setKey(K key);

        /**
         * get key.
         *
         * @return
         * @author liuruojing
         * @since ${PROJECT_NAME} 0.1.0
         */
        K getKey();

        /**
         * set value.
         *
         * @param value value
         * @author liuruojing
         * @since ${PROJECT_NAME} 0.1.0
         */
        void setValue(V value);

        /**
         * get value.
         *
         * @return V
         * @author liuruojing
         * @since ${PROJECT_NAME} 0.1.0
         */
        V getValue();

    }


}
