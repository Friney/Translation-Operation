public class UsersArrayList implements UsersList {

    public UsersArrayList() {
        users = new User[DEFAULT_CAPACITY];
    }

    @Override
    public User getByIndex(int index) throws IndexOutOfBoundsException {
        if (index >= 0 && index <= size) {
            return users[index];
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public User getById(int id) {
        for (User user : users) {
            if (user.getIdentifier() == id) {
                return user;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean add(User user) {
        ++size;
        if (size == users.length) {
            User[] newUsers = new User[users.length * 2];
            for (int i = 0; i < users.length; ++i) {
                newUsers[i] = users[i];
            }
            users = newUsers;
        }
        users[size - 1] = user;
        return true;
    }

    @Override
    public String toString() {
        String outString;
        outString = "Size: " + size + "\n";
        for (int i = 0; i < size; ++i) {
            outString += users[i] + "\n";
        }
        return outString;
    }

    private User[] users;
    private int size = 0;
    private static final int DEFAULT_CAPACITY = 10;
}
