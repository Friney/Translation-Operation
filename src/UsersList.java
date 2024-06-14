public interface UsersList {
    public boolean add(User user);

    public User getByIndex(int index) throws IndexOutOfBoundsException;

    public User getById(int ID);

    public int size();

    @Override
    public String toString();
}
