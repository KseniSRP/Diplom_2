import java.util.*;

// Модель заказа для тестирования API создания заказов в Stellar Burgers
public class Order {
    private final List<String> ingredients;

    public Order(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getIngredients() {
        return ingredients;
    }
}
