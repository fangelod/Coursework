package croaker;

import org.junit.Test;

public class CroakerTest {
	
	// Brian's croaks
	static final String BC_1 = "Observer? I hardly know her!";
	
	// FB's croaks
	static final String FF_1 = "Established 50 years ago, "
			+ "the CS department at UNC is second-oldest in the US.";
	static final String FF_2 = "Its founder, Dr. Fred Brooks, is still here!";
	
	// Bill Nye's croaks
	static final String BN_1 = "s/o to kangaroos, their stomachs is pockets";
	static final String BN_2 = "there are 206 bones in your body ...";
	static final String BN_3 = "HOPEFULLY lol if not get your bones back cuz!";

	@Test
	public void test() {
		// Setup
		UserAccount brianCristante = new UserAccount("401_OG");
		UserAccount fredsFacts = new UserAccount("Dr_Fred");
		UserAccount billNyeTho = new UserAccount("bill_nye_tho");
		
		// Follow
		brianCristante.follow(brianCristante);  // of course I do ...
		brianCristante.follow(fredsFacts);
		brianCristante.follow(billNyeTho);
		
		// Croak away!
		billNyeTho.croak(BN_1);
		brianCristante.croak(BC_1);
		fredsFacts.croak(FF_1);
		fredsFacts.croak(FF_2);
		billNyeTho.croak(BN_2);
		billNyeTho.croak(BN_3);
		
		// Check results
		System.out.println(brianCristante);
	}

}
