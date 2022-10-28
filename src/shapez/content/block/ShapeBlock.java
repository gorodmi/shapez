package shapez.content.block;

import shapez.content.item.ShapeItem;

public class ShapeBlock extends RectBlock {
    public int shapeCapacity = 1;

    public ShapeBlock(String name){
        super(name);
    }

    public class ShapeBuild extends RectBuild {
        public void handleShape(ShapeBuild source, ShapeItem item) {}

        public boolean acceptShape(ShapeBuild source, ShapeItem item) {
            return false;
        }

        public void offloadShape(ShapeItem item) {
            if (item.empty()) return;
            int dump = this.cdump;
            for(int i = 0; i < this.proximity.size; ++i) {
                this.incrementDump(this.proximity.size);
                Object other = this.proximity.get((i + dump) % this.proximity.size);
                if (!(other instanceof ShapeBuild)) continue;
                ShapeBuild sBuild = (ShapeBuild)other;
                if (sBuild.team == this.team && sBuild.acceptShape(this, item)) {
                    sBuild.handleShape(this, item);
                    return;
                }
            }
            this.handleShape(this, item);
        }
    }
}
