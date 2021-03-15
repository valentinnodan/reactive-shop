package reactive_mongo_driver;

public enum Currency {
    EUR(87.80), RUB(1), USD(73.5);

    public double getCost() {
        return cost;
    }

    private final double cost;
    Currency(double cost) {this.cost = cost;}
}
