package com.uoj.equipment.dto;

import java.util.List;

public class PurchaseCreateDTO {

    private String title;
    private String justification;
    private List<ItemLine> items;

    public PurchaseCreateDTO() {}

    public static class ItemLine {
        private String itemName;
        private String category;
        private int quantity;
        private Double estimatedUnitCost;

        public ItemLine() {}

        public String getItemName() { return itemName; }
        public String getCategory() { return category; }
        public int getQuantity() { return quantity; }
        public Double getEstimatedUnitCost() { return estimatedUnitCost; }

        public void setItemName(String itemName) { this.itemName = itemName; }
        public void setCategory(String category) { this.category = category; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
        public void setEstimatedUnitCost(Double estimatedUnitCost) { this.estimatedUnitCost = estimatedUnitCost; }
    }

    public String getTitle() { return title; }
    public String getJustification() { return justification; }
    public List<ItemLine> getItems() { return items; }

    public void setTitle(String title) { this.title = title; }
    public void setJustification(String justification) { this.justification = justification; }
    public void setItems(List<ItemLine> items) { this.items = items; }
}
