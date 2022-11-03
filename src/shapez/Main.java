package shapez;

import arc.*;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.mod.*;
import mindustry.type.*;
import shapez.content.block.*;
import shapez.content.block.crafter.*;
import shapez.content.item.ColorItem;
import shapez.content.item.MindustryItem;
import shapez.content.item.quad.QuadItem;

public class Main extends Mod{
    public static final Color outline = Color.valueOf("555555");

    public static MindustryItem circle, square;
    public static ShapeConveyor shapeConveyor;
    public static ShapeBalancer shapeBalancer;
    public static ShapeStorage shapeStorage;
    public static ShapeCreator shapeCreator;
    public static ShapeVoid shapeVoid;
    public static ShapeSplitter shapeSplitter;
    public static ShapeStacker shapeStacker;
    public static ShapeRotator shapeRotator;
    public static ShapePainter shapePainter;
    public static ColorMixer colorMixer;
    public static ShapeExtractor shapeExtractor;
    public static ShapeOre circleOre, squareOre, starOre, windmillOre, redOre, greenOre, blueOre;

    @Override
    public void loadContent(){
        circle = new MindustryItem(QuadItem.fromString("CuCuCuCu"));
        square = new MindustryItem(QuadItem.fromString("RuRuRuRu"));

        shapeConveyor = new ShapeConveyor("shape-conveyor"){{
            requirements(Category.distribution, new ItemStack[0], true);
            speed = 0.03f;
            displayedSpeed = 4.2f;
        }};

        shapeBalancer = new ShapeBalancer("shape-balancer"){{
            requirements(Category.distribution, new ItemStack[0], true);
        }};

        shapeStorage = new ShapeStorage("shape-storage"){{
            requirements(Category.distribution, new ItemStack[0], true);
        }};

        shapeCreator = new ShapeCreator("shape-creator"){{
            requirements(Category.distribution, new ItemStack[0], true);
        }};

        shapeVoid = new ShapeVoid("shape-void"){{
            requirements(Category.distribution, new ItemStack[0], true);
        }};

        shapeSplitter = new ShapeSplitter("shape-splitter"){{
            requirements(Category.crafting, new ItemStack[0], true);
        }};

        shapeStacker = new ShapeStacker("shape-stacker"){{
            requirements(Category.crafting, new ItemStack[0], true);
        }};

        shapeRotator = new ShapeRotator("shape-rotator"){{
            requirements(Category.crafting, new ItemStack[0], true);
        }};

        shapePainter = new ShapePainter("shape-painter"){{
            requirements(Category.crafting, new ItemStack[0], true);
        }};

        colorMixer = new ColorMixer("color-mixer"){{
            requirements(Category.crafting, new ItemStack[0], true);
        }};

        shapeExtractor = new ShapeExtractor("shape-extractor"){{
            requirements(Category.production, new ItemStack[0], true);
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
