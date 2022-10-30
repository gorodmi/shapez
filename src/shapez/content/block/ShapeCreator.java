package shapez.content.block;

import arc.scene.ui.layout.Table;
import arc.util.io.Reads;
import arc.util.io.Writes;
import shapez.content.item.ColorItem;
import shapez.content.item.ShapeItem;
import shapez.content.item.quad.QuadItem;

public class ShapeCreator extends ShapeBlock {
    public ShapeCreator(String name) {
        super(name);
        configurable = true;
        saveConfig = true;
        noUpdateDisabled = true;

        width = 1;
        height = 1;

        config(String.class, (ShapeCreatorBuild tile, String code) -> {
            tile.code = code;
            tile.item = (QuadItem) ShapeItem.fromString("QuadItem", tile.code);
        });
        configClear((ShapeCreatorBuild tile) -> {
            tile.code = "CuCuCuCu";
            tile.item = (QuadItem) ShapeItem.fromString("QuadItem", tile.code);
        });
    }

    public class ShapeCreatorBuild extends ShapeBuild {
        String code = "CuCuCuCu";
        QuadItem item = QuadItem.fromString(code);

        @Override
        public void updateTile() {
            if (item != null) offloadShape(item);
            else if (ColorItem.validCode(code)) offloadShape(ColorItem.fromString(code));
            noSleep();
        }

        @Override
        public void buildConfiguration(Table table){
            Table t = new Table().top();
            t.field(code, this::configure).width(90f).expandX().growX();
            table.top().add(t);
        }

        @Override
        public void draw() {
            super.draw();
            if (item != null) item.draw(x, y, 7f);
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.str(code);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            configure(read.str());
        }
    }
}
