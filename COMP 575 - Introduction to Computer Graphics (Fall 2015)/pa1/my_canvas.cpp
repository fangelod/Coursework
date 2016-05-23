/*
 * Copyright 2015 Franz Dominno
 */

#include "GPixel.h"
#include "GRect.h"
#include "GColor.h"
#include "GCanvas.h"
#include "GBitmap.h"
#include "GMath.h"

    
class Mine: public GCanvas {
  public:
	Mine(const GBitmap& bitmap);

	void clear(const GColor& color) {
		GColor c = color.pinToUnit();
		unsigned a = (int)(c.fA * 255.99999);
		unsigned r = (int)(c.fR * c.fA * 255.99999);
		unsigned g = (int)(c.fG * c.fA * 255.99999);
		unsigned b = (int)(c.fB * c.fA * 255.99999);

		GPixel* dst = draw.fPixels;
		for (int y = 0; y < draw.height(); ++y) {
			for (int x = 0; x < draw.width(); ++x) {
				dst[x] = GPixel_PackARGB(a, r, g, b);
			}
			dst = (GPixel*)((char*)dst + draw.rowBytes());
		}
	}

	void fillRect(const GRect& rect, const GColor& color) {
		// dimensions of source rectangle
		int le = GRoundToInt(rect.left());
		int to = GRoundToInt(rect.top());
		int ri = GRoundToInt(rect.right());
		int bo = GRoundToInt(rect.bottom());

		// fix rectangle boundaries
		if (le < 0) {
			le = 0;
		}
		if (to < 0) {
			to = 0;
		}
		if (ri > draw.width()) {
			ri = draw.width();
		}
		if (bo > draw.height()) {
			bo = draw.height();
		}
		
		// end method if rectangle is of zero size in either x or y
		if ((le < 0 && ri < 0) || (le > draw.width() && ri > draw.width())) {
			return;
		}
		if ((to < 0 && bo < 0) || (to > draw.height() && bo > draw.height())) {
			return;
		}
		if ((to == bo) || (le == ri)) {
			return;
		}

		// Create rectangle with fixed boundaries
		GRect fix = GRect::MakeLTRB(le, to, ri, bo);

		// Find intersection of last rectangle and current rectangle
		float left = std::max(lastr.fLeft, fix.fLeft);
        float top = std::max(lastr.fTop, fix.fTop);
        float right = std::min(lastr.fRight, fix.fRight);
        float bottom = std::min(lastr.fBottom, fix.fBottom);
        bool sect = (left < right && top < bottom);

        // Print messages to help debug
        /*
        printf("rect.fLeft: %f\n", rect.left());
        printf("rect.fTop: %f\n", rect.top());
        printf("rect.fRight: %f\n", rect.right());
        printf("rect.fBottom: %f\n", rect.bottom());
        printf("draw.width(): %i\n", draw.width());
        printf("draw.height(): %i\n", draw.height());
        printf("fix.fLeft: %f\n", fix.left());
        printf("fix.fTop: %f\n", fix.top());
        printf("fix.fRight: %f\n", fix.right());
        printf("fix.fBottom: %f\n", fix.bottom());
        printf("lastr.fLeft: %f\n", lastr.left());
        printf("lastr.fTop: %f\n", lastr.top());
        printf("lastr.fRight: %f\n", lastr.right());
        printf("lastr.fBottom: %f\n", lastr.bottom());
        printf("left: %f\n", left);
        printf("top: %f\n", top);
        printf("right: %f\n", right);
        printf("bottom: %f\n", bottom);
        printf("\n");
		*/
		
        // If alpha value is 0, end method
        if (color.fA <= 0) {
        	return;
        }

        // Prepare color to place into bitmap
        GColor c = color.pinToUnit();
        unsigned a = (int)(c.fA * 255.99999);
        unsigned r = (int)(c.fR * c.fA * 255.99999);
        unsigned g = (int)(c.fG * c.fA * 255.99999);
        unsigned b = (int)(c.fB * c.fA * 255.99999);

        // Put color into the rectangle
		GPixel* dst = draw.fPixels;
		dst = (GPixel*)((char*)dst + to * draw.rowBytes());
		for (int y = fix.fTop; y < fix.fBottom; ++y) {
			for (int x = fix.fLeft; x < fix.fRight; ++x) {
				dst[x] = GPixel_PackARGB(a, r, g, b);
			}
			dst = (GPixel*)((char*)dst + draw.rowBytes());
		}

		// Replace the intersecting region color with the blended color
		if (sect) {
			GColor o = lastc.pinToUnit();
			float sA = c.fA + (o.fA * (1 - c.fA));
			float sR;
			float sG;
			float sB;
			
			if (sA == 0.0f) {
				sR = 0.0f;
				sG = 0.0f;
				sB = 0.0f;
			} else if (o.fA == 1.0f) {
				sA = 1.0f;
				sR = (c.fR * c.fA) + (o.fR * (1.0f - c.fA));
				sG = (c.fG * c.fA) + (o.fG * (1.0f - c.fA));
				sB = (c.fB * c.fA) + (o.fB * (1.0f - c.fA));
			} else {
				sR = (c.fR * c.fA) + (o.fR * o.fA * (1.0f - c.fA)) / sA;
				sG = (c.fG * c.fA) + (o.fG * o.fA * (1.0f - c.fA)) / sA;
				sB = (c.fB * c.fA) + (o.fB * o.fA * (1.0f - c.fA)) / sA;
			}
			GColor blend = GColor::MakeARGB(sA, sR, sG, sB);
			
			unsigned nA = (int)(sA * 255.99999);
			unsigned nR = (int)(sR * sA * 255.99999);
			unsigned nG = (int)(sG * sA * 255.99999);
			unsigned nB = (int)(sB * sA * 255.99999);
			
			GPixel* dst = draw.fPixels;
			dst = (GPixel*)((char*)dst + (int)top * draw.rowBytes());
	        for (int y = top; y < bottom; ++y)  {
        	    for (int x = left; x < right; ++x) {
                	dst[x] = GPixel_PackARGB(nA, nR, nG, nB);
			  	}
                dst = (GPixel*)((char*)dst + draw.rowBytes());
            }
			lastc = blend;
		}
		
		// if first time fillRect is run or if no intersection
		if (!sect) {
			lastc = color;
		}
		lastr = fix;
	}

