/**
 * Some examples of tests. You'll want to write more tests.
 */

public class ExampleTests {

    private static interface Animal {
        public String speak();
    }
    
    public static class Fish implements Animal{
		String name;
		public Fish(String name){
			this.name = name;
		}
		
		public int hashCode(){
			return 435;
		}

		public String speak() {
			return name;
		}
	}
    
    private static class Dog implements Animal {
        public String speak() {
            return "woof";
        }
    }

    private static class Cat implements Animal {
        public String speak() {
            return "meow";
        }
    }

    public static void main(String[] args) {

        // Hash table
        HashTable<String, Animal> animals = HashTableFactory.create();
        animals.put("dog", new Dog());
        animals.put("cat", new Cat());
        System.out.println("Expecting 'woof': " + animals.get("dog").speak());
        System.out.println("Expecting 'meow': " + animals.get("cat").speak());
        
        animals.remove("dog");
        animals.remove("cat");
        if (animals.get("dog") != null) {
        	System.out.println("Error. Removal of dog unsuccessfull or get "
        			+ "method doesn't return null when key is not found");
        }
        if (animals.get("cat") != null) {
        	System.out.println("Error. Removal of cat unsuccessfull or get "
        			+ "method doesn't return null when key is not found");
        }
        if (animals.get("dog") == null && animals.get("cat") == null) {
        	System.out.println("Removal of dog and cat was successfull");
        }
        
        
        HashTable<Fish, Fish> fishes = HashTableFactory.create();
        Fish fishOne = new Fish("terry");
		Fish fishTwo = new Fish("penny");
		Fish fishThree = new Fish("fran");
		fishes.put(fishOne, fishOne);
		fishes.put(fishTwo, fishTwo);
		fishes.put(fishThree, fishThree);
		fishes.remove(fishTwo);
		if (fishes.get(fishOne) != null && fishes.get(fishTwo) == null && fishes.get(fishThree) != null) {
			System.out.println("Removal at middle node successfull");
		} else {
			System.out.println("Removal at middle node UNSUCCESSFULL. Try again");
		}
		fishes.remove(fishOne);
		fishes.remove(fishThree);
		
		fishes.put(fishOne, fishOne);
		fishes.put(fishTwo, fishTwo);
		fishes.put(fishThree, fishThree);
		fishes.remove(fishThree);
		if (fishes.get(fishOne) != null && fishes.get(fishTwo) != null && fishes.get(fishThree) == null) {
			System.out.println("Removal at last node successfull");
		} else {
			System.out.println("Removal at last node UNSUCCESSFULL. Try again");
		}
		fishes.remove(fishOne);
		fishes.remove(fishTwo);

        // Graph building
        Graph graph = GraphFactory.create(3);
        Node a = NodeFactory.create("a");
        Node b = NodeFactory.create("b");
        Node c = NodeFactory.create("c");
        graph.addNodes(a, b, c);
        graph.addEdge(a, b);
        graph.addEdge(b, c);
        System.out.println("Expecting unique ids from nodes c, b, a:");
        System.out.println("ID for c: " + graph.lookupNode("c").getId());
        System.out.println("ID for b: " + graph.lookupNode("b").getId());
        System.out.println("ID for a: " + graph.lookupNode("a").getId());

        // Graph analysis
        System.out.println("Expecting an acyclic graph with sorted output: a, b, c");
        graph.analyze();
    }

}

