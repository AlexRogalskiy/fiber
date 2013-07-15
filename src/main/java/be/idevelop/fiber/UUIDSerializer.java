package be.idevelop.fiber;

import java.nio.charset.Charset;
import java.util.UUID;

final class UUIDSerializer extends be.idevelop.fiber.Serializer<UUID> {

    private static final Charset UTF_8 = Charset.forName("UTF-8");

    UUIDSerializer() {
        super(UUID.class);
    }

    @Override
    public UUID read(Input input) {
        int length = input.readInteger();
        byte[] bytes = input.readBytes(length);
        UUID uuid = UUID.fromString(new String(bytes, UTF_8));
        addReferenceForDeserialization(uuid);
        return uuid;
    }

    @Override
    public void write(UUID uuid, Output output) {
        addReferenceForSerialization(uuid);
        byte[] bytes = uuid.toString().getBytes(UTF_8);
        output.writeInt(bytes.length);
        output.writeBytes(bytes);
    }

    @Override
    public boolean isImmutable() {
        return true;
    }
}
