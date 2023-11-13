import java.awt.*;

public class CommonConstants {
//    size config
    public static final Dimension FRAME_SIZE = new Dimension(540,760);
    public static final Dimension BOARD_SIZE = new Dimension((int)(FRAME_SIZE.width*0.96),(int)(FRAME_SIZE.height*0.6));
    public static final Dimension buttonSize =new Dimension(100,100);
    public static final Dimension result_dialog_size = new Dimension((int)(FRAME_SIZE.width/3), (int)(FRAME_SIZE.height/6));

//    color config
    public static final Color background_color = Color.decode("#011627");
    public static final Color x_color = Color.decode("#E71D36");
    public static final Color o_color = Color.decode("#FF9F1C");
    public static final Color bar_color = Color.decode("#2Ec486");
    public static final Color board_color = Color.decode("#FDFFFC");

//    value config
    public static final String x_label= "X";
    public static final String o_label= "O";
    public static final String  score_label = "X : 0 | O : 0";
}
