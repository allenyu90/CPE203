import processing.core.PImage;

import java.util.*;

public final class WorldModel
{
    private static final String QUAKE_ID = "quake";
    private static final int QUAKE_ACTION_PERIOD = 1100;
    private static final int QUAKE_ANIMATION_PERIOD = 100;
    private static final int PROPERTY_KEY = 0;
    private static final String BGND_KEY = "background";
    private static final String MINER_KEY = "miner";
    private static final String OBSTACLE_KEY = "obstacle";
    public static final String ORE_KEY = "ore";
    private static final String SMITH_KEY = "blacksmith";
    private static final String VEIN_KEY = "vein";
    private static final int BGND_NUM_PROPERTIES = 4;
    private static final int BGND_ID = 1;
    private static final int BGND_COL = 2;
    private static final int BGND_ROW = 3;
    private static final int MINER_NUM_PROPERTIES = 7;
    private static final int OBSTACLE_NUM_PROPERTIES = 4;
    private static final int OBSTACLE_ID = 1;
    private static final int OBSTACLE_COL = 2;
    private static final int OBSTACLE_ROW = 3;
    private static final int MINER_ID = 1;
    private static final int MINER_COL = 2;
    private static final int MINER_ROW = 3;
    private static final int MINER_LIMIT = 4;
    private static final int MINER_ACTION_PERIOD = 5;
    private static final int MINER_ANIMATION_PERIOD = 6;
    private static final int ORE_NUM_PROPERTIES = 5;
    private static final int ORE_ID = 1;
    private static final int ORE_COL = 2;
    private static final int ORE_ROW = 3;
    private static final int ORE_ACTION_PERIOD = 4;
    public static final int ORE_REACH = 1;
    private static final int SMITH_NUM_PROPERTIES = 4;
    private static final int SMITH_ID = 1;
    private static final int SMITH_COL = 2;
    private static final int SMITH_ROW = 3;
    private static final int VEIN_NUM_PROPERTIES = 5;
    private static final int VEIN_ID = 1;
    private static final int VEIN_COL = 2;
    private static final int VEIN_ROW = 3;
    private static final int VEIN_ACTION_PERIOD = 4;

    public int numRows;
    public int numCols;
    private Background background[][];
    private Entity occupancy[][];
    public Set<Entity> entities;

    public WorldModel(int numRows, int numCols, Background defaultBackground) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.background = new Background[numRows][numCols];
        this.occupancy = new Entity[numRows][numCols];
        this.entities = new HashSet<>();

