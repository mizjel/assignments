package minecraft.teamocto;

import org.bukkit.Location;
import org.bukkit.Material;

public interface DefenseCreationEventInterface {

	boolean isCancelled();

	void setCancelled(boolean isCancelled);

	Material getMaterialOfTool();

	Location getLocation();

	String getMessage();

}