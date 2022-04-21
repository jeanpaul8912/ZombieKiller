package edu.puj.pattern_design.zombie_killer.gui;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class CursorObjectPool {

    private static final Map<String, Cursor> cursors;

    public static final String IMG_FONDO_MIRA_1_P_PNG = "/img/Fondo/mira1p.png";

    public static final String IMG_FONDO_MIRA_1_PNG = "/img/Fondo/mira1.png";

    public static final String IMG_FONDO_CUCHILLO_PNG = "/img/Fondo/Cuchillo.png";

    static {
        cursors = new HashMap<>();

        ImageIcon miraM1911ImageIcon = new ImageIcon(Objects.requireNonNull(CursorObjectPool.class.getResource(IMG_FONDO_MIRA_1_P_PNG)));
        Cursor m1911Cursor = Toolkit.getDefaultToolkit().createCustomCursor(miraM1911ImageIcon.getImage(),
                new Point(16, 16), "C");
        cursors.put(IMG_FONDO_MIRA_1_P_PNG, m1911Cursor);

        ImageIcon remingtonImageIcon = new ImageIcon(Objects.requireNonNull(CursorObjectPool.class.getResource(IMG_FONDO_MIRA_1_PNG)));
        Cursor remingtonCursor = Toolkit.getDefaultToolkit().createCustomCursor(remingtonImageIcon.getImage(),
                new Point(16, 16), "C2");
        cursors.put(IMG_FONDO_MIRA_1_PNG, remingtonCursor);

        ImageIcon knifeImageIcon = new ImageIcon(Objects.requireNonNull(CursorObjectPool.class.getResource(IMG_FONDO_CUCHILLO_PNG)));
        Cursor knifeCursor = Toolkit.getDefaultToolkit().createCustomCursor(knifeImageIcon.getImage(), new Point(1, 1),
                "C2");
        cursors.put(IMG_FONDO_CUCHILLO_PNG, knifeCursor);
    }

    private CursorObjectPool() {

    }

    public static Cursor getCursor(String path) {
        return cursors.get(path);
    }

}
