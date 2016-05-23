/**
 *  Copyright 2015 Mike Reed
 */

#include "image.h"
#include "GCanvas.h"
#include "GBitmap.h"
#include "GColor.h"
#include "GPoint.h"
#include "GRect.h"
#include "GShader.h"
#include <string>


#define STRIP_SIZE  100
const int STRIP_SIDE = 4;
const int STRIP_N    = STRIP_SIDE*STRIP_SIDE;
const int STRIP_W   = STRIP_SIZE * STRIP_SIDE;
const int STRIP_H   = STRIP_SIZE * STRIP_SIDE;

static void draw_strip(GCanvas* canvas, GShader* a, GShader* b) {
    GRect r = GRect::MakeWH(STRIP_SIZE, STRIP_SIZE);
    float t = 0;
    float dt = 1.0f / (STRIP_N - 1);
    canvas->save();
    for (int y = 0; y < STRIP_SIDE; ++y) {
        canvas->save();
        for (int x = 0; x < STRIP_SIDE; ++x) {
            GShader* s = GShader::FromLerp(a, b, t);
            if (s) {
                canvas->shadeRect(r, s);
                delete s;
            }
            canvas->translate(r.width(), 0);
            t += dt;
        }
        canvas->restore();
        canvas->translate(0, r.height());
    }
    canvas->restore();
}

static void draw_lerp0(GCanvas* canvas) {
    GBitmap tex;
    tex.readFromFile("apps/spock.png");
    GShader* a = GShader::FromBitmap(tex, GRect::MakeWH(STRIP_SIZE, STRIP_SIZE));

    const GPoint pts[]{ {0, 0}, {STRIP_SIZE,STRIP_SIZE} };
    const GColor colors[] = { GColor::MakeARGB(1, 1, 0, 0), GColor::MakeARGB(1,0, 0, 1) };
    GShader* b = GShader::FromLinearGradient(pts, colors);
    draw_strip(canvas, a, b);
    delete b;
    delete a;
}

static void draw_lerp1(GCanvas* canvas) {
    const GPoint pts[]{ {0, 0}, {STRIP_SIZE,STRIP_SIZE} };
    const GColor colors[] = { GColor::MakeARGB(1, 1, 0, 0), GColor::MakeARGB(1,0, 0, 1) };
    GShader* a = GShader::FromLinearGradient(pts, colors);
    
    const GColor colorsb[] = { GColor::MakeARGB(1, 0, 1, 0), GColor::MakeARGB(1, 1, 0, 1) };
    GShader* b = GShader::FromRadialGradient(GPoint{STRIP_SIZE/2, STRIP_SIZE/2}, STRIP_SIZE*3/4,
                                             colorsb);
    
    draw_strip(canvas, a, b);
    delete b;
    delete a;
}

///////////////////////////////////////////////////////////////////////////////////////////////////

static void make_patch(GBitmap* patch, GIRect* center) {
    center->setXYWH(28, 28, 8, 8);
    patch->readFromFile("apps/nine.png");
}

static void make_patch2(GBitmap* patch, GIRect* center) {
    center->setXYWH(27, 27, 1, 1);
    patch->readFromFile("apps/newpatch.png");
}

static void draw_nine_proc(GCanvas* canvas, void (*proc)(GBitmap*, GIRect*)) {
    GBitmap patch;
    GIRect center;
    proc(&patch, &center);
    const float w = patch.width();
    const float h = patch.height();
    
    const GRect dst[] = {
        GRect::MakeXYWH(0, 0, w, h),
        GRect::MakeXYWH(w + 20, 0, 200, h),
        GRect::MakeXYWH(0, h + 20, w, 200),
        GRect::MakeXYWH(w + 20, h + 20, 200, 200),
    };

    canvas->translate(60, 60);
    for (auto& d : dst) {
        canvas->fillBitmapNine(patch, center, d);
    }
}

static void draw_nine0(GCanvas* canvas) {
    draw_nine_proc(canvas, make_patch);
}

static void draw_nine1(GCanvas* canvas) {
    draw_nine_proc(canvas, make_patch2);
}

///////////////////////////////////////////////////////////////////////////////////////////////////

const CS575DrawRec gDrawRecs[] = {
    { draw_lerp0,     STRIP_W, STRIP_H,     7, "lerp0" },
    { draw_lerp1,     STRIP_W, STRIP_H,     7, "lerp1" },

    { draw_nine0,     400, 400,             7, "nine0" },
    { draw_nine1,     400, 400,             7, "nine1" },

    { NULL, 0, 0, 0, NULL },
};
