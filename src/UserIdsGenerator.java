public class UserIdsGenerator {
    private int id = 0;

    private static class SingletonHolder  {
        public static final UserIdsGenerator INSTANCE = new UserIdsGenerator();
    }

    public static UserIdsGenerator getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public int generateId() {
        return ++id;
    }
}
