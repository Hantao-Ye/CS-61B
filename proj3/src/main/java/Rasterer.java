import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    /**
     * The root upper left/lower right longitudes and latitudes represent the bounding box of
     * the root tile, as the images in the img/ folder are scraped.
     * Longitude == x-axis; latitude == y-axis.
     */
    private static final double ROOT_ULLAT = 37.892195547244356, ROOT_ULLON = -122.2998046875,
            ROOT_LRLAT = 37.82280243352756, ROOT_LRLON = -122.2119140625;
    /**
     * Each tile is 256x256 pixels.
     */
    private static final int TILE_SIZE = 256;

    private int grid_h, grid_w;
    private String[][] render_grid;
    private double raster_ul_lon, raster_ul_lat, raster_lr_lon, raster_lr_lat;
    private int depth;
    private boolean query_success;

    public Rasterer() {
        render_grid = new String[1][1];

        raster_ul_lon = ROOT_ULLON;
        raster_ul_lat = ROOT_ULLAT;
        raster_lr_lon = ROOT_LRLON;
        raster_lr_lat = ROOT_LRLAT;

        depth = 0;
        query_success = false;
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
        double lr_lon = params.get("lrlon");
        double lr_lat = params.get("lrlat");
        double ul_lon = params.get("ullon");
        double ul_lat = params.get("ullat");

        if ((ul_lon >= ROOT_LRLON && ul_lat <= ROOT_LRLAT) || (lr_lon <= ROOT_ULLON && lr_lat >= ROOT_ULLAT)) {
            query_success = false;
            return getResult();
        }

        double w = params.get("w");
        double h = params.get("h");
        double user_lon_dpp = (lr_lon - ul_lon) / w;

        depth = 0;
        query_success = true;
        raster_ul_lon = ROOT_ULLON;
        raster_ul_lat = ROOT_ULLAT;
        raster_lr_lon = ROOT_LRLON;
        raster_lr_lat = ROOT_LRLAT;

        if (lr_lon > ROOT_LRLON) {
            lr_lon = ROOT_LRLON;
        }
        if (lr_lat < ROOT_LRLAT) {
            lr_lat = ROOT_LRLAT;
        }
        if (ul_lon < ROOT_ULLON) {
            ul_lon = ROOT_ULLON;
        }
        if (ul_lat > ROOT_ULLAT) {
            ul_lat = ROOT_ULLAT;
        }

        double lon_dpp = (ROOT_LRLON - ROOT_ULLON) / TILE_SIZE;

        int index_ul_lat = 0, index_ul_lon = 0;
        int index_lr_lat = depth + 1, index_lr_lon = depth + 1;

        while (lon_dpp > user_lon_dpp) {
            if (depth == 7) {
                break;
            }
            depth++;

            index_ul_lat *= 2;
            index_ul_lon *= 2;
            index_lr_lat *= 2;
            index_lr_lon *= 2;

            double half_raster_lon = (raster_lr_lon + raster_ul_lon) / 2;
            double half_raster_lat = (raster_lr_lat + raster_ul_lat) / 2;

            int half_index_lon = (index_lr_lon + index_ul_lon) / 2;
            int half_index_lat = (index_lr_lat + index_ul_lat) / 2;

            if (half_raster_lon <= ul_lon) {
                raster_ul_lon = half_raster_lon;
                index_ul_lon = half_index_lon;
            } else if ((raster_ul_lon + half_raster_lon) / 2 <= ul_lon && (half_index_lon - index_ul_lon) > 1) {
                raster_ul_lon = (raster_ul_lon + half_raster_lon) / 2;
                index_ul_lon = (index_ul_lon + half_index_lon) / 2;
            }

            if (half_raster_lon >= lr_lon) {
                raster_lr_lon = half_raster_lon;
                index_lr_lon = half_index_lon;
            } else if ((raster_lr_lon + half_raster_lon) / 2 >= lr_lon && (index_lr_lon - half_index_lon) > 1) {
                raster_lr_lon = (raster_lr_lon + half_raster_lon) / 2;
                index_lr_lon = (index_lr_lon + half_index_lon) / 2;
            }

            if (half_raster_lat >= ul_lat) {
                raster_ul_lat = half_raster_lat;
                index_ul_lat = half_index_lat;
            } else if ((raster_ul_lat + half_raster_lat) / 2 >= ul_lat && (half_index_lat - index_ul_lat) > 1) {
                raster_ul_lat = (raster_ul_lat + half_raster_lat) / 2;
                index_ul_lat = (index_ul_lat + half_index_lat) / 2;
            }

            if (half_raster_lat <= lr_lat) {
                raster_lr_lat = half_raster_lat;
                index_lr_lat = half_index_lat;
            } else if ((raster_lr_lat + half_raster_lat) / 2 <= lr_lat && (index_lr_lat - half_index_lat) > 1) {
                raster_lr_lat = (raster_lr_lat + half_raster_lat) / 2;
                index_lr_lat = (index_lr_lat + half_index_lat) / 2;
            }

            lon_dpp /= 2;
        }

        render_grid = new String[index_lr_lat - index_ul_lat][index_lr_lon - index_ul_lon];
        for (int m = index_ul_lat; m < index_lr_lat; m++) {
            for (int n = index_ul_lon; n < index_lr_lon; n++) {
                render_grid[m - index_ul_lat][n - index_ul_lon] = "d" + depth + "_x" + n + "_y" + m + ".png";
            }
        }

        return getResult();
    }

    private Map<String, Object> getResult() {
        Map<String, Object> result = new HashMap<>();
        result.put("render_grid", render_grid);
        result.put("raster_ul_lon", raster_ul_lon);
        result.put("raster_ul_lat", raster_ul_lat);
        result.put("raster_lr_lon", raster_lr_lon);
        result.put("raster_lr_lat", raster_lr_lat);
        result.put("depth", depth);
        result.put("query_success", query_success);

        return result;
    }

}
