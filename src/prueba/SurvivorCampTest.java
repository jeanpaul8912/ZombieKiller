package prueba;

import interfaz.InterfazZombieKiller;
import junit.framework.TestCase;
import mundo.camp.Puntaje;
import mundo.camp.SurvivorCamp;
import mundo.defenseStrategies.StrategyContext;
import mundo.defenseStrategies.ThrowGrenadeStrategy;
import mundo.zombies.Zombie;

import java.io.IOException;
import java.util.List;

public class SurvivorCampTest extends TestCase {

    private SurvivorCamp sC;
    private InterfazZombieKiller interfaz = new InterfazZombieKiller();
    private StrategyContext attackStrategy = new StrategyContext(new ThrowGrenadeStrategy(interfaz));

    /**
     * Crea un campo con 5 zombies vivos y de la ronda 5
     */
    private void setupEscenario2() {
        sC = new SurvivorCamp();
        interfaz.setCampo(sC);
        sC.generarZombie(5);
        sC.generarZombie(5);
        sC.generarZombie(5);
        sC.generarZombie(5);
        sC.generarZombie(5);
    }

    /**
     * Crea un campo con 2 zombies vivos y uno muriendo
     */
    private void setupEscenario3() {
        sC = new SurvivorCamp();
        interfaz.setCampo(sC);
        sC.generarZombie(4);
        sC.generarZombie(4);
        sC.generarZombie(4);
        sC.getZombNodoCercano().getAtras().setEstadoActual(Zombie.MURIENDO);
    }

    /**
     * crea diferentes partidas guardando 3 puntajes diferentes
     */
    private void setupEscenario4() {
        sC = new SurvivorCamp();
        interfaz.setCampo(sC);
        // 5 bajas, de ellas 3 son de tiro a la cabeza y un total de 310 puntos
        sC.getPersonaje().aumentarScore(70);
        sC.getPersonaje().aumentarTirosALaCabeza();
        sC.getPersonaje().aumentarScore(60);
        sC.getPersonaje().aumentarScore(50);
        sC.getPersonaje().aumentarScore(60);
        sC.getPersonaje().aumentarTirosALaCabeza();
        sC.getPersonaje().aumentarScore(70);
        sC.getPersonaje().aumentarTirosALaCabeza();
        try {
            sC.aniadirMejoresPuntajes("Camilo");
        } catch (IOException e) {
            fail("No guarda bien el puntaje obtenido");
        }
        sC = new SurvivorCamp();
        try {
            sC.cargarPuntajes();
        } catch (ClassNotFoundException | IOException e1) {
            fail("No se estan cargando los puntajes");
        }
        // 5 bajas, de ellas 2 son de tiro a la cabeza y un total de 300 puntos
        sC.getPersonaje().aumentarScore(60);
        sC.getPersonaje().aumentarTirosALaCabeza();
        sC.getPersonaje().aumentarScore(60);
        sC.getPersonaje().aumentarScore(50);
        sC.getPersonaje().aumentarScore(60);
        sC.getPersonaje().aumentarScore(70);
        sC.getPersonaje().aumentarTirosALaCabeza();
        try {
            sC.aniadirMejoresPuntajes("Lusho");
        } catch (IOException e) {
            fail("No guarda bien el puntaje obtenido");
        }
        sC = new SurvivorCamp();
        try {
            sC.cargarPuntajes();
        } catch (ClassNotFoundException | IOException e1) {
            fail("No se estan cargando los puntajes");
        }
        // 6 bajas, de ellas 1 son de tiro a la cabeza y un total de 305 puntos
        sC.getPersonaje().aumentarScore(60);
        sC.getPersonaje().aumentarScore(60);
        sC.getPersonaje().aumentarScore(55);
        sC.getPersonaje().aumentarScore(60);
        sC.getPersonaje().aumentarScore(30);
        sC.getPersonaje().aumentarScore(40);
        sC.getPersonaje().aumentarTirosALaCabeza();
        try {
            sC.aniadirMejoresPuntajes("Lusho");
        } catch (IOException e) {
            fail("No guarda bien el puntaje obtenido");
        }
    }

