package node;

import org.junit.jupiter.api.Test;

/**
 * @author dong
 * @create 2021-05-10 20:31
 */
public class NodeList {
    public static void main(String[] args) {
        var h1 = new Node(1);
        var h2 = new Node(2);
        var h3 = new Node(3);
        var h4 = new Node(4);

        var node = new MyNode();
        node.addAtTail(1);
        node.addAtTail(2);
        node.addAtTail(3);
        node.addAtTail(4);

        node.addAtHead(3);
        node.list();


    }
    @Test
    public void test(){
        System.out.println("git");
    }
}

class Node {
    public int data;
    Node next;

    public Node(int data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                '}';
    }
}

class MyNode {
    /**
     * Initialize your data structure here.
     */

    private int size;
    Node head = new Node(-1);

    /**
     * Get the value of the index-th node in the linked list. If the index is invalid, return -1.
     */
    public int get(int index) {
        if (index < 0 || index >= size) {
            return -1;
        }
        Node temp = head;
        for (int x = 0; x <= index; x++) {
            temp = temp.next;
        }
        return temp.data;
    }

    /**
     * Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list.
     */
    public void addAtHead(int val) {
        Node node = new Node(val);
        Node temp = head;
        node.next = head.next;
        temp.next = node;
        size++;
    }

    /**
     * Append a node of value val to the last element of the linked list.
     */
    public void addAtTail(int val) {
        Node node = new Node(val);
        Node temp = head;
        while (temp.next != null) temp = temp.next;
        temp.next = node;
        size++;
    }

    /**
     * Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted.
     */
    public void addAtIndex(int index, int val) {
        Node node = new Node(val);
        Node temp = head;
        if (index < 0 || index > size) {
            return;
        }
        for (int x = 0; x < index; x++) {
            temp = temp.next;
        }
        node.next = temp.next;
        temp.next = node;
        size++;
    }

    /**
     * Delete the index-th node in the linked list, if the index is valid.
     */
    public void deleteAtIndex(int index) {
        if (index < 0 || index >= size) {
            return;
        }
        Node temp = head;

        for (int x = 0; x < index; x++) {
            temp = temp.next;
        }
        temp.next = temp.next.next;
        size--;
    }

    public void list() {
        if (head == null) {
            System.out.println("null");
            return;
        }
        //头节点不能动，因此我们需要一个辅助变量来遍历
        Node temp = head.next;
        while (true) {
            //判断是否到链表的最后
            if (temp == null) {
                break;
            }
            //如果没到，退出循环，输出节点信息
            System.out.println(temp);
            //将 temp 后移
            temp = temp.next;
        }
    }

}