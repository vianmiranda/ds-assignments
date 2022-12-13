package kindergarten;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;
import javax.swing.plaf.multi.MultiInternalFrameUI;

/**
 * This class represents a Classroom, with:
 * - an SNode instance variable for students in line,
 * - an SNode instance variable for musical chairs, pointing to the last student in the list,
 * - a boolean array for seating availability (eg. can a student sit in a given seat), and
 * - a Student array parallel to seatingAvailability to show students filed into seats 
 * --- (more formally, seatingAvailability[i][j] also refers to the same seat in studentsSitting[i][j])
 * 
 * @author Ethan Chou
 * @author Kal Pandit
 * @author Maksims Kurjanovics Kravcenko
 */
public class Classroom {
    private SNode studentsInLine;             // when students are in line: references the FIRST student in the LL
    private SNode musicalChairs;              // when students are in musical chairs: references the LAST student in the CLL
    private boolean[][] seatingAvailability;  // represents the classroom seats that are available to students
    private Student[][] studentsSitting;      // when students are sitting in the classroom: contains the students

    /**
     * Constructor for classrooms. Do not edit.
     * @param l passes in students in line
     * @param m passes in musical chairs
     * @param a passes in availability
     * @param s passes in students sitting
     */
    public Classroom ( SNode l, SNode m, boolean[][] a, Student[][] s ) {
		studentsInLine      = l;
        musicalChairs       = m;
		seatingAvailability = a;
        studentsSitting     = s;
	}
    /**
     * Default constructor starts an empty classroom. Do not edit.
     */
    public Classroom() {
        this(null, null, null, null);
    }

    /**
     * This method simulates students coming into the classroom and standing in line.
     * 
     * Reads students from input file and inserts these students in alphabetical 
     * order to studentsInLine singly linked list.
     * 
     * Input file has:
     * 1) one line containing an integer representing the number of students in the file, say x
     * 2) x lines containing one student per line. Each line has the following student 
     * information separated by spaces: FirstName LastName Height
     * 
     * @param filename the student information input file
     */
    public void makeClassroom ( String filename ) {

        // WRITE YOUR CODE HERE
        StdIn.setFile(filename);

        int students = StdIn.readInt();
        Student[] arr = new Student[students];

        for (int s = 0; s < students; s++) arr[s] = new Student(StdIn.readString(), StdIn.readString(), StdIn.readInt());

        for (int i = 0; i < students; i++) {
            for (int j = i; j < students; j++) {
                if (arr[i].compareNameTo(arr[j]) > 0) {
                    Student temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }

        studentsInLine = new SNode();
        SNode curr = studentsInLine;
        curr.setStudent(arr[0]);

        for (int i = 1; i < students; i++) {
            SNode temp = new SNode();
            temp.setStudent(arr[i]);
            curr.setNext(temp);
            curr = curr.getNext();
        }


    }

    /**
     * 
     * This method creates and initializes the seatingAvailability (2D array) of 
     * available seats inside the classroom. Imagine that unavailable seats are broken and cannot be used.
     * 
     * Reads seating chart input file with the format:
     * An integer representing the number of rows in the classroom, say r
     * An integer representing the number of columns in the classroom, say c
     * Number of r lines, each containing c true or false values (true denotes an available seat)
     *  
     * This method also creates the studentsSitting array with the same number of
     * rows and columns as the seatingAvailability array
     * 
     * This method does not seat students on the seats.
     * 
     * @param seatingChart the seating chart input file
     */
    public void setupSeats(String seatingChart) {

	    // WRITE YOUR CODE HERE
        StdIn.setFile(seatingChart);
        
        int row = StdIn.readInt(), col = StdIn.readInt();
        seatingAvailability = new boolean[row][col];
        studentsSitting = new Student[row][col];

        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                seatingAvailability[r][c] = StdIn.readBoolean();
            }
        }

    }

    /**
     * 
     * This method simulates students taking their seats in the classroom.
     * 
     * 1. seats any remaining students from the musicalChairs starting from the front of the list
     * 2. starting from the front of the studentsInLine singly linked list
     * 3. removes one student at a time from the list and inserts them into studentsSitting according to
     *    seatingAvailability
     * 
     * studentsInLine will then be empty
     */
    public void seatStudents () {

	    // WRITE YOUR CODE HERE
        for (int r = 0; r < seatingAvailability.length; r++) {
            for (int c = 0; c < seatingAvailability[0].length; c++) {
                if (seatingAvailability[r][c]) {
                    if (musicalChairs != null) {
                        studentsSitting[r][c] = musicalChairs.getStudent();
                        musicalChairs = null;
                    } else if (studentsInLine != null) {
                        studentsSitting[r][c] = studentsInLine.getStudent();
                        studentsInLine = studentsInLine.getNext();
                    }
                }
            }
        }        
	
    }

