package shapez.content.block;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.util.Tmp;
import mindustry.gen.Building;
import shapez.content.item.ShapeItem;

public class ShapeBridge extends ShapeBlock {
    public int range = 5;

    public ShapeBridge(String name) {
        super(name);

        width = 1;
        height = 1;
    }

    public class ShapeBridgeBuild extends ShapeBuild {
        public boolean isOutput = false;

        public ShapeBridgeBuild second() {
            Tmp.p1.set(1, 0).rotate(rotation);
            Tmp.p2.set(0, 0);
            for (int i = 0; i < range; i++) {
                Tmp.p2.add(Tmp.p1);
                Building build = nearby(Tmp.p2.x, Tmp.p2.y);
                if (build instanceof ShapeBridgeBuild && build.rotation == (rotation + 2) % 4) {
                    isOutput = !((ShapeBridgeBuild) build).isOutput;
                    return (ShapeBridgeBuild) build;
                }
            }
            isOutput = false;
            return null;
        }

        @Override
        public void draw() {
            second();
            if (isOutput) Draw.color(Color.green);
            super.draw();
            Draw.color();
        }

        @Override
        public boolean acceptShape(ShapeBuild source, ShapeItem item, int side, int i) {
            return side == 2 && second() != null && !isOutput;
        }

        @Override
        public void handleShape(ShapeBuild source, ShapeItem item, int side, int i) {
            if (source == second() && isOutput) outputShape(item, 2, 0);
            else {
                ShapeBridgeBuild bridge = second();
                if (bridge == null) return;
                bridge.handleShape(this, item, 2, 0);
            }
        }

        @Override
        public boolean isOutput(ShapeBuild source) {
            second();
            return source == atSide(2, 0) && isOutput;
        }
    }
}
