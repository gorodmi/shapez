package shapez.content;

import arc.math.geom.Point2;
import mindustry.gen.*;
import mindustry.world.*;
import shapez.ShapeItem;

public class ShapeBlock extends Block {
    public int shapeCapacity = 1;

    public ShapeBlock(String name){
        super(name);
    }

    public class ShapeBuild extends Building {
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

        public Building atSide(int r, int i) {
            Point2 point = new Point2();
            int cornerX = -(size-1)/2, cornerY = -(size-1)/2, s = size;
            int rot = (rotation + r) % 4;
            if (rot == 0) point.set(cornerX + s, cornerY + (size-1-i));
            else if (rot == 1) point.set(cornerX + i, cornerY + s);
            else if (rot == 2) point.set(cornerX - 1, cornerY + i);
            else if (rot == 3) point.set(cornerX + (size-1-i), cornerY - 1);
            return nearby(point.x, point.y);
        }
    }
}
