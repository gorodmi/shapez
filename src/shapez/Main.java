package shapez;

import arc.*;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import mindustry.content.Items;
import mindustry.mod.*;
import mindustry.type.*;
import shapez.content.block.RectBlock;
import shapez.content.block.ShapeConveyor;
import shapez.content.block.crafter.*;
import shapez.content.block.ShapeCreator;
import shapez.content.block.ShapeVoid;

public class Main extends Mod{
    public static final Color outline = Color.valueOf("555555");

    public static ShapeConveyor shapeConveyor;
    public static ShapeCreator shapeCreator;
    public static ShapeVoid shapeVoid;
    public static ShapeSplitter shapeSplitter;
    public static ShapeStacker shapeStacker;
    public static ShapeRotator shapeRotator;
    public static ShapePainter shapePainter;
    public static ColorMixer colorMixer;

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
            requirements(Category.distribution, new ItemStack[]{new ItemStack(Items.coal, 1)}, true);
        }};

        shapeStacker = new ShapeStacker("shape-stacker"){{
            requirements(Category.distribution, new ItemStack[]{new ItemStack(Items.coal, 1)}, true);
        }};

        shapeRotator = new ShapeRotator("shape-rotator"){{
            requirements(Category.distribution, new ItemStack[]{new ItemStack(Items.coal, 1)}, true);
        }};

        shapePainter = new ShapePainter("shape-painter"){{
            requirements(Category.distribution, new ItemStack[]{new ItemStack(Items.coal, 1)}, true);
        }};

        colorMixer = new ColorMixer("color-mixer"){{
            requirements(Category.distribution, new ItemStack[]{new ItemStack(Items.coal, 1)}, true);
        }};
    }
}
