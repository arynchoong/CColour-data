package com.arynchoong.ccolour

import android.util.Log
import com.arynchoong.ccolour.colours.*

/**
 * Java Code to get a color name from rgb/hex value/awt color
 *
 * The part of looking up a color name from the rgb values is edited from
 * https://gist.github.com/nightlark/6482130#file-gistfile1-java (that has some errors) by Ryan Mast (nightlark)
 *
 * @author Xiaoxiao Li
 */
class ColourAnalyser {
    var colourList = Colour()

    fun getColourName(r: Int, g: Int, b: Int, hsl: FloatArray): String {
        var colour: String
        if (r < 35 && g < 35 && b < 35) {
            colour = "Black"
            Log.d("ColourAnalyser", "R:$r G:$g B:$b")
/*            colourList = ColourBlack()
            colour = colourList.getColorNameFromHsl(hsl[0], hsl[1], hsl[2])*/
        } else if (r > 250 && g > 250 && b > 250){
            colour = "White"
/*            colourList = ColourWhite()
            colour = colourList.getColorNameFromHsl(hsl[0], hsl[1], hsl[2])*/
        } else if ((-5 < (r - b) && (r - b)  < 5) && (-5 < (g - b) && (g - b)  < 5) && (-5 < (r - g) && (r - g)  < 5)) {
            colour = "Grey"
/*            colourList = ColourGrey()
            colour = colourList.getColorNameFromHsl(hsl[0], hsl[1], hsl[2])*/
        }
        else {
            colourList = ColourHue()
            colour = colourList.getColorNameFromHsl(hsl[0], hsl[1], hsl[2])
        }
        return colour
    }

}