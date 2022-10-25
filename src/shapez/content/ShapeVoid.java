package shapez.content;

import shapez.ShapeItem;

public class ShapeVoid extends ShapeBlock {
    public ShapeVoid(String name) {
        super(name);
        update = true;
    }

    public class ShapeVoidBuild extends ShapeBuild {
        @Override
        public boolean acceptShape(ShapeBuild source, ShapeItem item) {
            return true;
        }

        @Override
        public void handleShape(ShapeBuild source, ShapeItem item) {

        }
    }
}
