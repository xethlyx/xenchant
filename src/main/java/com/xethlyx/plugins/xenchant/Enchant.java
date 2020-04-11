package com.xethlyx.plugins.xenchant;

import org.bukkit.Material;
import org.bukkit.event.Listener;

import java.util.List;

public class Enchant<T extends Listener> {
    public String Name = "Unnamed Enchant";
    public String Description = "No description";

    public int MaxLevel = 0;
    public List<Material> AllowedItems;

    public T EnchantListener = null;
}
