package program.boot;

import com.callumcarmicheal.solar.Main;

// USE THIS WHILE EXPORTING
public class BootStrap {
	public static void main(String[] args) {
		Main.instance = new Main();
		Main.instance.Init(args);
	}
}
