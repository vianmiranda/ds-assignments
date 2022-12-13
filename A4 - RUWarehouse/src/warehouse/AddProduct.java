package warehouse;

/*
 * Use this class to test to addProduct method.
 */
public class AddProduct {
    public static void main(String[] args) {
        StdIn.setFile("addproduct.in");
        StdOut.setFile("addproduct.out");

        // Tester code
        Warehouse w = new Warehouse();

        int len = StdIn.readInt();
        for (int ii = 0; ii < len; ii++) {
            int l = StdIn.readInt(), i = StdIn.readInt();
            String n = StdIn.readString();
            int s = StdIn.readInt(), d = StdIn.readInt();

            w.addProduct(i, n, s, l, d);
        }

        StdOut.println(w);

	// Use this file to test addProduct
    }
}
