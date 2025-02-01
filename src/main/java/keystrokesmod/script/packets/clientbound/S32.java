package keystrokesmod.script.packets.clientbound;

import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.network.PacketBuffer;
import java.io.IOException;
import java.lang.reflect.Field;

public class S32 extends SPacket {
    public int windowId;
    public short actionNumber;
    public boolean accepted;

    public S32(S32PacketConfirmTransaction packet) {
        super(packet);
        this.windowId = packet.getWindowId();
        this.actionNumber = packet.getActionNumber();
        this.accepted = getAcceptedValue(packet);
    }

    public S32(int windowId, short actionNumber, boolean accepted) {
        super(new S32PacketConfirmTransaction(windowId, actionNumber, accepted));
        this.windowId = windowId;
        this.actionNumber = actionNumber;
        this.accepted = accepted;
    }

    public void readPacketData(PacketBuffer buf) throws IOException {
        this.windowId = buf.readUnsignedByte();
        this.actionNumber = buf.readShort();
        this.accepted = buf.readBoolean();
    }

    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeByte(this.windowId);
        buf.writeShort(this.actionNumber);
        buf.writeBoolean(this.accepted);
    }

    private boolean getAcceptedValue(S32PacketConfirmTransaction packet) {
        try {
            Field field = S32PacketConfirmTransaction.class.getDeclaredField("field_149537_c");
            field.setAccessible(true);
            return field.getBoolean(packet);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }
}