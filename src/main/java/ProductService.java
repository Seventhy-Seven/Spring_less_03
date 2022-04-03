public interface ProductService {


    String getProductList();
    void saveUpdate(Product product);
    Product getProductId(int id);
    void deleteId(int id);
}
