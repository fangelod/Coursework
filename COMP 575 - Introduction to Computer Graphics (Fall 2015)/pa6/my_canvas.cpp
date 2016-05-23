/*
 * Copyright 2015 Franz Dominno
 */

#include "GPixel.h"
#include "GRect.h"
#include "GColor.h"
#include "GCanvas.h"
#include "GBitmap.h"
#include "GMath.h"
#include "GPoint.h"
#include "GShader.h"
#include <algorithm>
#include <vector>
#include <cmath>

class Mine: public GCanvas {
  public:
	Mine(const GBitmap& bitmap);

	/**
	 *	Fill the entire canvas with the specified color, using SRC porter-duff mode.
	 */
	void clear(const GColor& color) {
		// Prepare color to fill
		GColor c = color.pinToUnit();
		unsigned a = (int)(c.fA * 255.99999);
		unsigned r = (int)(c.fR * c.fA * 255.99999);
		unsigned g = (int)(c.fG * c.fA * 255.99999);
		unsigned b = (int)(c.fB * c.fA * 255.99999);

		// Fill entire bitmap with color
		GPixel* dst = draw.fPixels;
		for (int y = 0; y < draw.height(); ++y) {
			for (int x = 0; x < draw.width(); ++x) {
				dst[x] = GPixel_PackARGB(a, r, g, b);
			}
			dst = (GPixel*)((char*)dst + draw.rowBytes());
		}
	}

	/**
	 *	Fill the rectangle with color, using SRC_OVER porter-duff mode.
	 *
	 *	The affected pixels are those whose centers are "contained" inside the rectangle:
	 *		e.g. contained == center > min_edge && center <= max_edge
	 *
	 *	Any area in the rectangle that is outside of the bounds of the canvas is ignored.
	 */
	void fillRect(const GRect& rect, const GColor& color) {
		// Get dimensions of rectangle
		int le = GRoundToInt(rect.left());
		int to = GRoundToInt(rect.top());
		int ri = GRoundToInt(rect.right());
		int bo = GRoundToInt(rect.bottom());

		// Fix rectangle boundaries
		if (le < 0) {le = 0;}
		if (to < 0) {to = 0;}
		if (ri > draw.width()) {ri = draw.width();}
		if (bo > draw.height()) {bo = draw.height();}

		// Don't draw if rectangle is of zero size
		if ((le < 0 && ri < 0) || (le > draw.width() && ri > draw.width())) {
			return;
		}
		if ((to < 0 && bo < 0) || (to > draw.height() && bo > draw.height())) {
			return;
		}
		if ((to == bo) || (le == ri)) {
			return;
		}

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

        // Fill desired pixels
        GPixel* dst = draw.fPixels;
		dst = (GPixel*)((char*)dst + (int)to * draw.rowBytes());
        for (int y = to; y < bo; ++y)  {
    	    for (int x = le; x < ri; ++x) {
    	    	if (GPixel_GetA(*draw.getAddr(x, y)) > 0) {
    	    		// blend
					float sA = c.fA + ((GPixel_GetA(*draw.getAddr(x, y)) / 255.99999) * (1.0f - c.fA));
					float sR = (c.fR * c.fA) + ((GPixel_GetR(*draw.getAddr(x, y)) / 255.99999) * (1.0f - c.fA));
					float sG = (c.fG * c.fA) + ((GPixel_GetG(*draw.getAddr(x, y)) / 255.99999) * (1.0f - c.fA));
					float sB = (c.fB * c.fA) + ((GPixel_GetB(*draw.getAddr(x, y)) / 255.99999) * (1.0f - c.fA));
									
					unsigned nA = (int)(sA * 255.99999);
					unsigned nR = (int)(sR * 255.99999);
					unsigned nG = (int)(sG * 255.99999);
					unsigned nB = (int)(sB * 255.99999);

    	    		dst[x] = GPixel_PackARGB(nA, nR, nG, nB);
    	    	} else {
    	    		// Fill color unblended
    	    		dst[x] = GPixel_PackARGB(a, r, g, b);
    	    	}
		  	}
            dst = (GPixel*)((char*)dst + draw.rowBytes());
        }
	}

////////////////////////////////////////////////////////////////////////////////

	/**
	 *	Scale and translate the bitmap such that is fills the specific rectangle.
	 *
	 *	Any area in the rectangle that is outside of the bounds of the canvas is ignored.
	 *
	 *	If a given pixel in the bitmap is not opaque (e.g. GPixel_GetA() < 255) then blend it
	 *	using SRCOVER blend mode.
	 */
	void fillBitmapRect(const GBitmap& src, const GRect& dst) {
		//if (CTM[0] != 1 && CTM[4] != 1) {
		//	GShader* s = GShader::FromBitmap(src, CTM);
		//	shadeRect(dst, s);
		//} else {
			// Store dimensions of dst rectangle
			int left = dst.left();
			int top = dst.top();
			int right = dst.right();
			int bottom = dst.bottom();
			int rW = dst.width();
			int rH = dst.height();

			// Store dimensions of src bitmap
			int bW = src.width();
			int bH = src.height();

			// Find necessary scale factor for both width and height
			float sx = (float)bW/(float)rW;
			float sy = (float)bH/(float)rH;

			float tx = 0 - left;
			float ty = 0 - top;

			// Create matrix for scaling
			float scale[6] = 
				{sx, 0, 0, 
				 0, sy, 0};

			// Create matrix for translating
			float translate[6] =
				{1, 0, tx,
				 0, 1, ty};

			// Combine the matrices
			float both[6] = 
				{sx, 0, sx*tx,
				 0, sy, sy*ty};

			// For loop that takes each point in dst and find corresponding point in src
			GPixel* d = draw.fPixels;
			d = (GPixel*)((char*)d + (int)top * draw.rowBytes());
			for (int y = top; y < bottom; ++y) {
				for (int x = left; x < right; ++x) {
					// Only map pixels that are in the bitmap
					if ((x >= 0 && x < draw.width()) && (y >= 0 && y < draw.height())) {
						// Find corresponding point in src bitmap
						int xP;
						int yP;

						xP = both[0] * x + both[1] * y + both[2];
						yP = both[3] * x + both[4] * y + both[5];

						// Apply CTM to x and y
						int nX = (CTM[0] * x) + (CTM[1] * y) + (CTM[2]);
						int nY = (CTM[3] * x) + (CTM[4] * y) + (CTM[5]);

						// Prepare color to fill
						unsigned a = GPixel_GetA(*src.getAddr(xP, yP));
						unsigned r = GPixel_GetR(*src.getAddr(xP, yP));
						unsigned g = GPixel_GetG(*src.getAddr(xP, yP));
						unsigned b = GPixel_GetB(*src.getAddr(xP, yP));

						unsigned nA = a;
						unsigned nR = r;
						unsigned nG = g;
						unsigned nB = b;

						if (GPixel_GetA(*draw.getAddr(x, y)) > 0) {
		    	    		// blend
		    	    			// Get a, r, g, b from bitmap
							float lA = GPixel_GetA(*src.getAddr(xP, yP)) / 255.99999;
							float lR = GPixel_GetR(*src.getAddr(xP, yP)) / 255.99999;
							float lG = GPixel_GetG(*src.getAddr(xP, yP)) / 255.99999;
							float lB = GPixel_GetB(*src.getAddr(xP, yP)) / 255.99999;

								// Blend bitmap and dst
							float sA = lA + ((GPixel_GetA(*draw.getAddr(x, y)) / 255.99999) * (1.0f - lA));
							float sR = lR + ((GPixel_GetR(*draw.getAddr(x, y)) / 255.99999) * (1.0f - lA));
							float sG = lG + ((GPixel_GetG(*draw.getAddr(x, y)) / 255.99999) * (1.0f - lA));
							float sB = lB + ((GPixel_GetB(*draw.getAddr(x, y)) / 255.99999) * (1.0f - lA));
														
								// Change to unsigned int			
							nA = (int)(sA * 255.99999);
							nR = (int)(sR * 255.99999);
							nG = (int)(sG * 255.99999);
							nB = (int)(sB * 255.99999);

								// Place color into pixel
		    	    		d[x] = GPixel_PackARGB(nA, nR, nG, nB);
	    	    		} else {
	    	    			d[x] = GPixel_PackARGB(a, r, g, b);
	    	    		}	    		
					}
				}
				d = (GPixel*)((char*)d + draw.rowBytes());
			}
		//}
	}

////////////////////////////////////////////////////////////////////////////////

