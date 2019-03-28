package map;

/**
 * 手写实现map
 */

import java.util.Map;

/**
 * Map 在jdk1.7版本之前 采用数组+链表的方式实现
 * map被实例化是 会初始化一个数组
 * 在put时 通过k的hasCode 散列后 存储到数组中
 * 如果 hasCode冲突 会采用 链表的形式存储
 *
 *
 *   * 处理null ##
 *   * 扩容 ##
 *   * 死锁场景 www.importnew.com/22011.html
 *
 * @param <K>
 * @param <V>
 */
public class MyMap<K,V>  {

    private static final Integer DEFATUL_LENGTH = 1;
    private Integer size;
    private Entity[] table ;

    MyMap(){
        this.table = new Entity[DEFATUL_LENGTH]; //默认8个长度
        size = 0;
    }

    public Object get(K key){

        if (key == null){
            if (table[0] == null){
                return  null;
            }else{
                for (Entity entity = table[0];entity != null; entity = entity.getNext()){
                    if (entity.getKey()==null){
                        return entity.getValue();
                    }
                }
            }

        }
        int index = getHasCode(key);
        for (Entity entity = table[index];entity!=null;entity.getNext()){
            if (entity.getKey().equals(key)){
                return entity.getValue();
            }
        }
        return null;
    }

    /**
     *
     * @param key
     * @param value
     * @return
     */
    public Object put(K key,V value){

        if (key==null){
            addEntiry(key, value, 0);
            if (table[0].getNext() != null){
                for (Entity entity = table[0];entity != null; entity = entity.getNext()){
                    if (key==null){
                        return entity.getValue();
                    }
                }
            }else{
                return null;
            }
        }

        int index = getHasCode(key);
        for(Entity<K,V> entity = table[index]; entity!=null ; entity = entity.getNext()){
            if (key.equals(entity.getKey())){
                Object oldValue = entity.getValue();
                entity.setValue(value);
                return oldValue;
            }
        }
        addEntiry(key, value, index);
        return null;
    }

    private int getHasCode(K key) {
        int hasCode = key.hashCode() & Integer.MAX_VALUE; // hasCode 可能出现负数
        return hasCode % table.length ;
    }

    private void addEntiry(K key, V value, int index) {
        table[index] = new Entity(key,value,table[index]);
        size++;
    }

    class Entity<K,V>{
        private K key;
        private V value;
        private Entity<K,V> next;

        public Entity(K key, V value, Entity<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public Entity<K, V> getNext() {
            return next;
        }

        public void setNext(Entity<K, V> next) {
            this.next = next;
        }
    }


    public static void main(String args[]){
        MyMap myMap = new MyMap();
        myMap.put(null,"我是null");
        myMap.put(1,"我是1");
        System.out.println(myMap.get(null));
        /*System.out.println(myMap.put("myKey","oldValue")); // null
        System.out.println(myMap.get("myKey")); // oldValue
        System.out.println(myMap.put("myKey","mayValue"));//oldValue
        System.out.println(myMap.get("myKey"));//mayValue
        for (int i=0;i<10;i++){
            myMap.put("forKey"+i,"forValue"+i);
        }
        System.out.println(myMap);*/

    }
}
