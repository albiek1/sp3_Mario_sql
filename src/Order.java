import java.util.Collections;
import java.util.List;

public class Order {
    String pizzas;
    int totalPrice;
    int timeStamp;
    Boolean actual;
    Order(String pizzas, int totalPrice, int timeStamp, Boolean actual){
        this.pizzas = pizzas;
        this.totalPrice = totalPrice;
        this.timeStamp = timeStamp;
        this.actual = actual;
    }

    public String returnPizzas(){
        return pizzas;
    }

    public int returnTotal(){
        return totalPrice;
    }

    public int returnTime(){
        return timeStamp;
    }

}
