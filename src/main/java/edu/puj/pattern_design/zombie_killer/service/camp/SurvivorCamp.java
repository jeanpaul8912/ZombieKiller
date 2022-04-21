package edu.puj.pattern_design.zombie_killer.service.camp;

import edu.puj.pattern_design.zombie_killer.service.camp.impl.Character;
import edu.puj.pattern_design.zombie_killer.service.camp.impl.CharacterScore;
import edu.puj.pattern_design.zombie_killer.service.camp.impl.SurvivorCampImpl;
import edu.puj.pattern_design.zombie_killer.service.exceptions.DatosErroneosException;
import edu.puj.pattern_design.zombie_killer.service.exceptions.InvalidNameException;
import edu.puj.pattern_design.zombie_killer.service.exceptions.SurvivorCampException;
import edu.puj.pattern_design.zombie_killer.service.zombies.Boss;
import edu.puj.pattern_design.zombie_killer.service.zombies.Enemy;
import edu.puj.pattern_design.zombie_killer.service.zombies.Zombie;
import edu.puj.pattern_design.zombie_killer.service.zombies.ZombieZigZag;

import java.io.IOException;
import java.util.List;

public interface SurvivorCamp {

    char getGameStatus();

    void setGameStatus(char newGameStatus);

    CharacterScore getRootScores();

    void updateScores(CharacterScore actual);

    Character getCharacter();

    ZombieZigZag getZombieFarNode();

    SurvivorCampImpl loadGame() throws SurvivorCampException, DatosErroneosException, CloneNotSupportedException;

    int getCurrentRound();

    void verifyName(String playerName) throws InvalidNameException;

    void addToBestScores(String playerName) throws IOException;

    Enemy getBoss();

    void saveGame() throws IOException;

    Zombie generateZombie(int level);

    char pauseGame();

    void changeWeapon();

    void updateCurrentRound(int level);

    Boss generateBoss();

    List<CharacterScore> sortScoresByScores();

    List<CharacterScore> sortScoresByDeadZombies();

    List<CharacterScore> sortScoresByHeadShots();

    CharacterScore searchByScoreOfPlayerName(String playerName);

    ZombieZigZag getZombieNearNode();

    int getZombiesGeneratedCount();

    void loadScores() throws IOException, ClassNotFoundException;
}
