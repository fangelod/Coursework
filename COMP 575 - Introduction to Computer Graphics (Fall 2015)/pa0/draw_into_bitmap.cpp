/*
 *  Copyright 2015 Franz Dominno
 */

#include "GBitmap.h"
#include "GPixel.h"

extern void cs575_draw_into_bitmap(const GBitmap&);

/*
 *  The bitmap has already been sized and allocated. This function's job is to fill in the
 *  pixels to create the custom image. See src/image.cpp for examples.
 */
void cs575_draw_into_bitmap(const GBitmap& bitmap) {
    //
    // Your code goes here
    //
  const float rx = 200;
  const float ry = 50;
  const float rz = (rx - ry)/bitmap.width();

  const float bx = 50;
  const float by = 200;
  const float bz = (by - bx)/bitmap.width();

  GPixel* dst = bitmap.fPixels;
  float r = rx + rz/4;
  for (int y = 0; y < bitmap.height(); ++y) {
  	float b = bx + bz/4;
	for (int x = 0; x < bitmap.width(); ++x) {
		dst[x] = GPixel_PackARGB(0xFF, (int)r, 0, (int)b);
		b += bz;
	}
	r += rz;
	dst = (GPixel*)((char*)dst + bitmap.rowBytes());
  }
}

