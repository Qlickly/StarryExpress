package org.aussiebox.starexpress.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.aussiebox.starexpress.StarryExpress;
import org.jetbrains.annotations.NotNull;

public record OpenConfigS2CPacket() implements CustomPacketPayload {
    public static final ResourceLocation OPEN_CONFIG_PAYLOAD_ID = StarryExpress.id("open_config");
    public static final CustomPacketPayload.Type<OpenConfigS2CPacket> TYPE = new CustomPacketPayload.Type<>(OPEN_CONFIG_PAYLOAD_ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, OpenConfigS2CPacket> CODEC = StreamCodec.of(
            OpenConfigS2CPacket::write,
            OpenConfigS2CPacket::read
    );

    public OpenConfigS2CPacket() {
    }

    public CustomPacketPayload.@NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void write(FriendlyByteBuf buf, OpenConfigS2CPacket packet) {

    }

    public static OpenConfigS2CPacket read(FriendlyByteBuf buf) {
        return new OpenConfigS2CPacket();
    }
}