    /**
     * comprueba que todos los zombies en el mapa se incendien y brinden al jugador
     * el correspondiente puntaje
     */
    public void testseLanzoGranada1() {
        setupEscenario2();
        attackStrategy.executeAttack();
        interfaz.setCampo(sC);
        Zombie actual = sC.getZombNodoCercano().getAtras();
        int cantidadDeZombies = 0;
        while (!actual.getEstadoActual().equals(Zombie.NODO)) {
            assertEquals("Se esperaba que todos los zombies estuvieran en llamas", Zombie.MURIENDO_INCENDIADO,
                    actual.getEstadoActual());
            cantidadDeZombies++;
            actual = actual.getAtras();
        }
        assertEquals(cantidadDeZombies * 50, sC.getPersonaje().getScore());
    }

    /**
     * Comprueba que al lanzar una granada, no vaya a aumentar mas puntos de los que
     * ya estaban muertos y que los que ya se estan muriendo no cambien su estado
     */
    public void testseLanzoGranada2() {
        setupEscenario3();
        attackStrategy.executeAttack();
        interfaz.setCampo(sC);
        Zombie actual = sC.getZombNodoCercano().getAtras();
        int cantidadDeZombiesafectadosPorGranada = 0;
        assertEquals("Se esperaba que el primer zombie no estuviera en llamas porque ya estaba muerto por balas",
                Zombie.MURIENDO, actual.getEstadoActual());
        actual = actual.getAtras();
        while (!actual.getEstadoActual().equals(Zombie.NODO)) {
            assertEquals("Se esperaba que todos los zombies estuvieran en llamas", Zombie.MURIENDO_INCENDIADO,
                    actual.getEstadoActual());
            cantidadDeZombiesafectadosPorGranada++;
            actual = actual.getAtras();
        }
        assertEquals(cantidadDeZombiesafectadosPorGranada * 50, sC.getPersonaje().getScore());
    }

    /**
     * comprueba que se ordenen los puntajes segun la cantidad de tiros a la cabeza,
     * de mayor a menor
     */
    public void testOrdenarPorTirosALaCabeza() {
        setupEscenario4();
        List<Puntaje> ordenado = sC.ordenarPuntajePorTirosALaCabeza();
        for (int i = 1; i < ordenado.size(); i++) {
            int HSanterior = ordenado.get(i - 1).getTirosALaCabeza();
            assertTrue(ordenado.get(i).getTirosALaCabeza() <= HSanterior);
        }
    }

    /**
     * Comprueba que ordene los puntajes segun la cantidad de bajas, de mayor a
     * menor
     */
    public void testOrdenarPorBajas() {
        setupEscenario4();
        List<Puntaje> ordenado = sC.ordenarPuntajePorBajas();

        for (int i = 1; i < ordenado.size(); i++) {
            int HSanterior = ordenado.get(i - 1).getBajas();
            assertTrue(ordenado.get(i).getBajas() <= HSanterior);
        }
    }

    /**
     * Comprueba que ordene los puntajes segun el score, de mayor a menor
     */
    public void testOrdenarPorScore() {
        setupEscenario4();
        List<Puntaje> ordenado = sC.ordenarPuntajePorScore();
        for (int i = 1; i < ordenado.size(); i++) {
            int HSanterior = ordenado.get(i - 1).getPuntaje();
            assertTrue(ordenado.get(i).getPuntaje() <= HSanterior);
        }
    }

    /**
     * comprueba que busque correctamente el mejor puntaje del nombre ingresado por
     * parametro
     */
    public void testBuscarPuntajeDe1() {
        setupEscenario4();
        Puntaje p = sC.buscarPuntajeDe("Lusho");
        assertEquals("Lusho", p.getNombreKiller());
        assertEquals(305, p.getPuntaje());
    }

    public void testBuscarPuntajeDe2() {
        setupEscenario4();
        Puntaje p = sC.buscarPuntajeDe("x");
        assertNull(p);
    }
}