	/**
	 *	Fill the convex polygon with the color, following the same "containment" rule as
	 *	rectangles.
	 *
	 *	Any area in the polygon that is outside of the bounds of the canvas is ignored.
	 *
	 *	If the color's alpha is < 1, blend it using SRCOVER blend mode.
	 */
	void fillConvexPolygon(const GPoint* po, int count, const GColor& color) {
		// Consider points
			// int count = # of points

		// Do nothing if less than 3 points (i.e. not enough points to make a polygon)
		if (count < 3) {
			return;
		}

		// Adjust points according to CTM
			// Array to hold adjusted points
		GPoint* p = new GPoint[count];

			// Loop to change each point
		for (int i = 0; i < count; i++) {
			float px;
			float py;

			px = (CTM[0] * po[i].x()) + (CTM[1] * po[i].y()) + (CTM[2]);
			py = (CTM[3] * po[i].x()) + (CTM[4] * po[i].y()) + (CTM[5]);

			// Set new x and y
			p[i].set(px, py); 
		}

		// Create edges
		Edge tmpE;
		std::vector<Edge> eArr;
		for (int i = 0; i < count; i++) {
			if (i == (count - 1)) {
				if (GFloorToInt(p[i].x() + 0.5) == GFloorToInt(p[0].x() + 0.5)) {
					// vertical line
					tmpE.top = GFloorToInt(std::min(p[i].y(),p[0].y()) + 0.5);
					tmpE.bottom = GFloorToInt(std::max(p[i].y(),p[0].y()) + 0.5);
					tmpE.m = 0;
					tmpE.b = 0;
					tmpE.v = GFloorToInt(p[i].x()  + 0.5);
					tmpE.x = tmpE.v;
				} else if (GFloorToInt(p[i].y() + 0.5) == GFloorToInt(p[0].y() + 0.5)) {
					// horizontal line
					tmpE.top = 0;
					tmpE.bottom = 0;
					tmpE.m = 0;
					tmpE.b = 0;
					tmpE.v = 0;
					tmpE.x = 0;
				} else {
					tmpE.top = GFloorToInt(std::min(p[i].y(),p[0].y()) + 0.5);
					tmpE.bottom = GFloorToInt(std::max(p[i].y(),p[0].y()) + 0.5);
					tmpE.m = ((p[0].x()-p[i].x())/(p[0].y()-p[i].y()));
					tmpE.b = (p[i].x()-(tmpE.m * p[i].y()));
					tmpE.v = 0;
					tmpE.x = GFloorToInt(std::min(p[i].x(), p[0].x()) + 0.5);
				}
			} else {
				if (GFloorToInt(p[i].x() + 0.5) == GFloorToInt(p[i+1].x() + 0.5)) {
					// vertical line
					tmpE.top = GFloorToInt(std::min(p[i].y(),p[i+1].y()) + 0.5);
					tmpE.bottom = GFloorToInt(std::max(p[i].y(),p[i+1].y()) + 0.5);
					tmpE.m = 0;
					tmpE.b = 0;
					tmpE.v = GFloorToInt(p[i].x()  + 0.5);
					tmpE.x = tmpE.v;
				} else if (GFloorToInt(p[i].y() + 0.5) == GFloorToInt(p[i+1].y() + 0.5)) {
					// horizontal line
					tmpE.top = 0;
					tmpE.bottom = 0;
					tmpE.m = 0;
					tmpE.b = 0;
					tmpE.v = 0;
					tmpE.x = 0;
				} else {
					tmpE.top = GFloorToInt(std::min(p[i].y(),p[i+1].y()) + 0.5);
					tmpE.bottom = GFloorToInt(std::max(p[i].y(),p[i+1].y()) + 0.5);
					tmpE.m = ((p[i+1].x()-p[i].x())/(p[i+1].y()-p[i].y()));
					tmpE.b = (p[i].x()-(tmpE.m * p[i].y()));
					tmpE.v = 0;
					tmpE.x = GFloorToInt(std::min(p[i].x(), p[i+1].x()) + 0.5);
				}
			}

			if (tmpE.top != tmpE.bottom) {
				eArr.push_back(tmpE);
			}
		}

		// Sort edges
		std::sort(eArr.begin(), eArr.end(), sortEdges);
		
		// Prepare color to fill
		GColor c = color.pinToUnit();
        unsigned a = (int)(c.fA * 255.99999);
        unsigned r = (int)(c.fR * c.fA * 255.99999);
        unsigned g = (int)(c.fG * c.fA * 255.99999);
        unsigned b = (int)(c.fB * c.fA * 255.99999);

        // Find starting y pixel
        int startY = eArr[0].top;

        // Find ending y pixel
        int endY = eArr.back().bottom;

        // Fill desired pixels
        	// Set indexes for left and right edges at beginning
        int index;
        int currL;
        int currR;

        index = 0;
        currL = index;
        currR = index + 1;
        index += 2;

		GPixel* d = draw.fPixels;
		d = (GPixel*)((char*)d + (int)startY * draw.rowBytes());
		for (int y = startY; y < endY; ++y) {
			for (int x = lrBound(eArr, currL, y); x < lrBound(eArr, currR, y); ++x) {
				// blend if alpha isn't 255

				// if pixel is out of bounds, skip pixel
				if ((x >= 0) && (x < draw.width()) && (y >= 0) && (y < draw.height())) {
					if (GPixel_GetA(*draw.getAddr(x, y)) > 0) {
	    	    		// blend
						float sA = c.fA + ((GPixel_GetA(*draw.getAddr(x, y)) / 255.99999) * (1.0f - c.fA));
						float sR = (c.fR * c.fA) + ((GPixel_GetR(*draw.getAddr(x, y)) / 255.99999) * (1.0f - c.fA));
						float sG = (c.fG * c.fA) + ((GPixel_GetG(*draw.getAddr(x, y)) / 255.99999) * (1.0f - c.fA));
						float sB = (c.fB * c.fA) + ((GPixel_GetB(*draw.getAddr(x, y)) / 255.99999) * (1.0f - c.fA));
										
						unsigned nA = (int)(sA * 255.99999);
						unsigned nR = (int)(sR * 255.99999);
						unsigned nG = (int)(sG * 255.99999);
						unsigned nB = (int)(sB * 255.99999);

	    	    		d[x] = GPixel_PackARGB(nA, nR, nG, nB);
    	    		} else {
    	    			d[x] = GPixel_PackARGB(a, r, g, b);
    	    		}
				}

				if (eArr[currL].bottom == y) {
					currL = index;
					index++;
				}
				if (eArr[currR].bottom == y) {
					currR = index;
					index++;
				}
			}
			d = (GPixel*)((char*)d + draw.rowBytes());
		}
	}

////////////////////////////////////////////////////////////////////////////////

	/**
	 *	Saves a copy of the CTM, allowing subsequent modifications (by calling concat()) to be
	 *	undone when restore() is called.
	 */
	void save() {
		// Create a new Store object to hold CTM
		Store s;
		//s.mat = CTM;
		std::copy(CTM, CTM + 6, s.mat);

		// Adds the CTM to the vector of CTMs
		saves.push_back(s);
	}

	/**
	 *	Balances calls to save(), returning the CTM to the state it was in when the corresponding
	 *	call to save() was made. These calls can be nested.
	 */
	void restore() {
		// Sets the CTM to the last save
		//CTM = saves[saves.size() - 1].mat;
		std::copy(saves[saves.size() - 1].mat, saves[saves.size() - 1].mat + 6, CTM);

		// Removes last element in vector of CTMs
		saves.pop_back();
	}

	/**
	 *	Modifies the CTM (current transformation matrix) by pre-concatenating it with the specified
	 *	matrix.
	 *
	 *	CTM' = CTM * matrix
	 *
	 *	The result is that any drawing that uses the new CTM' will be affected AS-IF it were
	 *	first transformed by matrix, and then transformed by the previous CTM.
	 */
	void concat(const float matrix[6]) {
		// Store CTM and matrix into local variables for ease
		//float c[6] = CTM;
		float c[6];
		std::copy(CTM, CTM + 6, c);
		
		//float m[6] = matrix;
		float m[6];
		std::copy(matrix, matrix + 6, m);
		
		float combined[6];

		// Concatenate
		combined[0] = (c[0] * m[0]) + (c[1] * m[3]);
		combined[1] = (c[0] * m[1]) + (c[1] * m[4]);
		combined[2] = (c[0] * m[2]) + (c[1] * m[5]) + (c[2]);
		combined[3] = (c[3] * m[0]) + (c[4] * m[3]);
		combined[4] = (c[3] * m[1]) + (c[4] * m[4]);
		combined[5] = (c[3] * m[2]) + (c[4] * m[5]) + (c[5]);

		// Change CTM to reflect concatenation
		//CTM = combined;
		std::copy(combined, combined + 6, CTM);
	}

////////////////////////////////////////////////////////////////////////////////

