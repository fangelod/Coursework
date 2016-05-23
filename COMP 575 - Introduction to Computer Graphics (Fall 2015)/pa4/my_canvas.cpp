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
#include <algorithm>
#include <vector>

    
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

	/**
	 *	Scale and translate the bitmap such that is fills the specific rectangle.
	 *
	 *	Any area in the rectangle that is outside of the bounds of the canvas is ignored.
	 *
	 *	If a given pixel in the bitmap is not opaque (e.g. GPixel_GetA() < 255) then blend it
	 *	using SRCOVER blend mode.
	 */
	void fillBitmapRect(const GBitmap& src, const GRect& dst) {
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
						float lA = GPixel_GetA(*src.getAddr(xP, yP)) / 255.99999;
						float lR = GPixel_GetR(*src.getAddr(xP, yP)) / 255.99999;
						float lG = GPixel_GetG(*src.getAddr(xP, yP)) / 255.99999;
						float lB = GPixel_GetB(*src.getAddr(xP, yP)) / 255.99999;

						
						float sA = lA + ((GPixel_GetA(*draw.getAddr(x, y)) / 255.99999) * (1.0f - lA));
						float sR = lR + ((GPixel_GetR(*draw.getAddr(x, y)) / 255.99999) * (1.0f - lA));
						float sG = lG + ((GPixel_GetG(*draw.getAddr(x, y)) / 255.99999) * (1.0f - lA));
						float sB = lB + ((GPixel_GetB(*draw.getAddr(x, y)) / 255.99999) * (1.0f - lA));
						

						/*
						float sA = lA + ((GPixel_GetA(*draw.getAddr(nX, nY)) / 255.99999) * (1.0f - lA));
						float sR = lR + ((GPixel_GetR(*draw.getAddr(nX, nY)) / 255.99999) * (1.0f - lA));
						float sG = lG + ((GPixel_GetG(*draw.getAddr(nX, nY)) / 255.99999) * (1.0f - lA));
						float sB = lB + ((GPixel_GetB(*draw.getAddr(nX, nY)) / 255.99999) * (1.0f - lA));
						*/
										
						nA = (int)(sA * 255.99999);
						nR = (int)(sR * 255.99999);
						nG = (int)(sG * 255.99999);
						nB = (int)(sB * 255.99999);

	    	    		d[x] = GPixel_PackARGB(nA, nR, nG, nB);
	    	    		//d = (GPixel*)((char*)d + (int)nY * draw.rowBytes());
	    	    		//d[nX] = GPixel_PackARGB(nA, nR, nG, nB);
	    	    		//d = (GPixel*)((char*)d - (int)nY * draw.rowBytes());
    	    		} else {
    	    			d[x] = GPixel_PackARGB(a, r, g, b);
    	    			//d = (GPixel*)((char*)d + (int)nY * draw.rowBytes());
	    	    		//d[nX] = GPixel_PackARGB(a, r, g, b);
	    	    		//d = (GPixel*)((char*)d - (int)nY * draw.rowBytes());
    	    		}	    		
				}
			}
			d = (GPixel*)((char*)d + draw.rowBytes());
		}
	}

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

	// Helper functions
	float *scalePoint(float x, float y, int in[3]) {
		/*
			[sx  0  0][x] = [(sx)(x) + (0)(y) + (0)(1)] = [(sx)(x)]
			[0  sy  0][y] = [(0)(x) + (sy)(y) + (0)(1)] = [(sy)(y)]
			[0   0  1][1] = [(0)(x) + (0)(y) +  (1)(1)] = [   1   ]
		*/
		float sM[3][3] = {{x, 0, 0}, {0, y, 0}, {0, 0, 1}};
		float* calc = new float[3];
		calc[0] = (sM[0][0]*in[0])+(sM[0][1]*in[1])+(sM[0][2]*in[2]); 
		calc[1] = (sM[1][0]*in[0])+(sM[1][1]*in[1])+(sM[1][2]*in[2]); 
		calc[2] = (sM[2][0]*in[0])+(sM[2][1]*in[1])+(sM[2][2]*in[2]);

		return calc;
	}

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


		/*
		// Get dimensions of destination rectangle
		int left = dst.left();
		int top = dst.top();
		int right = dst.right();
		int bottom = dst.bottom();
		int rW = dst.width();
		int rH = dst.height();

    	if (src.width() == 391 && dst.left() != -80) {
    		printf("DST:\n");
			printf("L: %f\n", dst.left());
			printf("T: %f\n", dst.top());
			printf("R: %f\n", dst.right());
			printf("B: %f\n", dst.bottom());
			printf("\n");
    	}

		// Fix rectangle if needed
		
		if (left < 0) {left = 0;}
		if (top < 0) {top = 0;}
		if (right > draw.width()) {right = draw.width();}
		if (bottom > draw.height()) {bottom = draw.height();}
		

		// Get dimensions of source bitmap
		int bW = src.width();
		int bH = src.height();

		// Scale factor from destination to source
		float sx = (float)bW/(float)rW;
		float sy = (float)bH/(float)rH;

		// for loop that takes each point in dst and find corresponding point in src
		GPixel* d = draw.fPixels;
		d = (GPixel*)((char*)d + (int)top * draw.rowBytes());
		for (int y = top; y < bottom; ++y) {
			for (int x = left; x < right; ++x) {
				int point[3] = {x, y, 1};
				float *fp;
				fp = scalePoint(sx, sy, point);
				
				int xP;
				int yP;

				// Conditions for handling scaled points
				if (sx >= 1 && sy >= 1) {
					xP = GFloorToInt(*(fp)) + GFloorToInt(0.5 * sx);
					yP = GFloorToInt(*(fp + 1)) + GFloorToInt(0.5 * sy);
				} else if (sx < 1 && sy < 1) {
					xP = GFloorToInt(*fp);
					yP = GFloorToInt(*(fp + 1));
				} else if (sx < 1 && sy >= 1) {
					xP = GFloorToInt(*fp);
					yP = GFloorToInt(*(fp + 1)) + GFloorToInt(0.5 * sy);
				} else if (sx >= 1 && sy < 1) {
					xP = GFloorToInt(*(fp)) + GFloorToInt(0.5 * sx);
					yP = GFloorToInt(*(fp + 1));
				}

				if (xP >= src.width()) {
					xP = src.width() - 1;
				} 
				if (yP >= src.height()) {
					yP = src.height() - 1;
				}

				// blend if alpha isn't 255

				
				float ba = GPixel_GetA(*src.getAddr(xP, yP)) / 255.99999;
				float br = GPixel_GetR(*src.getAddr(xP, yP)) / 255.99999; 
				float bg = GPixel_GetG(*src.getAddr(xP, yP)) / 255.99999;
				float bb = GPixel_GetB(*src.getAddr(xP, yP)) / 255.99999;

				float ra = GPixel_GetA(*src.getAddr(xP, yP)) / 255.99999;
				float rr = GPixel_GetR(*src.getAddr(xP, yP)) / 255.99999; 
				float rg = GPixel_GetG(*src.getAddr(xP, yP)) / 255.99999;
				float rb = GPixel_GetB(*src.getAddr(xP, yP)) / 255.99999;

				float sA = ba + (ra * (1.0f - ba));
				float sR = br + (rr * (1.0f - ba));
				float sG = bg + (rg * (1.0f - ba));
				float sB = bb + (rb * (1.0f - ba));
								
				unsigned nA = (int)(sA * 255.99999);
				unsigned nR = (int)(sR * 255.99999);
				unsigned nG = (int)(sG * 255.99999);
				unsigned nB = (int)(sB * 255.99999);

	    		d[x] = GPixel_PackARGB(nA, nR, nG, nB);
	    		
	    		if ((x >= 0 && x < draw.width()) && (y >= 0 && y < draw.height())) {
	    			d[x] = GPixel_PackARGB(GPixel_GetA(*src.getAddr(xP, yP)), 
					GPixel_GetR(*src.getAddr(xP, yP)), 
					GPixel_GetG(*src.getAddr(xP, yP)), 
					GPixel_GetB(*src.getAddr(xP, yP)));
	    		}
			}
			d = (GPixel*)((char*)d + draw.rowBytes());
		}
		*/