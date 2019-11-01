import com.deliverr.Allocation;
import com.deliverr.InventoryAllocator;
import com.deliverr.Order;
import com.deliverr.Warehouse;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class InventoryAllocatorTest {
    InventoryAllocator allocator = new InventoryAllocator();

    /*
    1. zero item
    2. one item
    3. multiple items

    a. zero warehouse
    b. one warehouse
    c. multiple warehouses

    A. inventory < required
    B. inventory == required
    C. inventory > required
     */


    // 1+a
    @Test
    public void noOrderNoWarehouse() {
        Order order = new Order("{}");
        String warehousesJson = "[]";
        List<Warehouse> warehouses = Warehouse.parseWarehouses(warehousesJson);

        List<Allocation> allocations = allocator.allocate(order, warehouses);
        List<Allocation> expectedAllocations = new ArrayList<>();

        Assert.assertEquals(allocations, expectedAllocations);
    }

    // 1+b
    @Test
    public void noOrderOneWarehouse() {
        Order order = new Order("{}");
        String warehousesJson = "[{\"name\": \"owd\", \"inventory\": {\"apple\": 1}}]";
        List<Warehouse> warehouses = Warehouse.parseWarehouses(warehousesJson);

        List<Allocation> allocations = allocator.allocate(order, warehouses);
        List<Allocation> expectedAllocations = new ArrayList<>();

        Assert.assertEquals(allocations, expectedAllocations);
    }

    // 1+c
    @Test
    public void noOrderMultipleWarehouses() {
        Order order = new Order("{}");
        String warehousesJson = "[{\"name\": \"owd\", \"inventory\": {\"apple\": 1}}, " +
                "{\"name\": \"dm\", \"inventory\": {\"apple\": 1}}]";
        List<Warehouse> warehouses = Warehouse.parseWarehouses(warehousesJson);

        List<Allocation> allocations = allocator.allocate(order, warehouses);
        List<Allocation> expectedAllocations = new ArrayList<>();

        Assert.assertEquals(allocations, expectedAllocations);
    }

    // 2+a
    @Test
    public void oneOrderZeroWarehouse() {
        Order order = new Order("{\"apple\": 1}");
        String warehousesJson = "[]";
        List<Warehouse> warehouses = Warehouse.parseWarehouses(warehousesJson);

        List<Allocation> allocations = allocator.allocate(order, warehouses);
        List<Allocation> expectedAllocations = new ArrayList<>();

        Assert.assertEquals(allocations, expectedAllocations);
    }

    // 2+b+A
    @Test
    public void oneOrderOneWarehouseNotEnough() {
        Order order = new Order("{\"apple\": 10}");
        String warehousesJson = "[{\"name\":\"owd\",\"inventory\":{\"apple\":1}}]";
        List<Warehouse> warehouses = Warehouse.parseWarehouses(warehousesJson);

        List<Allocation> allocations = allocator.allocate(order, warehouses);
        List<Allocation> expectedAllocations = new ArrayList<>();

        Assert.assertEquals(allocations, expectedAllocations);
    }

    // 2+b+B
    @Test
    public void oneOrderOneWarehouseEnough() {
        Order order = new Order("{\"apple\": 1}");
        String warehousesJson = "[{\"name\":\"owd\",\"inventory\":{\"apple\":1}}]";

        List<Warehouse> warehouses = Warehouse.parseWarehouses(warehousesJson);

        List<Allocation> allocations = allocator.allocate(order, warehouses);

        Allocation allocation1 = new Allocation("owd");
        allocation1.inventory.put("apple", 1);
        List<Allocation> expectedAllocations = new ArrayList<Allocation>() {{
            add(allocation1);
        }};

        Assert.assertEquals(allocations, expectedAllocations);
    }

    // 2+b+C
    @Test
    public void oneOrderOneWarehouseMore() {
        Order order = new Order("{\"apple\": 1}");
        String warehousesJson = "[{\"name\":\"owd\",\"inventory\":{\"apple\":10}}]";
        List<Warehouse> warehouses = Warehouse.parseWarehouses(warehousesJson);

        List<Allocation> allocations = allocator.allocate(order, warehouses);

        Allocation allocation1 = new Allocation("owd");
        allocation1.inventory.put("apple", 1);
        List<Allocation> expectedAllocations = new ArrayList<Allocation>() {{
            add(allocation1);
        }};

        Assert.assertEquals(allocations, expectedAllocations);
    }

    // 2+c+A
    @Test
    public void oneOrderMultipleNotEnough() {
        Order order = new Order("{\"apple\": 10}");
        String warehousesJson = "[{\"name\": \"owd\", \"inventory\": {\"apple\": 1}}, " +
                "{\"name\": \"dm\", \"inventory\": {\"apple\": 1}}]";
        List<Warehouse> warehouses = Warehouse.parseWarehouses(warehousesJson);

        List<Allocation> allocations = allocator.allocate(order, warehouses);
        List<Allocation> expectedAllocations = new ArrayList<>();

        Assert.assertEquals(allocations, expectedAllocations);
    }

    // 2+c+B
    @Test
    public void oneOrderMultipleEnough1() {
        Order order = new Order("{\"apple\": 10}");
        String warehousesJson = "[{\"name\": \"owd\", \"inventory\": {\"apple\": 5}}, " +
                "{\"name\": \"dm\", \"inventory\": {\"apple\": 5}}]";
        List<Warehouse> warehouses = Warehouse.parseWarehouses(warehousesJson);

        List<Allocation> allocations = allocator.allocate(order, warehouses);
        Allocation allocation1 = new Allocation("owd");
        allocation1.inventory.put("apple", 5);
        Allocation allocation2 = new Allocation("dm");
        allocation2.inventory.put("apple", 5);
        List<Allocation> expectedAllocations = new ArrayList<Allocation>() {{
            add(allocation1);
            add(allocation2);
        }};

        Assert.assertEquals(allocations, expectedAllocations);
    }

    @Test
    public void oneOrderMultipleEnough2() {
        Order order = new Order("{\"apple\": 10}");
        String warehousesJson = "[{\"name\": \"owd\", \"inventory\": {\"apple\": 10}}, " +
                "{\"name\": \"dm\", \"inventory\": {\"apple\": 50}}]";
        List<Warehouse> warehouses = Warehouse.parseWarehouses(warehousesJson);

        List<Allocation> allocations = allocator.allocate(order, warehouses);
        Allocation allocation1 = new Allocation("owd");
        allocation1.inventory.put("apple", 10);
        List<Allocation> expectedAllocations = new ArrayList<Allocation>() {{
            add(allocation1);
        }};

        Assert.assertEquals(allocations, expectedAllocations);
    }

    // 2+c+C
    @Test
    public void oneOrderMultipleMore1() {
        Order order = new Order("{\"apple\": 10}");
        String warehousesJson = "[{\"name\": \"owd\", \"inventory\": {\"apple\": 5}}, " +
                "{\"name\": \"dm\", \"inventory\": {\"apple\": 10}}]";
        List<Warehouse> warehouses = Warehouse.parseWarehouses(warehousesJson);

        List<Allocation> allocations = allocator.allocate(order, warehouses);
        Allocation allocation1 = new Allocation("owd");
        allocation1.inventory.put("apple", 5);
        Allocation allocation2 = new Allocation("dm");
        allocation2.inventory.put("apple", 5);
        List<Allocation> expectedAllocations = new ArrayList<Allocation>() {{
            add(allocation1);
            add(allocation2);
        }};

        Assert.assertEquals(allocations, expectedAllocations);
    }

    @Test
    public void oneOrderMultipleMore2() {
        Order order = new Order("{\"apple\": 10}");
        String warehousesJson = "[{\"name\": \"owd\", \"inventory\": {\"apple\": 20}}," +
                " {\"name\": \"dm\", \"inventory\": {\"apple\": 50}}]";
        List<Warehouse> warehouses = Warehouse.parseWarehouses(warehousesJson);

        List<Allocation> allocations = allocator.allocate(order, warehouses);
        Allocation allocation1 = new Allocation("owd");
        allocation1.inventory.put("apple", 10);
        List<Allocation> expectedAllocations = new ArrayList<Allocation>() {{
            add(allocation1);
        }};

        Assert.assertEquals(allocations, expectedAllocations);
    }

    // 3+a
    @Test
    public void multipleOrdersZeroWarehouse() {
        Order order = new Order("{\"apple\": 10, \"orange\": 10}");
        String warehousesJson = "[]";
        List<Warehouse> warehouses = Warehouse.parseWarehouses(warehousesJson);

        List<Allocation> allocations = allocator.allocate(order, warehouses);
        List<Allocation> expectedAllocations = new ArrayList<>();

        Assert.assertEquals(allocations, expectedAllocations);
    }

    // 3+b+A
    @Test
    public void multipleOrdersOneWarehouseNotEnough1() {
        Order order = new Order("{\"apple\": 10, \"orange\": 10}");
        String warehousesJson = "[{\"name\": \"owd\", \"inventory\": {\"apple\": 20}}]";
        List<Warehouse> warehouses = Warehouse.parseWarehouses(warehousesJson);

        List<Allocation> allocations = allocator.allocate(order, warehouses);
        List<Allocation> expectedAllocations = new ArrayList<>();

        Assert.assertEquals(allocations, expectedAllocations);
    }

    @Test
    public void multipleOrdersOneWarehouseNotEnough2() {
        Order order = new Order("{\"apple\": 10, \"orange\": 10}");
        String warehousesJson = "[{\"name\": \"owd\", \"inventory\": {\"apple\": 20, \"orange\": 5}}]";
        List<Warehouse> warehouses = Warehouse.parseWarehouses(warehousesJson);

        List<Allocation> allocations = allocator.allocate(order, warehouses);
        List<Allocation> expectedAllocations = new ArrayList<>();

        Assert.assertEquals(allocations, expectedAllocations);
    }

    // 3+b+B
    @Test
    public void multipleOrdersOneWarehouseEnough() {
        Order order = new Order("{\"apple\": 10, \"orange\": 10}");
        String warehousesJson = "[{\"name\": \"owd\", \"inventory\": {\"apple\": 10, \"orange\": 10}}]";
        List<Warehouse> warehouses = Warehouse.parseWarehouses(warehousesJson);

        List<Allocation> allocations = allocator.allocate(order, warehouses);
        Allocation allocation1 = new Allocation("owd");
        allocation1.inventory.put("apple", 10);
        allocation1.inventory.put("orange", 10);
        List<Allocation> expectedAllocations = new ArrayList<Allocation>() {{
            add(allocation1);
        }};

        Assert.assertEquals(allocations, expectedAllocations);
    }

    // 3+b+C
    @Test
    public void multipleOrdersOneWarehouseMore() {
        Order order = new Order("{\"apple\": 10, \"orange\": 10}");
        String warehousesJson = "[{\"name\": \"owd\", \"inventory\": {\"apple\": 20, \"orange\": 20, \"banana\": 20}}]";
        List<Warehouse> warehouses = Warehouse.parseWarehouses(warehousesJson);

        List<Allocation> allocations = allocator.allocate(order, warehouses);
        Allocation allocation1 = new Allocation("owd");
        allocation1.inventory.put("apple", 10);
        allocation1.inventory.put("orange", 10);
        List<Allocation> expectedAllocations = new ArrayList<Allocation>() {{
            add(allocation1);
        }};

        Assert.assertEquals(allocations, expectedAllocations);
    }

    // 3+c+A
    @Test
    public void multipleOrdersMultipleWarehouseNotEnough1() {
        Order order = new Order("{\"apple\": 10, \"orange\": 10}");
        String warehousesJson = "[{\"name\": \"owd\", \"inventory\": {\"apple\": 10}}, " +
                "{\"name\": \"dm\", \"inventory\": {\"apple\": 10}}]";
        List<Warehouse> warehouses = Warehouse.parseWarehouses(warehousesJson);

        List<Allocation> allocations = allocator.allocate(order, warehouses);
        List<Allocation> expectedAllocations = new ArrayList<>();

        Assert.assertEquals(allocations, expectedAllocations);
    }
    @Test
    public void multipleOrdersMultipleWarehouseNotEnough2() {
        Order order = new Order("{\"apple\": 10, \"orange\": 10}");
        String warehousesJson = "[{\"name\": \"owd\", \"inventory\": {\"apple\": 10, \"orange\": 1}}, " +
                "{\"name\": \"dm\", \"inventory\": {\"apple\": 10, \"orange\": 1}}]";
        List<Warehouse> warehouses = Warehouse.parseWarehouses(warehousesJson);

        List<Allocation> allocations = allocator.allocate(order, warehouses);
        List<Allocation> expectedAllocations = new ArrayList<>();

        Assert.assertEquals(allocations, expectedAllocations);
    }

    // 3+c+B
    @Test
    public void multipleOrdersMultipleWarehouseEnough1() {
        Order order = new Order("{\"apple\": 10, \"orange\": 10}");
        String warehousesJson = "[{\"name\": \"owd\", \"inventory\": {\"apple\": 10, \"orange\": 10}}, " +
                "{\"name\": \"dm\", \"inventory\": {\"apple\": 10, \"orange\": 10}}]";
        List<Warehouse> warehouses = Warehouse.parseWarehouses(warehousesJson);

        List<Allocation> allocations = allocator.allocate(order, warehouses);
        Allocation allocation1 = new Allocation("owd");
        allocation1.inventory.put("apple", 10);
        allocation1.inventory.put("orange", 10);
        List<Allocation> expectedAllocations = new ArrayList<Allocation>() {{
            add(allocation1);
        }};

        Assert.assertEquals(allocations, expectedAllocations);
    }
    @Test
    public void multipleOrdersMultipleWarehouseEnough2() {
        Order order = new Order("{\"apple\": 10, \"orange\": 10}");
        String warehousesJson = "[{\"name\": \"owd\", \"inventory\": {\"apple\": 5, \"orange\": 5}}, " +
                "{\"name\": \"dm\", \"inventory\": {\"apple\": 5, \"orange\": 5}}]";
        List<Warehouse> warehouses = Warehouse.parseWarehouses(warehousesJson);

        List<Allocation> allocations = allocator.allocate(order, warehouses);
        Allocation allocation1 = new Allocation("owd");
        allocation1.inventory.put("apple", 5);
        allocation1.inventory.put("orange", 5);
        Allocation allocation2 = new Allocation("dm");
        allocation2.inventory.put("apple", 5);
        allocation2.inventory.put("orange", 5);
        List<Allocation> expectedAllocations = new ArrayList<Allocation>() {{
            add(allocation1);
            add(allocation2);
        }};

        Assert.assertEquals(allocations, expectedAllocations);
    }
    @Test
    public void multipleOrdersMultipleWarehouseEnough3() {
        Order order = new Order("{\"apple\": 10, \"orange\": 10}");
        String warehousesJson = "[{\"name\": \"owd\", \"inventory\": {\"apple\": 10}}, " +
                "{\"name\": \"dm\", \"inventory\": {\"orange\": 10}}]";
        List<Warehouse> warehouses = Warehouse.parseWarehouses(warehousesJson);

        List<Allocation> allocations = allocator.allocate(order, warehouses);
        Allocation allocation1 = new Allocation("owd");
        allocation1.inventory.put("apple", 10);
        Allocation allocation2 = new Allocation("dm");
        allocation2.inventory.put("orange", 10);
        List<Allocation> expectedAllocations = new ArrayList<Allocation>() {{
            add(allocation1);
            add(allocation2);
        }};

        Assert.assertEquals(allocations, expectedAllocations);
    }

    // 3+c+C
    @Test
    public void multipleOrdersMultipleWarehouseMore1() {
        Order order = new Order("{\"apple\": 10, \"orange\": 10}");
        String warehousesJson = "[{\"name\": \"owd\", \"inventory\": {\"apple\": 20, \"orange\": 20}}, " +
                "{\"name\": \"dm\", \"inventory\": {\"apple\": 20, \"orange\": 20}}]";
        List<Warehouse> warehouses = Warehouse.parseWarehouses(warehousesJson);

        List<Allocation> allocations = allocator.allocate(order, warehouses);
        Allocation allocation1 = new Allocation("owd");
        allocation1.inventory.put("apple", 10);
        allocation1.inventory.put("orange", 10);
        List<Allocation> expectedAllocations = new ArrayList<Allocation>() {{
            add(allocation1);
        }};

        Assert.assertEquals(allocations, expectedAllocations);
    }
    @Test
    public void multipleOrdersMultipleWarehouseMore2() {
        Order order = new Order("{\"apple\": 10, \"orange\": 10}");
        String warehousesJson = "[{\"name\": \"owd\", \"inventory\": {\"apple\": 1, \"orange\": 1}}, " +
                "{\"name\": \"dm\", \"inventory\": {\"apple\": 20, \"orange\": 20}}]";
        List<Warehouse> warehouses = Warehouse.parseWarehouses(warehousesJson);

        List<Allocation> allocations = allocator.allocate(order, warehouses);
        Allocation allocation1 = new Allocation("owd");
        allocation1.inventory.put("apple", 1);
        allocation1.inventory.put("orange", 1);
        Allocation allocation2 = new Allocation("dm");
        allocation2.inventory.put("apple", 9);
        allocation2.inventory.put("orange", 9);
        List<Allocation> expectedAllocations = new ArrayList<Allocation>() {{
            add(allocation1);
            add(allocation2);
        }};

        Assert.assertEquals(allocations, expectedAllocations);
    }
    @Test
    public void multipleOrdersMultipleWarehouseMore3() {
        Order order = new Order("{\"apple\": 10, \"orange\": 10}");
        String warehousesJson = "[{\"name\": \"owd\", \"inventory\": {\"apple\": 20}}, " +
                "{\"name\": \"dm\", \"inventory\": {\"orange\": 20}}]";
        List<Warehouse> warehouses = Warehouse.parseWarehouses(warehousesJson);

        List<Allocation> allocations = allocator.allocate(order, warehouses);
        Allocation allocation1 = new Allocation("owd");
        allocation1.inventory.put("apple", 10);
        Allocation allocation2 = new Allocation("dm");
        allocation2.inventory.put("orange", 10);
        List<Allocation> expectedAllocations = new ArrayList<Allocation>() {{
            add(allocation1);
            add(allocation2);
        }};

        Assert.assertEquals(allocations, expectedAllocations);
    }


}
