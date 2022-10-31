package shapez;

import arc.math.Mathf;
import arc.math.geom.Point2;
import arc.struct.Seq;
import arc.util.Tmp;
import mindustry.gen.Building;
import mindustry.world.Tile;
import shapez.content.block.ShapeBlock;

import static mindustry.Vars.world;

public class RectEdges {
    public static Tile getFacingEdge(ShapeBlock.ShapeBuild tile, Building other){
        return getFacingEdge((ShapeBlock) tile.block(), tile.tileX(), tile.tileY(), tile.rotation(), other.tile());
    }

    public static Tile getFacingEdge(Tile tile, Tile other){
        return getFacingEdge((ShapeBlock) tile.block(), tile.x, tile.y, tile.build.rotation(), other);
    }

    public static Tile getFacingEdge(ShapeBlock b, int tilex, int tiley, int rot, Tile other){
        int width = rot % 2 == 0 ? b.width : b.height;
        int height = rot % 2 == 0 ? b.height : b.width;
        return world.tile(tilex + Mathf.clamp(other.x - tilex, -(width - 1) / 2, (width / 2)),
                tiley + Mathf.clamp(other.y - tiley, -(height - 1) / 2, (height / 2)));
    }

    public static Point2[] getEdges(int width, int height, int rot) {
        Seq<Point2> points = new Seq<>();
        for (int i = 0; i < width; i++) {
            points.add(new Point2(-i, -1).rotate(rot));
            points.add(new Point2(-i, height).rotate(rot));
        }
        for (int i = 0; i < height; i++) {
            points.add(new Point2(-1, i).rotate(rot));
            points.add(new Point2(width, i).rotate(rot));
        }
        points.sort((a, b) -> Float.compare(Mathf.angle(a.x, a.y), Mathf.angle(b.x, b.y)));
        return points.toArray(Point2.class);
    }
}
