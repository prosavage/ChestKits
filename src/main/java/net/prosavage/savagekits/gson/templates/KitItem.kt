package net.prosavage.savagekits.gson.templates

import org.bukkit.Material
import java.util.*

class KitItem(var type: Material, var name: String, var lore: List<String>, var glowing: Boolean) {

    init {
        type = Material.APPLE
        name = "&cExample Apple"
        lore = Arrays.asList("&cExample Lore line 1",
                "&cKit: {kit}")
        glowing = true
    }







}

