import java.util.List;

public class Pizza {
    String Name;
    List<String> Ingredients;
    int price, id;
    Pizza(String Name, List<String> Ingredients, int price, int id){
        this.Name = Name;
        this.Ingredients = Ingredients;
        this.price = price;
        this.id = id;
    }

    public String returnName(){
        return Name;
    }

    public List<String> returnIngredients(){
        return Ingredients;
    }

    public int returnPrice(){
        return price;
    }
}
