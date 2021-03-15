package reactive_mongo_driver;

import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.Success;
import rx.Observable;

public class ProductsReactiveMongoDriver {
    private static MongoClient client = createMongoClient();

    private static final String DB_NAME = "products";
    private static final String USERS_COLLECTION = "users";
    private static final String PRODUCTS_COLLECTION = "products";


    public Observable<Success> addUser(User user) {
        return client.getDatabase(DB_NAME)
                .getCollection(USERS_COLLECTION)
                .insertOne(user.toDocument());
    }

    public Observable<Success> addProduct(Product product) {
        return client.getDatabase(DB_NAME)
                .getCollection(PRODUCTS_COLLECTION)
                .insertOne(product.toDocument());
    }

    public Observable<User> getUser(int id) {
        return client.getDatabase(DB_NAME)
                .getCollection(USERS_COLLECTION)
                .find(com.mongodb.client.model.Filters.eq("id", id))
                .toObservable()
                .map(User::new);
    }


    public Observable<Product> getProducts() {
        return client.getDatabase(DB_NAME)
                .getCollection(PRODUCTS_COLLECTION)
                .find()
                .toObservable()
                .map(Product::new);
    }



    private static MongoClient createMongoClient() {
        return MongoClients.create("mongodb://localhost:27017");
    }

}
