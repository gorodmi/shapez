package shapez.content.crafter;

import arc.math.geom.Point2;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.gen.Building;
import shapez.ShapeItem;

public class ShapeSplitter extends ShapeCrafter {
    public ShapeSplitter(String name) {
        super(name);
    }

    public class ShapeSplitterBuild extends ShapeCrafterBuild {
        ShapeItem input = null;
        ShapeItem[] output = new ShapeItem[2];

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
            return input != null && output[0] == null && output[1] == null;
        }

        @Override
        public void output() {
            Building a = atSide(0, 1);
            Building b = atSide(0, 0);
            if (a instanceof ShapeBuild && output[0] != null && ((ShapeBuild) a).acceptShape(this, output[0])) {
                ((ShapeBuild) a).handleShape(this, output[0]);
                output[0] = null;
            }
            if (b instanceof ShapeBuild && output[1] != null && ((ShapeBuild) b).acceptShape(this, output[1])) {
                ((ShapeBuild) b).handleShape(this, output[1]);
                output[1] = null;
            }
        }

        @Override
        public void craft() {
            Seq<ShapeItem> shapes = input.split();
            output[0] = shapes.get(0);
            output[1] = shapes.get(1);
            input = null;
        }
    }
}
