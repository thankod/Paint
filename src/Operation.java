public class Operation {
    public enum Type {ADD, DEL, MOV}
    private Type type;
    private int lastX, lastY, nowX, nowY;
    private Shape shape;

    public Operation(Type t, Shape shape1) {
        type = t;
        lastX = nowX = lastY = nowY = 0;
        shape = shape1;
    }

    public Operation(Type t, Shape shape1, int lastX1, int lastY1, int nowX1, int nowY1) {
        type = t;
        lastX = lastX1;
        lastY = lastY1;
        nowX = nowX1;
        nowY = nowY1;
        shape = shape1;
    }

    public void undo() {
        switch (type) {
            case ADD:
                shape.setDeleted(true);
                break;
            case DEL:
                shape.setDeleted(false);
                break;
            case MOV:
                shape.moveShape(nowX - lastX, nowY - lastY);
                break;
        }
    }

    public void redo() {
        switch (type) {
            case ADD:
                shape.setDeleted(false);
                break;
            case DEL:
                shape.setDeleted(true);
                break;
            case MOV:
                shape.moveShape(lastX - nowX, lastY - nowY);
                break;
        }
    }
}
