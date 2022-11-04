package shapez.content.block.crafter;

import arc.struct.Seq;
import mindustry.gen.Building;
import shapez.content.item.quad.QuadItem;
import shapez.content.item.ShapeItem;

public class ShapeSplitter extends ShapeCrafter {
    public ShapeSplitter(String name) {
        super(name);
        width = 1;
        height = 2;
        useRotationRegions = true;
    }

    public class ShapeSplitterBuild extends ShapeCrafterBuild {
        QuadItem input = null;
        QuadItem[] output = new QuadItem[2];

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
            return input != null && output[0] == null && output[1] == null;
        }

        @Override
        public void output() {
            if (output[0] != null && outputShape(output[0], 0, 0)) output[0] = null;
            if (output[1] != null && outputShape(output[1], 0, 1)) output[1] = null;
        }

        @Override
        public void craft() {
            Seq<QuadItem> shapes = input.split();
            output[0] = shapes.get(0);
            output[1] = shapes.get(1);
            input = null;
        }

        @Override
        public boolean isOutput(ShapeBuild source) {
            return source == atSide(0, 0) || source == atSide(0, 1);
        }
    }
}
