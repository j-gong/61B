/*package byog.Core;
import static java.lang.Math.sqrt;
import static java.lang.Math.pow;


public class Connect {

    private static final double radius = 7;

    public static void search(Hallway hall) {

        Location anchor = hall.exit.copy();

        Location reach = findClosest(anchor);

        if (reach == null) {
            return;
        }

        connekt(anchor, reach);

        Map.OPENHALLS[Map.HALLCOUNTER] = hall;

    }

    private static Location findClosest(Location anchor) {

        double closest = Map.WIDTH;
        Location result = null;
        for (int i = 0; i < Map.HALLCOUNTER; i += 1) {
            Location check = Map.OPENHALLS[i].exit.copy();
            double dist = distance(anchor, check);
            if (dist < closest && dist < radius) {
                result = check;
                closest = dist;
            }
        }
        return result;
    }

    private static double distance(Location A, Location B) {
        return sqrt(pow(A.xPos - B.xPos, 2) + pow(A.yPos - B.yPos, 2));
    }

    private static void connekt(Location anchor, Location reach) {
        int xcoord = reach.xPos - anchor.xPos;
        int ycoord = reach.yPos - anchor.yPos;

        int xdirect;
        int ydirect;

        if (xcoord < 0) {
            xdirect = 0;
        } else {
            xdirect = 2;
        }

        if (ycoord < 0) {
            ydirect =
        }

        Hallway hallHorz = new Hallway(anchor, )
        buildHallway()

                //cant figure out how to snake it around existing rooms and hallways that might be in the way,
                //or cancel if it goes through a hallway
    }
}
*/