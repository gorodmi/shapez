package shapez.content.item.quad;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import shapez.content.item.ShapeColor;

public class Quadrant {
    public static final Quadrant empty = new Quadrant(ShapeType.empty, ShapeColor.empty);
    public final ShapeType shapeType;
    public final ShapeColor color;

    public Quadrant(ShapeType shapeType, ShapeColor color) {
        this.shapeType = shapeType;
        this.color = color;
    }

    public void draw(int i, float x, float y, float size, float origSize) {
        Lines.stroke(origSize / 18f);
        Draw.color(color.color);
        shapeType.draw(i, x, y, size);
        Draw.color();
    }

    public boolean empty() {
        return shapeType == ShapeType.empty;
    }

    public static boolean validCode(String code) {
        if (code.length() != 2) return false;
        return ShapeType.fromString(code.substring(0, 1)) != null && ShapeColor.fromString(code.substring(1, 2)) != null;
    }

    public static Quadrant fromString(String code) {
        return new Quadrant(ShapeType.fromString(code.substring(0, 1)), ShapeColor.fromString(code.substring(1, 2)));
    }

    @Override
    public String toString() {
        return shapeType.toString() + color.toString();
    }
}
