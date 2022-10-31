package shapez.content.block;

import arc.graphics.Color;
import arc.graphics.g2d.Font;
import arc.graphics.g2d.GlyphLayout;
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
        public boolean acceptShape(ShapeBuild source, ShapeItem item) {
            if (source == atSide(2, 0) || source == atSide(2, 1))
                return balance == null || (balance.equals(item) && amount < shapeCapacity);
            return false;
        }

        @Override
        public void handleShape(ShapeBuild source, ShapeItem item) {
            if (balance == null) {
                balance = item;
                amount = 1;
            } else amount++;
        }

        @Override
        public void updateTile() {
            if (balance == null) return;
            Building a = atSide(0, 0);
            Building b = atSide(0, 1);
            Seq<Building> buildings = Seq.with(a, b).select(build -> build instanceof ShapeBuild && ((ShapeBuild) build).acceptShape(this, balance));
            if (buildings.size == 0) return;
            side %= buildings.size;
            Building next = buildings.get(side);
            ((ShapeBuild) next).handleShape(this, balance);
            amount--;
            if (amount <= 0) balance = null;
            side = (side + 1) % buildings.size;
        }

        @Override
        public void draw() {
            super.draw();
            if (balance == null) return;
            balance.draw(centerX(), centerY(), Math.min(width, height) * Vars.tilesize / 2f);
            Font font = Fonts.outline;
            font.draw(String.valueOf(amount), centerX() + Vars.tilesize / 1.5f, centerY() - Vars.tilesize / 3f, Color.white, Math.min(width, height) / 1.5f / Vars.tilesize, false, 0);
        }
    }
}
