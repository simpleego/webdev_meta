import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

// 소스를 입력하고 Ctrl+Shift+O를 눌러서 필요한 파일을 포함한다. 

public class TicTacToeServer {

	public static void main(String[] args) throws Exception {
		// 포트번호 9001번을 게임 서버의 포트로 사용한다. 
		ServerSocket ss = new ServerSocket(9001);
		System.out.println("Tic Tac Toe 서버가 시작되었습니다.");

//경기자가 2명 모이면 새로운 게임이 시작된다. 게임은 얼마든지 않이 생성될 수 있다. 
		try {
			while (true) {
				Game game = new Game(); // 새로운 게임을 생성한다. 
	
				// 경기자를 나타내는 객체를 2개 생성한다. 
				Player player1 = new Player(game, ss.accept(), 'X');
				Player player2 = new Player(game, ss.accept(), 'O');

				// 상대방이 누구인지를 알린다. 
				player1.setOther(player2);
				player2.setOther(player1);

				// 경기자가 2명 모집되면 게임을 시작한다. 
				player1.start();
				player2.start();
				System.out.println("페어가 만들어 졌습니다. ");
			}
		} finally {
			ss.close();
		}
	}
}

// 하나의 게임을 나타내는 클래스이다. 
class Game {
	char[][] boards = new char[3][3];	// 2차원 문자 배열을 이용하여서 보드를 나타낸다. 

	public void setBoard(int i, int j, char playerMark) {
		boards[i][j] = playerMark;
	}

	public char getBoard(int i, int j) {
		return boards[i][j];
	}

	// 보드의 현재 상태를 콘솔에 출력한다. 
	public void printBoard() {
		for (int k = 0; k < 3; k++) {
			System.out.println("  " + boards[k][0] + "|  " + boards[k][1] + "|  " + boards[k][2]);
			if (k != 2)
				System.out.println("---|---|---");

		}
	}
}

// 하나의 경기자를 나타내는 클래스이다. 
class Player extends Thread {
	Game game;		// 경기자가 속한 게임을 가리킨다. 
	Socket socket;	// 현재 경기에 연결된 소켓
	BufferedReader input;	// 소켓에서 얻은 입력 스트림
	PrintWriter output;	// 소켓에서 얻은 출력 스트림
	char playerMark;		// 현재 경기자가 ‘X’인지 ‘O’ 인지를 나타낸다. 
	Player other;			// 상대방 경기자 객체

	public Player(Game game, Socket socket, char playerMark) {
		this.game = game;
		this.socket = socket;
		this.playerMark = playerMark;
		try {
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream(), true);
			output.println("START " + playerMark);
			output.println("PRINT 다른 경기자를 기다립니다.");
		} catch (IOException e) {
			System.out.println("연결이 끊어졌습니다. " + e);
		}
	}

	public void setOther(Player other) {
		this.other = other;
	}

	public void run() {
		try {
			output.println("PRINT 모든 경기자가 연결되었습니다. ");

			if (playerMark == 'X') {
				output.println("PRINT 당신 차례입니다.");
			}

			// 클라이언트로부터 명령어를 받아서 처리한다. 
			while (true) {
				String command = input.readLine();
				if (command == null)
					continue;
				if (command.startsWith("MOVE")) {	// MOVE 명령어 
					int i = Integer.parseInt(command.substring(5, 6));
					int j = Integer.parseInt(command.substring(7, 8));
					game.setBoard(i, j, playerMark);
					game.printBoard();
					other.output.println("OTHER " + i + " " + j);
					output.println("PRINT 기다리세요!");
					other.output.println("PRINT 당신 차례입니다.");
				} else if (command.startsWith("QUIT")) {	// QUIT 명령어
					return;
				}
			}
		} catch (IOException e) {
			System.out.println("연결이 끊어졌습니다. " + e);
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
			}
		}
	}
}