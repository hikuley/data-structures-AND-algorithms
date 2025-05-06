package org.dsa;

import org.dsa.datastructures.linkedlist.LinkedList;


public class Main {

    public static void main(String[] args) {
        LinkedList linkedList = new LinkedList(1);
        linkedList.append(1);
        linkedList.append(0);
        linkedList.append(1);


        System.out.println("before called the binaryToDecimal method: ");
        linkedList.printList();

        final int binaryToDecimal = linkedList.binaryToDecimal();

        System.out.println("after called the binaryToDecimal method: " + binaryToDecimal);



    }
}