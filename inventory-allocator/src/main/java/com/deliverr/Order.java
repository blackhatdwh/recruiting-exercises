package com.deliverr;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * com.deliverr.Order represents the first input, it contains a Map item2count,
 * which maps ordered item to the ordered count.
 * We use gson to convert a json string into an com.deliverr.Order object
 */
public class Order {
    private static Gson gson = new Gson();

    public Map<String, Integer> item2count;

    /**
     * Construct an com.deliverr.Order object from a json string
     * @param orderJson The first input, which should be a json string
     */
    public Order(String orderJson) {
        Type type = new TypeToken<Map<String, Integer>>(){}.getType();
        item2count = gson.fromJson(orderJson, type);
    }
}
