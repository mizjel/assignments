package minecraft.teamocto;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerInteractEvent;
//import minecraft.squidsquad;

public class DefenseCreationEvent extends Event implements Cancellable, DefenseCreationEventInterface {

	private boolean isCancelled;
	private Location location;
	private Material toolMaterial;
	private PlayerInteractEvent initiationEvent;
	private static final HandlerList HANDLERS = new HandlerList();

	public DefenseCreationEvent(PlayerInteractEvent event) {
		initiationEvent = event;
		toolMaterial = event.getPlayer().getInventory().getItemInMainHand().getType();
		location = event.getClickedBlock().getRelative(BlockFace.UP).getLocation();
		isCancelled = false;
	}

	public DefenseCreationEvent(boolean isAsync) {
		super(isAsync);
	}

    @Override
	public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
	public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
        return HANDLERS;
    }

	@Override
	public Material getMaterialOfTool() {
		/**
		 * Material.WOODEN_PICKAXE corresponds to a wall
		 * Material.GOLDEN_PICKAXE corresponds to a shield
		 */
		return toolMaterial;
	}

	@Override
	public Location getLocation() {
		return location;
	}

	@Override
	public String getMessage() {
		return "Building a defense-object!";
	}
}