	/**
	 *	Fill the specified rect using the shader. The colors returned by the shader are blended
	 *	into the canvas using SRC_OVER blend mode.
	 */
	void shadeRect(const GRect& rect, GShader* shader) {
		// pts[4]
		// shader = FromColor(c)
		// shadeConvexPolygon(pts, 4, shader)

		// s->shadeRow(x-coordinate, y-coordinate, number-of-pixels in line, storage)
			// This is used to get the pixels and store them in a row

		GPoint pts[4];
		pts[0].set(rect.left(), rect.top());
		pts[1].set(rect.right(), rect.top());
		pts[2].set(rect.right(), rect.bottom());
		pts[3].set(rect.left(), rect.bottom());
		shadeConvexPolygon(pts, 4, shader);
	}

	/**
	 *	Fill the specified polygon using the shader. The colors returned by the shader are blended
	 *	into the canvas using SRC_OVER blend mode.
	 */
	void shadeConvexPolygon(const GPoint po[], int count, GShader* shader) {
		// pts' <- CTM[pts]
		// shader -> setContext(CTM)
		// scan_convert(pts)
		//	clip
		//	edges
		//	sorts
		//	y-loop
		//		x0, x1 intervals
		//		shadeRow(x0, y, x1-x0, storage)
		//		blend(storage)

		// Consider points
			// int count = # of points

		// Do nothing if less than 3 points (i.e. not enough points to make a polygon)
		if (count < 3) {
			return;
		}

		// Adjust points according to CTM
			// Array to hold adjusted points
		GPoint* p = new GPoint[count];

			// Loop to change each point
		for (int i = 0; i < count; i++) {
			float px;
			float py;

			px = (CTM[0] * po[i].x()) + (CTM[1] * po[i].y()) + (CTM[2]);
			py = (CTM[3] * po[i].x()) + (CTM[4] * po[i].y()) + (CTM[5]);

			// Set new x and y
			p[i].set(px, py); 
		}
		shader->setContext(CTM);

		// Create edges
		Edge tmpE;
		std::vector<Edge> eArr;
		for (int i = 0; i < count; i++) {
			if (i == (count - 1)) {
				if (GFloorToInt(p[i].x() + 0.5) == GFloorToInt(p[0].x() + 0.5)) {
					// vertical line
					tmpE.top = GFloorToInt(std::min(p[i].y(),p[0].y()) + 0.5);
					tmpE.bottom = GFloorToInt(std::max(p[i].y(),p[0].y()) + 0.5);
					tmpE.m = 0;
					tmpE.b = 0;
					tmpE.v = GFloorToInt(p[i].x()  + 0.5);
					tmpE.x = tmpE.v;
				} else if (GFloorToInt(p[i].y() + 0.5) == GFloorToInt(p[0].y() + 0.5)) {
					// horizontal line
					tmpE.top = 0;
					tmpE.bottom = 0;
					tmpE.m = 0;
					tmpE.b = 0;
					tmpE.v = 0;
					tmpE.x = 0;
				} else {
					tmpE.top = GFloorToInt(std::min(p[i].y(),p[0].y()) + 0.5);
					tmpE.bottom = GFloorToInt(std::max(p[i].y(),p[0].y()) + 0.5);
					tmpE.m = ((p[0].x()-p[i].x())/(p[0].y()-p[i].y()));
					tmpE.b = (p[i].x()-(tmpE.m * p[i].y()));
					tmpE.v = 0;
					tmpE.x = GFloorToInt(std::min(p[i].x(), p[0].x()) + 0.5);
				}
			} else {
				if (GFloorToInt(p[i].x() + 0.5) == GFloorToInt(p[i+1].x() + 0.5)) {
					// vertical line
					tmpE.top = GFloorToInt(std::min(p[i].y(),p[i+1].y()) + 0.5);
					tmpE.bottom = GFloorToInt(std::max(p[i].y(),p[i+1].y()) + 0.5);
					tmpE.m = 0;
					tmpE.b = 0;
					tmpE.v = GFloorToInt(p[i].x()  + 0.5);
					tmpE.x = tmpE.v;
				} else if (GFloorToInt(p[i].y() + 0.5) == GFloorToInt(p[i+1].y() + 0.5)) {
					// horizontal line
					tmpE.top = 0;
					tmpE.bottom = 0;
					tmpE.m = 0;
					tmpE.b = 0;
					tmpE.v = 0;
					tmpE.x = 0;
				} else {
					tmpE.top = GFloorToInt(std::min(p[i].y(),p[i+1].y()) + 0.5);
					tmpE.bottom = GFloorToInt(std::max(p[i].y(),p[i+1].y()) + 0.5);
					tmpE.m = ((p[i+1].x()-p[i].x())/(p[i+1].y()-p[i].y()));
					tmpE.b = (p[i].x()-(tmpE.m * p[i].y()));
					tmpE.v = 0;
					tmpE.x = GFloorToInt(std::min(p[i].x(), p[i+1].x()) + 0.5);
				}
			}

			if (tmpE.top != tmpE.bottom) {
				eArr.push_back(tmpE);
			}
		}

		// Sort edges
		std::sort(eArr.begin(), eArr.end(), sortEdges);

        // Find starting y pixel
        int startY = eArr[0].top;

        // Find ending y pixel
        int endY = eArr.back().bottom;        	

        // Fill desired pixels
        	// Set indexes for left and right edges at beginning
        int index;
        int currL;
        int currR;

        index = 0;
        currL = index;
        currR = index + 1;
        index += 2;

		GPixel* d = draw.fPixels;
		d = (GPixel*)((char*)d + (int)startY * draw.rowBytes());
		for (int y = startY; y < endY; ++y) {
			GPixel* store = new GPixel[draw.width()];
			shader->shadeRow(lrBound(eArr, currL, y), y, draw.width(), store);

			for (int x = lrBound(eArr, currL, y); x < lrBound(eArr, currR, y); ++x) {
				// blend if alpha isn't 255

				// if pixel is out of bounds, skip pixel
				if ((x >= 0) && (x < draw.width()) && (y >= 0) && (y < draw.height())) {
						unsigned a = GPixel_GetA(store[x]);
						unsigned r = GPixel_GetR(store[x]);
						unsigned g = GPixel_GetG(store[x]);
						unsigned b = GPixel_GetB(store[x]);

					if (GPixel_GetA(*draw.getAddr(x, y)) > 0) {
	    	    		// blend
						float sA = (a / 255.99999) + ((GPixel_GetA(*draw.getAddr(x, y)) / 255.99999) * (1.0f - (a / 255.99999)));
						float sR = ((r / 255.99999) * (a / 255.99999)) + ((GPixel_GetR(*draw.getAddr(x, y)) / 255.99999) * (1.0f - (a / 255.99999)));
						float sG = ((g / 255.99999) * (a / 255.99999)) + ((GPixel_GetG(*draw.getAddr(x, y)) / 255.99999) * (1.0f - (a / 255.99999)));
						float sB = ((b / 255.99999) * (a / 255.99999)) + ((GPixel_GetB(*draw.getAddr(x, y)) / 255.99999) * (1.0f - (a / 255.99999)));
										
						unsigned nA = (int)(sA * 255.99999);
						unsigned nR = (int)(sR * 255.99999);
						unsigned nG = (int)(sG * 255.99999);
						unsigned nB = (int)(sB * 255.99999);
						//printf("argb(%u, %u, %u, %u)\n", nA, nR, nG, nB);

	    	    		d[x] = GPixel_PackARGB(nA, nR, nG, nB);
    	    		} else {
    	    			//printf("Didn't Change!\n");
    	    			//if (a != 255) {
    	    				d[x] = GPixel_PackARGB(255, r, g, b);
    	    			//}
    	    		}
				}

				if (eArr[currL].bottom == y) {
					currL = index;
					index++;
				}
				if (eArr[currR].bottom == y) {
					currR = index;
					index++;
				}
			}
			d = (GPixel*)((char*)d + draw.rowBytes());
			//delete store;
		}

		delete p;
	}

////////////////////////////////////////////////////////////////////////////////

