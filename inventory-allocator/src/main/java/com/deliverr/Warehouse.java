package com.deliverr;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * com.deliverr.Warehouse class literally represents a warehouse, it has a name and an map,
 * which maps the item name in the inventory to its count.
 * There's a static method used to parse the input json string
 */
public class Warehouse {
    String name;
    public Map<String, Integer> inventory = new HashMap<>();

    /**
     * Constructor
     * @param name the name of the warehouse
     */
    public Warehouse(String name) {
        this.name = name;
    }

    /**
     * Take in the second input string, and return a List of com.deliverr.Warehouse
     * @param warehousesJson a json string which represents a bunch of warehouses
     * @return a List of parsed com.deliverr.Warehouse, in original order
     */
    public static List<Warehouse> parseWarehouses(String warehousesJson) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Warehouse>>(){}.getType();
        return gson.fromJson(warehousesJson, type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Warehouse warehouse = (Warehouse) o;
        return Objects.equals(name, warehouse.name) &&
                Objects.equals(inventory, warehouse.inventory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, inventory);
    }
}
