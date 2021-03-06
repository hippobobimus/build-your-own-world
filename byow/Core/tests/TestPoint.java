package byow.Core.tests;

import byow.Core.*;
import byow.TileEngine.Tileset;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the Point class.
 * @author Rob Masters
 */
public class TestPoint {
    @Test
    public void testConstructors() {
        Point p1 = new Point(3, 9);
        Point p2 = new Point(2, 87);
        Point p3 = new Point(98, 37, 18747);

        assertEquals(3, p1.getX());
        assertEquals(9, p1.getY());
        assertEquals(0, p1.getPriority());

        assertEquals(2, p2.getX());
        assertEquals(87, p2.getY());
        assertEquals(0, p2.getPriority());

        assertEquals(98, p3.getX());
        assertEquals(37, p3.getY());
        assertEquals(18747, p3.getPriority());
    }

    @Test
    public void testSettersGetters() {
        Point p = new Point(98, 37, 18747);

        assertEquals(98, p.getX());
        assertEquals(37, p.getY());

        assertEquals(18747, p.getPriority());
        p.setPriority(74773);
        assertEquals(74773, p.getPriority());
    }

    public static void main(String[] args) {
        jh61b.junit.TestRunner.runTests("all", TestPoint.class);
    }
}