/*
public class ExampleTests {

    private static interface Animal {
        public String speak();
    }
    private static class Dog implements Animal {
        public String speak() {
            return "woof";
        }
    }

    private static class Cat implements Animal {
        public String speak() {
            return "meow";
        }
    }

    public static void main(String[] args) {

        // Hash table
        HashTable<String, Animal> animals = HashTableFactory.create();
        animals.put("dog", new Dog());
        animals.put("cat", new Cat());
        System.out.println("Expecting 'woof': " + animals.get("dog").speak());
        System.out.println("Expecting 'meow': " + animals.get("cat").speak());

        // Graph building
        Graph graph = GraphFactory.create(3);
        Node a = NodeFactory.create("a");
        Node b = NodeFactory.create("b");
        Node c = NodeFactory.create("c");
        graph.addNodes(a, b, c);
        graph.addEdge(a, b);
        graph.addEdge(b, c);
        System.out.println("Expecting unique ids from nodes c, b, a:");
        System.out.println("ID for c: " + graph.lookupNode("c").getId());
        System.out.println("ID for b: " + graph.lookupNode("b").getId());
        System.out.println("ID for a: " + graph.lookupNode("a").getId());

        // Graph analysis
        System.out.println("Expecting an acyclic graph with sorted output: a, b, c");
        graph.analyze();
    }

}
*/
/*
public class ExampleTests {

	private static interface Animal {
		public String speak();
	}
	
	
	public static class Fish implements Animal{
		String name;
		public Fish(String name){
			this.name = name;
		}
		
		public int hashCode(){
			return 435;
		}

		public String speak() {
			return name;
		}
	}

	
	private static class Dog implements Animal {
		public String speak() {
			return "woof";
		}
	}

	private static class Cat implements Animal {
		private static int count = 0;
		private String speak = "";
		
		public Cat(){
			speak = "meow " + count++;
		}
		public String speak() {
			return speak;
		}
	}

	/*
	public static void main(String[] args) {
		int errorCount = 0;
		boolean printErrorMessage = false;

		HashTable<String, Animal> animals = HashTableFactory.create();
		HashTable<Fish, Fish> fishes = HashTableFactory.create(); 
		Fish fishOne = new Fish("terry");
		Fish fishTwo = new Fish("penny");
		Fish fishThree = new Fish("fran");
		// Hash table
		fishes.put(fishOne, fishOne);
		fishes.put(fishTwo, fishTwo);
		fishes.put(fishThree, fishThree);
		
		animals.put("dog", new Dog());
		
		for(int i = 0; i < 999; i++){
			animals.put("cat " + i, new Cat());
		}
		
		if(!"terry".equals(fishes.get(fishOne).speak())) printErrorMessage = true;
		if(!"penny".equals(fishes.get(fishTwo).speak())) printErrorMessage = true;
		if(!"fran".equals(fishes.get(fishThree).speak())) printErrorMessage = true;
		System.out.println(printErrorMessage? "problem getting object hashed to same location " + "Error:" + errorCount++: "Nicely done,"
				+ "everything looks good when getting an Object hashed to the same location.");
		printErrorMessage = false;
		
		if(!fishes.remove(fishOne).speak().equals("terry")) printErrorMessage = true;
		if(fishes.get(fishOne) != null) printErrorMessage = true;
		if(!"penny".equals(fishes.get(fishTwo).speak())) printErrorMessage = true;
		if(!"fran".equals(fishes.get(fishThree).speak())) printErrorMessage = true;
		System.out.println(printErrorMessage? "problem removing first object from location with mulitple objects " + "Error:" + errorCount++: 
				 "everything looks dandy removing first object from location with multiple objects");
		printErrorMessage = false;
		
		fishes.put(fishOne, fishOne);
		if(!fishes.remove(fishThree).speak().equals("fran")) printErrorMessage = true;
		if(!"penny".equals(fishes.get(fishTwo).speak())) printErrorMessage = true;
		if(!"terry".equals(fishes.get(fishOne).speak())) printErrorMessage = true;
		if(fishes.get(fishThree) != null) printErrorMessage = true;
		System.out.println(printErrorMessage? "problem removing middle object from location with mulitple objects" + "Error:" + errorCount++:
				"everything looks peachy removing middle object from location with multiple objects");
		printErrorMessage = false;
		
		fishes.put(fishThree, fishThree);
		if(!fishes.remove(fishThree).speak().equals("fran")) printErrorMessage = true;
		if(!"penny".equals(fishes.get(fishTwo).speak())) printErrorMessage = true;
		if(!"terry".equals(fishes.get(fishOne).speak())) printErrorMessage = true;
		if(fishes.get(fishThree) != null) printErrorMessage = true;
		System.out.println(printErrorMessage? "problem removing last object from location with mulitple objects " + "Error:" + errorCount++:
				 "everything looks great removing last object from location with multiple objects");
		printErrorMessage = false;
		
		if(!"woof".equals(animals.get("dog").speak())) printErrorMessage = true;
		for(int i = 0; i < 999; i++){
			if(!("meow " + i).equals(animals.get("cat "+ i).speak())) printErrorMessage = true;
		}
		System.out.println(printErrorMessage? "Problem with putting or getting multiple objects":
				 "everything looks beautiful when putting and getting multiple objects");
		printErrorMessage = false;

		// Graph building
		Graph graph = GraphFactory.create(1000);

		for(int i = 0; i < 1000; i++){
			graph.addNode(NodeFactory.create("node " + i));
		}
		
		int[] ids = new int[1000];
		for(int i = 0; i < 1000; i++){
			ids[i] = graph.lookupNode("node " + i).getId();
			for(int j = 0; j < i;j++){
				if(ids[i] == ids[j]) printErrorMessage = true;
			}
		}
		System.out.println(printErrorMessage? "Problem adding 1000 nodes to graph or getting unique ids":
				 "everything looks nice, added 1000 nodes and they all have unique ids \n");
		printErrorMessage = false;
		
		
		for(int i = 0; i < 500; i++){
			graph.addEdge(graph.lookupNode("node " + i), graph.lookupNode("node "+ (i + 1)));
		}
		for(int i = 0; i < 498; i++){
			graph.addEdge(graph.lookupNode("node " + (999 - i)), graph.lookupNode("node " + (999 - (i+1))));
		}
		System.out.println("Expecting an acyclic graph with sorted output: node 0 to node 500 then 999 to 501");
		graph.analyze();
		
		System.out.println();
		System.out.println("Expecting an acyclic graph with sorted output: 999 then node 0 to node 500 then 998 to 501");
		graph.addEdge(graph.lookupNode("node 999"), graph.lookupNode("node 0"));
		graph.analyze();
		
		System.out.println();
		System.out.println("Expecting a cyclic graph");
		graph.addEdge(graph.lookupNode("node 300"), graph.lookupNode("node 700"));
		graph.addEdge(graph.lookupNode("node 700"), graph.lookupNode("node 0"));
		graph.analyze();
		
		System.out.println();
		System.out.println(errorCount == 0? "Woot!! as long as there were no problems on graph.analyze() everthing looks good :)": "Testing reports "+ errorCount +" errors :(");
	}

}
*/



