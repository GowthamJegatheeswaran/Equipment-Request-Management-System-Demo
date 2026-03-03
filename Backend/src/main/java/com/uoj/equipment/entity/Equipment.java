package com.uoj.equipment.entity;

import com.uoj.equipment.enums.ItemType;
import jakarta.persistence.*;

@Entity
@Table(name = "equipment")
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 120)
    private String category;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_type", nullable = false, length = 30)
    private ItemType itemType = ItemType.RETURNABLE;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "lab_id")
    private Lab lab;

    @Column(name = "total_qty", nullable = false)
    private int totalQty;

    @Column(name = "available_qty", nullable = false)
    private int availableQty;

    @Column(nullable = false)
    private boolean active = true;

    public Equipment() {}

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public ItemType getItemType() { return itemType; }
    public Lab getLab() { return lab; }
    public int getTotalQty() { return totalQty; }
    public int getAvailableQty() { return availableQty; }
    public boolean isActive() { return active; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCategory(String category) { this.category = category; }
    public void setItemType(ItemType itemType) { this.itemType = itemType; }
    public void setLab(Lab lab) { this.lab = lab; }
    public void setTotalQty(int totalQty) { this.totalQty = totalQty; }
    public void setAvailableQty(int availableQty) { this.availableQty = availableQty; }
    public void setActive(boolean active) { this.active = active; }
}
