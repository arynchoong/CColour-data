package com.arynchoong.ccolour.colours

class ColourBlack(upperColour: ColorName? = null) : Colour(){
    var UpperColour = ColorName("Black", 0x1C, 0x17, 0x17, 360.0f, 0.1f, 0.1f, 17.9f, 11.0f)
    /**
     * Initialize the color list that we have.
     */
    init {
        // Black
        colourList.add(ColorName("Black",0x00,0x00,0x00,0.0F,0.0F,0.0F,0.0F,0.0F))
        colourList.add(ColorName("Rich black",0x01,0x0B,0x13,207.0F,0.9F,0.04F, 0.947f, 0.075f))
        colourList.add(ColorName("Smoky black",0x10,0x0C,0x08,30.0F,0.33F,0.05F, 0.5f, 0.063f))
        colourList.add(ColorName("Black chocolate",0x1B,0x18,0x11,42.0F,0.23F,0.09F, 0.37f, 0.106f))
        colourList.add(ColorName("Eerie black",0x1B,0x1B,0x1B,0.0F,0.0F,0.11F, 0.0f, 0.106f))
        colourList.add(ColorName("Raisin black",0x24,0x21,0x24,300.0F,0.04F,0.14F, 0.083f, 0.141f ))
        colourList.add(ColorName("Onyx black",0x35,0x38,0x39,195.0F,0.04F,0.22F, 0.07f, 0.224f))
        colourList.add(ColorName("Outer space black",0x2D,0x38,0x3A,189.0F,0.13F,0.2F, 0.224f, 0.227f))
        colourList.add(ColorName("Gunmetal black",0x2a,0x34,0x39,200.0F,0.15F,0.19F, 0.263f, 0.224f))
        colourList.add(ColorName("Charleston green black",0x23,0x2B,0x2B,180.0F,0.1F,0.15F, 0.186f, 0.169f))
        colourList.add(ColorName("Pine tree black",0x2A,0x2F,0x23,85.0F,0.15F,0.16F, 0.255f, 0.184f))
        colourList.add(ColorName("Dark jungle green black",0x1A,0x24,0x21,162.0F,0.16F,0.12F, 0.278f, 0.141f))
        colourList.add(ColorName("Black coffee",0x3B,0x2F,0x2F,0.0F,0.11F,0.21F, 0.203f, 0.231f))
        colourList.add(ColorName("Kombu green black",0x35,0x42,0x30,103.0F,0.16F,0.22F, 0.273f, 0.259f))
        colourList.add(ColorName("Dark lava black",0x48,0x3C,0x32,27.0F,0.18F,0.24F, 0.306f, 0.282f))
        colourList.add(ColorName("Old burgundy black",0x43,0x30,0x2E,6.0F,0.19F,0.22F, 0.313f, 0.263f))
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