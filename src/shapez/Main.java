package shapez;

import arc.*;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import mindustry.content.Items;
import mindustry.mod.*;
import mindustry.type.*;
import shapez.content.block.*;
import shapez.content.block.crafter.*;
import shapez.content.item.ColorItem;
import shapez.content.item.quad.QuadItem;

public class Main extends Mod{
    public static final Color outline = Color.valueOf("555555");

    public static ShapeConveyor shapeConveyor;
    public static ShapeBalancer shapeBalancer;
    public static ShapeCreator shapeCreator;
    public static ShapeVoid shapeVoid;
    public static ShapeSplitter shapeSplitter;
    public static ShapeStacker shapeStacker;
    public static ShapeRotator shapeRotator;
    public static ShapePainter shapePainter;
    public static ColorMixer colorMixer;
    public static ShapeExtractor shapeExtractor;
    public static ShapeOre circleOre, squareOre, starOre, windmillOre, redOre, greenOre, blueOre;

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

        shapeBalancer = new ShapeBalancer("shape-balancer"){{
            requirements(Category.distribution, new ItemStack[]{new ItemStack(Items.coal, 1)}, true);
        }};

        shapeCreator = new ShapeCreator("shape-creator"){{
            requirements(Category.distribution, new ItemStack[]{new ItemStack(Items.coal, 1)}, true);
        }};

        shapeVoid = new ShapeVoid("shape-void"){{
            requirements(Category.distribution, new ItemStack[]{new ItemStack(Items.coal, 1)}, true);
        }};

        shapeSplitter = new ShapeSplitter("shape-splitter"){{
            requirements(Category.crafting, new ItemStack[]{new ItemStack(Items.coal, 1)}, true);
        }};

        shapeStacker = new ShapeStacker("shape-stacker"){{
            requirements(Category.crafting, new ItemStack[]{new ItemStack(Items.coal, 1)}, true);
        }};

        shapeRotator = new ShapeRotator("shape-rotator"){{
            requirements(Category.crafting, new ItemStack[]{new ItemStack(Items.coal, 1)}, true);
        }};

        shapePainter = new ShapePainter("shape-painter"){{
            requirements(Category.crafting, new ItemStack[]{new ItemStack(Items.coal, 1)}, true);
        }};

        colorMixer = new ColorMixer("color-mixer"){{
            requirements(Category.crafting, new ItemStack[]{new ItemStack(Items.coal, 1)}, true);
        }};

        shapeExtractor = new ShapeExtractor("shape-extractor"){{
            requirements(Category.production, new ItemStack[]{new ItemStack(Items.coal, 1)}, true);
        }};

        circleOre = new ShapeOre(QuadItem.fromString("CuCuCuCu"));
        squareOre = new ShapeOre(QuadItem.fromString("RuRuRuRu"));
        // TODO: make not full
        starOre = new ShapeOre(QuadItem.fromString("SuSuSuSu"));
        windmillOre = new ShapeOre(QuadItem.fromString("WuWuWuWu"));
        redOre = new ShapeOre(ColorItem.fromString("r"));
        greenOre = new ShapeOre(ColorItem.fromString("g"));
        blueOre = new ShapeOre(ColorItem.fromString("b"));
    }
}
