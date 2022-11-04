package shapez.content.block;

import arc.util.Log;
import arc.util.Tmp;
import mindustry.gen.Building;
import shapez.content.item.ShapeItem;

public class ShapeBlock extends RectBlock {
    public int shapeCapacity = 1;

    public ShapeBlock(String name){
        super(name);
        buildCost = 0;
    }

    public class ShapeBuild extends RectBuild {
        public boolean outputShape(Building out, ShapeItem item, int side, int i) {
            if (!(out instanceof ShapeBuild)) return false;
            ShapeBuild shapeBuild = (ShapeBuild) out;
            ShapeBlock shapeBlock = (ShapeBlock) shapeBuild.block;
            int otherSide = (side + rotation - shapeBuild.rotation + 2) % 4;
            int otherI =
                    Tmp.p1.set(otherSide % 2 == 0 ? shapeBlock.width : shapeBlock.height,
                               otherSide % 2 == 0 ? shapeBlock.height : shapeBlock.width)
                    .add(Tmp.p2.set(out.tileX() - tileX(), tileY() - out.tileY()).rotate(otherSide + shapeBuild.rotation))
                    .y - i - 1;
            // -width
            if (!shapeBuild.acceptShape(this, item, otherSide, otherI)) return false;
            shapeBuild.handleShape(this, item, otherSide, otherI);
            return true;
        }

        public boolean outputShape(ShapeItem item, int side, int i) {
            return outputShape(atSide(side, i), item, side, i);
        }

        public boolean outputShape(Building build, ShapeItem item) {
            int side = 0;
            for (; side < 4; side++)
                if (atSide(side, 0) == build)
                    break;
            return outputShape(build, item, side, 0);
        }

        public void handleShape(ShapeBuild source, ShapeItem item, int side, int i) {}
        public boolean acceptShape(ShapeBuild source, ShapeItem item, int side, int i) {
            return false;
        }

        public boolean isOutput(ShapeBuild source) {
            return false;
        }
    }
}
