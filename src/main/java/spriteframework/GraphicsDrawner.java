package spriteframework;

import spriteframework.sprite.Position;

import java.awt.*;

public class GraphicsDrawner {

    private Graphics graphics;

    public void setGraphics(Graphics graphics) {
        this.graphics = graphics;
    }

    public void drawRectangle(Rectangle rectangle, Color color) {
        graphics.setColor(color);
        graphics.drawRect(
                rectangle.x,
                rectangle.y,
                rectangle.width,
                rectangle.height
        );
    }

    public void fillRectangle(Rectangle rectangle, Color color) {
        graphics.setColor(color);
        graphics.fillRect(
                rectangle.x,
                rectangle.y,
                rectangle.width,
                rectangle.height
        );
    }

    public void drawString(Font font, Color color, Position position, String message) {
        graphics.setColor(color);
        graphics.setFont(font);
        graphics.drawString(
                message,
                position.getxPosition(),
                position.getyPosition()
        );
    }

    public void drawLine(Position initialPosition, Position finalPosition, Color color){
        graphics.setColor(color);
        graphics.drawLine(
                initialPosition.getxPosition(),
                initialPosition.getyPosition(),
                finalPosition.getxPosition(),
                finalPosition.getyPosition()
        );
    }
}
