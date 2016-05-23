package lec14.ex3;

public interface Game {

	public abstract void addObserver(GameObserver o);

	public abstract void deleteObserver(GameObserver o);

	public abstract void notifyObservers(String who_scored);

	public abstract void scorePoints(String team, int points_to_add);

	public abstract void printScore();

	public abstract String whoIsWinning();

}