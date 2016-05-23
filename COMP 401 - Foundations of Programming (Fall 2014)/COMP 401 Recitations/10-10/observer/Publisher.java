package observer;

/** The "Observable" interface. */
public interface Publisher {
	void registerFollower(Follower f);
	void croak(String message);
	void instantMessage(Follower f, String message);
}
