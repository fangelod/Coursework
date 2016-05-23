package a6gradescript.testers;

public class TestAssert {

	static void fail(String description) {
		throw new AssertionError("Failed: " + description);
	}
	
	static void assertNull(Object value, String description) {
		if (value != null) {
			throw new AssertionError("Expected " + description + " to be null.");
		}
	}
	
	static void assertNull(Object value) {
		assertNull(value, "value");
	}
	
	static void assertNotNull(Object value, String description) {
		if (value == null) {
			throw new AssertionError("Expected " + description + " to be not null.");
		}
	}
	
	static void assertNotNull(Object value) {
		assertNotNull(value, "value");
	}

	static void assertFalse(boolean assertion, String description) {
		if (assertion) {
			throw new AssertionError("Expected " + description + " to be false.");
		}
	}
	
	static void assertFalse(boolean assertion) {
		assertFalse(assertion, "value");
	}
	
	static void assertTrue(boolean assertion, String description) {
		if (!assertion) {
			throw new AssertionError("Expected " + description + " to be true.");
		}
	}
	
	static void assertTrue(boolean assertion) {
		assertTrue(assertion, "value");
	}
	
	static void assertEquals(double expected, double actual, double eps, String description) {
		if (Math.abs(expected-actual) > eps) {
			throw new AssertionError("Expected " + description + " to be within " + eps + " of " + expected + ". Received " + actual + " instead.");
		}
	}

	static void assertEquals(double expected, double actual, double eps) {
		assertEquals(expected, actual, eps, "value");
	}

	static void assertEquals(String expected, String actual, String description) {
		if (!expected.equals(actual)) {
			throw new AssertionError("Expected " + description + " to be "+ expected + ". Received " + actual + " instead.");
		}
	}

	static void assertEquals(String expected, String actual) {
		assertEquals(expected, actual, "value");
	}

	static void assertEquals(int expected, int actual, String description) {
		if (expected != actual) {
			throw new AssertionError("Expected " + description + " to be "+ expected + ". Received " + actual + " instead.");
		}
	}

	static void assertEquals(int expected, int actual) {
		assertEquals(expected, actual, "value");
	}

	static void assertEquals(Object expected, Object actual, String description) {
		if (expected != actual) {
			throw new AssertionError("Expected " + description + " to be "+ expected + ". Received " + actual + " instead.");
		}
	}

	static void assertEquals(Object expected, Object actual) {
		assertEquals(expected, actual, "value");
	}

	static void assertNotEquals(Object expected, Object actual, String description) {
		if (expected == actual) {
			throw new AssertionError("Expected " + description + " to not be "+ expected + ". Received " + actual + " instead.");
		}
	}

	static void assertNotEquals(Object expected, Object actual) {
		assertNotEquals(expected, actual, "value");
	}

	
}
