import java.util.List;
import java.util.Optional;
import java.util.Random;

import processing.core.PImage;

public interface Entity
{
//    private static final String BLOB_KEY = "blob";
//    private static final String BLOB_ID_SUFFIX = " -- blob";
//    private static final int BLOB_PERIOD_SCALE = 4;
//    private static final int BLOB_ANIMATION_MIN = 50;
//    private static final int BLOB_ANIMATION_MAX = 150;
//    private static final Random rand = new Random();
//    private static final String ORE_ID_PREFIX = "ore -- ";
//    private static final int ORE_CORRUPT_MIN = 20000;
//    private static final int ORE_CORRUPT_MAX = 30000;
//    public static final int ORE_REACH = 1;
//    private static final String QUAKE_KEY = "quake";
//    private static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;
//    public EntityKind kind;
//    private String id;
//    public Point position;
//    private List<PImage> images;
//    private int imageIndex;
//    private int resourceLimit;
//    private int resourceCount;
//    private int actionPeriod;
//    private int animationPeriod;

//    public Entity(
//            EntityKind kind,
//            String id,
//            Point position,
//            List<PImage> images,
//            int resourceLimit,
//            int resourceCount,
//            int actionPeriod,
//            int animationPeriod)
//    {
//        this.kind = kind;
//        this.id = id;
//        this.position = position;
//        this.images = images;
//        this.imageIndex = 0;
//        this.resourceLimit = resourceLimit;
//        this.resourceCount = resourceCount;
//        this.actionPeriod = actionPeriod;
//        this.animationPeriod = animationPeriod;
//    }
    Point getPosition();
    void setPosition(Point p);
    int getAnimationPeriod();
//    public int getAnimationPeriod()
//    {
//        switch (this.kind) {
//            case MINER_FULL:
//            case MINER_NOT_FULL:
//            case ORE_BLOB:
//            case QUAKE:
//                return this.animationPeriod;
//            default:
//                throw new UnsupportedOperationException(
//                        String.format("getAnimationPeriod not supported for %s",
//                                      this.kind));
//        }
//    }

