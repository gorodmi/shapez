package shapez.content.item;

import arc.graphics.Color;
import arc.struct.Seq;

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

    public final String code;
    public final Color color;
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
