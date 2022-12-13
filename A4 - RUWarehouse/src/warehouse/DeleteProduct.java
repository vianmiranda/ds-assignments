package warehouse;

/*
 * Use this class to test the deleteProduct method.
 */ 
public class DeleteProduct {
    public static void main(String[] args) {
        StdIn.setFile("deleteproduct.in");
        StdOut.setFile("deleteproduct.out");

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
            } else if (aorRQuerie.equalsIgnoreCase("delete")) {
                int i = StdIn.readInt();

                w.deleteProduct(i);
            }
        }

        StdOut.println(w);

	// Use this file to test deleteProduct
    }
}
