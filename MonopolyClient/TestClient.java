package MonopolyClient;

import java.util.Scanner;

public class TestClient {
	public static void main(String... args) {
		MainClient client = new MainClient("127.0.0.1",8888);
		Scanner in = new Scanner(System.in);
		System.out.println("Enter any to ready");
		in.nextLine();
		client.send("Ready 1");
	}
}
