package reactive_mongo_driver;

import org.bson.Document;

public class User {
    public final int id;
    public final String name;

    public Currency getCurrency() {
        return currency;
    }

    public final Currency currency;

    public User(Document doc) {
        this(doc.getInteger("id"), doc.getString("name"), Currency.valueOf(doc.getString("currency")));
    }

    public User(int id, String name, Currency currency) {
        this.id = id;
        this.name = name;
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", currency='" + currency + "'" +
                '}';
    }

    public Document toDocument() {
        return new Document("id", id)
                .append("name", name)
                .append("currency", currency.toString());
    }
}