	/**
	 *	Stroke the specified polygon using the Stroke settings. If isClosed is true, then the
	 *	drawn stroke should connect the first and last points of the polygon, else it should not,
	 *	an those end-caps should reflect the Stroke.fAddCap setting.
	 */
	void strokePolygon(const GPoint po[], int n, bool isClosed, const Stroke& s, GShader* g) {
		// Adjust points according to CTM
			// Array to hold adjusted points
		GPoint* p = new GPoint[n];

			// Loop to change each point
		for (int i = 0; i < n; i++) {
			float px;
			float py;

			px = (CTM[0] * po[i].x()) + (CTM[1] * po[i].y()) + (CTM[2]);
			py = (CTM[3] * po[i].x()) + (CTM[4] * po[i].y()) + (CTM[5]);

			// Set new x and y
			p[i].set(px, py); 
		}
		g->setContext(CTM);

		// We want to get 4 pts for each edge, and draw that rect
		for (int i = 0; i < n; i++) {
			if ((i == (n - 1)) && (isClosed == true)) { // p[n-1] -> p[0]
				// (x, y) = (Bx - Ax, By - Ay)
				// (x, y) -> (-y, x) or (y, -x)
				float dx = p[0].x() - p[i].x();
				float dy = p[0].y() - p[i].y();
				float len = sqrt((dx*dx)+(dy*dy));
				float rad = s.fWidth / 2;
				dx = dx * rad / len;
				dy = dy * rad / len;
				GPoint t = GPoint::Make((-1*dy), dx);

				// A ± (AB)T
				// B ± (AB)T

				GPoint pts[4];
				pts[0].set((p[i].x()-t.x()), (p[i].y()-t.y()));
				pts[1].set((p[0].x()-t.x()), (p[0].y()-t.y()));
				pts[2].set((p[0].x()+t.x()), (p[0].y()+t.y()));
				pts[3].set((p[i].x()+t.x()), (p[i].y()+t.y()));
				shadeConvexPolygon(pts, 4, g);
			} else if ((i == (n-1)) && (isClosed == false) && (n <= 2)) {
				// don't draw last edge
			} else { // p[i] -> p[i+1]
				// (x, y) = (Bx - Ax, By - Ay)
				// (x, y) -> (-y, x) or (y, -x)
				float dx = p[i+1].x() - p[i].x();
				float dy = p[i+1].y() - p[i].y();
				float len = sqrt((dx*dx)+(dy*dy));
				float rad = s.fWidth / 2;
				dx = dx * rad / len;
				dy = dy * rad / len;
				GPoint t = GPoint::Make((-1*dy), dx);

				// A ± (AB)T
				// B ± (AB)T

				GPoint pts[4];
				pts[0].set((p[i].x()-t.x()), (p[i].y()-t.y()));
				pts[1].set((p[i+1].x()-t.x()), (p[i+1].y()-t.y()));
				pts[2].set((p[i+1].x()+t.x()), (p[i+1].y()+t.y()));
				pts[3].set((p[i].x()+t.x()), (p[i].y()+t.y()));
				shadeConvexPolygon(pts, 4, g);
			}
		}

		// caps
		for (int i = 0; i < n; i++) {
			if (n > 2) {
				if (isClosed == true) {
					if (i == 0) { // p[n-1] -> p[0] <- p[1]
						float abx = p[0].x()-p[n-1].x();
						float aby = p[0].y()-p[n-1].y();
						float abmag = sqrt((abx*abx)+(aby*aby));
						GPoint ab = GPoint::Make(abx/abmag,aby/abmag);
							// find BC
						float bcx = p[1].x()-p[0].x();
						float bcy = p[1].y()-p[0].y();
						float bcmag = sqrt((bcx*bcx)+(bcy*bcy));
						GPoint bc = GPoint::Make(bcx/bcmag,bcy/bcmag);
							// find AB·BC
						float abbc = abx*bcx + aby*bcy;
						float h = sqrt(2/(1-abbc));

						// compute points for p[i-1] -> p[i]
						float ldx = p[0].x() - p[n-1].x();
						float ldy = p[0].y() - p[n-1].y();
						float llen = sqrt((ldx*ldx)+(ldy*ldy));
						float lrad = s.fWidth / 2;
						ldx = ldx * lrad / llen;
						ldy = ldy * lrad / llen;
						GPoint lt = GPoint::Make((-1*ldy), ldx);

						// compute points for p[i+1] -> p[i]
						float rdx = p[1].x() - p[0].x();
						float rdy = p[1].y() - p[0].y();
						float rlen = sqrt((rdx*rdx)+(rdy*rdy));
						float rrad = s.fWidth / 2;
						rdx = rdx * rrad / rlen;
						rdy = rdy * rrad / rlen;
						GPoint rt = GPoint::Make((-1*rdy), rdx);

						// find outer points
						GPoint q; // Left outside point
						GPoint r; // Right outside point

						// Find point furthest from C
						float qmdx = p[1].x() - (p[0].x()-lt.x());
						float qmdy = p[1].y() - (p[0].y()-lt.y());
						float qpdx = p[1].x() - (p[0].x()+lt.x());
						float qpdy = p[1].y() - (p[0].y()+lt.y());
						float qm = sqrt((qmdx*qmdx)+(qmdy*qmdy));
						float qp = sqrt((qpdx*qpdx)+(qpdy*qpdy));
						if (qm > qp) {
							q.set(p[0].x()-lt.x(), p[0].y()-lt.y());
						} else {
							q.set(p[0].x()+lt.x(), p[0].y()+lt.y());
						}

						// Find point furthest from A
						float rmdx = p[n-1].x() - (p[0].x()-lt.x());
						float rmdy = p[n-1].y() - (p[0].y()-lt.y());
						float rpdx = p[n-1].x() - (p[0].x()+lt.x());
						float rpdy = p[n-1].y() - (p[0].y()+lt.y());
						float rm = sqrt((rmdx*rmdx)+(rmdy*rmdy));
						float rp = sqrt((rpdx*rpdx)+(rpdy*rpdy));
						if (rm > rp) {
							r.set(p[0].x()-rt.x(), p[0].y()-rt.y());
						} else {
							r.set(p[0].x()+rt.x(), p[0].y()+rt.y());
						}

						if (h > s.fMiterLimit) {
							// Bevel cap
								// Make triangle with vertex and outer-edge pts
									// p[i] is one point of the triangle
							// create and shade triangle
							GPoint tri[3];
							tri[0].set(p[0].x(), p[0].y());
							tri[1].set(q.x(), q.y());
							tri[2].set(r.x(), r.y());
							shadeConvexPolygon(tri, 3, g);
						} else {
							// Miter cap
								// |BP| = W/2 * sqrt(2/(1 - U·V))
							float uv = ab.x()*bc.x() + ab.y()*bc.y();
							float bp = s.fWidth / 2 * sqrt(2 / (1 - uv));

							GPoint quad[4];
							quad[0].set(p[0].x(),p[0].y());
							quad[1].set(q.x(), q.y());
							quad[2].set(p[0].x()+bp,p[0].y()+bp);
							quad[3].set(r.x(), r.y());
							shadeConvexPolygon(quad, 4, g);
						}
					} else if (i == (n-1)) { // p[n-2] -> p[n-1] <- p[0]
						float abx = p[n-1].x()-p[n-2].x();
						float aby = p[n-1].y()-p[n-2].y();
						float abmag = sqrt((abx*abx)+(aby*aby));
						GPoint ab = GPoint::Make(abx/abmag,aby/abmag);
							// find BC
						float bcx = p[0].x()-p[n-1].x();
						float bcy = p[0].y()-p[n-1].y();
						float bcmag = sqrt((bcx*bcx)+(bcy*bcy));
						GPoint bc = GPoint::Make(bcx/bcmag,bcy/bcmag);
							// find AB·BC
						float abbc = abx*bcx + aby*bcy;
						float h = sqrt(2/(1-abbc));

						// compute points for p[i-1] -> p[i]
						float ldx = p[n-1].x() - p[n-2].x();
						float ldy = p[n-1].y() - p[n-2].y();
						float llen = sqrt((ldx*ldx)+(ldy*ldy));
						float lrad = s.fWidth / 2;
						ldx = ldx * lrad / llen;
						ldy = ldy * lrad / llen;
						GPoint lt = GPoint::Make((-1*ldy), ldx);

						// compute points for p[i+1] -> p[i]
						float rdx = p[0].x() - p[n-1].x();
						float rdy = p[0].y() - p[n-1].y();
						float rlen = sqrt((rdx*rdx)+(rdy*rdy));
						float rrad = s.fWidth / 2;
						rdx = rdx * rrad / rlen;
						rdy = rdy * rrad / rlen;
						GPoint rt = GPoint::Make((-1*rdy), rdx);

						// find outer points
						GPoint q; // Left outside point
						GPoint r; // Right outside point

						// Find point furthest from C
						float qmdx = p[0].x() - (p[n-1].x()-lt.x());
						float qmdy = p[0].y() - (p[n-1].y()-lt.y());
						float qpdx = p[0].x() - (p[n-1].x()+lt.x());
						float qpdy = p[0].y() - (p[n-1].y()+lt.y());
						float qm = sqrt((qmdx*qmdx)+(qmdy*qmdy));
						float qp = sqrt((qpdx*qpdx)+(qpdy*qpdy));
						if (qm > qp) {
							q.set(p[n-1].x()-lt.x(), p[n-1].y()-lt.y());
						} else {
							q.set(p[n-1].x()+lt.x(), p[n-1].y()+lt.y());
						}

						// Find point furthest from A
						float rmdx = p[n-2].x() - (p[n-1].x()-lt.x());
						float rmdy = p[n-2].y() - (p[n-1].y()-lt.y());
						float rpdx = p[n-2].x() - (p[n-1].x()+lt.x());
						float rpdy = p[n-2].y() - (p[n-1].y()+lt.y());
						float rm = sqrt((rmdx*rmdx)+(rmdy*rmdy));
						float rp = sqrt((rpdx*rpdx)+(rpdy*rpdy));
						if (rm > rp) {
							r.set(p[n-1].x()-rt.x(), p[n-1].y()-rt.y());
						} else {
							r.set(p[n-1].x()+rt.x(), p[n-1].y()+rt.y());
						}

						if (h > s.fMiterLimit) {
							// Bevel cap
								// Make triangle with vertex and outer-edge pts
									// p[i] is one point of the triangle
							// create and shade triangle
							GPoint tri[3];
							tri[0].set(p[n-1].x(), p[n-1].y());
							tri[1].set(q.x(), q.y());
							tri[2].set(r.x(), r.y());
							shadeConvexPolygon(tri, 3, g);
						} else {
							// Miter cap
								// |BP| = W/2 * sqrt(2/(1 - U·V))
							float uv = ab.x()*bc.x() + ab.y()*bc.y();
							float bp = s.fWidth / 2 * sqrt(2 / (1 - uv));

							GPoint quad[4];
							quad[0].set(p[n-1].x(),p[n-1].y());
							quad[1].set(q.x(), q.y());
							quad[2].set(p[n-1].x()+bp,p[n-1].y()+bp);
							quad[3].set(r.x(), r.y());
							shadeConvexPolygon(quad, 4, g);
						}
					} else { // p[i-1] -> p[i] <- p[i+1]
						float abx = p[i].x()-p[i-1].x();
						float aby = p[i].y()-p[i-1].y();
						float abmag = sqrt((abx*abx)+(aby*aby));
						GPoint ab = GPoint::Make(abx/abmag,aby/abmag);
							// find BC
						float bcx = p[i+1].x()-p[i].x();
						float bcy = p[i+1].y()-p[i].y();
						float bcmag = sqrt((bcx*bcx)+(bcy*bcy));
						GPoint bc = GPoint::Make(bcx/bcmag,bcy/bcmag);
							// find AB·BC
						float abbc = abx*bcx + aby*bcy;
						float h = sqrt(2/(1-abbc));

						// compute points for p[i-1] -> p[i]
						float ldx = p[i].x() - p[i-1].x();
						float ldy = p[i].y() - p[i-1].y();
						float llen = sqrt((ldx*ldx)+(ldy*ldy));
						float lrad = s.fWidth / 2;
						ldx = ldx * lrad / llen;
						ldy = ldy * lrad / llen;
						GPoint lt = GPoint::Make((-1*ldy), ldx);

						// compute points for p[i+1] -> p[i]
						float rdx = p[i+1].x() - p[i].x();
						float rdy = p[i+1].y() - p[i].y();
						float rlen = sqrt((rdx*rdx)+(rdy*rdy));
						float rrad = s.fWidth / 2;
						rdx = rdx * rrad / rlen;
						rdy = rdy * rrad / rlen;
						GPoint rt = GPoint::Make((-1*rdy), rdx);

						// find outer points
						GPoint q; // Left outside point
						GPoint r; // Right outside point

						// Find point furthest from C
						float qmdx = p[i+1].x() - (p[i].x()-lt.x());
						float qmdy = p[i+1].y() - (p[i].y()-lt.y());
						float qpdx = p[i+1].x() - (p[i].x()+lt.x());
						float qpdy = p[i+1].y() - (p[i].y()+lt.y());
						float qm = sqrt((qmdx*qmdx)+(qmdy*qmdy));
						float qp = sqrt((qpdx*qpdx)+(qpdy*qpdy));
						if (qm > qp) {
							q.set(p[i].x()-lt.x(), p[i].y()-lt.y());
						} else {
							q.set(p[i].x()+lt.x(), p[i].y()+lt.y());
						}

						// Find point furthest from A
						float rmdx = p[i-1].x() - (p[i].x()-lt.x());
						float rmdy = p[i-1].y() - (p[i].y()-lt.y());
						float rpdx = p[i-1].x() - (p[i].x()+lt.x());
						float rpdy = p[i-1].y() - (p[i].y()+lt.y());
						float rm = sqrt((rmdx*rmdx)+(rmdy*rmdy));
						float rp = sqrt((rpdx*rpdx)+(rpdy*rpdy));
						if (rm > rp) {
							r.set(p[i].x()-rt.x(), p[i].y()-rt.y());
						} else {
							r.set(p[i].x()+rt.x(), p[i].y()+rt.y());
						}

						if (h > s.fMiterLimit) {
							// Bevel cap
								// Make triangle with vertex and outer-edge pts
									// p[i] is one point of the triangle
							// create and shade triangle
							GPoint tri[3];
							tri[0].set(p[i].x(), p[i].y());
							tri[1].set(q.x(), q.y());
							tri[2].set(r.x(), r.y());
							shadeConvexPolygon(tri, 3, g);
						} else {
							// Miter cap
								// |BP| = W/2 * sqrt(2/(1 - U·V))
							float uv = ab.x()*bc.x() + ab.y()*bc.y();
							float bp = s.fWidth / 2 * sqrt(2 / (1 - uv));

							GPoint quad[4];
							quad[0].set(p[i].x(),p[i].y());
							quad[1].set(q.x(), q.y());
							quad[2].set(p[i].x()+bp,p[i].y()+bp);
							quad[3].set(r.x(), r.y());
							shadeConvexPolygon(quad, 4, g);
						}
					}
				} else {
					// not connected between p[n-1] -> p[0]
					if (i == 0) { // p[n-1] -> p[0] <- p[1]
						// no joint
						if (s.fAddCap == true) {
							// add square cap to beginning p[0]
							float dx = p[1].x() - p[0].x();
							float dy = p[1].y() - p[0].y();
							float len = sqrt((dx*dx)+(dy*dy));
							float rad = s.fWidth / 2;
							dx = dx * rad / len;
							dy = dy * rad / len;
							GPoint t = GPoint::Make((-1*dy), dx);

							GPoint cap[4];
							cap[0].set(p[0].x()+t.x(),p[0].y()+t.y());
							cap[1].set(p[0].x()-t.x(),p[0].y()-t.y());

							float px, py;

							if (p[0].x() < p[1].x()) {
								px = p[0].x() - (s.fWidth/2);
							} else {
								px = p[0].x() + (s.fWidth/2);
							}

							if (p[0].y() < p[1].y()) {
								py = p[0].y() - (s.fWidth/2);
							} else {
								py = p[0].y() + (s.fWidth/2);
							}

							cap[2].set(px-t.x(), py-t.y());
							cap[3].set(px+t.x(), py+t.y());
							shadeConvexPolygon(cap, 4, g);
						} else {
							// nothing happens
						}
					} else if (i == (n-1)) { // p[n-2] -> p[n-1] <- p[0]
						// no joint
						if (s.fAddCap == true) {
							// add square cap to end p[n-1]
							float dx = p[0].x() - p[n-1].x();
							float dy = p[0].y() - p[n-1].y();
							float len = sqrt((dx*dx)+(dy*dy));
							float rad = s.fWidth / 2;
							dx = dx * rad / len;
							dy = dy * rad / len;
							GPoint t = GPoint::Make((-1*dy), dx);

							GPoint cap[4];
							cap[0].set(p[n-1].x()+t.x(),p[n-1].y()+t.y());
							cap[1].set(p[n-1].x()-t.x(),p[n-1].y()-t.y());

							float px, py;

							if (p[n-1].x() < p[0].x()) {
								px = p[n-1].x() - (s.fWidth/2);
							} else {
								px = p[n-1].x() + (s.fWidth/2);
							}

							if (p[n-1].y() < p[0].y()) {
								py = p[n-1].y() - (s.fWidth/2);
							} else {
								py = p[n-1].y() + (s.fWidth/2);
							}

							cap[2].set(px-t.x(), py-t.y());
							cap[3].set(px+t.x(), py+t.y());
							shadeConvexPolygon(cap, 4, g);
						} else {
							// nothing happens
						}
					} else { // p[i-1] -> p[i] <- p[i+1]
						float abx = p[i].x()-p[i-1].x();
						float aby = p[i].y()-p[i-1].y();
						float abmag = sqrt((abx*abx)+(aby*aby));
						GPoint ab = GPoint::Make(abx/abmag,aby/abmag);
							// find BC
						float bcx = p[i+1].x()-p[i].x();
						float bcy = p[i+1].y()-p[i].y();
						float bcmag = sqrt((bcx*bcx)+(bcy*bcy));
						GPoint bc = GPoint::Make(bcx/bcmag,bcy/bcmag);
							// find AB·BC
						float abbc = abx*bcx + aby*bcy;
						float h = sqrt(2/(1-abbc));

						// compute points for p[i-1] -> p[i]
						float ldx = p[i].x() - p[i-1].x();
						float ldy = p[i].y() - p[i-1].y();
						float llen = sqrt((ldx*ldx)+(ldy*ldy));
						float lrad = s.fWidth / 2;
						ldx = ldx * lrad / llen;
						ldy = ldy * lrad / llen;
						GPoint lt = GPoint::Make((-1*ldy), ldx);

						// compute points for p[i+1] -> p[i]
						float rdx = p[i+1].x() - p[i].x();
						float rdy = p[i+1].y() - p[i].y();
						float rlen = sqrt((rdx*rdx)+(rdy*rdy));
						float rrad = s.fWidth / 2;
						rdx = rdx * rrad / rlen;
						rdy = rdy * rrad / rlen;
						GPoint rt = GPoint::Make((-1*rdy), rdx);

						if (h > s.fMiterLimit) {
							// Bevel cap
								// Make triangle with vertex and outer-edge pts
									// p[i] is one point of the triangle
							// create and shade triangle
							GPoint tri[3];
							tri[0].set(p[i].x(), p[i].y());
							tri[1].set(p[i].x()-lt.x(),p[i].y()-lt.y());
							tri[2].set(p[i].x()+rt.x(),p[i].y()+rt.y());
							shadeConvexPolygon(tri, 3, g);
						} else {
							// Miter cap
								// |BP| = W/2 * sqrt(2/(1 - U·V))
							float uv = ab.x()*bc.x() + ab.y()*bc.y();
							float bp = s.fWidth / 2 * sqrt(2 / (1 - uv));

							GPoint quad[4];
							quad[0].set(p[i].x(),p[i].y());
							quad[1].set(p[i].x()-lt.x(),p[i].y()-lt.y());
							quad[2].set(p[i].x()+rt.x(),p[i].y()+rt.y());
							quad[3].set(p[i].x()+bp,p[i].y()+bp);
							shadeConvexPolygon(quad, 4, g);
						}
					}
				}
			} else {
				// isClosed == false
					// i.e. can't be closed because only two points
				if (s.fAddCap == true && i == 0) {
					// add caps to both ends of rect
					float dx = p[i+1].x() - p[i].x();
					float dy = p[i+1].y() - p[i].y();
					float len = sqrt((dx*dx)+(dy*dy));
					float rad = s.fWidth / 2;
					dx = dx * rad / len;
					dy = dy * rad / len;
					GPoint t = GPoint::Make((-1*dy), dx);

					GPoint lcap[4];
					GPoint rcap[4];

					lcap[0].set(p[i].x()+t.x(),p[i].y()+t.y());
					lcap[1].set(p[i].x()-t.x(),p[i].y()-t.y());

					rcap[0].set(p[i+1].x()+t.x(),p[i+1].y()+t.y());
					rcap[1].set(p[i+1].x()-t.x(),p[i+1].y()-t.y());

					float lpx, lpy, rpx, rpy;

					if (p[i].x() < p[i+1].x()) {
						lpx = p[i].x() - (s.fWidth/2);
						rpx = p[i+1].x() + (s.fWidth/2);
					} else {
						lpx = p[i].x() + (s.fWidth/2);
						rpx = p[i+1].x() - (s.fWidth/2);
					}

					if (p[i].y() < p[i+1].y()) {
						lpy = p[i].y() - (s.fWidth/2);
						rpy = p[i+1].y() + (s.fWidth/2);
					} else {
						lpy = p[i].y() + (s.fWidth/2);
						rpy = p[i+1].y() - (s.fWidth/2);
					}

					
					lcap[2].set(lpx-t.x(), lpy-t.y());
					lcap[3].set(lpx+t.x(), lpy+t.y());

					rcap[2].set(rpx-t.x(), rpy-t.y());
					rcap[3].set(rpx+t.x(), rpy+t.y());

					shadeConvexPolygon(lcap, 4, g);
					shadeConvexPolygon(rcap, 4, g);
				} // no else
			}
		}

		delete p;
	}

