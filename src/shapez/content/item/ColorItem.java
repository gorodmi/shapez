package shapez.content.item;

import arc.graphics.g2d.Draw;
import arc.struct.ObjectMap;
import arc.struct.Seq;

public class ColorItem extends ShapeItem {
    public static final Seq<ShapeColor> primary = Seq.with(ShapeColor.red, ShapeColor.green, ShapeColor.blue);
    public static final ObjectMap<ShapeColor, Seq<ShapeColor>> secondary = ObjectMap.of(
            ShapeColor.cyan, Seq.with(ShapeColor.green, ShapeColor.blue),
            ShapeColor.yellow, Seq.with(ShapeColor.red, ShapeColor.green),
            ShapeColor.purple, Seq.with(ShapeColor.red, ShapeColor.blue)
    );
    public final ShapeColor color;

    public ColorItem(ShapeColor color) {
        this.color = color;
    }

    @Override
    public void draw(float x, float y, float size) {
        Draw.color(color.color);
        Draw.rect("error", x, y, size, size);
        Draw.color();
    }

    // lmao
    public ColorItem mix(ColorItem other) {
        if (this == other) return this;
        for (ObjectMap.Keys<ShapeColor> keys = secondary.keys(); keys.hasNext();) {
            ShapeColor k = keys.next();
            Seq<ShapeColor> v = secondary.get(k);
            if ((v.contains(color) && v.contains(other.color)) ||
                    (k == color && v.contains(other.color)) ||
                    (k == other.color && v.contains(color))) return new ColorItem(k);
        }
        Seq<ShapeColor> keys = secondary.keys().toSeq();
        if ((keys.contains(color) && primary.contains(other.color)) || (keys.contains(other.color) && primary.contains(color)))
            return new ColorItem(ShapeColor.white);
        return null;
    }

    @Override
    public String toString() {
        return color.toString();
    }

    public static boolean validCode(String code) { return ShapeColor.fromString(code) != null; }
    public static ColorItem fromString(String str) {
        return new ColorItem(ShapeColor.fromString(str));
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ColorItem && obj.toString().equals(toString());
    }
}
