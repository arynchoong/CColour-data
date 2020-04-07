package com.arynchoong.ccolour.colours

open class Colour {
    val colourList : ArrayList<ColorName> = ArrayList()
    /**
     * Initialize the color list that we have.
     */
    init {
        colourList.add(ColorName("White", 0xFF, 0xFF, 0xFF, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F))
        colourList.add(ColorName("Light grey", 0xC0, 0xC0, 0xC0, 0.0F, 0.0F, 0.75F, 0.0F, 0.75F))
        colourList.add(ColorName("Dark grey", 0x80, 0x80, 0x80, 0.0F, 0.0F, 0.5F, 0.0F, 0.5F))
        colourList.add(ColorName("Black", 0x00, 0x00, 0x00, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F))
        colourList.add(ColorName("Red", 0xFF, 0x00, 0x00, 0.0F, 1.0F, 0.5F, 1.0F, 1.0F))
        colourList.add(ColorName("Dark red", 0x80, 0x00, 0x00, 0.0F, 1.0F, 0.25F, 1.0F, 0.5F))
        colourList.add(ColorName("Yellow", 0xFF, 0xFF, 0x00, 60.0F, 1.0F, 0.5F, 1.0F, 1.0F))
        colourList.add(ColorName("Brown", 0x80, 0x80, 0x00, 60.0F, 1.0F, 0.25F, 1.0F, 0.5F))
        colourList.add(ColorName("Lime green", 0x00, 0xFF, 0x00, 120.0F, 1.0F, 0.5F, 1.0F, 1.0F))
        colourList.add(ColorName("Green", 0x00, 0x80, 0x00, 120.0F, 1.0F, 0.25F, 1.0F, 0.5F))
        colourList.add(ColorName("Cyan", 0x00, 0xFF, 0xFF, 180.0F, 1.0F, 0.5F, 1.0F, 1.0F))
        colourList.add(ColorName("Dark cyan", 0x00, 0x80, 0x80, 180.0F, 1.0F, 0.25F, 1.0F, 0.5F))
        colourList.add(ColorName("Blue", 0x00, 0x00, 0xFF, 240.0F, 1.0F, 0.5F, 1.0F, 1.0F))
        colourList.add(ColorName("Dark blue", 0x00, 0x00, 0x80, 240.0F, 1.0F, 0.25F, 1.0F, 0.5F))
        colourList.add(ColorName("Bright purple", 0xFF, 0x00, 0xFF, 300.0F, 1.0F, 0.5F, 1.0F, 1.0F))
        colourList.add(ColorName("Purple", 0x80, 0x00, 0x80, 300.0F, 1.0F, 0.25F, 1.0F, 0.5F))
    }

    /**
     * SubClass of ColorUtils. In order to lookup color name
     */
    inner class ColorName(var name: String, var r: Int, var g: Int, var b: Int, var h: Float, var sl: Float, var l: Float, var sv: Float, var v: Float) {
        /**
         * name: String
         * r, g, b: 0-255 : Int
         * h: 0.0 - 360.0 : Float
         * sl, l, sv, v: 0.0 - 1.0 : Float
         */
        fun computeMSE(pixR: Int, pixG: Int, pixB: Int): Int {
            return (((pixR - r) * (pixR - r) + (pixG - g) * (pixG - g) + ((pixB - b)
                    * (pixB - b))) / 3)
        }

        fun computeMseHSL(hue: Float, sat: Float, light: Float): Float {
            return ((( ((hue - h) * (hue - h)) / 360.0f * 47.5f) + ((sat - sl) * (sat - sl) * 28.75f) + ((light - l) * (light - l) * 23.75f)) / 3)
        }

        fun computeMseSL(hue: Float, sat: Float, light: Float): Float {
            return ((( ((hue - h) * (hue - h)) / 360.0f) + ((sat - sl) * (sat - sl)) + ((light - l) * (light - l))) / 3)
        }
    }

    /**
     * Get the closest color name from our list
     *
     * @param r
     * @param g
     * @param b
     * @return
     */
    fun getColorNameFromRgb(r: Int, g: Int, b: Int): String {
        var closestMatch: ColorName? = null
        var minMSE = Int.MAX_VALUE
        var mse: Int
        for (c in colourList) {
            mse = c.computeMSE(r, g, b)
            if (mse < minMSE) {
                minMSE = mse
                closestMatch = c
            }
        }
        return closestMatch?.name ?: "No matched color name."
    }

    /**
     * Convert hexColor to rgb, then call getColorNameFromRgb(r, g, b)
     *
     * @param hexColor
     * @return
     */
    fun getColorNameFromHex(hexColor: Int): String {
        val r = hexColor and 0xFF0000 shr 16
        val g = hexColor and 0xFF00 shr 8
        val b = hexColor and 0xFF
        return getColorNameFromRgb(r, g, b)
    }

    /**
     * Generate closest color name from our list
     *
     * @param h :Float 0-360
     * @param s :Float 0-1
     * @param l :Float 0-1
     * @return
     */
    open fun getColorNameFromHsl(h: Float, s: Float, l:Float): String {
        val sHue  = h
        val sSat = s
        val sLum = s
        var closestMatch: ColorName? = null
        var minMSE = Float.MAX_VALUE
        var mse: Float
        for (c in colourList) {
            mse = c.computeMseHSL(sHue,sSat,sLum)
            if (mse < minMSE) {
                minMSE = mse
                closestMatch = c
            }
        }

        return closestMatch?.name ?: "No matched color name."
    }

    fun getColorHue(h: Float): String {
        if (h < 30) {
            return "Red"
        } else if (h < 42) {
            return "Orange"
        } else if (h < 65) {
            return "Yellow"
        } else if (h < 160) {
            return "Green"
        } else if (h < 267) {
            return "Blue"
        } else if (h < 310) {
            return "Purple"
        } else {
            return "Red"
        }
    }
}