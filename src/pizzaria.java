import java.util.*;
import java.sql.*;

public class pizzaria {
    static ArrayList<Pizza> pizzaMenu = new ArrayList<>();
    static ArrayList<Order> orderList = new ArrayList<>();
    static Scanner input = new Scanner(System.in);
    static String cmd = "0";
    static boolean running = true;
    static boolean displayStats = false;
    static final String DB_URL = "jdbc:mysql://localhost:3306/test";
    static final String USER = "root";
    static final String PASS = "nukeman2000";
    public static void main(String[] args) throws NumberFormatException, ArrayIndexOutOfBoundsException {
        Connection conn = null;
        Statement stmt = null;
        menuSetup();
        try {
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            //setup
            String sql;
            ResultSet rs;
            sql = "SELECT COUNT(idPizza) FROM pizza";
            rs = stmt.executeQuery(sql);
            rs.next();
            int pizzaCount = 14;
            //sql = "INSERT INTO pizza VALUES (1, 'Vesuvio', 'tomatsauce, ost, skinke, oregano', 57)";
            //stmt.executeUpdate(sql);
            sql = "TRUNCATE TABLE pizza";
            stmt.executeUpdate(sql);
            for(int i = 0; i < pizzaCount; i++) {
                Pizza temp = pizzaMenu.get(i);
                sql = "INSERT INTO pizza VALUES " + "("+(i+1)+", '"+temp.Name+"', '"+temp.returnIngredients()+"', "+temp.price+")";
                stmt.executeUpdate(sql);
            }
            while (running) {
                if (cmd.equals("0")) {
                    System.out.println(pizzaCount);
                    System.out.println(" " +
                            "\n1) view menu" +
                            "\n2) create new order" +
                            "\n3) view pending orders" +
                            "\n4) stats" +
                            "\n5) exit");
                    cmd = input.nextLine();
                }
                switch (cmd) {
                    case "1" -> {
                        sql = "SELECT idPizza, PizzaName, PizzaIngred, PizzaPrice FROM pizza";
                        rs = stmt.executeQuery(sql);
                        while (rs.next()){
                            int idPizza = rs.getInt("idPizza");
                            String pizzaName = rs.getString("PizzaName");
                            String pizzaIngred = rs.getString("PizzaIngred");
                            int pizzaPrice = rs.getInt("PizzaPrice");
                            System.out.println(idPizza + ": " + pizzaName + ": " + pizzaIngred + ": " + pizzaPrice);
                        }
                        cmd = "0";
                    }
                    case "2" -> {
                        System.out.println("Input pizzas seperated by space");
                        int totalPrice = 0;
                        String pizzas = input.nextLine();
                        String[] temp = pizzas.split(" ");
                        String pizzasOrdered = "";
                        for(int i = 0; i < temp.length; i++){
                            sql = "SELECT PizzaPrice FROM pizza WHERE idPizza =" + temp[i];
                            rs = stmt.executeQuery(sql);
                            while(rs.next()){
                                int pizzaPrice = rs.getInt("PizzaPrice");
                                totalPrice += pizzaPrice;
                            }
                            sql = "SELECT PizzaName from pizza WHERE idPizza =" + temp[i];
                            rs = stmt.executeQuery(sql);
                            while(rs.next()){
                                String pizzaName = rs.getString("PizzaName") + ", ";
                                pizzasOrdered += pizzaName;
                            }
                        }
                        System.out.println("pizzas ordered: " + pizzasOrdered);
                        System.out.println("pickup time");
                        int pickupTime = input.nextInt();
                        Order o = new Order(pizzasOrdered, totalPrice, pickupTime, true);
                        orderList.add(o);
                        pizzasOrdered = "";
                        for (Order s : orderList) {
                            System.out.println(s.pizzas + " : " + pickupTime + " : " + totalPrice);
                        }
                        cmd = input.nextLine();
                        cmd = "0";
                    }
                    case "3" -> {
                        System.out.println("1) Show all orders" +
                                "\n2) Show pending orders" +
                                "\n3) complete order" +
                                "\n4) remove order");
                        cmd = input.nextLine();
                        switch (cmd) {
                            case "1" -> {
                                for (Order s : orderList) {
                                    System.out.println(s.pizzas + " : " + s.totalPrice + " : " + s.timeStamp);
                                }
                                cmd = "3";
                            }
                            case "2" -> {
                                for (Order s : orderList) {
                                    if (s.actual) {
                                        System.out.println(s.pizzas + " : " + s.totalPrice + " : " + s.timeStamp);
                                    }
                                }
                                cmd = "3";
                            }
                            case "3" -> {
                                int temp = 1;
                                ArrayList<Order> actualList = new ArrayList<>();
                                for (Order s : orderList) {
                                    if (s.actual) {
                                        System.out.println(temp + ": " + s.pizzas + " : " + s.totalPrice + " : " + s.timeStamp);
                                        temp++;
                                        actualList.add(s);
                                    }
                                }
                                cmd = input.nextLine();
                                int tempNum = Integer.parseInt(cmd);
                                actualList.get(tempNum - 1).actual = false;
                                cmd = "3";
                            }
                            case "4" -> {
                                int temp = 1;
                                for (Order s : orderList) {
                                    System.out.println(temp + ": " + s.pizzas + " : " + s.totalPrice + " : " + s.timeStamp + " : " + s.actual);
                                    temp++;
                                }
                                cmd = input.nextLine();
                                int tempNum = Integer.parseInt(cmd);
                                orderList.remove(tempNum - 1);
                                cmd = "3";
                            }
                        }
                    }
                    case "4" -> {
                        displayStats = false;
                        int vesuvioTotal = 0;
                        int amerikanerTotal = 0;
                        int cacciatoreTotal = 0;
                        int carbonaTotal = 0;
                        int dennisTotal = 0;
                        int bertilTotal = 0;
                        int silviaTotal = 0;
                        int victoriaTotal = 0;
                        int toronfoTotal = 0;
                        int capricciosaTotal = 0;
                        int hawaiTotal = 0;
                        int blissolaTotal = 0;
                        int veniziaTotal = 0;
                        int mafiaTotal = 0;
                        int totalSum = 0;
                        for (Order s : orderList) {
                            String[] sepPizza = s.pizzas.split(" ");
                            totalSum = totalSum + s.totalPrice;
                            for (String value : sepPizza) {
                                switch (value) {
                                    case "Vesuvio" -> vesuvioTotal++;
                                    case "Amerikaner" -> amerikanerTotal++;
                                    case "Cacciatore" -> cacciatoreTotal++;
                                    case "Carbona" -> carbonaTotal++;
                                    case "Dennis" -> dennisTotal++;
                                    case "Bertil" -> bertilTotal++;
                                    case "Silvia" -> silviaTotal++;
                                    case "Victoria" -> victoriaTotal++;
                                    case "Toronfo" -> toronfoTotal++;
                                    case "Capricciosa" -> capricciosaTotal++;
                                    case "Hawai" -> hawaiTotal++;
                                    case "Le_Blissola" -> blissolaTotal++;
                                    case "Venezia" -> veniziaTotal++;
                                    case "Mafia" -> mafiaTotal++;
                                }
                            }
                        }
                        if (!displayStats) {
                            System.out.println("Vesuvio Total:     " + vesuvioTotal);
                            System.out.println("Amerikaner  Total: " + amerikanerTotal);
                            System.out.println("Cacciatore Total:  " + cacciatoreTotal);
                            System.out.println("Carbona Total:     " + carbonaTotal);
                            System.out.println("Dennis Total:      " + dennisTotal);
                            System.out.println("Bertil Total:      " + bertilTotal);
                            System.out.println("Silvia Total:      " + silviaTotal);
                            System.out.println("Victoria Total:    " + victoriaTotal);
                            System.out.println("Toronfo Total:     " + toronfoTotal);
                            System.out.println("Capricciosa Total: " + capricciosaTotal);
                            System.out.println("Hawai Total:       " + hawaiTotal);
                            System.out.println("Le Blissola Total: " + blissolaTotal);
                            System.out.println("Venezia Total:     " + veniziaTotal);
                            System.out.println("Mafia Total:       " + mafiaTotal);
                            System.out.println("Total revenue: " + totalSum);
                            displayStats = true;
                        }
                        cmd = input.nextLine();
                    }
                    case "5" ->
                            running = false;
                    default -> {
                        System.out.println("please input valid number");
                        cmd = "0";
                    }
                }
            }
            stmt.close();
            conn.close();
        }catch (SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(conn!=null){
                    conn.close();
                }
            }catch (SQLException se){
                se.printStackTrace();
            }
        }
    }
    public static void menuSetup(){
        List<String> base = new ArrayList<>();
        base.add("tomatsauce");
        base.add("ost");
        base.add("oregano");
        List<String> vesuvio = new ArrayList<>(base);
        vesuvio.add(2, "skinke");
        pizzaMenu.add(new Pizza("Vesuvio", vesuvio, 57, 1));
        List<String> amerikaner = new ArrayList<>(base);
        amerikaner.add(2, "oksefars");
        pizzaMenu.add(new Pizza("Amerikaner", amerikaner, 53, 2));
        List<String> cacciatore = new ArrayList<>(base);
        cacciatore.add(2, "pepperoni");
        pizzaMenu.add(new Pizza("Cacciatore", cacciatore, 57, 3));
        List<String> carbona = new ArrayList<>(base);
        carbona.add(2, "kødsauce");
        carbona.add(2, "spaghetti");
        carbona.add(2, "cocktailpølser");
        pizzaMenu.add(new Pizza("Carbona", carbona, 63, 4));
        List<String> dennis = new ArrayList<>(base);
        dennis.add(2, "skinke");
        dennis.add(2, "pepperoni");
        dennis.add(2, "cocktailpølser");
        pizzaMenu.add(new Pizza("Dennis", dennis, 65, 5));
        List<String> bertil = new ArrayList<>(base);
        bertil.add(2, "bacon");
        pizzaMenu.add(new Pizza("Bertil", bertil, 57, 6));
        List<String> silva = new ArrayList<>(base);
        silva.add(2, "rød peber");
        silva.add(2, "løg");
        silva.add(2, "oliven");
        pizzaMenu.add(new Pizza("Silva", silva, 61, 7));
        List<String> victoria = new ArrayList<>(base);
        victoria.add(2, "skinke");
        victoria.add(2, "ananas");
        victoria.add(2, "champignon");
        victoria.add(2, "løg");
        pizzaMenu.add(new Pizza("Victoria", victoria, 61, 8));
        List<String> toronfo = new ArrayList<>(base);
        toronfo.add(2, "skinke");
        toronfo.add(2, "bacon");
        toronfo.add(2, "kebab");
        toronfo.add(2, "chili");
        pizzaMenu.add(new Pizza("Toronfo", toronfo, 61, 9));
        List<String> capricciosa = new ArrayList<>(base);
        capricciosa.add(2, "skinke");
        capricciosa.add(2, "champignon");
        pizzaMenu.add(new Pizza("Capricciosa", capricciosa, 61, 10));
        List<String> hawai = new ArrayList<>(base);
        hawai.add(2, "ananas");
        pizzaMenu.add(new Pizza("Hawai", hawai, 61, 11));
        List<String> la_blissola = new ArrayList<>(base);
        la_blissola.add(2, "skinke");
        la_blissola.add(2, "rejer");
        pizzaMenu.add(new Pizza("La Blisolla", la_blissola, 61, 12));
        List<String> venezia = new ArrayList<>(base);
        venezia.add(2, "skinke");
        venezia.add(2, "bacon");
        pizzaMenu.add(new Pizza("Venezia", venezia, 61, 13));
        List<String> mafia = new ArrayList<>(base);
        mafia.add(2, "pepperoni");
        mafia.add(2, "bacon");
        mafia.add(2, "løg");
        pizzaMenu.add(new Pizza("Mafia", mafia, 61, 14));
    }
}
