
import java.util.Scanner;
import java.util.UUID;

public class Menu {
    public Menu(String profileMod, TransactionsService transactionsService) {
        TRANSACTIONS_SERVICE = transactionsService;
        PROFILE_MOD = profileMod;
    }

    public void run() {
        int choice;
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                printMenu();
                choice = readIntToValid(scanner);
                scanner.nextLine();
                int id, amount;
                switch (choice) {
                    case 1 -> {
                        System.out.println("Enter a user name and a balance");
                        String name = scanner.next();
                        int balance = readIntToValid(scanner);
                        scanner.nextLine();
                        TRANSACTIONS_SERVICE.addUser(UserIdsGenerator.getInstance().generateId(), name, balance);
                        System.out.println("User with id = " + TRANSACTIONS_SERVICE.getUsersList()
                                .getByIndex(TRANSACTIONS_SERVICE.getUsersList().size() - 1).getIdentifier()
                                + " added");
                    }
                    case 2 -> {
                        System.out.println("Enter a user ID");
                        id = readIntToValid(scanner);
                        scanner.nextLine();
                        System.out.println(TRANSACTIONS_SERVICE.getUsersList().getById(id).getName() + " - "
                                + TRANSACTIONS_SERVICE.getUsersList().getById(id).getBalance());
                    }
                    case 3 -> {
                        boolean inputValid = false;
                        while (!inputValid) {
                            System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
                            int senderId = readIntToValid(scanner);
                            int recipientId = readIntToValid(scanner);
                            amount = readIntToValid(scanner);
                            scanner.nextLine();
                            try {
                                TRANSACTIONS_SERVICE.addTransaction(recipientId, senderId, amount);
                                System.out.println("The transfer is completed");
                                inputValid = true;
                            } catch (IllegalTransactionException e) {
                                System.out.println(e.getMessage());
                                System.out.println("Enter 1 to repeat entry or 0 to exit this item");
                                if (readIntToValid(scanner) != 1) {
                                    inputValid = true;
                                }
                            }
                        }
                    }
                    case 4 -> {
                        System.out.println("Enter a user ID");
                        id = readIntToValid(scanner);
                        scanner.nextLine();
                        for (Transaction transaction : TRANSACTIONS_SERVICE.getTransactionsByUserId(id)) {
                            System.out.println(transaction);
                        }
                    }
                    case 5 -> {
                        if (PROFILE_MOD.equals("dev")) {
                            System.out.println("Enter a user ID and a transfer ID");
                            int userId = readIntToValid(scanner);
                            UUID transferId = UUID.fromString(scanner.next());
                            amount = TRANSACTIONS_SERVICE.getUsersList().getById(userId).getTransactionsList()
                                    .get(transferId).getTransferAmount();
                            TRANSACTIONS_SERVICE.removeTransaction(userId, transferId);
                            System.out.println(
                                    "Transfer to " + TRANSACTIONS_SERVICE.getUsersList().getById(userId).getName()
                                            + "(id = " + userId + ") " + amount + " removed");
                        } else {
                            return;
                        }
                    }
                    case 6 -> {
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
                    }
                    case 7 -> {
                        return;
                    }
                    default -> System.out.println("Invalid choice");
                }
            }
        }
    }

    private void printMenu() {
        System.out.println("1. Add a user");
        System.out.println("2. View user balance");
        System.out.println("3. Perform a transfer");
        System.out.println("4. View all transactions for a specific user");
        int i = 5;
        if (PROFILE_MOD.equals("dev")) {
            System.out.println("5. DEV – remove a transfer by ID");
            System.out.println("6. DEV – check transfer validate");
            i = 7;
        }
        System.out.println(i + ". Finish execution");
    }

    private int readIntToValid(Scanner scanner) {
        boolean inputValid = false;
        int value = 0;
        while (!inputValid) {
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                inputValid = true;
            } else {
                System.out.println("Invalid input. Please try again.");
                scanner.next();
            }
        }
        return value;
    }

    private final TransactionsService TRANSACTIONS_SERVICE;
    private final String PROFILE_MOD;
}
