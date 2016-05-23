package observer;

import croaker.Croak;

/** The "Observer" interface. */
public interface Follower {
	void follow(Publisher p);
	void update(Croak croak);
}
