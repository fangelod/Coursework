package lec14.ex1;

public interface Game {

	public abstract void addObserver(GameObserver o);

	public abstract void deleteObserver(GameObserver o);

	public abstract void notifyObservers();

	public abstract void scorePoints(String team, int points_to_add);

	public abstract void printScore();

	public abstract String whoIsWinning();

}