package netcracker.edu.ishop.prototypes;

import com.google.gson.JsonObject;

public class JSONCommandImplementation {
    private static JsonObject json = new JsonObject();


    public static void main(String[] args) {
        json.addProperty("Status", "OK");
        json.addProperty("Content", "Test1 \n Test2");

        System.out.println(json.toString());
        System.out.println(json.get("Content").getAsString());

    }

}
