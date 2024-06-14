
import java.util.UUID;

public class TransactionsService {

    public TransactionsService() {
        USERS_LIST = new UsersArrayList();
        unpairedTransactions = new Transaction[0];
    }

    public void addUser(int identifier, String name, int balance) {
        USERS_LIST.add(new User(identifier, name, balance));
    }

    public UsersList getUsersList() {
        return USERS_LIST;
    }

    public int balanceById(int id) {
        return USERS_LIST.getById(id).getBalance();
    }

    public int balanceByIndex(int index) {
        return USERS_LIST.getByIndex(index).getBalance();
    }

    public void addTransaction(int receiverId, int senderId, int amount) {
        if (USERS_LIST.getById(senderId).getBalance() < amount) {
            throw new IllegalTransactionException("Sender doesn't have enough money");
        }
        UUID id = UUID.randomUUID();
        USERS_LIST.getById(receiverId).addTransaction(new Transaction(id, USERS_LIST.getById(receiverId),
                USERS_LIST.getById(senderId), Transaction.Type_transaction.DEBITS, amount));
        USERS_LIST.getById(senderId).addTransaction(new Transaction(id, USERS_LIST.getById(senderId),
                USERS_LIST.getById(receiverId), Transaction.Type_transaction.CREDITS, -amount));
    }

    public Transaction[] getTransactionsByUserIndex(int index) {
        return USERS_LIST.getByIndex(index).getTransactionsList().toArray();
    }

    public Transaction[] getTransactionsByUserId(int id) {
        return USERS_LIST.getById(id).getTransactionsList().toArray();
    }

    public boolean removeTransaction(int UserId, UUID transactionId) {
        Transaction transaction = USERS_LIST.getById(UserId).getTransactionsList().get(transactionId);
        if (transaction == null) {
            return false;
        }
        boolean isRemoved = USERS_LIST.getById(UserId).getTransactionsList().remove(transactionId);
        boolean isUnpaired = true;
        for (int i = 0; i < sizeUnpairedTransactions; i++) {
            if (unpairedTransactions[i].getIdentifier().equals(transactionId)) {
                isUnpaired = false;
                Transaction[] newUnpairedTransactions = new Transaction[--sizeUnpairedTransactions];
                System.arraycopy(unpairedTransactions, 0, newUnpairedTransactions, 0, i);
                System.arraycopy(unpairedTransactions, i + 1, newUnpairedTransactions, i, sizeUnpairedTransactions - i);
                unpairedTransactions = newUnpairedTransactions;
                break;
            }
        }
        if (isUnpaired) {
            ++sizeUnpairedTransactions;
            Transaction[] newUnpairedTransactions = new Transaction[sizeUnpairedTransactions];
            System.arraycopy(unpairedTransactions, 0, newUnpairedTransactions, 0, unpairedTransactions.length);
            newUnpairedTransactions[sizeUnpairedTransactions - 1] = transaction;
            unpairedTransactions = newUnpairedTransactions;
        }
        return isRemoved;
    }

    public Transaction[] getUnpairedTransactions() {
        return unpairedTransactions;
    }

    @Override
    public String toString() {
        String outString = "";
        outString += USERS_LIST + "\n";
        for (int i = 0; i < USERS_LIST.size(); ++i) {
            outString += "User: " + USERS_LIST.getByIndex(i).getName() + ", id "
                    + USERS_LIST.getByIndex(i).getIdentifier() + "\n";
            outString += "Transactions: " + USERS_LIST.getByIndex(i).getTransactionsList() + "\n";
        }
        outString += "UnpairedTransactions: ";
        for (int i = 0; i < sizeUnpairedTransactions; ++i) {
            outString += unpairedTransactions[i] + "\n";
        }
        outString += "\n";
        return outString;
    }

    private final UsersList USERS_LIST;
    private Transaction[] unpairedTransactions;
    private int sizeUnpairedTransactions = 0;
}
