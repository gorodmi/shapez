package shapez;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Angles;
import arc.math.Mathf;

public class ShapeDraw {
    public static void arc(float x, float y, float radius, float fraction, float rotation, Color outlineColor){
        Fill.arc(x, y, radius, fraction, rotation);
        Draw.color(outlineColor);
        int max = (int)(50 * fraction);
        Lines.beginLine();
        Lines.linePoint(x, y);
        for(int i = 0; i <= max; i++){
            float a = (float)i / max * fraction * 360f + rotation;
            float x1 = Angles.trnsx(a, radius);
            float y1 = Angles.trnsy(a, radius);
            Lines.linePoint(x + x1, y + y1);
        }
        Lines.linePoint(x, y);
        Lines.endLine();
    }

    public static void square(float x, float y, float size, float rotation, Color outlineColor) {
        float sin = Mathf.sinDeg(rotation);
        float cos = Mathf.cosDeg(rotation);
        float x1 = 0; float y1 = 0;
        float x2 = 0; float y2 = size;
        float x3 = size; float y3 = size;
        float x4 = size; float y4 = 0;
        float fx1 = x + x1 * cos - y1 * sin; float fy1 = y + x1 * sin + y1 * cos;
        float fx2 = x + x2 * cos - y2 * sin; float fy2 = y + x2 * sin + y2 * cos;
        float fx3 = x + x3 * cos - y3 * sin; float fy3 = y + x3 * sin + y3 * cos;
        float fx4 = x + x4 * cos - y4 * sin; float fy4 = y + x4 * sin + y4 * cos;
        Fill.polyBegin();
        Fill.polyPoint(fx1, fy1);
        Fill.polyPoint(fx2, fy2);
        Fill.polyPoint(fx3, fy3);
        Fill.polyPoint(fx4, fy4);
        Fill.polyEnd();
        Draw.color(outlineColor);
        Lines.beginLine();
        Lines.linePoint(fx1, fy1);
        Lines.linePoint(fx2, fy2);
        Lines.linePoint(fx3, fy3);
        Lines.linePoint(fx4, fy4);
        Lines.linePoint(fx1, fy1);
        Lines.endLine();
    }

    public static void windmill(float x, float y, float size, float rotation, Color outlineColor) {
        float sin = Mathf.sinDeg(rotation);
        float cos = Mathf.cosDeg(rotation);
        float x1 = 0; float y1 = 0;
        float x2 = 0; float y2 = size * 2 / 3;
        float x3 = size; float y3 = size;
        float x4 = size; float y4 = 0;
        float fx1 = x + x1 * cos - y1 * sin; float fy1 = y + x1 * sin + y1 * cos;
        float fx2 = x + x2 * cos - y2 * sin; float fy2 = y + x2 * sin + y2 * cos;
        float fx3 = x + x3 * cos - y3 * sin; float fy3 = y + x3 * sin + y3 * cos;
        float fx4 = x + x4 * cos - y4 * sin; float fy4 = y + x4 * sin + y4 * cos;
        Fill.polyBegin();
        Fill.polyPoint(fx1, fy1);
        Fill.polyPoint(fx2, fy2);
        Fill.polyPoint(fx3, fy3);
        Fill.polyPoint(fx4, fy4);
        Fill.polyEnd();
        Draw.color(outlineColor);
        Lines.beginLine();
        Lines.linePoint(fx1, fy1);
        Lines.linePoint(fx2, fy2);
        Lines.linePoint(fx3, fy3);
        Lines.linePoint(fx4, fy4);
        Lines.linePoint(fx1, fy1);
        Lines.endLine();
    }

    public static void star(float x, float y, float size, float rotation, Color outlineColor) {
        float sin = Mathf.sinDeg(rotation);
        float cos = Mathf.cosDeg(rotation);
        float x1 = 0; float y1 = 0;
        float x2 = 0; float y2 = size * 2 / 3;
        float x3 = size; float y3 = size;
        float x4 = size * 2 / 3; float y4 = 0;
        float fx1 = x + x1 * cos - y1 * sin; float fy1 = y + x1 * sin + y1 * cos;
        float fx2 = x + x2 * cos - y2 * sin; float fy2 = y + x2 * sin + y2 * cos;
        float fx3 = x + x3 * cos - y3 * sin; float fy3 = y + x3 * sin + y3 * cos;
        float fx4 = x + x4 * cos - y4 * sin; float fy4 = y + x4 * sin + y4 * cos;
        Fill.polyBegin();
        Fill.polyPoint(fx1, fy1);
        Fill.polyPoint(fx2, fy2);
        Fill.polyPoint(fx3, fy3);
        Fill.polyPoint(fx4, fy4);
        Fill.polyEnd();
        Draw.color(outlineColor);
        Lines.beginLine();
        Lines.linePoint(fx1, fy1);
        Lines.linePoint(fx2, fy2);
        Lines.linePoint(fx3, fy3);
        Lines.linePoint(fx4, fy4);
        Lines.linePoint(fx1, fy1);
        Lines.endLine();
    }
}
