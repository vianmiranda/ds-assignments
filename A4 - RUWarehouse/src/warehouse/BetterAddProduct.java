package warehouse;

/*
 * Use this class to test the betterAddProduct method.
 */ 
public class BetterAddProduct {
    public static void main(String[] args) {
        StdIn.setFile("betteraddproduct.in");
        StdOut.setFile("betteraddproduct.out");

        // Tester code
        Warehouse w = new Warehouse();

        int len = StdIn.readInt();
        for (int ii = 0; ii < len; ii++) {
            int l = StdIn.readInt(), i = StdIn.readInt();
            String n = StdIn.readString();
            int s = StdIn.readInt(), d = StdIn.readInt();

            w.betterAddProduct(i, n, s, l, d);
        }

        StdOut.println(w);

	// Use this file to test addProduct
    }
}
