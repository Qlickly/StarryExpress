package org.aussiebox.starexpress.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.aussiebox.starexpress.StarryExpress;
import org.jetbrains.annotations.NotNull;

public record ChangeConfigOptionC2SPacket(String option, Boolean value) implements CustomPacketPayload {
    public static final ResourceLocation ABILITY_PAYLOAD_ID = StarryExpress.id("change_config_option");
    public static final Type<ChangeConfigOptionC2SPacket> TYPE = new Type<>(ABILITY_PAYLOAD_ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, ChangeConfigOptionC2SPacket> CODEC = StreamCodec.of(
            ChangeConfigOptionC2SPacket::write,
            ChangeConfigOptionC2SPacket::read
    );

    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void write(FriendlyByteBuf buf, ChangeConfigOptionC2SPacket packet) {
        buf.writeUtf(packet.option);
        buf.writeBoolean(packet.value);
    }

    public static ChangeConfigOptionC2SPacket read(FriendlyByteBuf buf) {
        String option = buf.readUtf();
        Boolean value = buf.readBoolean();
        return new ChangeConfigOptionC2SPacket(option, value);
    }
}
