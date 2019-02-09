package net.prosavage.vatrixcore.storage.typeadapters

import InventoryUtil
import com.google.gson.*
import org.bukkit.inventory.Inventory
import java.lang.reflect.Type

class InventoryTypeAdapter : JsonSerializer<Inventory>, JsonDeserializer<Inventory> {


    override fun serialize(inventory: Inventory, type: Type, jsonSerializationContext: JsonSerializationContext): JsonElement {

        val `object` = JsonObject()
        `object`.add("contents", JsonPrimitive(InventoryUtil.toBase64(inventory)))
        return `object`
    }


    override fun deserialize(jsonElement: JsonElement, type: Type, jsonDeserializationContext: JsonDeserializationContext): Inventory {
        val `object` = jsonElement.asJsonObject
        return InventoryUtil.fromBase64(`object`.get("contents").asString)
    }


}