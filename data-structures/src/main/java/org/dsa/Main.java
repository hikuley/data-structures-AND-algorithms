package org.dsa;

import org.dsa.datastructures.linkedlist.LinkedList;


public class Main {

    public static void main(String[] args) {
        LinkedList linkedList = new LinkedList();
        linkedList.append(1);
        linkedList.append(4);
        linkedList.append(3);
        linkedList.append(2);
        linkedList.append(5);
        linkedList.append(2);


        System.out.println("before called the partitionList method: ");
        linkedList.printList();

        linkedList.partitionList(3);

        System.out.println("after called the partitionList method: ");
        linkedList.printList();

    }
}