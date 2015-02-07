import java.awt.Color;


public class Constants {
	public static int state_radius = 30;
	public static int inner_radius = 22;
	
	private static Color inactive_color = Color.WHITE;
	private static Color active_fail_color = Color.RED;
	private static Color active_success_color = Color.GREEN;
	
	public static Color select_color = Color.BLUE;
	
	public static Color link_color = Color.WHITE;
	
	public static Color background_color = Color.BLACK;
	
	public static int state_name_max_length = 8;
	
	public static Color getColor(boolean active, boolean is_successful){
		if(!active){
			return inactive_color;
		}
		
		return is_successful ? active_success_color : active_fail_color;
	}

}
