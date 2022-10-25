package shapez.content;

import arc.scene.ui.layout.Table;
import shapez.ShapeItem;

public class ShapeCreator extends ShapeBlock {
    public ShapeCreator(String name) {
        super(name);
        update = true;
        configurable = true;
        saveConfig = true;

        config(String.class, (ShapeCreatorBuild tile, String code) -> tile.code = code);
        configClear((ShapeCreatorBuild tile) -> tile.code = "CuCuCuCu");
    }

    public class ShapeCreatorBuild extends ShapeBuild {
        String code = "CuCuCuCu";

        @Override
        public void updateTile() {
            if (!ShapeItem.validCode(code)) return;
            offloadShape(ShapeItem.fromString(code));
            noSleep();
        }

        @Override
        public void buildConfiguration(Table table){
            Table t = new Table().top();
            t.area(code, this::configure);
            table.top().add(t);
        }

        @Override
        public void draw() {
            super.draw();
            if (!ShapeItem.validCode(code)) return;
            ShapeItem.fromString(code).draw(x, y, 8f);
        }
    }
}
