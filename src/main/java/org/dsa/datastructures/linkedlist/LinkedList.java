package org.dsa.datastructures.linkedlist;

public class LinkedList {

    private Node head;
    private Node tail;
    private int length;

    public class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "Node{" + "value=" + value + ", next=" + next + '}';
        }
    }

    public LinkedList() {
    }

    public LinkedList(int value) {
        Node newNode = new Node(value);
        head = newNode;
        tail = newNode;
        length = 1;
    }

    public void getHead() {
        System.out.println("Head:" + head.value);
    }

    public void getTail() {
        System.out.println("Tail:" + tail.value);
    }

    public void getLength() {
        System.out.println("Length:" + length);
    }

    public void printList() {
        Node temp = head;
        while (temp != null) {
            System.out.println(temp.value);
            temp = temp.next;
        }
    }

    public void append(int value) {
        Node newNode = new Node(value);
        if (length == 0) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        length++;
    }

    public Node removeLast() {
        if (length == 0) return null;
        Node temp = head;
        Node pre = head;
        while (temp.next != null) {
            pre = temp;
            temp = temp.next;
        }
        tail = pre;
        tail.next = null; // remove node
        length--;
        if (length == 0) { // if one node is not exist, empy the list.
            head = null;
            tail = null;
        }
        return temp;
    }

    public void prepend(int value) {
        Node newNode = new Node(value);
        if (length == 0) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }
        length++;
    }

    public Node removeFirst() {
        if (length == 0) return null;
        Node temp = head;
        head = head.next;
        temp.next = null;
        length--;
        if (length == 0) {
            tail = null;
        }
        return temp;
    }

    public Node get(int index) {
        if (index < 0 || index >= length) return null;
        Node temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        return temp;
    }

    public boolean set(int index, int value) {
        final Node temp = get(index);
        if (temp != null) {
            temp.value = value;
            return true;
        }
        return false;
    }

    public boolean insert(int index, int value) {
        if (index < 0 || index > length) return false;
        if (index == 0) return prependAndReturnTrue(value);
        if (index == length) return appendAndReturnTrue(value);

        Node newNode = new Node(value);
        Node temp = get(index - 1);

        // Put new node into the list.
        newNode.next = temp.next;
        temp.next = newNode;

        length++;

        return true;
    }

    public Node remove(int index) {
        if (index < 0 || index >= length) return null;
        if (index == 0) return removeFirst();
        if (index == length - 1) return removeLast();

        Node pre = get(index - 1);
        Node temp = pre.next;

        pre.next = temp.next;
        temp.next = null;

        length--;

        return temp;
    }

    public void reverse() {
        Node current = head;
        head = tail;
        tail = current;

        Node after = null;
        Node before = null;

        for (int i = 0; i < length; i++) {
            after = current.next;
            current.next = before; /* important point */

            before = current;
            current = after;
        }
    }


    private boolean prependAndReturnTrue(int value) {
        prepend(value);
        return true;
    }

    private boolean appendAndReturnTrue(int value) {
        append(value);
        return true;
    }

    public Node findMiddleNode() {
        Node slow = head;
        Node fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public boolean hasLoop() {
        Node slow = head;
        Node fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) return true;
        }
        return false;
    }


    public Node findKthFromEnd(int k) {
        Node slow = head;
        Node fast = head;

        for (int i = 0; i < k; i++) {
            if (fast == null) {
                return null;
            }
            fast = fast.next;
        }

        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }

        return slow;
    }


    public void removeDuplicates() {
        Node current = head;
        while (current != null) {
            Node runner = current;
            while (runner != null) {
                if (runner.next != null && current.value == runner.next.value) {
                    runner.next = runner.next.next; // Remove duplıcate node
                    length--;
                } else {
                    runner = runner.next;
                }
            }
            current = current.next;
        }
    }

    public int binaryToDecimal() {
        Node current = head;
        int num = 0;
        while (current != null) {
            num = num * 2 + current.value;
            current = current.next;
        }
        return num;
    }

    public void partitionList(int x) {
        if (head == null) return;

        Node smalls = null;
        Node biggers = null;
        Node current = head;

        while (current != null) {
            int currentValue = current.value;
            final Node newNode = new Node(currentValue);

            if (currentValue >= x) {
                if (biggers == null) {
                    biggers = newNode;
                } else {
                    Node findCurrent = biggers;
                    while (findCurrent.next != null) findCurrent = findCurrent.next;
                    findCurrent.next = newNode;
                }
            } else {
                if (smalls == null) {
                    smalls = newNode;
                } else {
                    Node findCurrent = smalls;
                    while (findCurrent.next != null) findCurrent = findCurrent.next;
                    findCurrent.next = newNode;
                }
            }
            current = current.next;
        }

        if (smalls != null && biggers != null) {
            Node smallTail = smalls;
            while (smallTail.next != null) smallTail = smallTail.next;
            smallTail.next = biggers;
            head = smalls;
        }

    }


}
