package com.deliverr;

import java.util.*;

/**
 * Our allocator, which can take in two inputs and output the allocating strategy using greedy algorithm
 */
public class InventoryAllocator {
    /**
     * Take in the order and warehouses, output the best allocation.
     * @param order User's order
     * @param warehouses A list of warehouses and their inventory
     * @return A list of com.deliverr.Allocation, indicating from a specific warehouse, what kinds of item, and how many of each kind, should we take
     */
    public List<Allocation> allocate(Order order, List<Warehouse> warehouses) {
        // store te original order of the warehouse list
        Map<String, Integer> warehouseName2index = new HashMap<>();
        getOrder(warehouses, warehouseName2index);

        // result list
        List<Allocation> result = new ArrayList<>();

        // warehouse name to it's allocating strategy
        Map<String, Allocation> name2allocation = new HashMap<>();

        // in the order, the required item to its count
        Map<String, Integer> item2count = order.item2count;

        // examine each item in the order
        for (String item : item2count.keySet()) {
            // how many items did the user required?
            int requiredCount = item2count.get(item);
            // iterate through the warehouses, from head to tail, to ensure that we are spending the least amount of money
            for (Warehouse warehouse : warehouses) {
                if (warehouse.inventory.containsKey(item)) {
                    // how many items can this warehouse provide?
                    int providedCount = warehouse.inventory.get(item);

                    // take out the corresponding allocating strategy of this warehouse
                    Allocation allocation = name2allocation.get(warehouse.name);
                    if (allocation == null) {
                        allocation = new Allocation(warehouse.name);
                        name2allocation.put(warehouse.name, allocation);
                    }

                    // update the allocating strategy.
                    // take the min of required count and provided count
                    allocation.inventory.put(item, Math.min(requiredCount, providedCount));

                    // after getting some items from this warehouse, decrease the required count
                    requiredCount -= Math.min(requiredCount, providedCount);

                    // if requirement is fulfilled, stop
                    if (requiredCount == 0) {
                        break;
                    }
                }
            }
            // after checking all the warehouses, the required count is still not fulfilled,
            // which means we don't have enough inventory for this order, so return an empty list
            if (requiredCount > 0) {
                return result;
            }
        }

        // after complete all the allocating strategy, put them into the result list
        result.addAll(name2allocation.values());

        // sort it according to the original order
        result.sort(new Comparator<Allocation>() {
            @Override
            public int compare(Allocation allocation1, Allocation allocation2) {
                int index1 = warehouseName2index.get(allocation1.name);
                int index2 = warehouseName2index.get(allocation2.name);
                return Integer.compare(index1, index2);
            }
        });
        return result;
    }

    /**
     * store the original order of the warehouse list. After we finished allocating,
     * we want to return the list of allocation in the same order
     * @param warehouses the list of warehouse
     * @param name2index an empty HashMap
     */
    private void getOrder(List<Warehouse> warehouses, Map<String, Integer> name2index) {
        for (int i = 0; i < warehouses.size(); i++) {
            name2index.put(warehouses.get(i).name, i);
        }
    }
}
