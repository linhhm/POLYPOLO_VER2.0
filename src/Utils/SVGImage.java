/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.Icon;
import javax.swing.JLabel;

/**
 *
 * @author X1
 */
public class SVGImage extends JLabel{
    
    public static Icon createSVGIcon(String path, int width, int height) {
        return new FlatSVGIcon(path, width, height);
    }
}