	/*
	struct Stroke {
        float   fWidth;         // width of the stroke
        float   fMiterLimit;    // limit for a width=1.0 stroke
        bool    fAddCap;        // if true, add a square cap at the ends
    };
    */

////////////////////////////////////////////////////////////////////////////////

	// Helper functions
	struct Edge {
		int top;
		int bottom;
		float b;
		float m;
		int v;
		int x;
	};

	static bool sortEdges(const Edge &lhs, const Edge &rhs) {
		if (lhs.top < rhs.top) {
			return true;
		}
		if (lhs.top > rhs.top) {
			return false;
		}

		if (lhs.x < rhs.x) {
			return true;
		}
		if (lhs.x > rhs.x) {
			return false;
		}

		if (lhs.m < rhs.m) {
			return true;
		}
		return false;
	}

	int lrBound(std::vector<Edge> a, int i, int y) {
		if (a[i].v != 0) {
			return a[i].v;
		} else {
			return GFloorToInt(((a[i].m * y) + a[i].b) + 0.5);
		}
	}

	struct Store {
		float mat[6];
	};

  private:
	GBitmap draw;
	std::vector<Store> saves;
	float CTM[6];
};

////////////////////////////////////////////////////////////////////////////////

Mine::Mine(const GBitmap& bitmap) {
	draw = bitmap;

	CTM[0] = 1;
	CTM[1] = 0;
	CTM[2] = 0;
	CTM[3] = 0;
	CTM[4] = 1;
	CTM[5] = 0;
}

