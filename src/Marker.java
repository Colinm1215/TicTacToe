import java.awt.*;

class Marker {
    private final int x;
    private final int y;
    private final int bottomX;
    private final int bottomY;
    private String marker = "";

    Marker(int x, int y) {
        this.x = x;
        this.y = y;
        bottomX = x + 220;
        bottomY = y + 220;
    }

    void setMarker(String marker) {
        this.marker = marker;
    }

    void draw(Graphics2D g2) {
        if (marker.equals("X")) {
            g2.drawLine(x, y, bottomX, bottomY);
            g2.drawLine(x, bottomY, bottomX, y);
        } else if (marker.equals("O")) {
            g2.drawOval(x, y, 220, 220);
        }
    }
}
