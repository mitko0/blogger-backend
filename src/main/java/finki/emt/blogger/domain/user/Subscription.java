package finki.emt.blogger.domain.user;

public enum Subscription {
    silver(15),
    gold(50),
    platinum(Integer.MAX_VALUE);

    public final int amount;

    Subscription(int amount) {
        this.amount = amount;
    }
}
