import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductServiceImplementation implements ProductService{

    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public String getProductList() {
        return productRepository.findAll();
    }

    @Override
    public void saveUpdate(Product product) {
        productRepository.saveUpdate(product);
    }

    @Override
    public Product getProductId(int id) {
        return productRepository.findProduct(id);
    }

    @Override
    public void deleteId(int id) {
        productRepository.deleteProduct(id);
    }
}
