package shapez.content.block.crafter;

import mindustry.gen.Building;
import shapez.content.item.quad.QuadItem;
import shapez.content.item.ShapeItem;

public class ShapeRotator extends ShapeCrafter {
    public ShapeRotator(String name) {
        super(name);
        width = 1;
        height = 1;
    }

    public class ShapeRotatorBuild extends ShapeCrafterBuild {
        QuadItem input = null;
        QuadItem output = null;

        @Override
        public boolean acceptShape(ShapeBuild source, ShapeItem item) {
            if (source == atSide(2, 0) && item instanceof QuadItem) return input == null;
            return false;
        }

        @Override
        public void handleShape(ShapeBuild source, ShapeItem item) {
            if (source == atSide(2, 0) && item instanceof QuadItem) input = (QuadItem) item;
        }

        @Override
        public boolean shouldRun() {
            return input != null && output == null;
        }

        @Override
        public void output() {
            if (output == null) return;
            Building a = atSide(0, 0);
            if (a instanceof ShapeBuild && ((ShapeBuild) a).acceptShape(this, output)) {
                ((ShapeBuild) a).handleShape(this, output);
                output = null;
            }
        }

        @Override
        public void craft() {
            output = input.rotate(true);
            input = null;
        }
    }
}