package shapez.content.item.quad;

import arc.struct.Seq;
import shapez.Main;
import shapez.ShapeDraw;

public enum ShapeType {
    circle("C") {
        @Override
        public void draw(int i, float x, float y, float size) {
            ShapeDraw.arc(x, y, size, 1/4f, 360 - i * 90, Main.outline);
        }
    }, rectangle("R") {
        @Override
        public void draw(int i, float x, float y, float size) {
            ShapeDraw.square(x, y, size, 360 - i * 90, Main.outline);
        }
    }, windmill("W") {
        @Override
        public void draw(int i, float x, float y, float size) {
            ShapeDraw.windmill(x, y, size, 360 - i * 90, Main.outline);
        }
    }, star("S") {
        @Override
        public void draw(int i, float x, float y, float size) {
            ShapeDraw.star(x, y, size, 360 - i * 90, Main.outline);
        }
    }, empty("-") {@Override public void draw(int i, float x, float y, float size) {}};

    public final String code;
    ShapeType(String code) {
        this.code = code;
    }

    public abstract void draw(int i, float x, float y, float size);
    public static ShapeType fromString(String code) {
        return Seq.with(values()).find(s -> s.code.equals(code));
    }

    @Override
    public String toString() {
        return code;
    }
}
