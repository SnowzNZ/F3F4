package dev.snowz.f3f4;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityStatus;
import org.bukkit.entity.Player;

public final class PEListener implements PacketListener {

    @Override
    public void onPacketSend(final PacketSendEvent event) {
        if (event.getPacketType() != PacketType.Play.Server.ENTITY_STATUS) return;

        final Player player = event.getPlayer();
        final WrapperPlayServerEntityStatus packet = new WrapperPlayServerEntityStatus(event);

        if (packet.getEntityId() != player.getEntityId()) return;

        if (player.hasPermission("minecraft.command.gamemode")) {
            packet.setStatus(4 + 24);
        }
    }
}
