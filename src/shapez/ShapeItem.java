package shapez;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.pooling.Pool;

public class ShapeItem {
    public static final Color outline = Color.valueOf("555555");
    public final Seq<Layer> layers;

    public ShapeItem() {
        layers = new Seq<>();
    }

    public ShapeItem(Seq<Layer> layers) {
        if (layers.size > 4) throw new RuntimeException("wrong layer count");
        this.layers = layers;
    }

    public boolean canStack(ShapeItem other) {
        return stackLevel(other) != -1;
    }

    public int stackLevel(ShapeItem other) {
        boolean stacked = false;
        for (int i = layers.size; i >= 0; i--) {
            for (int j = 0; j < other.layers.size; j++) {
                if (i + j >= layers.size) continue;
                Layer layer = layers.get(i + j);
                Layer otherLayer = other.layers.get(j);
                if (!layer.canStack(otherLayer)) return i + 1;
                stacked = true;
            }
        }
        return stacked ? 0 : -1;
    }

    public ShapeItem stack(ShapeItem other) {
        ShapeItem item = new ShapeItem(layers.copy());
        int level = stackLevel(other);
        for (int i = 0; i < other.layers.size; i++) {
            if (i + level < item.layers.size) {
                item.layers.set(i + level, item.layers.get(i + level).stack(other.layers.get(i)));
            } else if (i + level < 4) {
                item.layers.add(other.layers.get(i));
            }
        }
        item.fall();
        return item;
    }

    public Seq<ShapeItem> split() {
        ShapeItem a = new ShapeItem(layers.map(l -> l.split().get(0)));
        ShapeItem b = new ShapeItem(layers.map(l -> l.split().get(1)));
        a.fall();
        b.fall();
        return Seq.with(a, b);
    }

    public ShapeItem rotate(boolean clockwise) {
        ShapeItem item = new ShapeItem(layers.map(l -> l.rotate(clockwise)));
        item.fall();
        return item;
    }

    public void fall() {
        for (int i = 0; i < layers.size; i++) {
            Layer layer = layers.get(i);
            if (!layer.empty()) continue;
            layers.remove(i);
            i--;
        }
    }

    public void draw(float x, float y, float size) {
        for (int i = 0; i < layers.size; i++) {
            float qSize = size - (i * size / 5f + 1);
            layers.get(i).draw(x, y, qSize, size);
        }
    }

    public boolean empty() {
        return layers.find(Layer::empty) != null;
    }

    public static boolean validCode(String str) {
        for (String layer : str.split(":"))
            if (!Layer.validCode(layer)) return false;
        return true;
    }

    public static ShapeItem fromString(String str) {
        ShapeItem item = new ShapeItem();
        for (String layer : str.split(":"))
            item.layers.add(Layer.fromString(layer));
        return item;
    }

    @Override
    public String toString(){
        String out = "";
        for (int i = 0; i < layers.size; i++) {
            out += layers.get(i).toString();
            if (i != layers.size - 1) out += ":";
        }
        return out;
    }

    public static class Layer {
        public static final Layer empty = new Layer(Seq.with(Quadrant.empty, Quadrant.empty, Quadrant.empty, Quadrant.empty));
        public final Seq<Quadrant> quadrants;

        public Layer() {
            quadrants = new Seq<>();
        }

        public Layer(Seq<Quadrant> quadrants) {
            if (quadrants.size != 4) throw new RuntimeException("you need 4 quadrants");
            this.quadrants = quadrants;
        }

        public Layer stack(Layer other) {
            Layer layer = new Layer();
            for (int i = 0; i < quadrants.size; i++) {
                Quadrant quadrant = quadrants.get(i);
                Quadrant otherQuadrant = other.quadrants.get(i);
                layer.quadrants.add(quadrant.empty() ? otherQuadrant : quadrant);
            }
            return layer;
        }

        public boolean canStack(Layer other) {
            for (int i = 0; i < quadrants.size; i++)
                if (!quadrants.get(i).empty() && !other.quadrants.get(i).empty()) return false;
            return true;
        }

        public Seq<Layer> split() {
            Layer a = new Layer(Seq.with(quadrants.get(0), quadrants.get(1), Quadrant.empty, Quadrant.empty));
            Layer b = new Layer(Seq.with(Quadrant.empty, Quadrant.empty, quadrants.get(2), quadrants.get(3)));
            return Seq.with(a, b);
        }

