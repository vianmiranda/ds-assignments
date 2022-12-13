package warehouse;

public class Restock {
    public static void main(String[] args) {
        StdIn.setFile("restock.in");
        StdOut.setFile("restock.out");

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
            }
        }

        StdOut.println(w);

	// Use this file to test restock
    }
}
