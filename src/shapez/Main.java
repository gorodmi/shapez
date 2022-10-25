package shapez;

import arc.*;
import arc.graphics.g2d.*;
import mindustry.content.Items;
import mindustry.mod.*;
import mindustry.type.*;
import shapez.content.ShapeConveyor;
import shapez.content.crafter.ShapeStacker;
import shapez.content.ShapeCreator;
import shapez.content.ShapeVoid;
import shapez.content.crafter.ShapeRotator;
import shapez.content.crafter.ShapeSplitter;

public class Main extends Mod{
    public static ShapeConveyor shapeConveyor;
    public static ShapeCreator shapeCreator;
    public static ShapeVoid shapeVoid;
    public static ShapeSplitter shapeSplitter;
    public static ShapeStacker shapeStacker;
    public static ShapeRotator shapeRotator;

    public Main() {

    }

    @Override
    public void loadContent(){
        shapeConveyor = new ShapeConveyor("shape-conveyor"){{
            speed = 0.03f;
            regions = new TextureRegion[4][5];
            for (int i = 0; i < regions.length; i++)
                for (int j = 0; j < regions[i].length; j++)
                    regions[i][j] = Core.atlas.find("error");
            requirements(Category.distribution, new ItemStack[]{new ItemStack(Items.coal, 1)}, true);
        }};

        shapeCreator = new ShapeCreator("shape-creator"){{
            requirements(Category.distribution, new ItemStack[]{new ItemStack(Items.coal, 1)}, true);
        }};

        shapeVoid = new ShapeVoid("shape-void"){{
            requirements(Category.distribution, new ItemStack[]{new ItemStack(Items.coal, 1)}, true);
        }};

        shapeSplitter = new ShapeSplitter("shape-splitter"){{
            size = 2;
            requirements(Category.distribution, new ItemStack[]{new ItemStack(Items.coal, 1)}, true);
        }};

        shapeStacker = new ShapeStacker("shape-stacker"){{
            size = 2;
            shapeCapacity = 2;
            requirements(Category.distribution, new ItemStack[]{new ItemStack(Items.coal, 1)}, true);
        }};

        shapeRotator = new ShapeRotator("shape-rotator"){{
            requirements(Category.distribution, new ItemStack[]{new ItemStack(Items.coal, 1)}, true);
        }};
    }
}