    /**
     * Traverses studentsSitting row-wise (starting at row 0) removing a seated
     * student and adding that student to the end of the musicalChairs list.
     * 
     * row-wise: starts at index [0][0] traverses the entire first row and then moves
     * into second row.
     */
    public void insertMusicalChairs () {
        
        // WRITE YOUR CODE HERE
        musicalChairs = new SNode();
        SNode front = musicalChairs;
        boolean b = true;

        for (int r = 0; r < seatingAvailability.length; r++) {
            for (int c = 0; c < seatingAvailability[0].length; c++) {
                if (studentsSitting[r][c] != null) {
                    if (b) {
                        musicalChairs.setStudent(studentsSitting[r][c]);
                        b = !b;
                    } else {
                        SNode temp = new SNode();
                        temp.setStudent(studentsSitting[r][c]);
                        musicalChairs.setNext(temp);
                        musicalChairs = musicalChairs.getNext();
                    }

                    studentsSitting[r][c] = null;
                }
            }
        }

        musicalChairs.setNext(front);
     }

    /**
     * 
     * This method repeatedly removes students from the musicalChairs until there is only one
     * student (the winner).
     * 
     * Choose a student to be elimnated from the musicalChairs using StdRandom.uniform(int b),
     * where b is the number of students in the musicalChairs. 0 is the first student in the 
     * list, b-1 is the last.
     * 
     * Removes eliminated student from the list and inserts students back in studentsInLine 
     * in ascending height order (shortest to tallest).
     * 
     * The last line of this method calls the seatStudents() method so that students can be seated.
     */
    public void playMusicalChairs() {

        // WRITE YOUR CODE HERE
        int numOfStud = 0;
        SNode ptr = musicalChairs.getNext();
        do {
            ptr = ptr.getNext();
            numOfStud++;
        } while (ptr != musicalChairs.getNext());

        Student[] heightOrder = new Student[numOfStud - 1];
        int k = 0;

        while (numOfStud> 1) {
            int rand = StdRandom.uniform(numOfStud--);
            SNode curr = musicalChairs.getNext(), temp = musicalChairs;

            for (int i = 0; i < rand; i++) {
                temp = curr;
                curr = curr.getNext();
            }
            
            heightOrder[k++] = curr.getStudent();

            if (curr == musicalChairs) 
                musicalChairs = temp;
            if (temp == musicalChairs)
                musicalChairs.setNext(curr.getNext());
            else temp.setNext(curr.getNext());
        }

        for (int i = 0; i < heightOrder.length; i++) {
            for (int j = i + 1; j < heightOrder.length; j++) {
                if (heightOrder[i].getHeight() >= heightOrder[j].getHeight()) {
                    Student temp = heightOrder[i];
                    heightOrder[i] = heightOrder[j];
                    heightOrder[j] = temp;
                }
            }
        }

        SNode nodeSave = studentsInLine;
        studentsInLine = new SNode();
        SNode curr = studentsInLine;
        curr.setStudent(heightOrder[0]);

        for (int i = 1; i < heightOrder.length; i++) {
            SNode temp = new SNode();
            temp.setStudent(heightOrder[i]);
            curr.setNext(temp);
            curr = curr.getNext();
        }

        curr.setNext(nodeSave);

        seatStudents();

    } 

    /**
     * Insert a student to wherever the students are at (ie. whatever activity is not empty)
     * Note: adds to the end of either linked list or the next available empty seat
     * @param firstName the first name
     * @param lastName the last name
     * @param height the height of the student
     */
    public void addLateStudent ( String firstName, String lastName, int height ) {
        
        // WRITE YOUR CODE HERE
        Student late = new Student(firstName, lastName, height);
        SNode lateNode = new SNode();
        lateNode.setStudent(late);

        if (studentsInLine != null) {
            SNode curr = studentsInLine;

            while (curr.getNext() != null) {
                curr = curr.getNext();
            }

            curr.setNext(lateNode);
        } else if (musicalChairs != null) {
            SNode curr = musicalChairs.getNext(), temp = curr;

            do {
                curr = curr.getNext();
            } while (curr != musicalChairs);

            curr.setNext(lateNode);
            musicalChairs = curr.getNext();
            musicalChairs.setNext(temp);
        } else {
            boolean breaker = false;
            for (int r = 0; r < seatingAvailability.length; r++) {
                for (int c = 0; c < seatingAvailability[0].length; c++) {
                    if (seatingAvailability[r][c] && studentsSitting[r][c] == null) {
                        studentsSitting[r][c] = late;
                        breaker = !breaker;
                    }
                    if (breaker) break;
                }
                if (breaker) break;
            }
        }

        
    }

