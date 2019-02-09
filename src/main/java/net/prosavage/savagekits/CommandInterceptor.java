package net.prosavage.savagekits;

import com.earth2me.essentials.Kit;
import com.earth2me.essentials.Kits;
import com.earth2me.essentials.User;
import net.prosavage.savagekits.Util.EssentialsManager;
import net.prosavage.savagekits.Util.StringUtil;
import net.prosavage.savagekits.Util.itemnbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class CommandInterceptor implements Listener {

    private ChestKits chestKits = ChestKits.getInstance();

    @EventHandler
    public void onKitCommand(PlayerCommandPreprocessEvent event) throws Exception {
        // Check if its a kit command.
        if (!ChestKits.getInstance().getConfig().getBoolean("intercept-essentials-kits") || !event.getMessage().toLowerCase().startsWith("/kit")) {
            return;
        }

        String command = event.getMessage().toLowerCase();

        // Check if there are more arguments in case an admin is setting kits.
        if (command.split(" ").length != 2) {
            return;
        }
        String kitName = command.split(" ")[1];
        String formattedkitName = StringUtil.captializeFirstLetter(kitName);
        Kits kits = EssentialsManager.getEssentialsInstance().getKits();


        // Check if the kit exists.
        if (kits.getKit(kitName) == null) {
            return;
        }

        Player player = event.getPlayer();

        // Check if the player has permission to use the kit.
        if (!player.hasPermission("essentials.kits." + kitName)) {
            return;
        }


        // Check the kit delay.
        User user = EssentialsManager.getEssentialsInstance().getUser(player.getUniqueId());
        Map<String, Object> kitMap = kits.getKit(kitName);
        Kit kit = new Kit(kitName, EssentialsManager.getEssentialsInstance());
        if (EssentialsManager.getNextUse(user, kitName, kitMap) != 0) {
            return;
        }


        // Cancel event and give item.
        event.setCancelled(true);


        // Set kit delay.

        kit.setTime(user);
        // Create chest item
        ItemStack chest = new ItemStack(Material.CHEST);
        ItemMeta chestMeta = chest.getItemMeta();
        chestMeta.setDisplayName(StringUtil.color(chestKits.getConfig().getString("item.name").replace("{kit}", formattedkitName)));
        chestMeta.setLore(StringUtil.color(StringUtil.replaceInList("{kit}", kitName, chestKits.getConfig().getStringList("item.lore"))));
        chest.setItemMeta(chestMeta);
        NBTItem chestNbt = new NBTItem(chest);
        chestNbt.setBoolean("ChestKit", true);
        chestNbt.setString("KitName", kitName);
        chest = chestNbt.getItem();

        // Send message that they redeemed a kit.
        player.sendMessage(StringUtil.color(chestKits.getConfig().getString("messages.receive").replace("{kit}", formattedkitName)));


        Inventory inventory = player.getInventory();
        if (inventory.firstEmpty() != -1) {
            inventory.addItem(chest);
        } else {
            player.getLocation().getWorld().dropItemNaturally(player.getLocation(), chest);
            player.sendMessage(StringUtil.color(chestKits.getConfig().getString("messages.on-ground").replace("{kit}", formattedkitName)));
        }

    }


}