    void nextImage();
//    public void nextImage()
//    {
//        this.imageIndex = (this.imageIndex + 1) % this.images.size();
//    }


//    public void executeMinerFullActivity(
//            WorldModel world,
//            ImageStore imageStore,
//            EventScheduler scheduler)
//    {
//        Optional<Entity> fullTarget =
//                world.findNearest(this.position, EntityKind.BLACKSMITH);
//
//        if (fullTarget.isPresent() && moveToFull(world,
//                                                 fullTarget.get(), scheduler))
//        {
//            transformFull(world, scheduler, imageStore);
//        }
//        else {
//            scheduler.scheduleEvent(this,
//                          Activity.createActivityAction(this, world, imageStore),
//                          this.actionPeriod);
//        }
//    }

//    public void executeMinerNotFullActivity(
//            WorldModel world,
//            ImageStore imageStore,
//            EventScheduler scheduler)
//    {
//        Optional<Entity> notFullTarget =
//                world.findNearest(this.position, EntityKind.ORE);
//
//        if (!notFullTarget.isPresent() || !moveToNotFull(world,
//                                                         notFullTarget.get(),
//                                                         scheduler)
//                || !transformNotFull(world, scheduler, imageStore))
//        {
//            scheduler.scheduleEvent(this,
//                          Activity.createActivityAction(this, world, imageStore),
//                          this.actionPeriod);
//        }
//    }

//    public void executeOreActivity(
//            WorldModel world,
//            ImageStore imageStore,
//            EventScheduler scheduler)
//        {
//            Point pos = this.position;
//
//            world.removeEntity(this);
//            scheduler.unscheduleAllEvents(this);
//
//            Entity blob = world.createOreBlob(this.id + BLOB_ID_SUFFIX, pos,
//                    this.actionPeriod / BLOB_PERIOD_SCALE,
//                    BLOB_ANIMATION_MIN + rand.nextInt(
//                            BLOB_ANIMATION_MAX
//                                    - BLOB_ANIMATION_MIN),
//                    imageStore.getImageList(BLOB_KEY));
//
//            world.addEntity(blob);
//            blob.scheduleActions(scheduler, world, imageStore);
//    }

//    public void executeOreBlobActivity(
//            WorldModel world,
//            ImageStore imageStore,
//            EventScheduler scheduler)
//    {
//        Optional<Entity> blobTarget =
//                world.findNearest(this.position, EntityKind.VEIN);
//        long nextPeriod = this.actionPeriod;
//
//        if (blobTarget.isPresent()) {
//            Point tgtPos = blobTarget.get().position;
//
//            if (moveToOreBlob(world, blobTarget.get(), scheduler)) {
//                Entity quake = world.createQuake(tgtPos,
//                                           imageStore.getImageList(QUAKE_KEY));
//
//                world.addEntity(quake);
//                nextPeriod += this.actionPeriod;
//                quake.scheduleActions(scheduler, world, imageStore);
//            }
//        }
//
//        scheduler.scheduleEvent(this,
//                      Activity.createActivityAction(this, world, imageStore),
//                      nextPeriod);
//    }

//    public void executeQuakeActivity(
//            WorldModel world,
//            ImageStore imageStore,
//            EventScheduler scheduler)
//    {
//        scheduler.unscheduleAllEvents(this);
//        world.removeEntity(this);
//    }

//    public void executeVeinActivity(
//            WorldModel world,
//            ImageStore imageStore,
//            EventScheduler scheduler)
//    {
//        Optional<Point> openPt = world.findOpenAround(this.position);
//
//        if (openPt.isPresent()) {
//            Entity ore = world.createOre(ORE_ID_PREFIX + this.id, openPt.get(),
//                                   ORE_CORRUPT_MIN + rand.nextInt(
//                                           ORE_CORRUPT_MAX - ORE_CORRUPT_MIN),
//                                   imageStore.getImageList(WorldModel.ORE_KEY));
//            world.addEntity(ore);
//            ore.scheduleActions(scheduler, world, imageStore);
//        }
//
//        scheduler.scheduleEvent(this,
//                      Activity.createActivityAction(this, world, imageStore),
//                      this.actionPeriod);
//    }


//    public void scheduleActions(
//            EventScheduler scheduler,
//            WorldModel world,
//            ImageStore imageStore)
//    {
//        switch (this.kind) {
//            case MINER_FULL:
//                scheduler.scheduleEvent(this,
//                              Activity.createActivityAction(this, world, imageStore),
//                              this.actionPeriod);
//                scheduler.scheduleEvent(this,
//                              Animation.createAnimationAction(this, 0),
//                              getAnimationPeriod());
//                break;
//
//            case MINER_NOT_FULL:
//                scheduler.scheduleEvent(this,
//                              Activity.createActivityAction(this, world, imageStore),
//                              this.actionPeriod);
//                scheduler.scheduleEvent(this,
//                              Animation.createAnimationAction(this, 0),
//                              getAnimationPeriod());
//                break;
//
//            case ORE:
//                scheduler.scheduleEvent(this,
//                              Activity.createActivityAction(this, world, imageStore),
//                              this.actionPeriod);
//                break;
//
//            case ORE_BLOB:
//                scheduler.scheduleEvent(this,
//                              Activity.createActivityAction(this, world, imageStore),
//                              this.actionPeriod);
//                scheduler.scheduleEvent(this,
//                              Animation.createAnimationAction(this, 0),
//                              getAnimationPeriod());
//                break;
//
//            case QUAKE:
//                scheduler.scheduleEvent(this,
//                              Activity.createActivityAction(this, world, imageStore),
//                              this.actionPeriod);
//                scheduler.scheduleEvent(this, Animation.createAnimationAction(this, QUAKE_ANIMATION_REPEAT_COUNT),
//                              getAnimationPeriod());
//                break;
//
//            case VEIN:
//                scheduler.scheduleEvent(this,
//                              Activity.createActivityAction(this, world, imageStore),
//                              this.actionPeriod);
//                break;
//
//            default:
//        }
//    }

//    private boolean transformNotFull(
//            WorldModel world,
//            EventScheduler scheduler,
//            ImageStore imageStore)
//    {
//        if (this.resourceCount >= this.resourceLimit) {
//            Entity miner = world.createMinerFull(this.id, this.resourceLimit,
//                                           this.position, this.actionPeriod,
//                                           this.animationPeriod,
//                                           this.images);
//
//            world.removeEntity(this);
//            scheduler.unscheduleAllEvents(this);
//
//            world.addEntity(miner);
//            miner.scheduleActions(scheduler, world, imageStore);
//
//            return true;
//        }
//
//        return false;
//    }

//    private void transformFull(
//            WorldModel world,
//            EventScheduler scheduler,
//            ImageStore imageStore)
//    {
//        Entity miner = world.createMinerNotFull(this.id, this.resourceLimit,
//                                          this.position, this.actionPeriod,
//                                          this.animationPeriod,
//                                          this.images);
//
//        world.removeEntity(this);
//        scheduler.unscheduleAllEvents(this);
//
//        world.addEntity(miner);
//        miner.scheduleActions(scheduler, world, imageStore);
//    }


//    private boolean moveToNotFull(
//            WorldModel world,
//            Entity target,
//            EventScheduler scheduler)
//    {
//        if (this.position.adjacent(target.position)) {
//            this.resourceCount += 1;
//            world.removeEntity(target);
//            scheduler.unscheduleAllEvents(target);
//
//            return true;
//        }
//        else {
//            Point nextPos = nextPositionMiner(world, target.position);
//
//            if (!this.position.equals(nextPos)) {
//                Optional<Entity> occupant = world.getOccupant(nextPos);
//                if (occupant.isPresent()) {
//                    scheduler.unscheduleAllEvents(occupant.get());
//                }
//
//                world.moveEntity(this, nextPos);
//            }
//            return false;
//        }
//    }

//    private boolean moveToFull(
//            WorldModel world,
//            Entity target,
//            EventScheduler scheduler)
//    {
//        if (this.position.adjacent(target.position)) {
//            return true;
//        }
//        else {
//            Point nextPos = nextPositionMiner(world, target.position);
//
//            if (!this.position.equals(nextPos)) {
//                Optional<Entity> occupant = world.getOccupant(nextPos);
//                if (occupant.isPresent()) {
//                    scheduler.unscheduleAllEvents(occupant.get());
//                }
//
//                world.moveEntity(this, nextPos);
//            }
//            return false;
//        }
//    }

//    private boolean moveToOreBlob(
//            WorldModel world,
//            Entity target,
//            EventScheduler scheduler)
//    {
//        if (this.position.adjacent(target.position)) {
//            world.removeEntity(target);
//            scheduler.unscheduleAllEvents(target);
//            return true;
//        }
//        else {
//            Point nextPos = nextPositionOreBlob(world, target.position);
//
//            if (!this.position.equals(nextPos)) {
//                Optional<Entity> occupant = world.getOccupant(nextPos);
//                if (occupant.isPresent()) {
//                    scheduler.unscheduleAllEvents(occupant.get());
//                }
//
//                world.moveEntity(this, nextPos);
//            }
//            return false;
//        }
//    }


//    private Point nextPositionMiner(WorldModel world, Point destPos)
//    {
//        int horiz = Integer.signum(destPos.x - this.position.x);
//        Point newPos = new Point(this.position.x + horiz, this.position.y);
//
//        if (horiz == 0 || world.isOccupied(newPos)) {
//            int vert = Integer.signum(destPos.y - this.position.y);
//            newPos = new Point(this.position.x, this.position.y + vert);
//
//            if (vert == 0 || world.isOccupied(newPos)) {
//                newPos = this.position;
//            }
//        }
//
//        return newPos;
//    }

//    private Point nextPositionOreBlob(WorldModel world, Point destPos)
//    {
//        int horiz = Integer.signum(destPos.x - this.position.x);
//        Point newPos = new Point(this.position.x + horiz, this.position.y);
//
//        Optional<Entity> occupant = world.getOccupant(newPos);
//
//        if (horiz == 0 || (occupant.isPresent() && !(occupant.get().kind
//                == EntityKind.ORE)))
//        {
//            int vert = Integer.signum(destPos.y - this.position.y);
//            newPos = new Point(this.position.x, this.position.y + vert);
//            occupant = world.getOccupant(newPos);
//
//            if (vert == 0 || (occupant.isPresent() && !(occupant.get().kind
//                    == EntityKind.ORE)))
//            {
//                newPos = this.position;
//            }
//        }
//
//        return newPos;
//    }

//    public Action createAnimationAction(int repeatCount) {
//        return new Action(ActionKind.ANIMATION, this, null, null,
//                          repeatCount);
//    }

//    private Action createActivityAction(WorldModel world, ImageStore imageStore)
//    {
//        return new Action(ActionKind.ACTIVITY, this, world, imageStore, 0);
//    }

    PImage getCurrentImage();
//    public PImage getCurrentImage()
//    {
//        return images.get(imageIndex);
//    }
}
