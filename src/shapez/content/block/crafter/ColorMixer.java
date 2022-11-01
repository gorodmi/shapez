package shapez.content.block.crafter;

import arc.util.Log;
import mindustry.gen.Building;
import shapez.content.item.ColorItem;
import shapez.content.item.ShapeItem;

public class ColorMixer extends ShapeCrafter {
    public ColorMixer(String name) {
        super(name);
        width = 1;
        height = 2;
    }

    public class ShapePainterBuild extends ShapeCrafterBuild {
        ColorItem[] input = new ColorItem[2];
        ColorItem output = null;

        @Override
        public boolean acceptShape(ShapeBuild source, ShapeItem item) {
            if (source == atSide(2, 0) && item instanceof ColorItem) return input[0] == null;
            if (source == atSide(2, 1) && item instanceof ColorItem) return input[1] == null;
            return false;
        }

        @Override
        public void handleShape(ShapeBuild source, ShapeItem item) {
            if (source == atSide(2, 0) && item instanceof ColorItem) input[0] = (ColorItem) item;
            if (source == atSide(2, 1) && item instanceof ColorItem) input[1] = (ColorItem) item;
        }

        @Override
        public boolean shouldRun() {
            return input[0] != null && input[1] != null && output == null;
        }

        @Override
        public void output() {
            if (output == null) return;
            Building a = atSide(0, 1);
            if (a instanceof ShapeBuild && ((ShapeBuild) a).acceptShape(this, output)) {
                ((ShapeBuild) a).handleShape(this, output);
                output = null;
            }
        }

        @Override
        public boolean isOutput(ShapeBuild source) {
            return source == atSide(0, 1);
        }

        @Override
        public void craft() {
            output = input[0].mix(input[1]);
            input[0] = input[1] = null;
        }
    }
}
