
import java.util.UUID;

public interface TransactionsList {
    public boolean add(Transaction transaction);

    public Transaction get(UUID identifier);

    public boolean remove(UUID identifier);

    public Transaction[] toArray();

    @Override
    public String toString();
}
