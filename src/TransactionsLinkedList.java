
import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {
    private class Node {
        public Node next;
        public Node prev;
        public Transaction transaction;
    }

    private final Node HEAD;
    private final Node TAIL;

    public TransactionsLinkedList() {
        HEAD = new Node();
        TAIL = new Node();
        HEAD.next = TAIL;
        TAIL.prev = HEAD;
    }

    @Override
    public Transaction get(UUID identifier) {
        Node node;
        for (node = HEAD.next; node != TAIL; node = node.next) {
            if (node.transaction.getIdentifier().equals(identifier)) {
                return node.transaction;
            }
        }
        return null;
    }

    @Override
    public boolean add(Transaction transaction) {
        ++size;
        Node node = new Node();
        node.transaction = transaction;
        node.next = TAIL;
        node.prev = TAIL.prev;
        TAIL.prev.next = node;
        TAIL.prev = node;
        return true;
    }

    @Override
    public boolean remove(UUID identifier) {
        Node node;
        for (node = HEAD.next; node != TAIL; node = node.next) {
            if (node.transaction.getIdentifier().equals(identifier)) {
                node.prev.next = node.next;
                node.next.prev = node.prev;
                --size;
                return true;
            }
        }
        return false;
    }

    @Override
    public Transaction[] toArray() {
        Transaction[] transactions = new Transaction[size];
        Node node;
        int i = 0;
        for (node = HEAD.next; node != TAIL; node = node.next) {
            transactions[i++] = node.transaction;
        }
        return transactions;
    }

    @Override
    public String toString() {
        String outString = "Size: " + size + "\n";
        Node node;
        for (node = HEAD.next; node != TAIL; node = node.next) {
            outString += node.transaction + "\n";
        }
        return outString;
    }

    private int size = 0;
}
