public final class ImmutableRectangle {
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public ImmutableRectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public ImmutableRectangle move(int newX, int newY) {
        return new ImmutableRectangle(newX, newY, width, height);
    }

    public ImmutableRectangle stretch(int newWidth, int newHeight) {
        return new ImmutableRectangle(x, y, newWidth, newHeight);
    }

    public ImmutableRectangle rotate() {
        return new ImmutableRectangle(x, y, height, width);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    public static void main(String[] args) {
        ImmutableRectangle rectangle = new ImmutableRectangle(0, 0, 10, 5);

        System.out.println("Original: " + rectangle.getX() + ", " + rectangle.getY() +
                ", " + rectangle.getWidth() + ", " + rectangle.getHeight());

        ImmutableRectangle movedRectangle = rectangle.move(5, 5);
        System.out.println("Moved: " + movedRectangle.getX() + ", " + movedRectangle.getY() +
                ", " + movedRectangle.getWidth() + ", " + movedRectangle.getHeight());

        ImmutableRectangle stretchedRectangle = rectangle.stretch(15, 10);
        System.out.println("Stretched: " + stretchedRectangle.getX() + ", " + stretchedRectangle.getY() +
                ", " + stretchedRectangle.getWidth() + ", " + stretchedRectangle.getHeight());

        ImmutableRectangle rotatedRectangle = rectangle.rotate();
        System.out.println("Rotated: " + rotatedRectangle.getX() + ", " + rotatedRectangle.getY() +
                ", " + rotatedRectangle.getWidth() + ", " + rotatedRectangle.getHeight());
    }}
