package server;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.netty.protocol.http.server.HttpServer;
import reactive_mongo_driver.*;
import rx.Observable;

import java.util.List;
import java.util.Map;

public class ProductsRxNettyServer {
    static ProductsReactiveMongoDriver driver = new ProductsReactiveMongoDriver();

    public static void main(final String[] args) {
        HttpServer
                .newServer(8080)
                .start((req, resp) -> {

                    String action = req.getDecodedPath().substring(1);

                    rx.Observable<String> responseMessage;
                    try {
                        responseMessage = process(action, req.getQueryParameters());
                    } catch (RuntimeException e) {
                        responseMessage = Observable.just(e.getMessage());
                        resp.setStatus(HttpResponseStatus.BAD_REQUEST);
                    }

                    return resp.writeString(responseMessage);
                })
                .awaitShutdown();
    }

    private static Observable<String> process(String action, Map<String, List<String>> queryParameters) {
        switch (action) {
            case "addProduct":
                return addProduct(queryParameters);
            case "addUser":
                return addUser(queryParameters);
            case "getProducts":
                return getProducts(queryParameters);
            default:
                throw new RuntimeException("Bad request");
        }
    }

    private static Observable<String> getProducts(Map<String, List<String>> queryParameters) {
        int id = Integer.parseInt(queryParameters.get("id").get(0));
        return driver
                .getUser(id)
                .map(User::getCurrency)
                .flatMap(currency -> driver.getProducts()
                        .map(product -> new Product(product.id,
                                product.name,
                                product.price.convert(currency)
                        ).toString() + "\n")
                );

    }

    private static Observable<String> addUser(Map<String, List<String>> queryParameters) {
        int id = Integer.parseInt(queryParameters.get("id").get(0));
        String name = queryParameters.get("name").get(0);
        Currency currency = Currency.valueOf(queryParameters.get("currency").get(0));
        return driver.addUser(new User(id, name, currency))
                .map(success -> "New User added");
    }

    private static Observable<String> addProduct(Map<String, List<String>> queryParameters) {
        int id = Integer.parseInt(queryParameters.get("id").get(0));
        String name = queryParameters.get("name").get(0);
        Price price = new Price(Double.parseDouble(queryParameters.get("price").get(0)),
                Currency.valueOf(queryParameters.get("currency").get(0)));
        return driver.addProduct(new Product(id, name, price))
                .map(success -> "New Product added");
    }
}
