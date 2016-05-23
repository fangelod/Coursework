package assignment1;
import assignment1.VipQueue;

public class Tester {
	public static void main(String args[]) {
		VipQueue<Integer> vq = new VipQueue<Integer>();
		for (int i = 0; i < 5; i++) {
			vq.enqueue((Integer)i); // a "regular" enqueue
			vq.vipEnqueue((Integer)(i*i)); // a vip enqueue
		}
		while (!vq.isEmpty()) System.out.printf("->%d", vq.dequeue());
	}
}
