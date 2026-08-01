package org.dsa.dataStructures.binaryTree;

class Node {
    int value;
    Node left, right;

    public Node(int item) {
        value = item;
        left = right = null;
    }
}


public class BinaryTree {

    Node root;

    public BinaryTree() {
        root = null;
    }

    void printInOrder(Node node) {
        if (node == null) return;

        printInOrder(node.left);
        System.out.print(node.value + " ");
        printInOrder(node.right);

    }

    public static void main(String[] args) {

        /* Constructing the tree:

                 5
               /   \
              3     2
             / \
            10  11

        */


        final BinaryTree tree = new BinaryTree();
        tree.root = new Node(5);
        tree.root.left = new Node(3);
        tree.root.right = new Node(2);
        tree.root.left.left = new Node(10);
        tree.root.left.right = new Node(11);

        System.out.println("In-order traversal of the tree:");
        tree.printInOrder(tree.root);

    }


}
