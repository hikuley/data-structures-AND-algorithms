package org.dsa;

import org.dsa.datastructures.linkedlist.LinkedList;


public class Main {

    public static void main(String[] args) {
        LinkedList linkedList = new LinkedList(1);

        linkedList.append(1);
        linkedList.append(2);
        linkedList.append(3);

        linkedList.append(7);

        linkedList.append(8);
        linkedList.append(4);
        linkedList.append(5);


        System.out.println("before called the findMiddleNode method: ");
        linkedList.printList();


        System.out.println("after called the findMiddleNode method: ");
        final LinkedList.Node middleNode = linkedList.findMiddleNode();
        System.out.println(middleNode.getValue());
    }
}