	/**
	 *	Scale and translate the bitmap such that it fills the specific rectangle;
	 *
	 *	Any area in the rectangle that is outside of the bounds of the canvas is ignored.
	 *
	 *	If a given pixel in the bitmap is not opaque (e.g. GPixel_GetA() < 255) then blend it
	 *	using SRCOVER blend mode
	 */
	 /*
	void fillBitmapRect(const GBitmap& src, const GRect& dst) {
		// work backwords from dst

		// bitmap width and height
		int bW = dst.width();
		int bH = dst.height();

		// rectangle width and height
		int rW = dst.width();
		int rH = dst.height();

		// Scaling factor
		bool scale = false;
		float sW = 1;
		float sH = 1;
		if (bW != rW) {
			scale = true;
		}
		if (bH != rH) {
			scale = true;
		}
		if (scale) {
			sW = bW/rW;
			sH = bH/rH;
		}

		float px; float py;
		float tx; float ty;
		float sx; float sy;
		float pointMatrix[1][3] = {{px}, {py}, {1}};
		float tranlateMatrix[3][3] = {{1, 1, tx}, {0, 1, ty}, {0, 0, 1}};
		float scaleMatrix[3][3] = {{sx, 0, 0}, {0, sy, 0}, {0, 0, 1}};
		float fixMatrix[3][3] = {{sx, 0 , tx}, {0, sy, ty}, {0, 0, 1}};

		// Fill in destination rectangle
		GPixel* dst = draw.fPixels;
		for (int y = dst.top(); y < dst.bottom(); ++y) {
        	for (int x = dst.left(); x < dst.right(); ++x) {
        		GPixel p = src.getAddr(x * sW, y * sH);
        		if (GPixel_GetA(p) < 255) {
        			// blend
        		}
               	dst[x] = GPixel_PackARGB(GPixel_GetA(p), GPixel_GetR(p), GPixel_GetG(p), GPixel_GetB(p));
			}
            dst = (GPixel*)((char*)dst + draw.rowBytes());
        }
	}
*/
  private:
	GBitmap draw;
	GRect lastr;
	GColor lastc;
};

Mine::Mine(const GBitmap& bitmap) {
	draw = bitmap;
}

GCanvas* GCanvas::Create(const GBitmap& bitmap) {
    if (bitmap.width() < 0 || bitmap.height() < 0 || (bitmap.rowBytes() < (bitmap.width() * sizeof(GPixel)))) {
        return NULL;
    } else {
        return new Mine(bitmap);
    }       
}


