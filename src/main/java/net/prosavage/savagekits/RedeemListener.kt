package net.prosavage.savagekits

import com.earth2me.essentials.Kit
import com.earth2me.essentials.User
import net.prosavage.savagekits.Util.EssentialsManager
import net.prosavage.savagekits.Util.StringUtil
import net.prosavage.savagekits.Util.itemnbtapi.NBTItem
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent


class RedeemListener : Listener {

    @EventHandler
    @Throws(Exception::class)
    fun redeemKit(event: PlayerInteractEvent) {

        if (event.action != Action.RIGHT_CLICK_BLOCK && event.action != Action.RIGHT_CLICK_AIR) {
            return
        }


        // Check if the material is a chest.
        if (event.player.itemInHand.type != Material.CHEST) {
            return
        }
        val player = event.player
        val itemInHand = player.inventory.itemInHand


        val chestNBT = NBTItem(itemInHand)

        if (!chestNBT.hasKey("ChestKit")) {
            return
        }


        event.isCancelled = true

        val kit = Kit(chestNBT.getString("KitName"), EssentialsManager.getEssentialsInstance())

        if (itemInHand.amount == 1) {
            itemInHand.type = Material.AIR
        } else {
            itemInHand.amount = itemInHand.amount - 1
        }

        kit.expandItems(User(player, EssentialsManager.getEssentialsInstance()))


        player.itemInHand = itemInHand
        player.sendMessage(StringUtil.color(ChestKits.getInstance().config.getString("messages.open").replace("{kit}",
                StringUtil.captializeFirstLetter(kit.name))))


    }

}
