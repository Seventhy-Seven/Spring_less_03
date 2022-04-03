import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@Component
@Scope("prototype")
public class Cart {

    Map<Product,Integer> mapOfCart = new HashMap<>();

    public Map<Product,Integer> getMapOfCart(){
        return mapOfCart;
    }

    public void addProduct(Product product,Integer integer){
        if(product != null)
            mapOfCart.merge(product,integer,Integer::sum);
    }

    public void deleteProduct(Product product,Integer integer){
        if(mapOfCart.containsKey(product)){
            if(integer != null && mapOfCart.get(product).compareTo(integer)>0){
                mapOfCart.put(product,mapOfCart.get(product)-integer);
            }else {
                mapOfCart.remove(product);
            }
        }
    }

    public BigInteger getSumma(){
        BigInteger integer = BigInteger.valueOf(0);
        for (Map.Entry<Product,Integer> entry: mapOfCart.entrySet()) {
            integer = integer.add(entry.getKey().getCost().multiply(BigInteger.valueOf(entry.getValue())));
        }
        return integer;
    }
}
