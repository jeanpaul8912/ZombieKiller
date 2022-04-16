package edu.puj.pattern_design.zombie_killer.service.camp;

import java.util.Comparator;

public class CompareScoresByName implements Comparator<CharacterScore> {

    @Override
    public int compare(CharacterScore o1, CharacterScore o2) {
        int byName = o1.getKillerName().compareToIgnoreCase(o2.getKillerName());

        if (byName != 0) {
            return byName;
        }

        return o1.compareTo(o2);
    }
}
