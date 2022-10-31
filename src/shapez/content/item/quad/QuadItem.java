package shapez.content.item.quad;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.Pixmap;
import arc.graphics.Texture;
import arc.graphics.g2d.*;
import arc.graphics.gl.FrameBuffer;
import arc.struct.IntMap;
import arc.struct.ObjectIntMap;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Tmp;
import mindustry.Vars;
import org.w3c.dom.Text;
import shapez.content.item.ColorItem;
import shapez.content.item.ShapeItem;

public class QuadItem extends ShapeItem {
    private static final ObjectMap<String, Texture> cache = new ObjectMap<>();
    public final Seq<Layer> layers;

    public QuadItem() {
        layers = new Seq<>();
    }

    public QuadItem(Seq<Layer> layers) {
        if (layers.size > 4) throw new RuntimeException("wrong layer count");
        this.layers = layers;
    }

    public boolean canStack(QuadItem other) {
        return stackLevel(other) != -1;
    }

    public int stackLevel(QuadItem other) {
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

    public QuadItem paint(ColorItem color) {
        return new QuadItem(layers.map(l -> l.paint(color)));
    }

    public QuadItem stack(QuadItem other) {
        QuadItem item = new QuadItem(layers.copy());
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

    public Seq<QuadItem> split() {
        QuadItem a = new QuadItem(layers.map(l -> l.split().get(0)));
        QuadItem b = new QuadItem(layers.map(l -> l.split().get(1)));
        a.fall();
        b.fall();
        return Seq.with(a, b);
    }

    public QuadItem rotate(boolean clockwise) {
        QuadItem item = new QuadItem(layers.map(l -> l.rotate(clockwise)));
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

    public void cache() {
        FrameBuffer buffer = new FrameBuffer(256, 256);
        buffer.begin(Color.clear);
        Draw.proj(0, 0, 256, -256);
        drawRaw(128f, -128f, 241);
        buffer.end();
        cache.put(toString(), buffer.getTexture());
    }

    public void drawRaw(float x, float y, float size) {
        for (int i = 0; i < layers.size; i++) {
            float qSize = size - (i * size / 4f + 1);
            layers.get(i).draw(x, y, qSize, size);
        }
    }

    public void draw(float x, float y, float size) {
        if (!cache.containsKey(toString())) cache();
        Draw.rect(Draw.wrap(cache.get(toString())), x, y, size, size);
    }

    public boolean empty() {
        return layers.find(Layer::empty) != null;
    }

    public static boolean validCode(String str) {
        for (String layer : str.split(":"))
            if (!Layer.validCode(layer)) return false;
        return true;
    }

    public static QuadItem fromString(String str) {
        QuadItem item = new QuadItem();
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
}
