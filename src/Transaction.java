import java.util.UUID;

public class Transaction {
    public enum Type_transaction {
        DEBITS, CREDITS
    }

    public Transaction(UUID identifier, User recipient, User sender, Type_transaction transferCategory,
            int transferAmount) {
        this.IDENTIFIER = identifier;
        this.RECIPIENT = recipient;
        this.SENDER = sender;
        this.TRANSFER_CATEGORY = transferCategory;
        this.TRANSFER_AMOUNT = transferAmount;
        boolean isValidUser = sender.getIdentifier() != recipient.getIdentifier();
        isValidUser = isValidUser && sender.getBalance() >= 0 && recipient.getBalance() >= 0;
        boolean isValidAmount;
        if (transferCategory == Type_transaction.DEBITS) {
            isValidAmount = transferAmount > 0 && transferAmount <= sender.getBalance();
        } else {
            isValidAmount = transferAmount < 0 && -transferAmount <= recipient.getBalance();
        }
        this.IS_VALID = isValidAmount && isValidUser;
        if (this.IS_VALID) {
            recipient.addBalance(transferAmount);
        }
    }

    public UUID getIdentifier() {
        return IDENTIFIER;
    }

    public User getRecipient() {
        return RECIPIENT;
    }

    public User getSender() {
        return SENDER;
    }

    public Type_transaction getTransferCategory() {
        return TRANSFER_CATEGORY;
    }

    public int getTransferAmount() {
        return TRANSFER_AMOUNT;
    }

    public boolean IsVAlid() {
        return IS_VALID;
    }

    @Override
    public String toString() {
        String outString;
        if (!IS_VALID) {
            outString = "Transaction not valid";
        } else {
            outString = "To ";
            outString += SENDER.getName() + "(id = " + SENDER.getIdentifier() + ") ";

            if (TRANSFER_CATEGORY == Type_transaction.DEBITS) {
                outString += "+";
            }
            outString += TRANSFER_AMOUNT + " with id = " + IDENTIFIER;
        }
        return outString;
    }

    private final UUID IDENTIFIER;
    private final User RECIPIENT;
    private final User SENDER;
    private final Type_transaction TRANSFER_CATEGORY;
    private final int TRANSFER_AMOUNT;
    private final boolean IS_VALID;
}