/**
 *	If the bitmap is valid for drawing into, this returns a subclass that can perform the
 *	drawing. If bitmap is invalide, this returns NULL.
 */
GCanvas* GCanvas::Create(const GBitmap& bitmap) {
    if (bitmap.width() < 0 || bitmap.height() < 0 || (bitmap.rowBytes() < (bitmap.width() * sizeof(GPixel)))) {
        return NULL;
    } else {
        return new Mine(bitmap);
    }       
}

////////////////////////////////////////////////////////////////////////////////

class BitmapShade: public GShader {
	public:
		BitmapShade(const GBitmap& bitmap, const float localMatrix[6]) {
			b = bitmap;

			float invLocalMatrix[6];
			invertMatrix(localMatrix, invLocalMatrix);
			for (int i = 0; i < 6; i++) {m[i] = invLocalMatrix[i];}
		}
		bool setContext(const float ctm[6]) override {
			// All I want this method to do is:
				// concat inverse to m

			// Determine if invertible matrix
			if (!isValidMatrix(ctm)) {return false;}

			// Find inverse of matrix
			float invCTM[6];
			invertMatrix(ctm, invCTM);

			// Concat to m
			float concatted[6];
			float tempM[6];
			for (int i = 0; i < 6; i++) {tempM[i] = m[i];}
			concatMatrices(tempM, invCTM, concatted);
			for (int i = 0; i < 6; i++) {m[i] = concatted[i];}

			return true;
		}
	    void shadeRow(int x, int y, int count, GPixel row[]) override {
	    	// x is starting pixel
	    	// y is current row
	    	// count is the number of pixels to place into row
	    	// row[] is the array that we are storing the pixels to place into the bitmap

	    	float xP, yP;
	    	GPoint p;

	    	for (int i = x; i < count; i++) {
	    		p = givePoint(i, y, m);
	    		xP = p.x();
	    		yP = p.y();

	    		int fx, fy;

	    		// Clip if off edge
	    		if (xP < 0 && yP < 0) { // top-left
	    			fx = 0;
	    			fy = 0;
	    		} else if (xP >= b.width() && yP < 0) { // top-right
	    			fx = b.width() - 1;
	    			fy = 0;
	    		} else if (xP < 0 && yP >= b.height()) { // bottom-left
	    			fx = 0;
	    			fy = b.height() - 1;
	    		} else if (xP >= b.width() && yP >= b.height()) { // bottom-right
	    			fx = b.width() - 1;
	    			fy = b.height() - 1;
	    		} else if (xP < 0) { // left
	    			fx = 0;
	    			fy = (int)yP;
	    		} else if (yP < 0) { // top
	    			fx = (int)xP;
	    			fy = 0;
	    		} else if (xP >= b.width()) { // right
	    			fx = b.width() - 1;
	    			fy = (int)yP;
	    		} else if (yP >= b.height()) { // bottom
	    			fx = (int)xP;
	    			fy = b.height() - 1;
	    		} else { // within bounds
	    			fx = (int)xP;
	    			fy = (int)yP;
	    		}

	    		// Get pixel from bitmap
	    		row[i] = *b.getAddr(fx, fy);
	    	}

	    }
	    void invertMatrix(const float m[6], float inv[6]) {
			inv[0] = m[4] / ((m[0]*m[4]) - (m[1]*m[3]));
			inv[1] = m[1] / ((m[1]*m[3]) - (m[0]*m[4]));
			inv[2] = ((m[2]*m[4]) - (m[1]*m[4])) / ((m[1]*m[3]) - (m[0]*m[4]));
			inv[3] = m[3] / ((m[1]*m[3]) - (m[0]*m[4]));
			inv[4] = m[0] / ((m[0]*m[4]) - (m[1]*m[3]));
			inv[5] = ((m[2]*m[3]) - (m[0]*m[5])) / ((m[0]*m[4]) - (m[1]*m[3]));
		}

