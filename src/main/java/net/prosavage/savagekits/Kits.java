package net.prosavage.savagekits;

import net.prosavage.savagekits.gson.templates.Kit;
import net.prosavage.savagekits.gson.templates.KitItem;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class Kits {
    private static transient Kits i = new Kits();

    public static HashMap<String, Kit> kits = new HashMap<>();

    static {
        KitItem kitItem = new KitItem(Material.AIR, "&cExample Item", Arrays.asList("&cLine one", "&cKit: {kit}"), true);
        Kit kit = new Kit(Collections.singletonList(kitItem));
        kits.put("Example", kit);
    }




    public static void load() {
        ChestKits.getInstance().getPersist().loadOrSaveDefault(i, Kits.class, "kits");
    }

    public static void save() {
        ChestKits.getInstance().getPersist().save(i, "kits");
    }

}
