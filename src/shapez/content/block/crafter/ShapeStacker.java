package shapez.content.block.crafter;

import arc.util.Log;
import mindustry.gen.Building;
import shapez.content.item.ShapeItem;
import shapez.content.item.quad.QuadItem;

public class ShapeStacker extends ShapeCrafter {
    public ShapeStacker(String name) {
        super(name);
        width = 1;
        height = 2;
    }

    public class ShapeStackerBuild extends ShapeCrafterBuild {
        QuadItem[] input = new QuadItem[2];
        QuadItem output = null;

        @Override
        public boolean acceptShape(ShapeBuild source, ShapeItem item, int side, int i) {
            if (side == 2 && i == 0 && item instanceof QuadItem) return input[0] == null;
            if (side == 2 && i == 1 && item instanceof QuadItem) return input[1] == null;
            return false;
        }

        @Override
        public void handleShape(ShapeBuild source, ShapeItem item, int side, int i) {
            if (side == 2 && i == 0 && item instanceof QuadItem) input[0] = (QuadItem) item;
            if (side == 2 && i == 1 && item instanceof QuadItem) input[1] = (QuadItem) item;
        }

        @Override
        public boolean shouldRun() {
            return input[0] != null && input[1] != null && output == null;
        }

        @Override
        public void output() {
            if (output == null) return;
            if (outputShape(output, 0, 1)) output = null;
        }

        @Override
        public void craft() {
            if (input[0].canStack(input[1]))
                output = input[0].stack(input[1]);
            input[0] = input[1] = null;
        }

        @Override
        public boolean isOutput(ShapeBuild source) {
            return source == atSide(0, 1);
        }
    }
}