		GPoint givePoint(int a, int b, float m[6]) {
			float x = (a*m[0]) + (b*m[1]) + (m[2]);
			float y = (a*m[3]) + (b*m[4]) + (m[5]);

			return GPoint::Make(x, y);
		}

		void concatMatrices(float x[6], float y[6], float z[6]) {
			z[0] = x[0]*y[0] + x[1]*y[3];
			z[1] = x[0]*y[1] + x[1]*y[4];
			z[2] = x[0]*y[2] + x[1]*y[5] + x[2];
			z[3] = x[3]*y[0] + x[4]*y[3];
			z[4] = x[3]*y[1] + x[4]*y[4];
			z[5] = x[3]*y[2] + x[4]*y[5] + x[5];
		}

		bool isValidMatrix(const float m[6]) {
			float det = (m[0])*(m[4]) - (m[3])*(m[1]);

			if (det == 0) {return false;}

			return true;
		}

	private:
		GBitmap b;
		float m[6];
};

class LineShade: public GShader {
	public:
		LineShade(const GPoint pts[2], const GColor colors[2]) {
			// store parameters
			lp = pts[0];
			rp = pts[1];
			lc = colors[0];
			rc = colors[1];

			lc = lc.pinToUnit();
	    	la = (int)(lc.fA * 255.99999);
			lr = (int)(lc.fR * lc.fA * 255.99999);
			lg = (int)(lc.fG * lc.fA * 255.99999);
			lb = (int)(lc.fB * lc.fA * 255.99999);

			rc = rc.pinToUnit();
			ra = (int)(rc.fA * 255.99999);
			rr = (int)(rc.fR * rc.fA * 255.99999);
			rg = (int)(rc.fG * rc.fA * 255.99999);
			rb = (int)(rc.fB * rc.fA * 255.99999);
		}
		bool setContext(const float ctm[6]) override {
			if (!isValidMatrix(ctm)) {return false;}

			float invCTM[6];
			float tempCTM[6];

			for (int i = 0; i < 6; i++) {tempCTM[i] = ctm[i];}

			invertMatrix(tempCTM, invCTM);

			for (int i = 0; i < 6; i++) {m[i] = invCTM[i];}

			lp = givePoint(lp.x(), lp.y(), m);
			rp = givePoint(rp.x(), rp.y(), m);

			return true;
		}
	    void shadeRow(int x, int y, int count, GPixel row[]) override {
	    	// sx, sy <- inverse[x+0.5, y+0.5]
	    	// clamp 0...1
	    	//		 0...width, height (bitmap)
	    	// lookup()
	    	// t, loop a color

	    	// variables to hold a, r, g, b values
			unsigned na, nr, ng, nb;

			// variables used to calculate d/D
			float t, fx, fy;

			// apply a matrix to those points so that it sits on the x-axis
				//	{ dx  -dy   P0.x }
				//	{ dy   dx   P0.y }
				//	{ 0    0      1  }
			float dx = rp.x() - lp.x();
    		float dy = rp.y() - lp.y();
	    	float D = sqrt( dx*dx + dy*dy );

    		float u[6] = {dx, (-1*dy), lp.x(), dy, dx, lp.y()};
    		float invU[6];
    		invertMatrix(u, invU);
    		float both[6];
    		concatMatrices(invU, m, both);

    		GPoint f;

	    	for (int i = x; i < count; i++) {
	    		f = givePoint(i, y, both);

	    		t = f.x();
	
				na = (int)((lc.fA*(1 - t) + rc.fA*t) * 255.99999);
				nr = (int)((lc.fR*(1 - t) + rc.fR*t) * 255.99999);
				ng = (int)((lc.fG*(1 - t) + rc.fG*t) * 255.99999);
				nb = (int)((lc.fB*(1 - t) + rc.fB*t) * 255.99999);

				if (t < 0) {
					row[i] = GPixel_PackARGB(la, lr, lg, lb);
				} else if (t > 1) {
					row[i] = GPixel_PackARGB(ra, rr, rg, rb);
				} else {
					row[i] = GPixel_PackARGB(na, nr, ng, nb);
				}
			}
	    }
	    void invertMatrix(float m[6], float inv[6]) {		
	    	inv[0] = m[4] / ((m[0]*m[4]) - (m[1]*m[3]));
			inv[1] = m[1] / ((m[1]*m[3]) - (m[0]*m[4]));
			inv[2] = ((m[2]*m[4]) - (m[1]*m[4])) / ((m[1]*m[3]) - (m[0]*m[4]));
			inv[3] = m[3] / ((m[1]*m[3]) - (m[0]*m[4]));
			inv[4] = m[0] / ((m[0]*m[4]) - (m[1]*m[3]));
			inv[5] = ((m[2]*m[3]) - (m[0]*m[5])) / ((m[0]*m[4]) - (m[1]*m[3]));
		}

		GPoint givePoint(int a, int b, float m[6]) {
			float x = (a*m[0]) + (b*m[1]) + (m[2]);
			float y = (a*m[3]) + (b*m[4]) + (m[5]);

			return GPoint::Make(x, y);
		}

		void concatMatrices(float x[6], float y[6], float z[6]) {
			z[0] = x[0]*y[0] + x[1]*y[3];
			z[1] = x[0]*y[1] + x[1]*y[4];
			z[2] = x[0]*y[2] + x[1]*y[5] + x[2];
			z[3] = x[3]*y[0] + x[4]*y[3];
			z[4] = x[3]*y[1] + x[4]*y[4];
			z[5] = x[3]*y[2] + x[4]*y[5] + x[5];
		}

