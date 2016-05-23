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

///////////////////////////////////////////////////////////////////////////////////////////////////

static void draw_spocks_quad(GCanvas* canvas) {
    const int N = 300;

    GBitmap tex;
    tex.readFromFile("apps/spock.png");

    for (int y = 0; y < 2; ++y) {
        for (int x = 0; x < 2; ++x) {
            canvas->fillBitmapRect(tex, GRect::MakeXYWH(x * N - N/2, y * N - N/2, N, N));
        }
    }
}

static void draw_spocks_zoom(GCanvas* canvas) {
    const int N = 300;
    
    GBitmap tex;
    tex.readFromFile("apps/spock.png");

    for (int i = 0; i < 9; ++i) {
        GRect r = GRect::MakeLTRB(i * 10, i * 10, N - i * 10, N - i * 10);
        canvas->fillBitmapRect(tex, r);
    }
}

// After scaling by this, the caller need just cast to (int)
static const float gScaleUnitToByte = 255.99999f;

static GPixel pin_and_premul_to_pixel(GColor c) {
    c = c.pinToUnit();
    
    float a = c.fA * gScaleUnitToByte;
    int ia = (int)a;
    int ir = (int)(a * c.fR);
    int ig = (int)(a * c.fG);
    int ib = (int)(a * c.fB);
    return GPixel_PackARGB(ia, ir, ig, ib);
}

static void make_circle(const GBitmap& bitmap, const GColor& color) {
    const GPixel px = pin_and_premul_to_pixel(color);
    
    const float cx = (float)bitmap.width() / 2;
    const float cy = (float)bitmap.height() / 2;
    const float radius = cx - 1;
    const float radius2 = radius * radius;
    
    GPixel* dst = bitmap.pixels();
    for (int y = 0; y < bitmap.height(); ++y) {
        const float dy = y - cy;
        for (int x = 0; x < bitmap.width(); ++x) {
            const float dx = x - cx;
            const float dist2 = dx*dx + dy*dy;
            if (dist2 <= radius2) {
                dst[x] = px;
            } else {
                dst[x] = 0; // transparent
            }
        }
        dst = (GPixel*)((char*)dst + bitmap.rowBytes());
    }
}

class AutoBitmap : public GBitmap {
public:
    AutoBitmap(int width, int height) {
        // just to exercise the ability to have a rowbytes > width
        const int slop = (height >> 1) * sizeof(GPixel);

        fWidth = width;
        fHeight = height;
        fRowBytes = fWidth * sizeof(GPixel) + slop;
        fPixels = (GPixel*)malloc(fRowBytes * fHeight);
    }

    ~AutoBitmap() {
        free(fPixels);
    }
};

static void draw_bm_circles(GCanvas* canvas) {
    const int N = 300;

    AutoBitmap src(N, N);

    const struct {
        GRect   fRect;
        GColor  fColor;
    } recs[] = {
        { GRect::MakeXYWH(  0,   0,   N,   N), GColor::MakeARGB(1, 1, 1, 1) },

        { GRect::MakeXYWH(  0,   0, N/2, N/2), GColor::MakeARGB(0.8f, 0, 0, 1) },
        { GRect::MakeXYWH(N/2,   0, N/2, N/2), GColor::MakeARGB(0.6f, 0, 1, 0) },
        { GRect::MakeXYWH(  0, N/2, N/2, N/2), GColor::MakeARGB(0.4f, 1, 0, 0) },
        { GRect::MakeXYWH(N/2, N/2, N/2, N/2), GColor::MakeARGB(0.2f, 0, 0, 0) },

        { GRect::MakeXYWH(  0, N/3,   N, N/3), GColor::MakeARGB(0.5f, 1, 1, 0) },
        { GRect::MakeXYWH(N/3,   0, N/3,   N), GColor::MakeARGB(0.5f, 0, 1, 1) },
        { GRect::MakeXYWH(N/3, N/3, N/3, N/3), GColor::MakeARGB(0.5f, 1, 0, 1) },
    };

    for (int i = 0; i < GARRAY_COUNT(recs); ++i) {
        make_circle(src, recs[i].fColor);
        canvas->fillBitmapRect(src, recs[i].fRect);
    }
}

static void draw_circle_big(GCanvas* canvas) {
    const int N = 300;
    const float alpha = 0.4f;
    const GColor colors[] = {
        GColor::MakeARGB(alpha, 1, 0, 0),
        GColor::MakeARGB(alpha, 0, 1, 0),
        GColor::MakeARGB(alpha, 0, 0, 1),
    };

    int x = 0;
    int n = N;
    for (int i = 0; n > 4; ++i) {
        AutoBitmap src(n, n);
        make_circle(src, colors[i % GARRAY_COUNT(colors)]);
        canvas->fillBitmapRect(src, GRect::MakeXYWH(x, 0, N, N));
        x += N / 12;
        n >>= 1;
    }
}

///////////////////////////////////////////////////////////////////////////////////////////////////

const CS575DrawRec gDrawRecs[] = {
    { draw_solid_ramp,  256 * RAMP_W, 7*RAMP_H, "solid_ramp"    },
    { draw_blend_white, 200, 200,               "blend_white"   },
    { draw_blend_black, 200, 200,               "blend_black"   },

    { draw_spocks_quad, 300, 300,               "spocks_quad"   },
    { draw_spocks_zoom, 300, 300,               "spocks_zoom"   },
    { draw_bm_circles,  300, 300,               "circles_blend" },
    { draw_circle_big,  400, 300,               "circles_fat"   },
    { NULL, 0, 0, NULL    },
};
