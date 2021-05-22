import java.awt.*;

public class Player {
    private String name;
    private Color color;
    private boolean isBot;

    public Player(String name, Color color, boolean isBot) {
        this.name = name;
        this.color = color;
        this.isBot = isBot;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isBot() {
        return isBot;
    }

    public Color getColor() {
        return color;
    }

    public void setBot(boolean bot) {
        isBot = bot;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
