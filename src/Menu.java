
import java.util.Scanner;
import java.util.UUID;

public class Menu {
    public Menu(String profileMod, TransactionsService transactionsService) {
        TRANSACTIONS_SERVICE = transactionsService;
        PROFILE_MOD = profileMod;
    }

    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("1. Add a user");
                System.out.println("2. View user balances");
                System.out.println("3. Perform a transfer");
                System.out.println("4. View all transactions for a specific user");
                int i = 5;
                // if (PROFILE_MOD.equals("dev")) {
                System.out.println("5. DEV – remove a transfer by ID");
                System.out.println("6. DEV – check transfer validity");
                i = 7;
                // }
                System.out.println(i + ". Finish execution");
                int choice = scanner.nextInt();
                scanner.nextLine();
                int id;
                switch (choice) {
                    case 1:
                        System.out.println("Enter a user name and a balance");
                        String name = scanner.next();
                        int balance = scanner.nextInt();
                        scanner.nextLine();
                        TRANSACTIONS_SERVICE.addUser(UserIdsGenerator.getInstance().generateId(), name, balance);
                        System.out.println("User whith id = "
                                + TRANSACTIONS_SERVICE.getUsersList()
                                        .getByIndex(TRANSACTIONS_SERVICE.getUsersList().size() - 1).getIdentifier()
                                + " added");
                        break;
                    case 2:
                        System.out.println("Enter a user ID");
                        id = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println(TRANSACTIONS_SERVICE.getUsersList().getById(id).getName() + " - "
                                + TRANSACTIONS_SERVICE.getUsersList().getById(id).getBalance());
                        break;
                    case 3:
                        System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
                        int senderId = scanner.nextInt();
                        int recipientId = scanner.nextInt();
                        int amount = scanner.nextInt();
                        scanner.nextLine();
                        TRANSACTIONS_SERVICE.addTransaction(recipientId, senderId, amount);
                        System.out.println("The transfer is completed");
                        break;
                    case 4:
                        System.out.println("Enter a user ID");
                        id = scanner.nextInt();
                        scanner.nextLine();
                        for (Transaction transaction : TRANSACTIONS_SERVICE.getTransactionsByUserId(id)) {
                            System.out.println(transaction);
                        }
                        break;
                    case 5:
                        if (PROFILE_MOD.equals("dev")) {
                            System.out.println("Enter a user ID and a transfer ID");
                            int userId = scanner.nextInt();
                            UUID transferId = UUID.fromString(scanner.next());
                            amount = TRANSACTIONS_SERVICE.getUsersList().getByIndex(userId).getTransactionsList()
                                    .get(transferId).getTransferAmount();
                            TRANSACTIONS_SERVICE.removeTransaction(userId, transferId);
                            System.out.println(
                                    "Transfer to " + TRANSACTIONS_SERVICE.getUsersList().getByIndex(userId).getName()
                                            + "(id = " + userId + ") " + amount + " removed");
                        } else {
                            return;
                        }
                        break;
                    case 6:
                        if (PROFILE_MOD.equals("dev")) {
                            System.out.println("Check results:");
                            for (Transaction transaction : TRANSACTIONS_SERVICE.getUnpairedTransactions()) {
                                System.out.println(transaction.getRecipient().getName() + "(id = "
                                        + transaction.getRecipient().getIdentifier()
                                        + ") has an unacknowledged transfer id = " + transaction.getIdentifier()
                                        + "from " + transaction.getSender().getName() + "(id = "
                                        + transaction.getSender().getIdentifier() + ") for ");
                                if (transaction.getTransferCategory() == Transaction.Type_transaction.DEBITS) {
                                    System.out.println("+");
                                }
                                System.out.println(transaction.getTransferAmount());
                            }
                        }
                        break;
                    case 7:
                        return;
                    default:
                        System.out.println("Invalid choice");
                        break;
                }
            }
        }
    }

    private final TransactionsService TRANSACTIONS_SERVICE;
    private final String PROFILE_MOD;
}
