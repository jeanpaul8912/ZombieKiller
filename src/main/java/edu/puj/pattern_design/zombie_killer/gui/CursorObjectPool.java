package edu.puj.pattern_design.zombie_killer.gui;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public final class CursorObjectPool {

    private static final Map<String, Cursor> cursors;

    static {
        cursors = new HashMap<>();

        String pathMira1 = "/img/Fondo/mira1p.png";
        ImageIcon miraM1911ImageIcon = new ImageIcon(CursorObjectPool.class.getResource(pathMira1));
        Cursor m1911Cursor = Toolkit.getDefaultToolkit().createCustomCursor(miraM1911ImageIcon.getImage(),
                new Point(16, 16), "C");
        cursors.put(pathMira1, m1911Cursor);

        String pathMira1P = "/img/Fondo/mira1.png";
        ImageIcon remingtonImageIcon = new ImageIcon(CursorObjectPool.class.getResource(pathMira1P));
        Cursor remingtonCursor = Toolkit.getDefaultToolkit().createCustomCursor(remingtonImageIcon.getImage(),
                new Point(16, 16), "C2");
        cursors.put(pathMira1P, remingtonCursor);

        String pathCuchillo = "/img/Fondo/Cuchillo.png";
        ImageIcon knifeImageIcon = new ImageIcon(CursorObjectPool.class.getResource(pathCuchillo));
        Cursor knifeCursor = Toolkit.getDefaultToolkit().createCustomCursor(knifeImageIcon.getImage(), new Point(1, 1),
                "C2");
        cursors.put(pathCuchillo, knifeCursor);
    }

    private CursorObjectPool() {

    }

    public static Cursor getCursor(String path) {
        return cursors.get(path);
    }

}