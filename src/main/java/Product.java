import java.math.BigInteger;

public class Product {
    private int id;
    private String title;
    private Integer cost;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigInteger getCost() {
        BigInteger bigInteger;
        bigInteger = BigInteger.valueOf(cost);
        return bigInteger;
    }


    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Product(int id, String title, Integer cost) {
        this.id = id;
        this.title = title;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return String.format("Products {id = %-2s | title = %-15s | cost = %-8s}",id,title,cost);
    }
}
