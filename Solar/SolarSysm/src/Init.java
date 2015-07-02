import com.callumcarmicheal.solar.Main;



public class Init {

	public static Main instance;
	
	public static void main(String[] args) {
		instance = new Main();
		instance.main(args);
	}
}
