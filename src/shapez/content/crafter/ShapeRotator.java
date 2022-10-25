package shapez.content.crafter;

import arc.util.Log;
import mindustry.gen.Building;
import shapez.ShapeItem;

public class ShapeRotator extends ShapeCrafter {
    public ShapeRotator(String name) {
        super(name);
    }

    public class ShapeRotatorBuild extends ShapeCrafterBuild {
        ShapeItem input = null;
        ShapeItem output = null;

        @Override
        public boolean acceptShape(ShapeBuild source, ShapeItem item) {
            if (source == atSide(2, 0)) return input == null;
            return false;
        }

        @Override
        public void handleShape(ShapeBuild source, ShapeItem item) {
            if (source == atSide(2, 0)) input = item;
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
