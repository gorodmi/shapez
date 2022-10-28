package shapez.content.item;

import arc.graphics.g2d.Draw;
import shapez.content.item.quad.QuadItem;

public class ShapeItem {
    public void draw(float x, float y, float size) {
        Draw.rect("error", x, y, size, size);
    }

    public boolean empty() {
        return false;
    }

    public String toString() {
        return "";
    }

    public static ShapeItem fromString(String str) {
        return new ShapeItem();
    }

    public static ShapeItem fromString(String type, String str) {
        switch (type) {
            case "QuadItem": return QuadItem.validCode(str) ? QuadItem.fromString(str) : null;
            case "ColorItem": return ColorItem.validCode(str) ? ColorItem.fromString(str) : null;
            case "ShapeItem": return ShapeItem.fromString(str);
        };
        return null;
    }
}
