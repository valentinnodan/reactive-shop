package reactive_mongo_driver;

import org.bson.Document;

public class Product {
    public final int id;
    public final String name;
    public final Price price;

    public Product(Document doc) {
        this(doc.getInteger("id"), doc.getString("name"), new Price(doc.getDouble("price"), Currency.valueOf(doc.getString("currency"))));
    }

    public Product(int id, String name, Price price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }


    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                "price=" + price.toString() +
                '}';
    }

    public Document toDocument() {
        return new Document("id", id)
                .append("name", name)
                .append("price", price.value)
                .append("currency", price.currency.toString());
    }
}
