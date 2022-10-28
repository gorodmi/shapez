package shapez.content.item.quad;

import arc.struct.Seq;
import shapez.content.item.ColorItem;

public class Layer {
    public static final Layer empty = new Layer(Seq.with(Quadrant.empty, Quadrant.empty, Quadrant.empty, Quadrant.empty));
    public final Seq<Quadrant> quadrants;

    public Layer() {
        quadrants = new Seq<>();
    }

    public Layer(Seq<Quadrant> quadrants) {
        if (quadrants.size != 4) throw new RuntimeException("you need 4 quadrants");
        this.quadrants = quadrants;
    }

    public Layer paint(ColorItem color) {
        return new Layer(quadrants.map(q -> new Quadrant(q.shapeType, color.color)));
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
