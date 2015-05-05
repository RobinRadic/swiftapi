package nl.radic.swiftapi;

import nl.radic.swiftapi.thrift.ItemStack;
import nl.radic.swiftapi.thrift.Location;
import nl.radic.swiftapi.thrift.Player;
import org.spongepowered.api.data.properties.EquipmentProperty;
import org.spongepowered.api.data.properties.FoodRestorationProperty;

/**
 * Created by radic on 5/5/15.
 */
public class SpongeTransformer {
    public static Player convertPlayer(org.spongepowered.api.entity.player.Player spongePlayer){
        Player player = new Player();
        String hostname = spongePlayer.getConnection().getAddress().getHostName();
        int ping = spongePlayer.getConnection().getPing();
        String displayName = spongePlayer.getDisplayNameData().getDisplayName().toString();
       // int gameModeId = spongePlayer.getGameModeData().getGameMode().getId();
        //spongePlayer.
        player.setName(spongePlayer.getName());
        player.setLocation(convertLocation(spongePlayer.getLocation()));
        return player;
    }
    public static Location convertLocation(org.spongepowered.api.world.Location spongeLocation){
        Location loc = new Location();
        loc.setX(spongeLocation.getX());
        loc.setY(spongeLocation.getY());
        loc.setZ(spongeLocation.getZ());
        return loc;
    }

    public static ItemStack convertItemStack(org.spongepowered.api.item.inventory.ItemStack spongeStack)
    {
        ItemStack stack = new ItemStack();
        stack.setAmount(spongeStack.getQuantity());
        String resto = spongeStack.getItem().getDefaultProperty(FoodRestorationProperty.class).get().toString();
        return stack;
    }
}
