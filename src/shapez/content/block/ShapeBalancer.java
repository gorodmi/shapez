package shapez.content.block;

import arc.struct.Seq;
import mindustry.gen.Building;
import shapez.content.item.ShapeItem;

public class ShapeBalancer extends ShapeBlock {
    public ShapeBalancer(String name) {
        super(name);
        width = 1;
        height = 2;
    }

    public class ShapeBalancerBuild extends ShapeBuild {
        int side = 0;
        ShapeItem balance = null;

        @Override
        public boolean acceptShape(ShapeBuild source, ShapeItem item, int side, int i) {
            if (side == 2) return balance == null;
            return false;
        }

        @Override
        public void handleShape(ShapeBuild source, ShapeItem item, int side, int i) {
            balance = item;
        }

        @Override
        public void updateTile() {
            if (balance == null) return;
            Building a = atSide(0, 0);
            Building b = atSide(0, 1);
            Seq<Building> buildings = Seq.with(a, b).select(build -> build instanceof ShapeBuild);
            if (buildings.size == 0) return;
            side %= buildings.size;
            Building next = buildings.get(side);
            if (!(outputShape(next, balance) ||
                  buildings.size == 2 && outputShape(buildings.get(1 - side), balance))) return;
            remove();
            side = (side + 1) % buildings.size;
        }

        public void remove() {
            balance = null;
        }

        @Override
        public boolean isOutput(ShapeBuild source) {
            return source == atSide(0, 0) || source == atSide(0, 1);
        }
    }
}
