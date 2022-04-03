import java.math.BigInteger;

public interface CartService {


    Cart getNewCart();

    void addProduct(Cart cart,Product product,Integer integer);
    void addProduct(Cart cart,int id,Integer integer);

    void deleteProduct(Cart cart,Product product,Integer integer);

    BigInteger getSumma(Cart cart);

    void printCart(Cart cart);

    int getProductQuantity(Cart cart,Product product);
    int getProductQuantity(Cart cart, int id);

}

