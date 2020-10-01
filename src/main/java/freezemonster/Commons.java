package freezemonster;


public interface Commons {

    int GOSMA_HEIGHT = 5;

    int ALIEN_HEIGHT = 12;
    int ALIEN_WIDTH = 12;
    int ALIEN_INIT_X = 150;
    int ALIEN_INIT_Y = 5;

    int GO_DOWN = 15;
    int NUMBER_OF_MONSTERS_TO_DESTROY = 9;
    int CHANCE = 5;
    int PLAYER_WIDTH = 15;
    int PLAYER_HEIGHT = 10;
    int INIT_PLAYER_X = 270;
    int INIT_PLAYER_Y = 280;

    String PLAYER_IMAGE_PATH = "/freezemonsterimages/woody.png";
    String RAY_IMAGE_PATH = "/freezemonsterimages/ray.png";
    String MONSTER_SHOT_IMAGE_PATH = "/freezemonsterimages/gosma.png";
//    String MONSTER1_IMAGE_PATH = "/freezemonsterimages/monster1.png";
//    String MONSTER2_IMAGE_PATH = "/freezemonsterimages/monster2.png";
//    String MONSTER3_IMAGE_PATH = "/freezemonsterimages/monster3.png";
//    String MONSTER4_IMAGE_PATH = "/freezemonsterimages/monster4.png";
//    String MONSTER5_IMAGE_PATH = "/freezemonsterimages/monster5.png";
//    String MONSTER6_IMAGE_PATH = "/freezemonsterimages/monster6.png";
//    String MONSTER7_IMAGE_PATH = "/freezemonsterimages/monster7.png";
//    String MONSTER8_IMAGE_PATH = "/freezemonsterimages/monster8.png";
//    String MONSTER9_IMAGE_PATH = "/freezemonsterimages/monster9.png";
    String[] MONSTERS_PATH_IMAGES = {"/freezemonsterimages/monster1.png", "/freezemonsterimages/monster2.png",
                                     "/freezemonsterimages/monster3.png", "/freezemonsterimages/monster4.png",
                                     "/freezemonsterimages/monster5.png", "/freezemonsterimages/monster6.png",
                                     "/freezemonsterimages/monster7.png", "/freezemonsterimages/monster8.png",
                                     "/freezemonsterimages/monster9.png"};;

    int BORDER_RIGHT = 30;
    int BORDER_LEFT = 5;
    int GROUND = 290;
    int BOARD_WIDTH = 358;
    int BOARD_HEIGHT = 350;
    int DELAY = 17;
}