    /**
     * A student decides to leave early
     * This method deletes an early-leaving student from wherever the students 
     * are at (ie. whatever activity is not empty)
     * 
     * Assume the student's name is unique
     * 
     * @param firstName the student's first name
     * @param lastName the student's last name
     */
    public void deleteLeavingStudent ( String firstName, String lastName ) {

        // WRITE YOUR CODE HERE
        Student absent = new Student(firstName, lastName, 0);

        if (studentsInLine != null) {
            SNode curr = studentsInLine, temp = curr;

            while (absent.compareNameTo(curr.getStudent()) != 0) {
                temp = curr;
                curr = curr.getNext();
                if (curr == null) break;
            }


            if (curr != null) {
                if (curr == studentsInLine) studentsInLine = curr.getNext();
                else temp.setNext(curr.getNext());
            }
        } else if (musicalChairs != null) {
            SNode curr = musicalChairs.getNext(), temp = musicalChairs;
            int counter = 5;

            while (curr != musicalChairs) {
                curr = curr.getNext();
                counter++;
            }
            curr = musicalChairs.getNext();

            while (absent.compareNameTo(curr.getStudent()) != 0) {
                if (counter == 0) break;
                temp = curr;
                curr = curr.getNext();
                counter--;
            }

            if (counter != 0) {
                if (curr == musicalChairs) 
                    musicalChairs = temp;
                if (temp == musicalChairs)
                    musicalChairs.setNext(curr.getNext());
                else temp.setNext(curr.getNext());
            }
        } else {
            boolean breaker = false;
            for (int r = 0; r < seatingAvailability.length; r++) {
                for (int c = 0; c < seatingAvailability[0].length; c++) {
                    if (studentsSitting[r][c] != null && absent.compareNameTo(studentsSitting[r][c]) == 0) {
                        studentsSitting[r][c] = null;
                        breaker = !breaker;
                    }
                    if (breaker) break;
                }
                if (breaker) break;
            }
        }

    }

    /**
     * Used by driver to display students in line
     * DO NOT edit.
     */
    public void printStudentsInLine () {

        //Print studentsInLine
        StdOut.println ( "Students in Line:" );
        if ( studentsInLine == null ) { StdOut.println("EMPTY"); }

        for ( SNode ptr = studentsInLine; ptr != null; ptr = ptr.getNext() ) {
            StdOut.print ( ptr.getStudent().print() );
            if ( ptr.getNext() != null ) { StdOut.print ( " -> " ); }
        }
        StdOut.println();
        StdOut.println();
    }

    /**
     * Prints the seated students; can use this method to debug.
     * DO NOT edit.
     */
    public void printSeatedStudents () {

        StdOut.println("Sitting Students:");

        if ( studentsSitting != null ) {
        
            for ( int i = 0; i < studentsSitting.length; i++ ) {
                for ( int j = 0; j < studentsSitting[i].length; j++ ) {

                    String stringToPrint = "";
                    if ( studentsSitting[i][j] == null ) {

                        if (seatingAvailability[i][j] == false) {stringToPrint = "X";}
                        else { stringToPrint = "EMPTY"; }

                    } else { stringToPrint = studentsSitting[i][j].print();}

                    StdOut.print ( stringToPrint );
                    
                    for ( int o = 0; o < (10 - stringToPrint.length()); o++ ) {
                        StdOut.print (" ");
                    }
                }
                StdOut.println();
            }
        } else {
            StdOut.println("EMPTY");
        }
        StdOut.println();
    }

    /**
     * Prints the musical chairs; can use this method to debug.
     * DO NOT edit.
     */
    public void printMusicalChairs () {
        StdOut.println ( "Students in Musical Chairs:" );

        if ( musicalChairs == null ) {
            StdOut.println("EMPTY");
            StdOut.println();
            return;
        }
        SNode ptr;
        for ( ptr = musicalChairs.getNext(); ptr != musicalChairs; ptr = ptr.getNext() ) {
            StdOut.print(ptr.getStudent().print() + " -> ");
        }
        if ( ptr == musicalChairs) {
            StdOut.print(musicalChairs.getStudent().print() + " - POINTS TO FRONT");
        }
        StdOut.println();
    }

    /**
     * Prints the state of the classroom; can use this method to debug.
     * DO NOT edit.
     */
    public void printClassroom() {
        printStudentsInLine();
        printSeatedStudents();
        printMusicalChairs();
    }

    /**
     * Used to get and set objects.
     * DO NOT edit.
     */

    public SNode getStudentsInLine() { return studentsInLine; }
    public void setStudentsInLine(SNode l) { studentsInLine = l; }

    public SNode getMusicalChairs() { return musicalChairs; }
    public void setMusicalChairs(SNode m) { musicalChairs = m; }

    public boolean[][] getSeatingAvailability() { return seatingAvailability; }
    public void setSeatingAvailability(boolean[][] a) { seatingAvailability = a; }

    public Student[][] getStudentsSitting() { return studentsSitting; }
    public void setStudentsSitting(Student[][] s) { studentsSitting = s; }

}
