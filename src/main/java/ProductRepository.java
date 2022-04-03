import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ProductRepository {

    List<Product>productList = List.of(
            new Product(1,"Bread",50),
            new Product(2,"Milk",100),
            new Product(3,"Сhocolate",70),
            new Product(4,"Cola",80),
            new Product(5,"Water",30)
    );

    public void saveUpdate(Product product){
        int a = Integer.parseInt(null);
        if(product.getId() == a){
            int id = productList.size()+1;
            product.setId(id);
        }
        productList.add(product.getId(),product);
    }

    public Product findProduct(int id){
        return productList.get(id);
    }

    public void deleteProduct(int id){
        productList.remove(id);
    }

    public String findAll(){
        return productList.toString();
    }


}
