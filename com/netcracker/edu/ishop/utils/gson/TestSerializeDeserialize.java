package netcracker.edu.ishop.utils.gson;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import netcracker.edu.ishop.api.objects.AbstractBusinessObject;
import netcracker.edu.ishop.api.objects.Folder;
import netcracker.edu.ishop.api.objects.Item;
import netcracker.edu.ishop.api.objects.User;
import netcracker.edu.ishop.utils.DAOUtils;

import java.io.*;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.*;

public class TestSerializeDeserialize {

    private static Map<Class, Class> mapClassTypeToSerializer = new HashMap<>();
    private static Map<Class, Class> mapClassTypeToDeserializer = new HashMap<>();

    static {
        mapClassTypeToSerializer.put(User.class, UserSerialiser.class);
        mapClassTypeToDeserializer.put(User.class, UserDeserializer.class);
    }


    static private Gson prepareGsonSerializerForType(Class cls) throws IllegalAccessException, InstantiationException {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(User.class, mapClassTypeToSerializer.get(cls).newInstance());
        gsonBuilder.setPrettyPrinting();
        return gsonBuilder.create();
    }

    static private Gson prepareGsonDeserializerForType(Class cls) throws IllegalAccessException, InstantiationException {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(cls, mapClassTypeToDeserializer.get(cls).newInstance());
        return gsonBuilder.create();
    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {

        User u1 = new User(BigInteger.valueOf(1));
        u1.setName("u1");
        u1.setPassword("p1".toCharArray());

        User u2 = new User(BigInteger.valueOf(2));
        u2.setName("u2");
        u2.setPassword("p2".toCharArray());

        User u3 = new User(BigInteger.valueOf(3));
        u3.setName("u3");
        u3.setPassword("p3".toCharArray());

        Folder f1 = new Folder(BigInteger.valueOf(4));
        f1.setName("f1");
        f1.setParentFolderId(null);

        Folder f2 = new Folder(BigInteger.valueOf(5));
        f1.setName("f2");
        f1.setParentFolderId(f1.getId());

        Folder f3 = new Folder(BigInteger.valueOf(6));
        f1.setName("f3");
        f1.setParentFolderId(f3.getId());

        Item i1 = new Item(BigInteger.valueOf(7));
        i1.setFolderId(f1.getId());

        Item i2 = new Item(BigInteger.valueOf(8));
        i2.setFolderId(f1.getId());


//        Gson gsonSer = prepareGsonSerializerForType(User.class);
//
//        List<User> listofUsers = Arrays.asList(u1, u2 ,u3);;
//
////        final String json1 = gsonSer.toJson(u1);
////        final String json2 = gsonSer.toJson(u2);
////        final String json3 = gsonSer.toJson(u3);
//
//        try (Writer writer = new FileWriter("Output.json")) {
//            gsonSer.toJson(listofUsers, writer);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        Map<BigInteger, User> userMap = new HashMap<>();
        userMap.put(u1.getId(), u1);
        userMap.put(u2.getId(), u2);
        userMap.put(u3.getId(), u3);

        Gson gsonDeser = prepareGsonDeserializerForType(User.class);

        BufferedReader br = new BufferedReader(new FileReader("Output.json"));
        System.out.println(br.toString());
        Type listType = new TypeToken<List<User>>() {}.getType();
        //List<String> yourList = new Gson().fromJson(br, listType);

    }


//    private void jsonSerializeBObjectByType(Class abObjType, Map mapShard) throws ClassNotFoundException {
//        final String serializedFileName = "Output2.json";
//
//
//        Class cls = Class.forName(abObjType.getCanonicalName());
//
//        Type mapType = new TypeToken<Map<BigInteger, >>() {
//        }.getType();
//
//        Gson gson = prepareGson();
//        String json = gson.toJson(mapShard, mapType);
//        try {
//            FileWriter writer = new FileWriter(serializedFileName);
//            writer.write(json);
//            writer.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private Gson prepareGson() {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        return gsonBuilder.create();
    }

}









