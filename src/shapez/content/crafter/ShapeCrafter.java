package shapez.content.crafter;

import arc.struct.Seq;
import shapez.ShapeItem;
import shapez.content.ShapeBlock;

public class ShapeCrafter extends ShapeBlock {
    public ShapeCrafter(String name) {
        super(name);
        update = true;
        rotate = true;
        noUpdateDisabled = false;
    }

    public class ShapeCrafterBuild extends ShapeBuild {
        float progress = 0;
        Seq<ShapeItem> result = new Seq<>();

        @Override
        public boolean acceptShape(ShapeBuild source, ShapeItem item) {
            int rel = relativeTo(source);
            return super.acceptShape(source, item) && rel == (rotation + 2) % 4;
        }

        @Override
        public void handleShape(ShapeBuild source, ShapeItem item) {
            if (source == this) result.add(item);
            else super.handleShape(source, item);
        }

        @Override
        public void updateTile() {
            super.updateTile();

            if (shouldRun()) {
                progress += 0.005f;

                if (progress > 1) {
                    craft();
                    progress %= 1;
                }
            }

            if (timer(timerDump, dumpTime / timeScale)) {
                output();
            }
        }

        @Override
        public void offloadShape(ShapeItem item) {
            if (item.empty()) return;
            int dump = this.cdump;
            for(int i = 0; i < this.proximity.size; ++i) {
                this.incrementDump(this.proximity.size);
                Object other = this.proximity.get((i + dump) % this.proximity.size);
                if (!(other instanceof ShapeBuild)) continue;
                ShapeBuild sBuild = (ShapeBuild)other;
                int rel = relativeTo(sBuild);
                if (sBuild.team == this.team && sBuild.acceptShape(this, item) && rotation == rel) {
                    sBuild.handleShape(this, item);
                    return;
                }
            }
            this.handleShape(this, item);
        }

        public boolean shouldRun() {
            return result.size < shapeCapacity;
        }

        public void output() {
            if (result.size == 0) return;
            Seq<ShapeItem> resultCopy = result.copy();
            result.clear();
            resultCopy.each(this::offloadShape);
        }

        public void craft() {

        }
    }
}
