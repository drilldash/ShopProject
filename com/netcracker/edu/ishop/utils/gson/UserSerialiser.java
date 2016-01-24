package netcracker.edu.ishop.utils.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import netcracker.edu.ishop.api.objects.User;

import java.lang.reflect.Type;
import java.util.Arrays;

public class UserSerialiser implements JsonSerializer<User> {
    @Override
    public JsonElement serialize(final User user, final Type typeOfSrc, final JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("id", user.getId());

        jsonObject.addProperty("username", user.getName());

        if (user.getGroup() != null) {
            jsonObject.addProperty("group", user.getGroup().toString());
        } else {
            jsonObject.addProperty("group", "");
        }
        //todo: unsafe, char password changes to String
        jsonObject.addProperty("password", Arrays.toString(user.getPassword()));
        return jsonObject;
    }
}
