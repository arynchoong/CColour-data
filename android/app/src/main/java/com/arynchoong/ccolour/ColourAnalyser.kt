package com.arynchoong.ccolour

import java.util.*


/**
 * Java Code to get a color name from rgb/hex value/awt color
 *
 * The part of looking up a color name from the rgb values is edited from
 * https://gist.github.com/nightlark/6482130#file-gistfile1-java (that has some errors) by Ryan Mast (nightlark)
 *
 * @author Xiaoxiao Li
 */
class ColourAnalyser {
    /**
     * Initialize the color list that we have.
     */
    private fun initColorList(): ArrayList<ColorName> {
        val colorList = ArrayList<ColorName>()
//        colorList.add(ColorName("AliceBlue", 0xF0, 0xF8, 0xFF, 208, 100, 97))
//        colorList.add(ColorName("AntiqueWhite", 0xFA, 0xEB, 0xD7, 34, 78, 91))
        colorList.add(ColorName("Aqua", 0x00, 0xFF, 0xFF, 180, 100, 50))
        colorList.add(ColorName("Aquamarine", 0x7F, 0xFF, 0xD4, 160, 100, 75))
        colorList.add(ColorName("Azure", 0xF0, 0xFF, 0xFF, 180, 100, 97))
        colorList.add(ColorName("Beige", 0xF5, 0xF5, 0xDC, 60, 56, 91))
//        colorList.add(ColorName("Bisque", 0xFF, 0xE4, 0xC4, 33, 100, 88))
        colorList.add(ColorName("Black", 0x00, 0x00, 0x00, 0, 0, 0))
//        colorList.add(ColorName("BlanchedAlmond", 0xFF, 0xEB, 0xCD, 36, 100, 90))
        colorList.add(ColorName("Blue", 0x00, 0x00, 0xFF, 240, 100, 50))
        colorList.add(ColorName("BlueViolet", 0x8A, 0x2B, 0xE2, 271, 76, 53))
        colorList.add(ColorName("Brown", 0xA5, 0x2A, 0x2A, 0, 59, 41))
//        colorList.add(ColorName("BurlyWood", 0xDE, 0xB8, 0x87, 34, 57, 70))
//        colorList.add(ColorName("CadetBlue", 0x5F, 0x9E, 0xA0, 182, 25, 50))
//        colorList.add(ColorName("Chartreuse", 0x7F, 0xFF, 0x00, 90, 100, 50))
//        colorList.add(ColorName("Chocolate", 0xD2, 0x69, 0x1E, 25, 75, 47))
        colorList.add(ColorName("Coral", 0xFF, 0x7F, 0x50, 16, 100, 66))
        colorList.add(ColorName("CornflowerBlue", 0x64, 0x95, 0xED, 219, 79, 66))
//        colorList.add(ColorName("Cornsilk", 0xFF, 0xF8, 0xDC, 48, 100, 93))
        colorList.add(ColorName("Crimson", 0xDC, 0x14, 0x3C, 348, 83, 47))
        colorList.add(ColorName("Cyan", 0x00, 0xFF, 0xFF, 180, 100, 50))
        colorList.add(ColorName("DarkBlue", 0x00, 0x00, 0x8B, 240, 100, 27))
        colorList.add(ColorName("DarkCyan", 0x00, 0x8B, 0x8B, 180, 100, 27))
        colorList.add(ColorName("DarkGoldenRod", 0xB8, 0x86, 0x0B, 43, 89, 38))
        colorList.add(ColorName("DarkGray", 0xA9, 0xA9, 0xA9, 0, 0, 66))
        colorList.add(ColorName("DarkGreen", 0x00, 0x64, 0x00, 120, 100, 20))
        colorList.add(ColorName("DarkKhaki", 0xBD, 0xB7, 0x6B, 56, 38, 58))
        colorList.add(ColorName("DarkMagenta", 0x8B, 0x00, 0x8B, 300, 100, 27))
        colorList.add(ColorName("DarkOliveGreen", 0x55, 0x6B, 0x2F, 82, 39, 30))
        colorList.add(ColorName("DarkOrange", 0xFF, 0x8C, 0x00, 33, 100, 50))
        colorList.add(ColorName("DarkOrchid", 0x99, 0x32, 0xCC, 280, 61, 50))
        colorList.add(ColorName("DarkRed", 0x8B, 0x00, 0x00, 0, 100, 27))
        colorList.add(ColorName("DarkSalmon", 0xE9, 0x96, 0x7A, 15, 72, 70))
        colorList.add(ColorName("DarkSeaGreen", 0x8F, 0xBC, 0x8F, 120, 25, 65))
        colorList.add(ColorName("DarkSlateBlue", 0x48, 0x3D, 0x8B, 248, 39, 39))
        colorList.add(ColorName("DarkSlateGray", 0x2F, 0x4F, 0x4F, 180, 25, 25))
        colorList.add(ColorName("DarkTurquoise", 0x00, 0xCE, 0xD1, 181, 100, 41))
        colorList.add(ColorName("DarkViolet", 0x94, 0x00, 0xD3, 282, 100, 41))
        colorList.add(ColorName("DeepPink", 0xFF, 0x14, 0x93, 328, 100, 54))
        colorList.add(ColorName("DeepSkyBlue", 0x00, 0xBF, 0xFF, 195, 100, 50))
        colorList.add(ColorName("DimGray", 0x69, 0x69, 0x69, 0, 0, 41))
        colorList.add(ColorName("DodgerBlue", 0x1E, 0x90, 0xFF, 210, 100, 56))
//        colorList.add(ColorName("FireBrick", 0xB2, 0x22, 0x22, 0, 68, 42))
        colorList.add(ColorName("FloralWhite", 0xFF, 0xFA, 0xF0, 40, 100, 97))
        colorList.add(ColorName("ForestGreen", 0x22, 0x8B, 0x22, 120, 61, 34))
//        colorList.add(ColorName("Fuchsia", 0xFF, 0x00, 0xFF, 300, 100, 50))
//        colorList.add(ColorName("Gainsboro", 0xDC, 0xDC, 0xDC, 0, 0, 86))
        colorList.add(ColorName("GhostWhite", 0xF8, 0xF8, 0xFF, 240, 100, 99))
        colorList.add(ColorName("Gold", 0xFF, 0xD7, 0x00, 51, 100, 50))
        colorList.add(ColorName("GoldenRod", 0xDA, 0xA5, 0x20, 43, 74, 49))
        colorList.add(ColorName("Gray", 0x80, 0x80, 0x80, 0, 0, 50))
        colorList.add(ColorName("Green", 0x00, 0x80, 0x00, 120, 100, 25))
        colorList.add(ColorName("GreenYellow", 0xAD, 0xFF, 0x2F, 84, 100, 59))
//        colorList.add(ColorName("HoneyDew", 0xF0, 0xFF, 0xF0, 120, 100, 97))
        colorList.add(ColorName("HotPink", 0xFF, 0x69, 0xB4, 330, 100, 71))
        colorList.add(ColorName("IndianRed", 0xCD, 0x5C, 0x5C, 0, 53, 58))
        colorList.add(ColorName("Indigo", 0x4B, 0x00, 0x82, 275, 100, 25))
        colorList.add(ColorName("Ivory", 0xFF, 0xFF, 0xF0, 60, 100, 97))
//        colorList.add(ColorName("Khaki", 0xF0, 0xE6, 0x8C, 54, 77, 75))
//        colorList.add(ColorName("Lavender", 0xE6, 0xE6, 0xFA, 240, 67, 94))
//        colorList.add(ColorName("LavenderBlush", 0xFF, 0xF0, 0xF5, 340, 100, 97))
        colorList.add(ColorName("LawnGreen", 0x7C, 0xFC, 0x00, 90, 100, 49))
//        colorList.add(ColorName("LemonChiffon", 0xFF, 0xFA, 0xCD, 54, 100, 90))
        colorList.add(ColorName("LightBlue", 0xAD, 0xD8, 0xE6, 195, 53, 79))
        colorList.add(ColorName("LightCoral", 0xF0, 0x80, 0x80, 0, 79, 72))
        colorList.add(ColorName("LightCyan", 0xE0, 0xFF, 0xFF, 180, 100, 94))
        colorList.add(ColorName("LightGoldenRodYellow", 0xFA, 0xFA, 0xD2, 60, 80, 90))
        colorList.add(ColorName("LightGray", 0xD3, 0xD3, 0xD3, 0, 0, 83))
        colorList.add(ColorName("LightGreen", 0x90, 0xEE, 0x90, 120, 73, 75))
        colorList.add(ColorName("LightPink", 0xFF, 0xB6, 0xC1, 351, 100, 86))
        colorList.add(ColorName("LightSalmon", 0xFF, 0xA0, 0x7A, 17, 100, 74))
        colorList.add(ColorName("LightSeaGreen", 0x20, 0xB2, 0xAA, 177, 70, 41))
        colorList.add(ColorName("LightSkyBlue", 0x87, 0xCE, 0xFA, 203, 92, 75))
        colorList.add(ColorName("LightSlateGray", 0x77, 0x88, 0x99, 210, 14, 53))
        colorList.add(ColorName("LightSteelBlue", 0xB0, 0xC4, 0xDE, 214, 41, 78))
        colorList.add(ColorName("LightYellow", 0xFF, 0xFF, 0xE0, 60, 100, 94))
        colorList.add(ColorName("Lime", 0x00, 0xFF, 0x00, 120, 100, 50))
        colorList.add(ColorName("LimeGreen", 0x32, 0xCD, 0x32, 120, 61, 50))
        colorList.add(ColorName("Linen", 0xFA, 0xF0, 0xE6, 30, 67, 94))
        colorList.add(ColorName("Magenta", 0xFF, 0x00, 0xFF, 300, 100, 50))
        colorList.add(ColorName("Maroon", 0x80, 0x00, 0x00, 0, 100, 25))
        colorList.add(ColorName("MediumAquaMarine", 0x66, 0xCD, 0xAA, 160, 51, 60))
        colorList.add(ColorName("MediumBlue", 0x00, 0x00, 0xCD, 240, 100, 40))
//        colorList.add(ColorName("MediumOrchid", 0xBA, 0x55, 0xD3, 288, 59, 58))
        colorList.add(ColorName("MediumPurple", 0x93, 0x70, 0xDB, 260, 60, 65))
        colorList.add(ColorName("MediumSeaGreen", 0x3C, 0xB3, 0x71, 147, 50, 47))
        colorList.add(ColorName("MediumSlateBlue", 0x7B, 0x68, 0xEE, 249, 80, 67))
        colorList.add(ColorName("MediumSpringGreen", 0x00, 0xFA, 0x9A, 157, 100, 49))
        colorList.add(ColorName("MediumTurquoise", 0x48, 0xD1, 0xCC, 178, 60, 55))
        colorList.add(ColorName("MediumVioletRed", 0xC7, 0x15, 0x85, 322, 81, 43))
        colorList.add(ColorName("MidnightBlue", 0x19, 0x19, 0x70, 240, 64, 27))
//        colorList.add(ColorName("MintCream", 0xF5, 0xFF, 0xFA, 150, 100, 98))
//        colorList.add(ColorName("MistyRose", 0xFF, 0xE4, 0xE1, 6, 100, 94))
//        colorList.add(ColorName("Moccasin", 0xFF, 0xE4, 0xB5, 38, 100, 85))
//        colorList.add(ColorName("NavajoWhite", 0xFF, 0xDE, 0xAD, 36, 100, 84))
//        colorList.add(ColorName("Navy", 0x00, 0x00, 0x80, 240, 100, 25))
//        colorList.add(ColorName("OldLace", 0xFD, 0xF5, 0xE6, 39, 85, 95))
        colorList.add(ColorName("Olive", 0x80, 0x80, 0x00, 60, 100, 25))
//        colorList.add(ColorName("OliveDrab", 0x6B, 0x8E, 0x23, 80, 60, 35))
        colorList.add(ColorName("Orange", 0xFF, 0xA5, 0x00, 39, 100, 50))
        colorList.add(ColorName("OrangeRed", 0xFF, 0x45, 0x00, 16, 100, 50))
//        colorList.add(ColorName("Orchid", 0xDA, 0x70, 0xD6, 302, 59, 65))
//        colorList.add(ColorName("PaleGoldenRod", 0xEE, 0xE8, 0xAA, 55, 67, 80))
        colorList.add(ColorName("PaleGreen", 0x98, 0xFB, 0x98, 120, 93, 79))
        colorList.add(ColorName("PaleTurquoise", 0xAF, 0xEE, 0xEE, 180, 65, 81))
        colorList.add(ColorName("PaleVioletRed", 0xDB, 0x70, 0x93, 340, 60, 65))
//        colorList.add(ColorName("PapayaWhip", 0xFF, 0xEF, 0xD5, 37, 100, 92))
//        colorList.add(ColorName("PeachPuff", 0xFF, 0xDA, 0xB9, 28, 100, 86))
//        colorList.add(ColorName("Peru", 0xCD, 0x85, 0x3F, 30, 59, 53))
        colorList.add(ColorName("Pink", 0xFF, 0xC0, 0xCB, 350, 100, 88))
//        colorList.add(ColorName("Plum", 0xDD, 0xA0, 0xDD, 300, 47, 75))
        colorList.add(ColorName("PowderBlue", 0xB0, 0xE0, 0xE6, 187, 52, 80))
        colorList.add(ColorName("Purple", 0x80, 0x00, 0x80, 300, 100, 25))
//        colorList.add(ColorName("RebeccaPurple", 102, 51, 153, 270, 50, 40))
        colorList.add(ColorName("Red", 0xFF, 0x00, 0x00, 0, 100, 50))
        colorList.add(ColorName("RosyBrown", 0xBC, 0x8F, 0x8F, 0, 25, 65))
        colorList.add(ColorName("RoyalBlue", 0x41, 0x69, 0xE1, 225, 73, 57))
        colorList.add(ColorName("SaddleBrown", 0x8B, 0x45, 0x13, 25, 76, 31))
        colorList.add(ColorName("Salmon", 0xFA, 0x80, 0x72, 6, 93, 71))
        colorList.add(ColorName("SandyBrown", 0xF4, 0xA4, 0x60, 28, 87, 67))
        colorList.add(ColorName("SeaGreen", 0x2E, 0x8B, 0x57, 146, 50, 36))
//        colorList.add(ColorName("SeaShell", 0xFF, 0xF5, 0xEE, 25, 100, 97))
//        colorList.add(ColorName("Sienna", 0xA0, 0x52, 0x2D, 19, 56, 40))
        colorList.add(ColorName("Silver", 0xC0, 0xC0, 0xC0, 0, 0, 75))
        colorList.add(ColorName("SkyBlue", 0x87, 0xCE, 0xEB, 197, 71, 73))
        colorList.add(ColorName("SlateBlue", 0x6A, 0x5A, 0xCD, 248, 53, 58))
        colorList.add(ColorName("SlateGray", 0x70, 0x80, 0x90, 210, 13, 50))
        colorList.add(ColorName("Snow", 0xFF, 0xFA, 0xFA, 0, 100, 99))
        colorList.add(ColorName("SpringGreen", 0x00, 0xFF, 0x7F, 150, 100, 50))
        colorList.add(ColorName("SteelBlue", 0x46, 0x82, 0xB4, 207, 44, 49))
//        colorList.add(ColorName("Tan", 0xD2, 0xB4, 0x8C, 34, 44, 69))
        colorList.add(ColorName("Teal", 0x00, 0x80, 0x80, 180, 100, 25))
//        colorList.add(ColorName("Thistle", 0xD8, 0xBF, 0xD8, 300, 24, 80))
//        colorList.add(ColorName("Tomato", 0xFF, 0x63, 0x47, 9, 100, 64))
        colorList.add(ColorName("Turquoise", 0x40, 0xE0, 0xD0, 174, 72, 56))
        colorList.add(ColorName("Violet", 0xEE, 0x82, 0xEE, 300, 76, 72))
//        colorList.add(ColorName("Wheat", 0xF5, 0xDE, 0xB3, 39, 77, 83))
        colorList.add(ColorName("White", 0xFF, 0xFF, 0xFF, 0, 0, 100))
        colorList.add(ColorName("WhiteSmoke", 0xF5, 0xF5, 0xF5, 0, 0, 96))
        colorList.add(ColorName("Yellow", 0xFF, 0xFF, 0x00, 60, 100, 50))
        colorList.add(ColorName("YellowGreen", 0x9A, 0xCD, 0x32, 80, 61, 50))
        return colorList
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
        val colorList = initColorList()
        var closestMatch: ColorName? = null
        var minMSE = Int.MAX_VALUE
        var mse: Int
        for (c in colorList) {
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
     * SubClass of ColorUtils. In order to lookup color name
     */
    inner class ColorName(var name: String, var r: Int, var g: Int, var b: Int, var h: Int, var s: Int, var l: Int) {

        fun computeMSE(pixR: Int, pixG: Int, pixB: Int): Int {
            return (((pixR - r) * (pixR - r) + (pixG - g) * (pixG - g) + ((pixB - b)
                    * (pixB - b))) / 3)
        }
        fun computeMseHue(hue: Int): Int {
            return (hue - h)
        }
    }
}