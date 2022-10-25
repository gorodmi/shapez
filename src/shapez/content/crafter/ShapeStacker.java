package shapez.content.crafter;

import mindustry.gen.Building;
import shapez.ShapeItem;

public class ShapeStacker extends ShapeCrafter {
    public ShapeStacker(String name) {
        super(name);
    }

    public class ShapeStackerBuild extends ShapeCrafterBuild {
        ShapeItem[] input = new ShapeItem[2];
        ShapeItem output = null;

        @Override
        public boolean acceptShape(ShapeBuild source, ShapeItem item) {
            if (source == atSide(2, 1)) return input[0] == null;
            else if (source == atSide(2, 0)) return input[1] == null;
            return false;
        }

        @Override
        public void handleShape(ShapeBuild source, ShapeItem item) {
            if (source == atSide(2, 1)) input[0] = item;
            else if (source == atSide(2, 0)) input[1] = item;
        }

        @Override
        public boolean shouldRun() {
            return input[0] != null && input[1] != null && output == null;
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
            if (input[0].canStack(input[1]))
                output = input[0].stack(input[1]);
            input[0] = input[1] = null;
        }
    }
}
