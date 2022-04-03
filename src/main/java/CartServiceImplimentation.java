import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Map;

@Service
public class CartServiceImplimentation implements CartService{

    private ProductRepository productRepository;

    public CartServiceImplimentation(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Lookup
    @Override
    public Cart getNewCart() {
        return null;
    }

    @Override
    public void addProduct(Cart cart, Product product, Integer integer) {
        cart.addProduct(product,integer);
    }

    @Override
    public void addProduct(Cart cart, int id, Integer integer) {
        Product product = productRepository.findProduct(id);
        this.addProduct(cart,product,integer);
    }

    @Override
    public void deleteProduct(Cart cart, Product product, Integer integer) {
        cart.deleteProduct(product,integer);
    }

    @Override
    public BigInteger getSumma(Cart cart) {
        return cart.getSumma();
    }

    @Override
    public void printCart(Cart cart) {
        BigInteger bigInteger = BigInteger.valueOf(0);
        for (Map.Entry<Product,Integer> entryMap: cart.getMapOfCart().entrySet()) {

            Product product = entryMap.getKey();

            BigInteger prodSumma = product.getCost().multiply(BigInteger.valueOf(entryMap.getValue()));

            System.out.printf("Product Id: %-2s|Title = %-15s|Cost = %-8s|Quantity = %-3s|Summa = %-12s \n",
                    product.getId(),product.getTitle(),product.getCost(),entryMap.getValue(),prodSumma);

            bigInteger = bigInteger.add(prodSumma);
        }
        System.out.println("Общая стоимость продуктов в корзие: " + bigInteger);
    }

    @Override
    public int getProductQuantity(Cart cart, Product product) {
        if(cart.getMapOfCart().containsKey(product)){
            return cart.getMapOfCart().get(product);
        }
        return 0;
    }

    @Override
    public int getProductQuantity(Cart cart, int id) {
        Product product = productRepository.findProduct(id);
        return this.getProductQuantity(cart,product);
    }
}
