package MonopolyServer;

public class Main {
	public static void main(String[] args) throws Exception {
		MainServer MS = new MainServer(8888);
		MS.build();
		MS.listen();
	}
}