        for (int row = 0; row < numRows; row++) {
            Arrays.fill(this.background[row], defaultBackground);
        }
    }

    public Optional<Point> findOpenAround(Point pos) {
        for (int dy = -this.ORE_REACH; dy <= this.ORE_REACH; dy++) {
            for (int dx = -this.ORE_REACH; dx <= this.ORE_REACH; dx++) {
                Point newPt = new Point(pos.x + dx, pos.y + dy);
                if (withinBounds(newPt) && !isOccupied(newPt)) {
                    return Optional.of(newPt);
                }
            }
        }

        return Optional.empty();
    }

    public void load(
            Scanner in, ImageStore imageStore)
    {
        int lineNumber = 0;
        while (in.hasNextLine()) {
            try {
                if (!processLine(in.nextLine(), imageStore)) {
                    System.err.println(String.format("invalid entry on line %d",
                                                     lineNumber));
                }
            }
            catch (NumberFormatException e) {
                System.err.println(
                        String.format("invalid entry on line %d", lineNumber));
            }
            catch (IllegalArgumentException e) {
                System.err.println(
                        String.format("issue on line %d: %s", lineNumber,
                                      e.getMessage()));
            }
            lineNumber++;
        }
    }

    private boolean processLine(
            String line, ImageStore imageStore)
    {
        String[] properties = line.split("\\s");
        if (properties.length > 0) {
            switch (properties[PROPERTY_KEY]) {
                case BGND_KEY:
                    return parseBackground(properties, imageStore);
                case MINER_KEY:
                    return parseMiner(properties, imageStore);
                case OBSTACLE_KEY:
                    return parseObstacle(properties, imageStore);
                case ORE_KEY:
                    return parseOre(properties, imageStore);
                case SMITH_KEY:
                    return parseSmith(properties, imageStore);
                case VEIN_KEY:
                    return parseVein(properties, imageStore);
            }
        }

        return false;
    }

    private boolean parseBackground(
            String[] properties, ImageStore imageStore)
    {
        if (properties.length == BGND_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[BGND_COL]),
                                 Integer.parseInt(properties[BGND_ROW]));
            String id = properties[BGND_ID];
            setBackground(pt, new Background(id, imageStore.getImageList(id)));
        }

        return properties.length == BGND_NUM_PROPERTIES;
    }

    private boolean parseMiner(
            String[] properties, ImageStore imageStore)
    {
        if (properties.length == MINER_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[MINER_COL]),
                                 Integer.parseInt(properties[MINER_ROW]));
            Entity entity = (Entity) new MinerNotFull(properties[MINER_ID], pt, imageStore.getImageList(MINER_KEY),
                    Integer.parseInt(properties[MINER_LIMIT]), 0,Integer.parseInt(properties[MINER_ACTION_PERIOD]),
                    Integer.parseInt(properties[MINER_ANIMATION_PERIOD]));
            tryAddEntity(entity);
        }

        return properties.length == MINER_NUM_PROPERTIES;
    }

    private boolean parseObstacle(
            String[] properties, ImageStore imageStore)
    {
        if (properties.length == OBSTACLE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[OBSTACLE_COL]),
                                 Integer.parseInt(properties[OBSTACLE_ROW]));
            Entity entity = new Obstacle(properties[OBSTACLE_ID], pt, imageStore.getImageList(OBSTACLE_KEY), 0,
                    0, 0,0);
            tryAddEntity(entity);
        }

        return properties.length == OBSTACLE_NUM_PROPERTIES;
    }

    private boolean parseOre(
            String[] properties, ImageStore imageStore)
    {
        if (properties.length == ORE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[ORE_COL]),
                                 Integer.parseInt(properties[ORE_ROW]));
            Entity entity = new Ore(properties[ORE_ID], pt, imageStore.getImageList(ORE_KEY), 0, 0,
                    Integer.parseInt(properties[ORE_ACTION_PERIOD]), 0);
            tryAddEntity(entity);
        }

        return properties.length == ORE_NUM_PROPERTIES;
    }

    private boolean parseSmith(
            String[] properties, ImageStore imageStore)
    {
        if (properties.length == SMITH_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[SMITH_COL]),
                                 Integer.parseInt(properties[SMITH_ROW]));
            Entity entity = new Blacksmith(properties[SMITH_ID], pt, imageStore.getImageList(SMITH_KEY), 0,
                    0, 0, 0);
            tryAddEntity(entity);
        }

        return properties.length == SMITH_NUM_PROPERTIES;
    }

    private boolean parseVein(
            String[] properties, ImageStore imageStore)
    {
        if (properties.length == VEIN_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[VEIN_COL]),
                                 Integer.parseInt(properties[VEIN_ROW]));
            Entity entity = new Vein(properties[VEIN_ID], pt, imageStore.getImageList(VEIN_KEY), 0, 0,
                    Integer.parseInt(properties[VEIN_ACTION_PERIOD]), 0);
            tryAddEntity(entity);
        }

        return properties.length == VEIN_NUM_PROPERTIES;
    }

    private void tryAddEntity(Entity entity) {
        if (isOccupied(entity.getPosition())) {
            // arguably the wrong type of exception, but we are not
            // defining our own exceptions yet
            throw new IllegalArgumentException("position occupied");
        }

        addEntity(entity);
    }

    private boolean withinBounds(Point pos) {
        return pos.y >= 0 && pos.y < this.numRows && pos.x >= 0
                && pos.x < this.numCols;
    }

    public boolean isOccupied(Point pos) {
        return withinBounds(pos) && getOccupancyCell(pos) != null;
    }

    public Optional<Entity> findNearest(Point pos, Class kind)
    {
        List<Entity> ofType = new LinkedList<>();
        for (Entity entity : this.entities) {
            if (kind == entity.getClass()) {
                ofType.add(entity);
            }
        }

        return nearestEntity(ofType, pos);
    }

    /*
           Assumes that there is no entity currently occupying the
           intended destination cell.
        */
    public void addEntity(Entity entity) {
        if (withinBounds(entity.getPosition())) {
            setOccupancyCell(entity.getPosition(), entity);
            this.entities.add(entity);
        }
    }

    public void moveEntity(Entity entity, Point pos) {
        Point oldPos = entity.getPosition();
        if (withinBounds(pos) && !pos.equals(oldPos)) {
            setOccupancyCell(oldPos, null);
            removeEntityAt(pos);
            setOccupancyCell(pos, entity);
            entity.setPosition(pos);
        }
    }

    public void removeEntity(Entity entity) {
        removeEntityAt(entity.getPosition());
    }

    private void removeEntityAt(Point pos) {
        if (withinBounds(pos) && getOccupancyCell(pos) != null) {
            Entity entity = getOccupancyCell(pos);

            /* This moves the entity just outside of the grid for
             * debugging purposes. */
            entity.setPosition(new Point(-1, -1));
            this.entities.remove(entity);
            setOccupancyCell(pos, null);
        }
    }

    public Optional<PImage> getBackgroundImage(Point pos)
    {
        if (withinBounds(pos)) {
            return Optional.of(getBackgroundCell(pos).getCurrentImage());
        }
        else {
            return Optional.empty();
        }
    }

    private void setBackground(Point pos, Background background)
    {
        if (withinBounds(pos)) {
            setBackgroundCell(pos, background);
        }
    }

    public Optional<Entity> getOccupant(Point pos) {
        if (isOccupied(pos)) {
            return Optional.of(getOccupancyCell(pos));
        }
        else {
            return Optional.empty();
        }
    }

    private Entity getOccupancyCell(Point pos) {
        return this.occupancy[pos.y][pos.x];
    }

    private void setOccupancyCell(Point pos, Entity entity)
    {
        this.occupancy[pos.y][pos.x] = entity;
    }

    private Background getBackgroundCell(Point pos) {
        return this.background[pos.y][pos.x];
    }

    private void setBackgroundCell(Point pos, Background background)
    {
        this.background[pos.y][pos.x] = background;
    }

    private Optional<Entity> nearestEntity(
            List<Entity> entities, Point pos)
    {
        if (entities.isEmpty()) {
            return Optional.empty();
        }
        else {
            Entity nearest = entities.get(0);
            int nearestDistance = nearest.getPosition().distanceSquared(pos);

            for (Entity other : entities) {
                int otherDistance = other.getPosition().distanceSquared(pos);

                if (otherDistance < nearestDistance) {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }

            return Optional.of(nearest);
        }
    }
//
//    private Entity createBlacksmith(
//            String id, Point position, List<PImage> images)
//    {
//        return new Entity(EntityKind.BLACKSMITH, id, position, images, 0, 0, 0,
//                          0);
//    }
//
//    public Entity createMinerFull(
//            String id,
//            int resourceLimit,
//            Point position,
//            int actionPeriod,
//            int animationPeriod,
//            List<PImage> images)
//    {
//        return new Entity(EntityKind.MINER_FULL, id, position, images,
//                          resourceLimit, resourceLimit, actionPeriod,
//                          animationPeriod);
//    }
//
//    public Entity createMinerNotFull(
//            String id,
//            int resourceLimit,
//            Point position,
//            int actionPeriod,
//            int animationPeriod,
//            List<PImage> images)
//    {
//        return new Entity(EntityKind.MINER_NOT_FULL, id, position, images,
//                          resourceLimit, 0, actionPeriod, animationPeriod);
//    }
//
//    private Entity createObstacle(
//            String id, Point position, List<PImage> images)
//    {
//        return new Entity(EntityKind.OBSTACLE, id, position, images, 0, 0, 0,
//                          0);
//    }
//
//    public Entity createOre(
//            String id, Point position, int actionPeriod, List<PImage> images)
//    {
//        return new Entity(EntityKind.ORE, id, position, images, 0, 0,
//                          actionPeriod, 0);
//    }
//
//    public Entity createOreBlob(
//            String id,
//            Point position,
//            int actionPeriod,
//            int animationPeriod,
//            List<PImage> images)
//    {
//        return new Entity(EntityKind.ORE_BLOB, id, position, images, 0, 0,
//                          actionPeriod, animationPeriod);
//    }
//
//    public Entity createQuake(
//            Point position, List<PImage> images)
//    {
//        return new Entity(EntityKind.QUAKE, QUAKE_ID, position, images, 0, 0,
//                          QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD);
//    }
//
//    private Entity createVein(
//            String id, Point position, int actionPeriod, List<PImage> images)
//    {
//        return new Entity(EntityKind.VEIN, id, position, images, 0, 0,
//                          actionPeriod, 0);
//    }
}
