package byow.Core;

import static byow.Core.Constants.*;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * An extension of the 2D tile grid of tiles and points, incorporating a
 * distinct pathway within the grid and connected/unconnected regions of
 * pathway.
 * @author Rob Masters
 */
public class NavigableTileGrid extends TileGrid {
    private UnionFind<Point> regions;
    public PointGraph pathway;

    /* CONSTRUCTORS ----------------------------------------------------------*/

    /**
     * Full constructor for a 2D navigable tile grid. Initially there is no
     * navigable area.
     * @param width width
     * @param height height
     */
    public NavigableTileGrid(int width, int height) {
        super(width, height);

        pathway = new PointGraph();

        regions = new UnionFind<>();

        for (Point p : listAllPoints()) {
            regions.add(p);
        }
    }

    /**
     * Constructor that defaults to the width and height set in the constants
     * class.
     */
    public NavigableTileGrid() {
        this(MAP_WIDTH, MAP_HEIGHT);
    }

    /* PUBLIC METHODS --------------------------------------------------------*/

    /**
     * Configures the tile at the given point and any in its local neighbourhood
     * as appropriate. If the given point is on the pathway it becomes a floor
     * tile and any neighbouring tiles not on the pathway become wall tiles.
     * If it is not on the pathway then it and all its neighbours are set to
     * either wall or grass depending on whether the tile in question has any
     * neighbours on the pathway.
     * @param p point
     */
    public void setTileNeighbourhood(Point p) {
        if (pathway.contains(p)) {
            setTile(p, Tileset.FLOOR);

            for (Point nbr : listNeighbours(p)) {
                if (!pathway.contains(nbr)) {
                    setTile(nbr, Tileset.WALL);
                }
            }
        } else {
            closeTile(p);

            for (Point nbr : listNeighbours(p)) {
                if (!pathway.contains(nbr)) {
                    closeTile(nbr);
                }
            }
        }
    }

    /**
     * Adds the given point to the pathway and configures tiles accordingly.
     * Throws an exception if the given point is not contained in the grid.
     * @param p point
     */
    public void openPath(Point p) {
        validateGridPoint(p);

        addToPath(p);

        setTileNeighbourhood(p);
    }

    /**
     * Adds the given point to the pathway and configures tiles accordingly.
     * Throws an exception if the given coords are not within the grid bounds.
     * @param x x-coord
     * @param y y-coord
     */
    public void openPath(int x, int y) {
        openPath(get(x, y));
    }

    /**
     * Removes the given point from the pathway and configures tiles
     * accordingly. Throws an exception if the given point is not contained in
     * the grid. Does nothing if the point is not already on the pathway.
     * @param p point
     */
    public void closePath(Point p) {
        validateGridPoint(p);

        pathway.remove(p);

        setTileNeighbourhood(p);
    }

    /**
     * Removes the given point from the pathway and configures tiles
     * accordingly. Throws an exception if the given coords are not within the
     * grid bounds. Does nothing if the point is not already on the pathway.
     * @param x x-coord
     * @param y y-coord
     */
    public void closePath(int x, int y) {
        closePath(get(x, y));
    }

    /**
     * Returns a list of pathway neighbours of the given point. Throws an
     * exception if the given point is not contained within the grid or if it
     * isn't on the pathway.
     * @return exits
     */
    public List<Point> listExits(Point p) {
        validatePathPoint(p);

        return pathway.listNeighbours(p);
    }

    /* QUERIES ---------------------------------------------------------------*/

    /**
     * Determines whether the two given points are in the same connected region.
     * Points p and q must be contained in the grid otherwise an exception will
     * be thrown.
     * @param p point
     * @param q point
     * @return connected/not connected
     */
    public boolean isConnected(Point p, Point q) {
        validateGridPoint(p);
        validateGridPoint(q);

        return regions.isConnected(p, q);
    }

    /**
     * Determines whether the given point is on the pathway. Throws an exception
     * if the point is not contained in the grid.
     * @param p point
     * @return path?
     */
    public boolean isPath(Point p) {
        validateGridPoint(p);

        return pathway.contains(p);
    }

    /**
     * Determines whether the point at the given coords is on the pathway.
     * Throws an exception if the coords fall outside the grid boundary.
     * @param p point
     * @return path?
     */
    public boolean isPath(int x, int y) {
        return isPath(get(x, y));
    }

    /* PRIVATE HELPER METHODS ------------------------------------------------*/

    /**
     * Adds the given point to the pathway, adding edges to any neighbours in
     * the cardinal directions (UP, DOWN, LEFT, RIGHT) that are also within the
     * pathway and then connects regions containing these points.
     * @param p point
     */
    private void addToPath(Point p) {
        pathway.add(p);

        for (Point nbr : listCardinalNeighbours(p)) {
            if (pathway.contains(nbr)) {
                pathway.addEdge(p, nbr);

                regions.connect(p, nbr);
            }
        }
    }

    /**
     * If the point has any grid neighbours on the pathway then it becomes a
     * wall tile. Otherwise it becomes a grass tile.
     * @param p point
     */
    private void closeTile(Point p) {
        for (Point nbr : listNeighbours(p)) {
            if (pathway.contains(nbr)) {
                setTile(p, Tileset.WALL);
                return;
            }
        }

        setTile(p, Tileset.GRASS);
    }

    /**
     * Checks that the given point is contained within the grid and, if not,
     * throws an exception.
     * @param p point
     */
    private void validateGridPoint(Point p) {
        if (!contains(p)) {
            throw new IllegalArgumentException(
                    "The point is not contained within the grid. Given: " + p);
        }
    }

    /**
     * Checks that the given point is contained within the grid and is on the
     * pathway. If not, throws an exception.
     * @param p point
     */
    private void validatePathPoint(Point p) {
        validateGridPoint(p);

        if (!isPath(p)) {
            throw new IllegalArgumentException(
                    "The point is not contained on the pathway. Given: " + p);
        }
    }
}
