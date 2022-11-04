package shapez.content.block;

import shapez.content.item.ShapeItem;

public class ShapeVoid extends ShapeBlock {
    public ShapeVoid(String name) {
        super(name);
        width = 1;
        height = 1;
        useRotationRegions = true;
    }

    public class ShapeVoidBuild extends ShapeBuild {
        @Override
        public boolean acceptShape(ShapeBuild source, ShapeItem item, int side, int i) {
            return true;
        }

        @Override
        public void handleShape(ShapeBuild source, ShapeItem item, int side, int i) {

        }
    }
}
