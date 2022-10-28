package shapez.content.block;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.math.geom.Point2;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Reflect;
import arc.util.Tmp;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.Edges;
import mindustry.world.Tile;
import shapez.content.item.ShapeItem;

import static mindustry.Vars.world;

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
