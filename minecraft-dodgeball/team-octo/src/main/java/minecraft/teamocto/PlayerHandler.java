package minecraft.teamocto;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.enchantments.Enchantment;

class PlayerHandler implements Listener {
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();

    giveInitialItems(player);
  }

  @EventHandler
  public void onPlayerRespawn(PlayerRespawnEvent event) {
      Player player = event.getPlayer();
      giveInitialItems(player);
  }

  public void giveInitialItems(Player player){
      // for wall
      ItemStack wooden = new ItemStack(Material.WOODEN_PICKAXE, 1);
      makeUnbreakable(wooden);
      // for shield
      ItemStack golden = new ItemStack(Material.GOLDEN_PICKAXE, 1);
      makeUnbreakable(golden);
      // for AA
      ItemStack diamond = new ItemStack(Material.DIAMOND_PICKAXE, 1);
      makeUnbreakable(diamond);
      // to DESTROY!
      ItemStack bow = new ItemStack(Material.BOW, 1);
      makeUnbreakable(bow);
      addEnchantmentToBow(bow);

      player.getInventory().addItem(wooden, golden, diamond, bow);
  }

  public void makeUnbreakable(ItemStack item) {
      ItemMeta item_meta = item.getItemMeta();
      item_meta.setUnbreakable(true);
      item.setItemMeta(item_meta);
  }

  public void addEnchantmentToBow(ItemStack bow) {
      ItemMeta bow_meta = bow.getItemMeta(); //
      bow_meta.addEnchant(Enchantment.ARROW_DAMAGE, 5, true);
      bow.setItemMeta(bow_meta);
  }
}
