package shapez.content.block.crafter;

import mindustry.gen.Building;
import shapez.content.item.quad.QuadItem;
import shapez.content.item.ShapeItem;

public class ShapeRotator extends ShapeCrafter {
    public ShapeRotator(String name) {
        super(name);
        width = 1;
        height = 1;
        useRotationRegions = true;
    }

    public class ShapeRotatorBuild extends ShapeCrafterBuild {
        QuadItem input = null;
        QuadItem output = null;

        @Override
        public boolean acceptShape(ShapeBuild source, ShapeItem item, int side, int i) {
            if (side == 2 && i == 0 && item instanceof QuadItem) return input == null;
            return false;
        }

        @Override
        public void handleShape(ShapeBuild source, ShapeItem item, int side, int i) {
            if (side == 2 && i == 0 && item instanceof QuadItem) input = (QuadItem) item;
        }

        @Override
        public boolean shouldRun() {
            return input != null && output == null;
        }

        @Override
        public void output() {
            if (output == null) return;
            if (outputShape(output, 0, 0)) output = null;
        }

        @Override
        public void craft() {
            output = input.rotate(true);
            input = null;
        }

        @Override
        public boolean isOutput(ShapeBuild source) {
            return source == atSide(0, 0);
        }
    }
}
