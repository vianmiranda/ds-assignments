package warehouse;

/*
 * Use this class to put it all together.
 */ 
public class Everything {
    public static void main(String[] args) {
        StdIn.setFile("everything.in");
        StdOut.setFile("everything.out");

        // Tester code
        Warehouse w = new Warehouse();

        int len = StdIn.readInt();
        for (int ii = 0; ii < len; ii++) {
            String aorRQuerie = StdIn.readString();
            if (aorRQuerie.equalsIgnoreCase("add")) {
                int c = StdIn.readInt(), i = StdIn.readInt();
                String n = StdIn.readString();
                int s = StdIn.readInt(), d = StdIn.readInt();
                
                w.addProduct(i, n, s, c, d);
            } else if (aorRQuerie.equalsIgnoreCase("restock")) {
                int i = StdIn.readInt(), a = StdIn.readInt();

                w.restockProduct(i, a);
            } else if (aorRQuerie.equalsIgnoreCase("purchase")) {
                int c = StdIn.readInt(), i = StdIn.readInt(), a = StdIn.readInt();

                w.purchaseProduct(i, c, a);
            } else if (aorRQuerie.equalsIgnoreCase("delete")) {
                int i = StdIn.readInt();

                w.deleteProduct(i);
            }
        }

        StdOut.println(w);

	// Use this file to test all methods
    }
}
