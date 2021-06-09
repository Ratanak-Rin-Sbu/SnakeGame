import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class App {
    private SnakeWorld world;
    private StageCanvas canvas;
	private Timer timer;
	private JButton btnPlay;
	private JLabel lblScore;
	private Deque<Integer> keyCodeQueue;

	private enum GameState { Playing, Paused, GameOver }
    private GameState gameState;
    
	public void start() {
		JFrame frame = new JFrame();
		frame.setTitle("Snake Bite");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//rootPane
		Container rootPane = frame.getContentPane();
		rootPane.setLayout(new BoxLayout(rootPane, BoxLayout.Y_AXIS));

		//Canvas
		gameState = GameState.Playing;
		world = new SnakeWorld();
		canvas = new StageCanvas(world);
		canvas.setFocusable(true);
		keyCodeQueue = new DynamicArrayDeque<>();
		canvas.addKeyListener(new KeyHandler());
		rootPane.add(canvas);

		//Score
		lblScore = new JLabel("Score: " + 0);

		//Play/Pause Button
		btnPlay = new JButton();
		btnPlay.setText("Pause");
		btnPlay.addActionListener(e->onPlayPause());
		
		//Score, Button box
		Box hButtons = Box.createHorizontalBox();
		hButtons.add(lblScore);
		hButtons.add(Box.createHorizontalStrut(20));
		hButtons.add(btnPlay);
		rootPane.add(Box.createVerticalStrut(10));
		rootPane.add(hButtons);
		rootPane.add(Box.createVerticalStrut(10));

		frame.pack();
		frame.setVisible(true);

		//timer
		timer = new Timer(250/*ms*/, e -> onTimerTick());
		timer.start();

		//Background audio
		BackgroundAudio.play();
	}
	
	private void onTimerTick() {
		handleKey();
	    boolean gameOver = world.step();
	    if(gameOver) {
            timer.stop();
			BackgroundAudio.pause();
	        gameState = GameState.GameOver;
            btnPlay.setText("New Game");
			btnPlay.requestFocus();
	    }
	    else {
	        lblScore.setText("Score: " + world.getScore());
	        canvas.repaint();
			canvas.requestFocus();
	        int delay = 50 + 25 * (SnakeWorld.MAX_APPLES - world.getAppleCount());
	        timer.setDelay(delay);
	    }
	}

	private void onPlayPause() {
	    switch(gameState) {
        case GameOver:
            world.init();
	    case Paused:
            btnPlay.setText("Pause");
			timer.restart();
			BackgroundAudio.resume();
            gameState = GameState.Playing;
            break;
	    case Playing:
            btnPlay.setText("Play");
            timer.stop();
			BackgroundAudio.pause();
            gameState = GameState.Paused;
            break;
	    }
	}

	private class KeyHandler implements KeyListener {
		public void keyPressed(KeyEvent e) {
			keyCodeQueue.addLast(e.getKeyCode());
		}
		public void keyReleased(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
	}

	private void handleKey() {
		if(keyCodeQueue.isEmpty())
			return;
		int keyCode = keyCodeQueue.removeFirst();

	    if(gameState != GameState.Playing)
	        return;
	    if(keyCode == KeyEvent.VK_LEFT)   
	        world.turnRight(); //left -> right: reversed y axis
	    else if(keyCode == KeyEvent.VK_RIGHT)
            world.turnLeft();  //right -> left: reversed y axis
	}
	
	public static void main(String[] args) {
		new App().start();
	}
}
