public class User {
    public User(int identifier, String name, int balance) {
        this.identifier = identifier;
        this.name = name;
        this.balance = balance;
        this.transactionsLinkedList = new TransactionsLinkedList();
    }

    public int getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    public void addBalance(int amount) {
        balance += amount;
    }

    public void addTransaction(Transaction transaction) {
        transactionsLinkedList.add(transaction);
    }

    public TransactionsLinkedList getTransactionsList() {
        return transactionsLinkedList;
    }

    @Override
    public String toString() {
        return name + " balance " + balance + " identifier " + identifier;
    }

    private final int identifier;
    private final String name;
    private final TransactionsLinkedList transactionsLinkedList;
    private int balance;
}