package dev.snowz.f3f4;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityStatus;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class F3F4 extends JavaPlugin implements Listener {

    @Override
    public void onLoad() {
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        PacketEvents.getAPI().load();

        PacketEvents.getAPI().getEventManager().registerListener(
            new PEListener(),
            PacketListenerPriority.NORMAL
        );
    }

    @Override
    public void onEnable() {
        PacketEvents.getAPI().init();

        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        PacketEvents.getAPI().terminate();
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        getServer().getScheduler().runTaskLater(
            this, () -> {
                if (player.isOnline()) {
                    final WrapperPlayServerEntityStatus packet = new WrapperPlayServerEntityStatus(player.getEntityId(), 4 + 24);
                    PacketEvents.getAPI().getPlayerManager().sendPacket(player, packet);
                }
            }, 10L
        );
    }
}
