package com.uoj.equipment.dto;

import com.uoj.equipment.enums.ItemType;

public class EquipmentPublicDTO {
    private Long id;
    private String name;
    private String category;
    private ItemType itemType;
    private int totalQty;
    private int availableQty;
    private boolean active;
    private Long labId;

    public EquipmentPublicDTO() {}

    public EquipmentPublicDTO(Long id, String name, String category, ItemType itemType,
                              int totalQty, int availableQty, boolean active, Long labId) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.itemType = itemType;
        this.totalQty = totalQty;
        this.availableQty = availableQty;
        this.active = active;
        this.labId = labId;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public ItemType getItemType() { return itemType; }
    public int getTotalQty() { return totalQty; }
    public int getAvailableQty() { return availableQty; }
    public boolean isActive() { return active; }
    public Long getLabId() { return labId; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCategory(String category) { this.category = category; }
    public void setItemType(ItemType itemType) { this.itemType = itemType; }
    public void setTotalQty(int totalQty) { this.totalQty = totalQty; }
    public void setAvailableQty(int availableQty) { this.availableQty = availableQty; }
    public void setActive(boolean active) { this.active = active; }
    public void setLabId(Long labId) { this.labId = labId; }
}
