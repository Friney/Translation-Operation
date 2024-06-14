
public class Program {
        
        public static void main(String[] args) {
                String profile = "production";
                for (String arg : args) {
                        if (arg.equals("--profile=dev")) {
                                profile = "dev";
                        }
                }
                Menu menu = new Menu(profile, new TransactionsService());
                menu.run();
        }
}