        public Layer rotate(boolean clockwise) {
            if (clockwise)
                return new Layer(Seq.with(quadrants.get(3), quadrants.get(0), quadrants.get(1), quadrants.get(2)));
            else
                return new Layer(Seq.with(quadrants.get(1), quadrants.get(2), quadrants.get(3), quadrants.get(0)));
        }

        public boolean empty() {
            return quadrants.find(q -> !q.empty()) == null;
        }

        public void draw(float x, float y, float size, float origSize) {
            for (int i = 0; i < quadrants.size; i++) {
                float qSize = size / 2f;
                quadrants.get(i).draw(i, x, y, qSize, origSize);
            }
        }

        public static boolean validCode(String str) {
            if (str.length() != 8) return false;
            for (int i = 0; i < 4; i++)
                if (!Quadrant.validCode(str.substring(i * 2, i * 2 + 2))) return false;
            return true;
        }

        public static Layer fromString(String str) {
            Layer layer = new Layer();
            for (int i = 0; i < 4; i++)
                layer.quadrants.add(Quadrant.fromString(str.substring(i * 2, i * 2 + 2)));
            return layer;
        }

        @Override
        public String toString(){
            String out = "";
            for (int i = 0; i < quadrants.size; i++) out += quadrants.get(i).toString();
            return out;
        }
    }

    public static class Quadrant {
        public static final Quadrant empty = new Quadrant(Shape.empty, ShapeColor.empty);
        public final Shape shape;
        public final ShapeColor color;

        public Quadrant(Shape shape, ShapeColor color) {
            this.shape = shape;
            this.color = color;
        }

        public void draw(int i, float x, float y, float size, float origSize) {
            Lines.stroke(origSize / 18f);
            Draw.color(color.color);
            shape.draw(i, x, y, size);
            Draw.color();
        }

        public boolean empty() {
            return shape == Shape.empty;
        }

        public static boolean validCode(String code) {
            if (code.length() != 2) return false;
            return Shape.fromString(code.substring(0, 1)) != null && ShapeColor.fromString(code.substring(1, 2)) != null;
        }

        public static Quadrant fromString(String code) {
            return new Quadrant(Shape.fromString(code.substring(0, 1)), ShapeColor.fromString(code.substring(1, 2)));
        }

        @Override
        public String toString() {
            return shape.toString() + color.toString();
        }
    }

    public enum Shape {
        circle("C") {
            @Override
            public void draw(int i, float x, float y, float size) {
                ShapeDraw.arc(x, y, size, 1/4f, 360 - i * 90, outline);
            }
        }, rectangle("R") {
            @Override
            public void draw(int i, float x, float y, float size) {
                ShapeDraw.square(x, y, size, 360 - i * 90, outline);
            }
        }, windmill("W") {
            @Override
            public void draw(int i, float x, float y, float size) {
                ShapeDraw.windmill(x, y, size, 360 - i * 90, outline);
            }
        }, star("S") {
            @Override
            public void draw(int i, float x, float y, float size) {
                ShapeDraw.star(x, y, size, 360 - i * 90, outline);
            }
        }, empty("-") {@Override public void draw(int i, float x, float y, float size) {}};

        final String code;
        Shape(String code) {
            this.code = code;
        }

        public abstract void draw(int i, float x, float y, float size);
        public static Shape fromString(String code) {
            return Seq.with(values()).find(s -> s.code.equals(code));
        }

        @Override
        public String toString() {
            return code;
        }
    }

    public enum ShapeColor {
        red("r", Color.valueOf("ff666a")),
        green("g", Color.valueOf("78ff66")),
        blue("b", Color.valueOf("66a7ff")),
        yellow("y", Color.valueOf("fcf52a")),
        purple("p", Color.valueOf("dd66ff")),
        cyan("c", Color.valueOf("87fff5")),
        uncolored("u", Color.valueOf("aaaaaa")),
        white("w", Color.valueOf("ffffff")),
        empty("-", Color.valueOf("00000000"));

        final String code;
        final Color color;
        ShapeColor(String code, Color color) {
            this.code = code;
            this.color = color;
        }

        public static ShapeColor fromString(String code) {
            return Seq.with(values()).find(s -> s.code.equals(code));
        }

        @Override
        public String toString() {
            return code;
        }
    }
}
