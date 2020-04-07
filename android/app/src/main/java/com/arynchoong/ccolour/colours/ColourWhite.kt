package com.arynchoong.ccolour.colours

class ColourWhite(upperColour: ColorName? = null) : Colour(){
    var UpperColour = ColorName("White",0xFF,0xF5,0xF5,360.0F,0.99F,0.98F,0.04F,1.00F)
    /**
     * Initialize the color list that we have.
     */
    init {
        // White
        colourList.add(ColorName("White",0xFF,0xFF,0xFF,0.0F,0.0F,1.0F,0.00F,1.00F))
        colourList.add(ColorName("Snow",0xFF,0xFA,0xFA,0.0F,1.0F,0.99F,0.02F,1.00F))
        colourList.add(ColorName("Ghost white",0xF8,0xF8,0xFF,240.0F,1.0F,0.99F,0.03F,1.00F))
        colourList.add(ColorName("Baby powder white",0xFE,0xFE,0xFA,60.0F,0.67F,0.99F,0.02F,1.00F))
        colourList.add(ColorName("Mint cream white",0xF5,0xFF,0xFA,150.0F,1.0F,0.98F,0.04F,1.00F))
        colourList.add(ColorName("Honeydew white",0xF0,0xFF,0xF0,120.0F,1.0F,0.97F,0.06F,1.00F))
        colourList.add(ColorName("Azure white",0xF0,0xFF,0xFF,180.0F,1.0F,0.97F,0.06F,1.00F))
        colourList.add(ColorName("Alice blue white",0xF0,0xF8,0xFF,208.0F,1.0F,0.97F,0.06F,1.00F))
        colourList.add(ColorName("Seashell white",0xFF,0xF5,0xEE,25.0F,1.0F,0.97F,0.07F,1.00F))
        colourList.add(ColorName("Lavender blush white",0xFF,0xF0,0xF5,340.0F,1.0F,0.97F,0.06F,1.00F))
        colourList.add(ColorName("Floral white",0xFF,0xFA,0xF0,40.0F,1.0F,0.97F,0.06F,1.00F))
        colourList.add(ColorName("Ivory",0xFF,0xFF,0xF0,60.0F,1.0F,0.97F,0.06F,1.00F))
        colourList.add(ColorName("Old lace white",0xFD,0xF5,0xE6,39.0F,0.85F,0.95F,0.09F,0.99F))
        colourList.add(ColorName("Cosmic latte white",0xFF,0xF8,0xE7,43.0F,1.0F,0.95F,0.09F,1.00F))
        if (upperColour == null) {
            colourList.add(UpperColour)
        } else {
            colourList.add(upperColour)
        }
    }

    override fun getColorNameFromHsl(h: Float, s: Float, l:Float): String {
        val sHue  = h
        val sSat = s
        val sLum = s
        var closestMatch: ColorName? = null
        var minMSE = Float.MAX_VALUE
        var mse: Float
        for (c in colourList) {
            mse = c.computeMseSL(sHue,sSat,sLum)
            if (mse < minMSE) {
                minMSE = mse
                closestMatch = c
            }
        }

        return closestMatch?.name ?: "No matched color name."
    }
}