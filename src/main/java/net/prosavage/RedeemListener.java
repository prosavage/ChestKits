package net.prosavage;

import com.earth2me.essentials.Kit;
import com.earth2me.essentials.User;


import net.prosavage.Util.EssentialsManager;
import net.prosavage.Util.StringUtil;
import net.prosavage.Util.itemnbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;



public class RedeemListener implements Listener {

    @EventHandler
    public void redeemKit(PlayerInteractEvent event) throws Exception {

        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && !event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            return;
        }


        // Check if the material is a chest.
        if (!event.getPlayer().getItemInHand().getType().equals(Material.CHEST)) {
            return;
        }
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInHand();


        NBTItem chestNBT = new NBTItem(itemInHand);

        if (!chestNBT.hasKey("ChestKit")) {
            return;
        }


        event.setCancelled(true);

        Kit kit = new Kit(chestNBT.getString("KitName"), EssentialsManager.getEssentialsInstance());

        if (itemInHand.getAmount() == 1) {
            itemInHand.setType(Material.AIR);
        } else {
            itemInHand.setAmount(itemInHand.getAmount() - 1);
        }

        kit.expandItems(new User(player, EssentialsManager.getEssentialsInstance()));

        


        player.setItemInHand(itemInHand);
        player.sendMessage(StringUtil.color(ChestKits.getInstance().getConfig().getString("messages.open").replace("{kit}",
                StringUtil.captializeFirstLetter(kit.getName()))));


    }

}
