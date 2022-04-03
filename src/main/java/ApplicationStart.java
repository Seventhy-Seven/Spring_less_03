import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

@Component
public class ApplicationStart {

    private ProductService productService;
    private CartServiceImplimentation cartServiceImplimentation;

    public ApplicationStart(ProductService productService, CartServiceImplimentation cartServiceImplimentation) {
        this.productService = productService;
        this.cartServiceImplimentation = cartServiceImplimentation;
    }

    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(AppConfig.class);
    }

    private static void printList(List<?> list){
        System.out.println("Список продуктов: ");
        for (Object object:list) {
            System.out.println(object.toString());
        }
    }

    public static void printSeparator(){
        System.out.println("------------------------------");
    }

    @PostConstruct
    private void cartInteract() throws IOException{
        Cart cart = cartServiceImplimentation.getNewCart();
        System.out.println("Работа с корзиной: ");

        System.out.println("Консольное приложение для работы с корзиной. Для справки /?");
        listCommand();
        printSeparator();

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        boolean exit = false;
        while(!exit) {

            System.out.print("Введите команду: ");

            int prodId;
            int quantity;

            String str = in.readLine();
            if (!str.isEmpty()) {
                String[] parts = str.split("\\s");
                String command = parts[0];

                if (command.equalsIgnoreCase("exit")) {
                    exit = true; // флаг - выйти из цикла и завершить работу
                    System.out.println("Спасибо, что воспользовались нашим интернет-магазином.");
                } else if (command.equalsIgnoreCase("/?")) {
                    listCommand(); // справка
                    printSeparator();
                } else if (command.equalsIgnoreCase("test")) {
                    cartTest();
                } else if (command.equals("list")) {  // распечатать список продуктов

                    printSeparator();

                    printList(Collections.singletonList(productService.getProductList()));

                    printSeparator();
                } else if (command.equalsIgnoreCase("new")) {
                    // удалить корзину, создать новую
                    cart = cartServiceImplimentation.getNewCart();
                    System.out.println("Создана новая (пустая) корзина, старая - удалена.");
                } else if (command.equalsIgnoreCase("print")) {
                    printSeparator();
                    cartServiceImplimentation.printCart(cart); // распечатать содержимое корзины
                    printSeparator();
                } else if (command.equalsIgnoreCase("sum")) {
                    System.out.println(cartServiceImplimentation.getSumma(cart)); // распечатать стоимость корзины
                    printSeparator();
                    // параметры для удаления и добавления продуктов - должно быть три части
                } else if (parts.length == 3) {
                    // преобразуем данные в нужный формат
                    try {
                        prodId = Integer.valueOf(parts[1]);
                        quantity = Integer.parseInt(parts[2]);
                    } catch (NumberFormatException e) {
                        wrongCommand();
                        continue;
                    }

                    if (command.equalsIgnoreCase("add")) {
                        // добавить продукт
                        Product product = productService.getProductId(prodId);
                        if (product != null) {
                            cartServiceImplimentation.addProduct(cart, productService.getProductId(prodId), quantity);
                            System.out.println("В корзину добавлен товар: " + productService.getProductId(prodId) + " - " + quantity + " шт.");
                        } else {
                            System.out.println("Такого товара нет в списке.");
                        }
                    } else if (command.equalsIgnoreCase("del")) {
                        // удалить продукт
                        if (cartServiceImplimentation.getProductQuantity(cart, prodId) > 0) {
                            cartServiceImplimentation.deleteProduct(cart, productService.getProductId(prodId), quantity);
                            System.out.println("Из корзины удален товар: " + productService.getProductId(prodId) + " - " + quantity + " шт.");
                        } else {
                            System.out.println("Такого продукта нет в корзине.");
                        }
                    }
                } else {
                    wrongCommand();
                }
            }
        }
    }

    private static void wrongCommand() {
        System.out.println("Неправильный формат команды");
    }

    private static void listCommand() {
        System.out.println("Распечатать список продуктов: list");
        System.out.println("Добавить продукт: add [N продукта] [количество]");
        System.out.println("Удалить продукт: del [N продукта] [количество]");
        System.out.println("Если количество товара в корзине < 1, то он удаляется из списка.");
        System.out.println("Распечатать содержимое корзины: print");
        System.out.println("Распечатать только стоимость корзины: sum");
        System.out.println("Удалить корзину и создать новую: new");
        System.out.println("Тест (все операции с корзиной и продуктами): test");
        System.out.println("Завершить: exit");
    }

    private void cartTest() {
        Cart cart = cartServiceImplimentation.getNewCart();
        System.out.println("\n----------- ТЕСТИРОВАНИЕ КОРЗИНЫ -----------\n");

        printList(Collections.singletonList(productService.getProductList()));
        printSeparator();

        //вывести продукт с id = 2
        System.out.println("Распечатать: продукт с id=2");
        System.out.println(productService.getProductId(2));
        printSeparator();

        //удалить продукт с id = 3, добавить - с id = 8, заменить - id = 1 (сделать цену 0.01)
        System.out.println("Продукт с id=3 удалён.");
        productService.deleteId(3);
        System.out.println("Продукт с id=8 добавлен.");
        productService.saveUpdate(new Product(8, "Product Added", Integer.valueOf(900)));
        System.out.println("Продукт с id=1 изменён.");
        productService.saveUpdate(new Product(1, "Product Changed", Integer.valueOf(1)));
        printSeparator();

        //распечатать измененный список продуктов
        printList(Collections.singletonList(productService.getProductList()));
        printSeparator();

        System.out.println("КОРЗИНА №1 (все операции с корзиной - добавить, изменить количество, удалить продукт)");
        // добавить продукты в корзину
        cartServiceImplimentation.addProduct(cart, productService.getProductId(1), 1);
        cartServiceImplimentation.addProduct(cart, productService.getProductId(2), 3);
        cartServiceImplimentation.addProduct(cart, productService.getProductId(4), 9);
        cartServiceImplimentation.addProduct(cart, productService.getProductId(5), 9);
        cartServiceImplimentation.addProduct(cart, productService.getProductId(8), 5);
        // увеличить количество продуктов в корзине (добавить еще продукт с id=1 + 2шт.), итого 3шт.
        cartServiceImplimentation.addProduct(cart, productService.getProductId(1), 2);
        // уменьшить количество одного продукта в корзине (id=2 - 1шт.), итого должно быть 2шт.
        cartServiceImplimentation.deleteProduct(cart, productService.getProductId(2), 1);
        // удалить продукт из корзины
        cartServiceImplimentation.deleteProduct(cart, productService.getProductId(4), 999);
        cartServiceImplimentation.deleteProduct(cart, productService.getProductId(5), null);
        cartServiceImplimentation.printCart(cart);
        System.out.println("Проверка стоимости корзины (getSum): " + cartServiceImplimentation.getSumma(cart));
        printSeparator();

        // создать новую корзину
        System.out.println("КОРЗИНА №2");
        cart = cartServiceImplimentation.getNewCart();
        cartServiceImplimentation.addProduct(cart, 2, 2);
        cartServiceImplimentation.printCart(cart);
        System.out.println("\n----------- ТЕСТИРОВАНИЕ ЗАВЕРШЕНО -----------\n");
    }
}


