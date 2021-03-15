package reactive_mongo_driver;

public class Price {
    public final double value;
    public final Currency currency;

    public Price(double value, Currency currency) {
        this.value = value;
        this.currency = currency;
    }

    public Price convert(Currency to) {
        return new Price(value * currency.getCost() / to.getCost(), to);
    }

    @Override
    public String toString(){
        return value + " " + currency;
    }
}
