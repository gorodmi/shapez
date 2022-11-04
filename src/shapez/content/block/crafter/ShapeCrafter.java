package shapez.content.block.crafter;

import arc.struct.Seq;
import shapez.content.item.ShapeItem;
import shapez.content.block.ShapeBlock;

public class ShapeCrafter extends ShapeBlock {
    public ShapeCrafter(String name) {
        super(name);
    }

    public class ShapeCrafterBuild extends ShapeBuild {
        float progress = 0;

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

        public boolean shouldRun() {
            return true;
        }

        public void output() {

        }

        @Override
        public boolean isOutput(ShapeBuild source) {
            return true;
        }

        public void craft() {

        }
    }
}
