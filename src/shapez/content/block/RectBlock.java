package shapez.content.block;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.geom.Point2;
import arc.struct.Seq;
import arc.util.Eachable;
import arc.util.Reflect;
import arc.util.Tmp;
import mindustry.entities.units.BuildPlan;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.Edges;
import mindustry.world.Tile;

import static mindustry.Vars.*;
import static mindustry.Vars.player;

public class RectBlock extends Block {
    public TextureRegion[] rotationRegions;
    public boolean useRotationRegions = false;
    public int width = 1;
    public int height = 1;

    public RectBlock(String name){
        super(name);
        update = true;
        rotate = true;
        size = 1;
    }

    public Seq<Tile> getMulti(Tile tile, int rot) {
        Seq<Tile> tiles = new Seq<>();
        if (width >= height) {
            int xCount = width - size;
            for (int i = 0; i <= xCount; i++) {
                for (int j = 0; j < height; j++) {
                    Tmp.v1.set(i, j).rotate(rot * 90);
                    tiles.add(tile.nearby((int) Tmp.v1.x, (int) Tmp.v1.y));
                }
            }
        } else {
            int yCount = height - size;
            for (int i = 0; i <= yCount; i++) {
                for (int j = 0; j < width; j++) {
                    Tmp.v1.set(j, i).rotate(rot * 90);
                    tiles.add(tile.nearby((int) Tmp.v1.x, (int) Tmp.v1.y));
                }
            }
        }
        tiles.remove(tile);
        return tiles;
    }

    @Override
    public void load() {
        super.load();
        if (useRotationRegions) {
            rotationRegions = new TextureRegion[4];
            for (int i = 0; i < rotationRegions.length; i++) {
                rotationRegions[i] = Core.atlas.find(name + "-rot-" + i);
            }
            region = rotationRegions[0];
        }
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation) {
        return getMulti(tile, rotation).find(t -> !canReplace(t.block())) == null;
    }

    @Override
    public void drawDefaultPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
        Tmp.v1.set(-(width - 1) / 2f, (height - 1) / 2f)
                .rotate(plan.rotation * 90).scl(tilesize).add(plan.drawx(), plan.drawy());
        float x = Tmp.v1.x;
        float y = Tmp.v1.y;

        TextureRegion reg = getPlanRegion(plan, list);
        Draw.rect(reg, x, y, !rotate || !rotateDraw ? 0 : plan.rotation * 90);

        if(plan.worldContext && player != null && teamRegion != null && teamRegion.found()){
            if(teamRegions[player.team().id] == teamRegion) Draw.color(player.team().color);
            Draw.rect(teamRegions[player.team().id], x, y);
            Draw.color();
        }

        drawPlanConfig(plan, list);
    }

    @Override
    public TextureRegion getPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
        if (useRotationRegions) return rotationRegions[plan.rotation];
        else return region;
    }

    public class RectBuild extends Building {
        private boolean removing = false;
        private Seq<Tile> multi;

        public RectBuild() {
            Core.app.post(() -> {
                multi = getMulti(tile, rotation);
                multi.each(t -> {
                    t.build = this;
                    Reflect.set(t, "block", block);
                    Reflect.invoke(t, "changed");
                });
            });
        }

        @Override
        public void removeFromProximity(){
            onProximityRemoved();
            tmpTiles.clear();

            Point2[] nearby = Edges.getEdges(block.size);
            for(Point2 point : nearby){
                Building other = world.build(tile.x + point.x, tile.y + point.y);
                //remove this tile from all nearby tile's proximities
                if(other != null){
                    tmpTiles.add(other);
                }
            }

            for(Building other : tmpTiles){
                other.proximity.remove(self(), true);
                other.onProximityUpdate();
            }
            proximity.clear();
        }

        @Override
        public void updateProximity(){
            tmpTiles.clear();
            proximity.clear();

            Point2[] nearby = Edges.getEdges(block.size);
            for(Point2 point : nearby){
                Building other = world.build(tile.x + point.x, tile.y + point.y);

                if(other == null || !(other.tile.interactable(team))) continue;

                other.proximity.addUnique(self());

                tmpTiles.add(other);
            }

            //using a set to prevent duplicates
            for(Building tile : tmpTiles){
                proximity.add(tile);
            }

            onProximityAdded();
            onProximityUpdate();

            for(Building other : tmpTiles){
                other.onProximityUpdate();
            }
        }

        @Override
        public void onRemoved() {
            super.onRemoved();
            if (removing) return;
            removing = true;
            multi.each(Tile::setAir);
            removing = false;
        }

        @Override
        public void draw() {
            Tmp.v1.set(-(width - 1) / 2f, (height - 1) / 2f).rotate(drawrot()).scl(tilesize).add(x, y);
            float x = Tmp.v1.x;
            float y = Tmp.v1.y;

            if (this.block.variants != 0 && this.block.variantRegions != null) {
                Draw.rect(this.block.variantRegions[Mathf.randomSeed((long)this.tile.pos(), 0, Math.max(0, this.block.variantRegions.length - 1))], x, y, this.drawrot());
            } else if (useRotationRegions) {
                Draw.rect(((RectBlock) this.block).rotationRegions[rotation], x, y, this.drawrot());
            } else {
                Draw.rect(this.block.region, x, y, this.drawrot());
            }

            this.drawTeamTop();
        }

        public Building atSide(int side, int i) {
            Point2 point = new Point2();
            side = (rotation + side) % 4;
            if (rotation == 0) {
                if (side == 0) point.set(width, i);
                else if (side == 1) point.set(width - i - 1, height);
                else if (side == 2) point.set(-1, height - i - 1);
                else if (side == 3) point.set(i, -1);
            } else if (rotation == 1) {
                if (side == 0) point.set(1, i);
                else if (side == 1) point.set(-i, width);
                else if (side == 2) point.set(-height, width - i - 1);
                else if (side == 3) point.set(i - height + 1, -1);
            } else if (rotation == 2) {
                if (side == 0) point.set(1, i - height + 1);
                else if (side == 1) point.set(-i, 1);
                else if (side == 2) point.set(-width, -i);
                else if (side == 3) point.set(width - i - 1, -height);
            } else if (rotation == 3) {
                if (side == 0) point.set(height, width - i - 1);
                else if (side == 1) point.set(width - i - 1, 1);
                else if (side == 2) point.set(-1, -i);
                else if (side == 3) point.set(i, -width);
            }
            return nearby(point.x, point.y);
        }
    }
}