package finki.emt.blogger.domain.user;

public enum Subscription {
    silver(15),
    gold(50),
    platinum(Integer.MAX_VALUE);

    public final Integer amount;

    Subscription(Integer amount) {
        this.amount = amount;
    }
}
