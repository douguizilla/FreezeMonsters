package freezemonster;


import java.util.Random;

public interface Commons {

    int GOSMA_HEIGHT = 5;

    int ALIEN_HEIGHT = 12;
    int ALIEN_WIDTH = 12;
    int ALIEN_INIT_X = 150;
    int ALIEN_INIT_Y = 5;

    int GO_DOWN = 15;
    int CHANCE = 5;
    int PLAYER_WIDTH = 30;
    int PLAYER_HEIGHT = 50;
    int INIT_PLAYER_X = 270;
    int INIT_PLAYER_Y = 280;

    String PLAYER_IMAGE_PATH = "/freezemonsterimages/woody.png";
    String RAY_IMAGE_PATH = "/freezemonsterimages/ray.png";
    String MONSTER_SHOT_IMAGE_PATH = "/freezemonsterimages/gosma.png";
    String[] MONSTERS_PATH_IMAGES = {"/freezemonsterimages/monster1.png", "/freezemonsterimages/monster2.png",
                                     "/freezemonsterimages/monster3.png", "/freezemonsterimages/monster4.png",
                                     "/freezemonsterimages/monster5.png", "/freezemonsterimages/monster6.png",
                                     "/freezemonsterimages/monster7.png", "/freezemonsterimages/monster8.png",
                                     "/freezemonsterimages/monster9.png"};

    int NUMBER_OF_MONSTERS_TO_DESTROY = MONSTERS_PATH_IMAGES.length;
    int BORDER_RIGHT = 30;
    int BORDER_LEFT = 5;
    int GROUND = 290;
    int BOARD_WIDTH = 600;
    int BOARD_HEIGHT = 600;
    int DELAY = 17;

    public static int getRandomNumberInRage(int max, int min){
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
}
