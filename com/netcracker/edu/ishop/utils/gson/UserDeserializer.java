package netcracker.edu.ishop.utils.gson;

import com.google.gson.*;
import netcracker.edu.ishop.api.objects.User;
import netcracker.edu.ishop.utils.UserGroupTypes;

import java.lang.reflect.Type;
import java.math.BigInteger;

public class UserDeserializer implements JsonDeserializer<User> {

    @Override
    public User deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();

        final JsonElement jsonId = jsonObject.get("id");
        final BigInteger id =  jsonId.getAsBigInteger();

        final JsonElement jsonUserName = jsonObject.get("name");
        final String name =  jsonUserName.getAsString();

        final JsonElement jsonGroupType = jsonObject.get("group");
        final UserGroupTypes groupType = UserGroupTypes.valueOf(jsonGroupType.getAsString());

        final char[] password = jsonObject.get("password").getAsString().toCharArray();


        final User user = new User(id);

        user.setName(name);
        user.setGroupType(groupType);
        user.setPassword(password);

        return user;
    }
}