		bool isValidMatrix(const float m[6]) {
			float det = (m[0])*(m[4]) - (m[3])*(m[1]);

			if (det == 0) {return false;}

			return true;
		}

	private:
		GPoint lp;
		GPoint rp;
		GColor lc;
		GColor rc;
		unsigned la;
		unsigned lr;
		unsigned lg;
		unsigned lb;
		unsigned ra;
		unsigned rr;
		unsigned rg;
		unsigned rb;
		float m[6];
};

class CircleShade: public GShader {
	public:
		CircleShade(const GPoint& center, float radius, const GColor colors[2]) {
			c = center;
			r = radius;
			lc = colors[0];
			rc = colors[1];
		}
		bool setContext(const float ctm[6]) override {
			if (!isValidMatrix(ctm)) {return false;}

			float invCTM[6];
			float tempCTM[6];

			for (int i = 0; i < 6; i++) {tempCTM[i] = ctm[i];}

			invertMatrix(tempCTM, invCTM);

			for (int i = 0; i < 6; i++) {m[i] = invCTM[i];}

			//c = givePoint(c.x(), c.y(), m);

			return true;
		}
	    void shadeRow(int x, int y, int count, GPixel row[]) override {
	    	// x is irrelevant (i.e. not going to use it)
	    	// y stays the same across the row
	    	// count is the width of the bitmap
	    	// row[] is the array that we are storing the pixels to place into the bitmap

	    	lc = lc.pinToUnit();
	    	unsigned la = (int)(lc.fA * 255.99999);
			unsigned lr = (int)(lc.fR * lc.fA * 255.99999);
			unsigned lg = (int)(lc.fG * lc.fA * 255.99999);
			unsigned lb = (int)(lc.fB * lc.fA * 255.99999);

			rc = rc.pinToUnit();
			unsigned ra = (int)(rc.fA * 255.99999);
			unsigned rr = (int)(rc.fR * rc.fA * 255.99999);
			unsigned rg = (int)(rc.fG * rc.fA * 255.99999);
			unsigned rb = (int)(rc.fB * rc.fA * 255.99999);
	    	
	    	// Initialize variables
	    	float D, ld, t;
	    	float dx, dy;

	    	D = r;

	    	GPoint f;

	    	// for loop to fill in row[]
	    	for (int i = x; i < count; i++) {
	    		f = givePoint(i, y, m);

	    		dx = f.x() - c.x();
	    		dy = f.y() - c.y();
	    		ld = sqrt( dx*dx + dy*dy );
	    		t = ld / D;

	    		if (ld == 0) {
	    			row[i] = GPixel_PackARGB(la, lr, lg, lb);
	    		} else if (ld > D) {
	    			// clamp to endpoint (i.e. color at pts[1])
	    			row[i] = GPixel_PackARGB(ra, rr, rg, rb);
	    		} else {
	    			// use calculated c value
	    				// c' = lc(1-t) + rc(t)
	    			unsigned na = (int)((lc.fA*(1 - t) + rc.fA*t) * 255.99999);
					unsigned nr = (int)((lc.fR*(1 - t) + rc.fR*t) * 255.99999);
					unsigned ng = (int)((lc.fG*(1 - t) + rc.fG*t) * 255.99999);
					unsigned nb = (int)((lc.fB*(1 - t) + rc.fB*t) * 255.99999);
	    			row[i] = GPixel_PackARGB(na, nr, ng, nb);
	    		}
	    	}
	    }
	    void invertMatrix(float m[6], float inv[6]) {
	    	inv[0] = m[4] / ((m[0]*m[4]) - (m[1]*m[3]));
			inv[1] = m[1] / ((m[1]*m[3]) - (m[0]*m[4]));
			inv[2] = ((m[2]*m[4]) - (m[1]*m[4])) / ((m[1]*m[3]) - (m[0]*m[4]));
			inv[3] = m[3] / ((m[1]*m[3]) - (m[0]*m[4]));
			inv[4] = m[0] / ((m[0]*m[4]) - (m[1]*m[3]));
			inv[5] = ((m[2]*m[3]) - (m[0]*m[5])) / ((m[0]*m[4]) - (m[1]*m[3]));
		}

		GPoint givePoint(int a, int b, float m[6]) {
			float x = (a*m[0]) + (b*m[1]) + (m[2]);
			float y = (a*m[3]) + (b*m[4]) + (m[5]);

			return GPoint::Make(x, y);
		}

		//void givePoint(float a, float b, float m[6], float x, float y) {
		//	x = (a*m[0] + b*m[1] + m[2]);
		//	y = (a*m[3] + b*m[4] + m[5]);
		//}

		void concatMatrices(float x[6], float y[6], float z[6]) {
			z[0] = x[0]*y[0] + x[1]*y[3];
			z[1] = x[0]*y[1] + x[1]*y[4];
			z[2] = x[0]*y[2] + x[1]*y[5] + x[2];
			z[3] = x[3]*y[0] + x[4]*y[3];
			z[4] = x[3]*y[1] + x[4]*y[4];
			z[5] = x[3]*y[2] + x[4]*y[5] + x[5];
		}

		bool isValidMatrix(const float m[6]) {
			float det = (m[0])*(m[4]) - (m[3])*(m[1]);

			if (det == 0) {return false;}

			return true;
		}
	private:
		GPoint c;
		float r;
		GColor lc;
		GColor rc;
		float m[6];
};

/**
 *	Return a subclass of GShader that draws the specified bitmap and local matrix.
 *	Returns null if the either parameter is not valid.
 */
GShader* GShader::FromBitmap(const GBitmap& bitmap, const float localMatrix[6]) {
	// Test bitmap
	if (bitmap.width() < 0) {return NULL;}
	if (bitmap.height() < 0) {return NULL;}
	if ((bitmap.rowBytes() < (bitmap.width() * sizeof(GPixel)))) {return NULL;}

	// Test local matrix
    //if (!isValidMatrix(localMatrix)) {return NULL;}

	return new BitmapShade(bitmap, localMatrix);
}

/**
 *	Return a subclass of GShader that draws the specified linear gradient. Returns NULL if
 *	the parameters are not valid.
 */
GShader* GShader::FromLinearGradient(const GPoint pts[2], const GColor colors[2]) {
	// Test pts
	if (pts[0].x() == pts[1].x() && pts[0].y() == pts[1].y()) {return NULL;}

	// Test colors
		// Test color[0]
	if (colors[0].fA <= 0 || colors[0].fA > 255) {return NULL;}
	if (colors[0].fR < 0 || colors[0].fR > colors[0].fA) {return NULL;}
	if (colors[0].fG < 0 || colors[0].fG > colors[0].fA) {return NULL;}
	if (colors[0].fB < 0 || colors[0].fB > colors[0].fA) {return NULL;}
		// Test color[1]
	if (colors[1].fA <= 0 || colors[1].fA > 255) {return NULL;}
	if (colors[1].fR < 0 || colors[1].fR > colors[1].fA) {return NULL;}
	if (colors[1].fG < 0 || colors[1].fG > colors[1].fA) {return NULL;}
	if (colors[1].fB < 0 || colors[1].fB > colors[1].fA) {return NULL;}

	return new LineShade(pts, colors);
}

/**
 *	Return a subclass of GShader that draws the specified radial gradient. Returns NULL if
 *	the parameters are not valid.
 */
GShader* GShader::FromRadialGradient(const GPoint& center, float radius, const GColor colors[2]) {
	// Test center

	// Test radius
	if (radius <= 0) {return NULL;}

	// Test colors
		// Test color[0]
	if (colors[0].fA <= 0 || colors[0].fA > 255) {return NULL;}
	if (colors[0].fR < 0 || colors[0].fR > colors[0].fA) {return NULL;}
	if (colors[0].fG < 0 || colors[0].fG > colors[0].fA) {return NULL;}
	if (colors[0].fB < 0 || colors[0].fB > colors[0].fA) {return NULL;}
		// Test color[1]
	if (colors[1].fA <= 0 || colors[1].fA > 255) {return NULL;}
	if (colors[1].fR < 0 || colors[1].fR > colors[1].fA) {return NULL;}
	if (colors[1].fG < 0 || colors[1].fG > colors[1].fA) {return NULL;}
	if (colors[1].fB < 0 || colors[1].fB > colors[1].fA) {return NULL;}

	return new CircleShade(center, radius, colors);
}

