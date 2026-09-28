package shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // ===== 1. Категорії =====
        Category electronics = new Category(1, "Електроніка");
        Category smartphones = new Category(2, "Смартфони");
        Category accessories = new Category(3, "Аксесуари");
        Category gaming      = new Category(4, "Ігрові пристрої");
        Category home        = new Category(5, "Побутова техніка");

        // ===== 2. Каталог товарів =====
        List<Product> catalog = new ArrayList<>();
        catalog.add(new Product(1,  "Ноутбук ASUS ROG",            19999.99, "Високопродуктивний ноутбук для роботи та ігор", electronics));
        catalog.add(new Product(2,  "Смартфон Samsung Galaxy S24", 12999.50, "Смартфон з великим екраном та високою автономністю", smartphones));
        catalog.add(new Product(3,  "Навушники Sony WH-1000XM5",    2499.00, "Бездротові навушники з шумозаглушенням", accessories));
        catalog.add(new Product(4,  "Мишка Logitech G Pro",         1899.00, "Ігрова мишка з високою точністю сенсора", gaming));
        catalog.add(new Product(5,  "Клавіатура Razer BlackWidow",  3299.00, "Механічна ігрова клавіатура з підсвіткою", gaming));
        catalog.add(new Product(6,  "Монітор LG UltraGear 27\"",    8499.00, "Ігровий монітор 165 Гц з роздільною здатністю 2K", electronics));
        catalog.add(new Product(7,  "Планшет iPad Air",            17499.00, "Планшет для роботи, навчання та творчості", electronics));
        catalog.add(new Product(8,  "Годинник Apple Watch",         9999.00, "Смарт-годинник з безліччю функцій для здоров'я", accessories));
        catalog.add(new Product(9,  "Пилосос Dyson V15",           15999.00, "Бездротовий пилосос з потужним всмоктуванням", home));
        catalog.add(new Product(10, "Кавомашина De'Longhi",        11499.00, "Автоматична кавомашина для справжньої кави", home));

        // ===== 3. Кошик та історія замовлень =====
        Cart cart = new Cart();
        List<Order> orderHistory = new ArrayList<>();

        // ===== 4. Головне меню =====
        while (true) {
            System.out.println("\n========== МЕНЮ ==========");
            System.out.println("1 - Переглянути список товарів");
            System.out.println("2 - Додати товар до кошика");
            System.out.println("3 - Переглянути кошик");
            System.out.println("4 - Зробити замовлення");
            System.out.println("5 - Видалити товар з кошика");
            System.out.println("6 - Знайти товар");
            System.out.println("7 - Історія замовлень");
            System.out.println("0 - Вийти");
            System.out.print("Ваш вибір: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Будь ласка, введіть число.");
                scanner.next();
                continue;
            }
            int choice = scanner.nextInt();
            scanner.nextLine(); // очистка буфера після nextInt

            switch (choice) {
                case 1:
                    System.out.println("\n=== СПИСОК ТОВАРІВ (всього: " + catalog.size() + ") ===\n");
                    for (Product p : catalog) {
                        System.out.println(p);
                        System.out.println();
                    }
                    break;

                case 2:
                    System.out.print("Введіть ID товару: ");
                    if (scanner.hasNextInt()) {
                        int id = scanner.nextInt();
                        Product found = findById(catalog, id);
                        if (found != null) {
                            cart.addProduct(found);
                            System.out.println("✔ Товар \"" + found.getName() + "\" додано до кошика.");
                        } else {
                            System.out.println("✘ Товар з ID " + id + " не знайдено.");
                        }
                    } else {
                        System.out.println("✘ Некоректний ID.");
                        scanner.next();
                    }
                    break;

                case 3:
                    System.out.println("\n" + cart);
                    break;

                case 4:
                    if (cart.isEmpty()) {
                        System.out.println("✘ Кошик порожній. Додайте товари перед оформленням.");
                    } else {
                        Order order = new Order(cart);
                        orderHistory.add(order);
                        System.out.println("\n✔ Замовлення оформлено! Номер у історії: " + orderHistory.size());
                        System.out.println("\n" + order);
                        cart.clear();
                    }
                    break;

                case 5:
                    if (cart.isEmpty()) {
                        System.out.println("✘ Кошик порожній — нічого видаляти.");
                        break;
                    }
                    System.out.println("\nВміст кошика:");
                    for (Product p : cart.getProducts()) {
                        System.out.println("  [" + p.getId() + "] " + p.getName());
                    }
                    System.out.print("Введіть ID товару для видалення: ");
                    if (scanner.hasNextInt()) {
                        int id = scanner.nextInt();
                        Product found = findById(cart.getProducts(), id);
                        if (found != null) {
                            cart.removeProduct(found);
                            System.out.println("✔ Товар \"" + found.getName() + "\" видалено з кошика.");
                        } else {
                            System.out.println("✘ У кошику немає товару з ID " + id);
                        }
                    } else {
                        System.out.println("✘ Некоректний ID.");
                        scanner.next();
                    }
                    break;

                case 6:
                    System.out.println("\nПошук за:");
                    System.out.println("1 - Назвою");
                    System.out.println("2 - Категорією");
                    System.out.print("Ваш вибір: ");
                    if (scanner.hasNextInt()) {
                        int mode = scanner.nextInt();
                        scanner.nextLine();
                        if (mode == 1) {
                            System.out.print("Введіть частину назви: ");
                            String query = scanner.nextLine().toLowerCase().trim();
                            List<Product> results = new ArrayList<>();
                            for (Product p : catalog) {
                                if (p.getName().toLowerCase().contains(query)) {
                                    results.add(p);
                                }
                            }
                            printSearchResults(results, query, "назвою");
                        } else if (mode == 2) {
                            System.out.print("Введіть назву категорії: ");
                            String query = scanner.nextLine().toLowerCase().trim();
                            List<Product> results = new ArrayList<>();
                            for (Product p : catalog) {
                                if (p.getCategory() != null &&
                                    p.getCategory().getName().toLowerCase().contains(query)) {
                                    results.add(p);
                                }
                            }
                            printSearchResults(results, query, "категорією");
                        } else {
                            System.out.println("✘ Невірний режим пошуку.");
                        }
                    } else {
                        System.out.println("✘ Некоректний вибір.");
                        scanner.next();
                    }
                    break;

                case 7:
                    if (orderHistory.isEmpty()) {
                        System.out.println("\nІсторія замовлень порожня.");
                    } else {
                        System.out.println("\n=== ІСТОРІЯ ЗАМОВЛЕНЬ (всього: " + orderHistory.size() + ") ===");
                        int counter = 1;
                        for (Order order : orderHistory) {
                            System.out.println("\n--- Замовлення №" + counter + " ---");
                            System.out.println(order);
                            counter++;
                        }
                    }
                    break;

                case 0:
                    System.out.println("\nДякуємо, що користувалися нашим магазином! До побачення!");
                    scanner.close();
                    return;

                default:
                    System.out.println("✘ Невідома опція.");
                    break;
            }
        }
    }

    // ===== Допоміжні методи =====

    /** Пошук товару за ID у будь-якому списку */
    private static Product findById(List<Product> list, int id) {
        for (Product p : list) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    /** Виведення результатів пошуку */
    private static void printSearchResults(List<Product> results, String query, String byWhat) {
        if (results.isEmpty()) {
            System.out.println("✘ Нічого не знайдено за " + byWhat + ": \"" + query + "\"");
        } else {
            System.out.println("\n✔ Знайдено " + results.size() + " товар(ів):\n");
            for (Product p : results) {
                System.out.println(p);
                System.out.println();
            }
        }
    }
}