package byow.Core;

import static byow.Core.Constants.*;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

/**
 * An extension of the 2D grid of points incorporating tiles.
 * @author Rob Masters
 */
public class TileGrid extends Grid {
    private boolean animate;
    private TERenderer ter;
    private TETile[][] tiles;

    /* CONSTRUCTORS ----------------------------------------------------------*/

    /**
     * Full constructor for a 2D tile grid. Initially the tile grid is filled
     * with grass tiles.
     * @param width width
     * @param height height
     * @param animate animation/no animation
     */
    public TileGrid(int width, int height, String animate) {
        super(width, height);

        this.animate = animate.equals("animate") ? true : false;

        this.ter = new TERenderer();
        ter.initialize(getWidth(), getHeight());

        tiles = new TETile[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = Tileset.GRASS;
                Point p = get(x, y);
            }
        }
    }

    /* TILES -----------------------------------------------------------------*/

    /**
     * Returns a 2D array of tiles corresponding to points in the 2D grid.
     * @return 2D tile array
     */
    public TETile[][] getTiles() {
        return tiles;
    }

    /**
     * Sets the tile at the given point to the given tile. Throws an exception
     * if the point is not contained in the world.
     * @param p point
     * @param tile tile
     */
    public void setTile(Point p, TETile tile) {
        validatePoint(p);
        int x = p.getX();
        int y = p.getY();
        tiles[x][y] = tile;
    }

    /**
     * Sets the tile at the point corresponding to the given coords to the given
     * tile. Throws an exception if the coords are outside the bounds of the
     * world.
     * @param x x-coord
     * @param y y-coord
     * @param tile tile
     */
    public void setTile(int x, int y, TETile tile) {
        Point p = get(x, y);  // get throws an exception if outside world
        setTile(p, tile);
    }

    /* ANIMATION -------------------------------------------------------------*/

    /**
     * Renders the current tile state to screen.
     */
    public void render() {
        TETile[][] t = getTiles();
        ter.renderFrame(t);
    }

    /**
     * If animation has been specified, renders the current tile state to screen
     * and pauses for 10ms.
     */
    public void animate() {
        if (!animate) {
            return;
        }

        render();

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /* PRIVATE HELPER METHODS ------------------------------------------------*/

    /**
     * Checks that the given point is contained within the grid and, if not,
     * throws an exception.
     * @param p point
     */
    private void validatePoint(Point p) {
        if (!contains(p)) {
            throw new IllegalArgumentException();
        }
    }
}
