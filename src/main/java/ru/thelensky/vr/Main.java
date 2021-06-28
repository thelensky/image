package ru.thelensky.vr;

import java.awt.*;

public class Main extends Component {
    public static void main(String[] args) {
// TODO start program
        if (args[0] == null) {
            System.out.println("point path to img file");
        }else{
            VRImage.makeImg(args[0]);
        }
    }
}
