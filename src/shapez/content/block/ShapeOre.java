package shapez.content.block;

import arc.graphics.g2d.Draw;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.OverlayFloor;
import shapez.content.item.ColorItem;
import shapez.content.item.ShapeItem;
import shapez.content.item.quad.QuadItem;

public class ShapeOre extends OverlayFloor {
    public ShapeItem item;

    public ShapeOre(ShapeItem item) {
        super(item.toString() + "-ore");
        this.item = item;
    }

    @Override
    public void drawBase(Tile tile) {
        if (item instanceof QuadItem) Draw.color(((QuadItem) item).layers.get(0).quadrants.get(0).color.color);
        else if (item instanceof ColorItem) Draw.color(((ColorItem) item).color.color);
        Draw.rect("ore-copper1", tile.drawx(), tile.drawy());
        Draw.color();
//        item.draw(tile.drawx(), tile.drawy(), 16f);
    }
}
