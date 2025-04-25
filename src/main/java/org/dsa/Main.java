package org.dsa;

import org.dsa.datastructures.linkedlist.LinkedList;


public class Main {

    public static void main(String[] args) {
        LinkedList linkedList = new LinkedList(1);
        linkedList.append(1);
        linkedList.append(2);
        linkedList.append(3);
        linkedList.append(4);
        linkedList.append(5);

        System.out.println("before called the prepend method: ");
        linkedList.printList();

        linkedList.set(2, 5);

        System.out.println("after called the set method: ");
        linkedList.printList();


    }
}