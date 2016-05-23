/**
 *  Copyright 2015 Mike Reed
 */

#include "image.h"
#include "GCanvas.h"
#include "GBitmap.h"
#include "GColor.h"
#include "GRect.h"
#include <string>

#define RAMP_W      1
#define RAMP_H     28

static void draw_solid_ramp(GCanvas* canvas) {
    const float c = 1.0 / 512;
    const float d = 1.0 / 256;

    const struct {
        GColor  fC0, fDC;
    } rec[] = {
        { GColor::MakeARGB(1,   c,   c,   c), GColor::MakeARGB(0,  d,  d,  d) },   // grey
        { GColor::MakeARGB(1, 1-c,   0,   0), GColor::MakeARGB(0, -d,  0,  0) },   // red
        { GColor::MakeARGB(1,   0,   c,   c), GColor::MakeARGB(0,  0,  d,  d) },   // cyan
        { GColor::MakeARGB(1,   0, 1-c,   0), GColor::MakeARGB(0,  0, -d,  0) },   // green
        { GColor::MakeARGB(1,   c,   0,   c), GColor::MakeARGB(0,  d,  0,  d) },   // magenta
        { GColor::MakeARGB(1,   0,   0, 1-c), GColor::MakeARGB(0,  0,  0, -d) },   // blue
        { GColor::MakeARGB(1,   c,   c,   0), GColor::MakeARGB(0,  d,  d,  0) },   // yellow
    };

    
    for (int y = 0; y < GARRAY_COUNT(rec); ++y) {
        GColor color = rec[y].fC0;
        GColor delta = rec[y].fDC;
        for (int x = 0; x < 256; x++) {
            const GRect rect = GRect::MakeXYWH(x * RAMP_W, y * RAMP_H, RAMP_W, RAMP_H);
            canvas->fillRect(rect, color);
            color.fA += delta.fA;
            color.fR += delta.fR;
            color.fG += delta.fG;
            color.fB += delta.fB;
        }
    }
}

static void offset(GRect* r, float dx, float dy) {
    r->fLeft += dx;
    r->fRight += dx;
    r->fTop += dy;
    r->fBottom += dy;
}

static void draw_blend_ramp(GCanvas* canvas, const GColor& bg) {
    canvas->clear(bg);

    GRect rect = GRect::MakeXYWH(-25, -25, 70, 70);

    int delta = 8;
    for (int i = 0; i < 200; i += delta) {
        float r = i / 200.0;
        float g = fabs(cos(i/40.0));
        float b = fabs(sin(i/50.0));
        GColor color = GColor::MakeARGB(0.3, r, g, b);
        canvas->fillRect(rect, color);
        offset(&rect, delta, delta);
    }
}

static void draw_blend_white(GCanvas* canvas) {
    draw_blend_ramp(canvas, GColor::MakeARGB(1, 1, 1, 1));
}

static void draw_blend_black(GCanvas* canvas) {
    draw_blend_ramp(canvas, GColor::MakeARGB(1, 0, 0, 0));
}

const CS575DrawRec gDrawRecs[] = {
    { draw_solid_ramp,  256 * RAMP_W, 7*RAMP_H, "solid_ramp"    },
    { draw_blend_white, 200, 200,               "blend_white"   },
    { draw_blend_black, 200, 200,               "blend_black"   },
    { NULL, 0, 0, NULL    },
};
