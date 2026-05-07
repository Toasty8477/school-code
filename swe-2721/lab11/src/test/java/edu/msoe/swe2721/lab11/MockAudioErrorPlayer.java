package edu.msoe.swe2721.lab11;

public class MockAudioErrorPlayer implements StockTickerAudioInterface {

	@Override
	public void playHappyMusic() {
        System.out.println("playing money.wav");
	}

	@Override
	public void playSadMusic() {
        System.out.println("playing GRR.WAV");
	}

	@Override
	public void playErrorMusic() {
        System.out.println("playing apollo-failureisnotanoption.wav");
	}
}
