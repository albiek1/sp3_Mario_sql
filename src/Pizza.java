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

    public String returnIngredients(){
        String ingredientString = "";
        int i = 0;
        for(i = 0; i < Ingredients.size()-1; i++){
            ingredientString += Ingredients.get(i) + ", ";
        }
        ingredientString += Ingredients.get(i);
        return ingredientString;
    }

    public int returnPrice(){
        return price;
    }
}
