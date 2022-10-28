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
        public boolean acceptShape(ShapeBlock.ShapeBuild source, ShapeItem item) {
            if (source == atSide(2, 0) && item instanceof QuadItem) return input == null;
            if (source == atSide(1, 0) && item instanceof ColorItem) return color == null;
            return false;
        }

        @Override
        public void handleShape(ShapeBlock.ShapeBuild source, ShapeItem item) {
            if (source == atSide(2, 0) && item instanceof QuadItem) input = (QuadItem) item;
            if (source == atSide(1, 0) && item instanceof ColorItem) color = (ColorItem) item;
        }

        @Override
        public boolean shouldRun() {
            return input != null && color != null && output == null;
        }

        @Override
        public void output() {
            if (output == null) return;
            Building a = atSide(0, 0);
            if (a instanceof ShapeBlock.ShapeBuild && ((ShapeBlock.ShapeBuild) a).acceptShape(this, output)) {
                ((ShapeBlock.ShapeBuild) a).handleShape(this, output);
                output = null;
            }
        }

        @Override
        public void craft() {
            output = input.paint(color);
            color = null;
            input = null;
        }
    }
}
