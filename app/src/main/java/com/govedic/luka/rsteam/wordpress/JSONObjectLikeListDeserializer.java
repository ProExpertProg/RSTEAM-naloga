package com.govedic.luka.rsteam.wordpress;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Deserializer that can deserialize arrays, serialized as objects with integer indexes, to lists of the respective type
 * @param <T> the type of objects in the list
 */
public class JSONObjectLikeListDeserializer<T> implements JsonDeserializer<List<T>> {
    @Override
    public List<T> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        //Get the type of the list elements so that we can deserialize them individually later
        Type typeOfElem = ((ParameterizedType) typeOfT).getActualTypeArguments()[0];

        List<T> list = new ArrayList<>();

        if (json.isJsonNull()) return list;
        else if (json.isJsonArray()) {
            //if array, parse as normal list
            JsonArray array = json.getAsJsonArray();

            for(JsonElement jElem : array) {
                T elem = context.deserialize(jElem, typeOfElem);
                list.add(elem);
            }
            return list;

        } else if (json.isJsonObject()) {
            //if object, fill the list with elements and check for integer indexes
            JsonObject obj = json.getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> entries = obj.entrySet(); //will return members of the object
            for (Map.Entry<String, JsonElement> entry : entries) {
                String name = entry.getKey();

                try {
                    Integer.parseInt(name);
                } catch (NumberFormatException ex) {
                    throw new JsonParseException("Expected JSONObject with integer indexes, " + name +" found");
                }

                T elem = context.deserialize(entry.getValue(), typeOfElem);
                list.add(elem);
            }
            return list;
        } else {
            throw new JsonParseException("Expected JSONArray or JSONObject, primitive found");
        }
    }
}
