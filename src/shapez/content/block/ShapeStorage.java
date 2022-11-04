package shapez.content.block;

import arc.graphics.Color;
import arc.graphics.g2d.Font;
import arc.graphics.g2d.GlyphLayout;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.pooling.Pools;
import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.ui.Fonts;
import shapez.content.item.ShapeItem;

public class ShapeStorage extends ShapeBalancer {
    public final int shapeCapacity = 5000;

    public ShapeStorage(String name) {
        super(name);
        width = 2;
        height = 2;
    }

    public class ShapeStorageBuild extends ShapeBalancerBuild {
        int amount = 0;

        @Override
        public boolean acceptShape(ShapeBuild source, ShapeItem item, int side, int i) {
            if (side == 2)
                return balance == null || (balance.equals(item) && amount < shapeCapacity);
            return false;
        }

        @Override
        public void handleShape(ShapeBuild source, ShapeItem item, int side, int i) {
            if (balance == null) {
                balance = item;
                amount = 1;
            } else amount++;
        }

        @Override
        public void remove() {
            amount--;
            if (amount <= 0) balance = null;
        }

        @Override
        public void draw() {
            super.draw();
            if (balance == null) return;
            Vec2 center = center();
            balance.draw(center.x, center.y, Math.min(width, height) * Vars.tilesize / 2f);
            Font font = Fonts.outline;
            font.draw(String.valueOf(amount), center.x + Vars.tilesize / 1.5f, center.y - Vars.tilesize / 3f, Color.white, Math.min(width, height) / 1.5f / Vars.tilesize, false, 0);
        }

        @Override
        public boolean isOutput(ShapeBuild source) {
            return source == atSide(0, 0) || source == atSide(0, 1);
        }
    }
}
