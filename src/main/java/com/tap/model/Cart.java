package com.tap.model;

import java.util.HashMap;
import java.util.Map;

public class Cart {

    // Map storing itemId as Key and CartItem object as Value
    private Map<Integer, CartItem> items;

    public Cart() {
        this.items = new HashMap<>();
    }

    /**
     * Add item to cart. If item already exists, increase its quantity.
     */
    public void addItem(CartItem item) {
        int itemId = item.getItemId();
        
        if (items.containsKey(itemId)) {
            CartItem existingItem = items.get(itemId);
            existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
        } else {
            items.put(itemId, item);
        }
    }

    /**
     * Update quantity for a specific item. If quantity is 0 or less, remove it.
     */
    public void updateItem(int itemId, int quantity) {
        if (items.containsKey(itemId)) {
            if (quantity <= 0) {
                items.remove(itemId);
            } else {
                items.get(itemId).setQuantity(quantity);
            }
        }
    }

    /**
     * Remove an item completely from the cart.
     */
    public void removeItem(int itemId) {
        items.remove(itemId);
    }

    /**
     * Clear all items from the cart.
     */
    public void clear() {
        items.clear();
    }

    /**
     * Get all items currently in the cart.
     */
    public Map<Integer, CartItem> getItems() {
        return items;
    }

    /**
     * Calculate grand total price of all items in cart.
     */
    public double getTotalAmount() {
        double total = 0.0; 
        for (CartItem item : items.values()) {
            total += item.getTotalPrice();
        }
        return total;
    }

    /**
     * Get total number of distinct items or total quantities.
     */
    public int getTotalQuantity() {
        int count = 0;
        for (CartItem item : items.values()) {
            count += item.getQuantity();
        }
        return count;
    }
}