import com.callumcarmicheal.solar.Main;



public class BootStrap {
	
	public static void main(String[] args) {
		Main.instance = new Main();
		Main.instance.Init(args);
	}
}
