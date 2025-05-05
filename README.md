# DSA (Data Structures & Algorithms)

This project contains Java implementations of common data structures and algorithms for learning and practice purposes.

## Features

- Singly linked list implementation with comprehensive set of operations
- Node class for linked list elements with value access methods
- Methods for list manipulation and traversal
- Bounds checking for all operations
- Object-oriented design

## Methods

The LinkedList class provides the following methods:

- `append(int value)`: Adds a new node at the end of the list
- `prepend(int value)`: Adds a new node at the beginning of the list
- `removeLast()`: Removes and returns the last node
- `removeFirst()`: Removes and returns the first node
- `get(int index)`: Returns the node at the specified index
- `set(int index, int value)`: Updates the value of the node at the specified index
- `insert(int index, int value)`: Inserts a new node at the specified index
- `remove(int index)`: Removes and returns the node at the specified index
- `reverse()`: Reverses the order of nodes in the list
- `findMiddleNode()`: Returns the middle node of the list using the fast-slow pointer technique
- `hasLoop()`: Detects if the linked list contains a cycle using Floyd's cycle-finding algorithm
- `findKthFromEnd(int k)`: Returns the kth node from the end of the list using the fast-slow pointer technique
- `removeDuplicates()`: Removes all duplicate nodes from the list
- `partitionList(int x)`: Partitions the list around value x (nodes less than x come before nodes greater than or equal to x)
- `printList()`: Prints all values in the list
- `getHead()`: Prints the value of the head node
- `getTail()`: Prints the value of the tail node
- `getLength()`: Prints the current length of the list

The Node class provides:
- `getValue()`: Returns the integer value stored in the node

## Usage

1. Clone the repository:
   ```
   git clone https://github.com/your-username/dsa-java.git
   cd dsa-java
   ```

2. Build and run using your preferred IDE or with `javac`:
   ```
   javac -d out src/org/dsa/*.java
   java -cp out org.dsa.Main
   ```

## Structure

- `Main.java`: Entry point for running demonstrations
- `Node.java`: Node class for linked list
- `LinkedList.java`: Implementation of linked list logic

## License

This project is open source and available under the MIT License.