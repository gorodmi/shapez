package shapez.content.item;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.graphics.gl.FrameBuffer;
import mindustry.type.Item;

public class MindustryItem extends Item {
    public ShapeItem item;

    public MindustryItem(ShapeItem item) {
        super(item.toString() + "-item");
        this.item = item;
    }

    @Override
    public void loadIcon() {
        super.loadIcon();
        FrameBuffer buffer = new FrameBuffer(32, 32);
        buffer.begin(Color.clear);
        Draw.proj(0, 0, 32, -32);
        item.drawRaw(16, -16, 30);
        buffer.end();
        fullIcon = uiIcon = new TextureRegion(buffer.getTexture());
    }
}
