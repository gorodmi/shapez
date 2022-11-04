package shapez.content.block;

import arc.util.Log;
import mindustry.gen.Building;
import mindustry.world.blocks.environment.Floor;

public class ShapeExtractor extends ShapeBlock {
    public ShapeExtractor(String name) {
        super(name);
        width = 1;
        height = 1;
    }

    public class ShapeExtractorBuild extends ShapeBuild {
        @Override
        public void updateTile() {
            Floor floor = tile.overlay();
            if (!(floor instanceof ShapeOre)) return;
            ShapeOre ore = (ShapeOre) floor;
            outputShape(ore.item, 0, 0);
        }

        @Override
        public boolean isOutput(ShapeBuild source) {
            return source == atSide(0, 0);
        }
    }
}
