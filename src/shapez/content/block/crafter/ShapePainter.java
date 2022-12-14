package shapez.content.block.crafter;

import mindustry.gen.Building;
import shapez.content.block.ShapeBlock;
import shapez.content.item.ColorItem;
import shapez.content.item.ShapeItem;
import shapez.content.item.quad.QuadItem;

public class ShapePainter extends ShapeCrafter {
    public ShapePainter(String name) {
        super(name);
        width = 2;
        height = 1;
    }

    public class ShapePainterBuild extends ShapeCrafterBuild {
        QuadItem input = null;
        ColorItem color = null;
        QuadItem output = null;

        @Override
        public boolean acceptShape(ShapeBlock.ShapeBuild source, ShapeItem item, int side, int i) {
            if (side == 2 && i == 0 && item instanceof QuadItem) return input == null;
            if (side == 1 && i == 0 && item instanceof ColorItem) return color == null;
            return false;
        }

        @Override
        public void handleShape(ShapeBlock.ShapeBuild source, ShapeItem item, int side, int i) {
            if (side == 2 && i == 0 && item instanceof QuadItem) input = (QuadItem) item;
            if (side == 1 && i == 0 && item instanceof ColorItem) color = (ColorItem) item;
        }

        @Override
        public boolean shouldRun() {
            return input != null && color != null && output == null;
        }

        @Override
        public void output() {
            if (output == null) return;
            if (outputShape(output, 0, 0)) output = null;
        }

        @Override
        public void craft() {
            output = input.paint(color);
            color = null;
            input = null;
        }

        @Override
        public boolean isOutput(ShapeBuild source) {
            return source == atSide(0, 0);
        }
    }
}
