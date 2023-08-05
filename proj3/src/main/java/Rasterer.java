import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {

    private static final int LONGTOFEET = 288200;
    private String[][] renderGrid;
    private int depth;
    private double lrlon;
    private double lrlat;
    private double ullon;
    private double ullat;
    private double width;
    private double height;
    private double rasterullon;
    private double rasterullat;
    private double rasterlrlon;
    private double rasterlrlat;

    private int getDepth() {
        double lonDPP = (lrlon - ullon) * LONGTOFEET / width;
        double d0LonDPP = LONGTOFEET * (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / MapServer.TILE_SIZE;
        double n = Math.log(d0LonDPP / lonDPP) / Math.log(2);
        int depth = (int) Math.ceil(n);
        if (depth < 0) {
            depth = 0;
        } else if (depth > 7) {
            depth = 7;
        }
        return depth;
    }

    private boolean checkValid() {
        return !(lrlon < ullon) && !(ullat < lrlat) && !(lrlon <= MapServer.ROOT_ULLON) && !(ullon >= MapServer.ROOT_LRLON) && !(lrlat >= MapServer.ROOT_ULLAT) && !(ullat <= MapServer.ROOT_LRLAT);
    }


    private void getImage() {
        // the width of per picture
        double lonDPP = Math.abs(MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / Math.pow(2.0, depth);
        double latDPP = Math.abs(MapServer.ROOT_LRLAT - MapServer.ROOT_ULLAT) / Math.pow(2.0, depth);

        // find four key imags
        int xMin = (int) (Math.floor((ullon - MapServer.ROOT_ULLON) / lonDPP));
        int xMax = (int) (Math.floor((lrlon - MapServer.ROOT_ULLON) / lonDPP));
        int yMin = (int) (Math.floor((MapServer.ROOT_ULLAT - ullat) / latDPP));
        int yMax = (int) (Math.floor((MapServer.ROOT_ULLAT - lrlat) / latDPP));

        // find boundings
        rasterullon = MapServer.ROOT_ULLON + xMin * lonDPP;
        rasterlrlon = MapServer.ROOT_ULLON + (xMax + 1) * lonDPP;
        rasterullat = MapServer.ROOT_ULLAT - yMin * latDPP;
        rasterlrlat = MapServer.ROOT_ULLAT - (yMax + 1) * latDPP;

        // corner case
        if (ullon < MapServer.ROOT_ULLON) {
            xMin = 0;
            rasterullon = MapServer.ROOT_ULLON;
        }
        if (lrlon > MapServer.ROOT_LRLON) {
            xMax = (int) (Math.pow(2, depth) - 1);
            rasterlrlon = MapServer.ROOT_LRLON;
        }
        if (ullat > MapServer.ROOT_ULLAT) {
            yMin = 0;
            rasterullat = MapServer.ROOT_ULLAT;
        }
        if (lrlat < MapServer.ROOT_LRLAT) {
            yMax = (int) (Math.pow(2, depth) - 1);
            rasterlrlat = MapServer.ROOT_LRLAT;
        }

        renderGrid = new String[yMax - yMin + 1][xMax - xMin + 1];
        for (int i = yMin; i <= yMax; i++) {
            for (int j = xMin; j <= xMax; j++) {
                renderGrid[i - yMin][j - xMin] = "d" + depth + "_x" + j + "_y" + i + ".png";
            }
        }
    }


    public Rasterer() {
        // YOUR CODE HERE
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     * <p>
     * The grid of images must obey the following properties, where image in the
     * grid is referred to as a "tile".
     * <ul>
     *     <li>The tiles collected must cover the most longitudinal distance per pixel
     *     (LonDPP) possible, while still covering less than or equal to the amount of
     *     longitudinal distance per pixel in the query box for the user viewport size. </li>
     *     <li>Contains all tiles that intersect the query bounding box that fulfill the
     *     above condition.</li>
     *     <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     * </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     * forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        // get parameters
        ullon = params.get("ullon");
        ullat = params.get("ullat");
        lrlon = params.get("lrlon");
        lrlat = params.get("lrlat");
        width = params.get("w");
        height = params.get("h");

        depth = getDepth();
        boolean querySuccess = checkValid();

        Map<String, Object> results = new HashMap<>();
        getImage();
        results.put("render_grid", renderGrid);
        results.put("raster_ul_lon", rasterullon);
        results.put("raster_ul_lat", rasterullat);
        results.put("raster_lr_lon", rasterlrlon);
        results.put("raster_lr_lat", rasterlrlat);
        results.put("depth", depth);
        results.put("query_success", querySuccess);
        return results;
    }

}
