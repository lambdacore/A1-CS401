import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        String filePath = "/Users/lairos/IdeaProjects/A1-CS401Test/src/test.txt";
        ArrayList<ArrayList<String>> table = readFile(filePath);
        System.out.println(table);
        QuickUnion uf = buildConnections(table);

        int top = table.size() * table.get(0).size();
        int bottom = table.get(0).size() * table.size() + 1;
        boolean isDrainable = uf.isConnected(top,bottom);

        if(isDrainable){
            System.out.println("Allow water to drain");
        } else {
            System.out.println("Don't allow water to drain");
        }
    }

    public static WeightedQuickUnion buildConnections(ArrayList<ArrayList<String>> table) {
        int numRows = table.size();
        int numCols = table.get(0).size();

        WeightedQuickUnion uf = new WeightedQuickUnion(numRows * numCols + 2);

        // Define a top index and bottom index that are connected to the entire top and bottom row.
        int topIndex = numRows * numCols;
        int bottomIndex = numRows * numCols + 1;

        // Step 1: Connect all of the cells in the first row to top.
        for (int colIndex = 0; colIndex < numCols; colIndex++) {
            if (table.get(0).get(colIndex).equals("1")) {
                uf.union(topIndex, colIndex);
            }
        }

        // Step 2: Visit each cell starting from the first row,
        // and connect it to a cell directly above (row - 1) or
        // below (col - 1) if the cell has a "1" in it.
        for (int rowIndex = 0; rowIndex < numRows; rowIndex++) {
            for (int colIndex = 0; colIndex < numCols; colIndex++) {
                // If this cell isn't a 1, we don't connect it to anything.
                if (!table.get(rowIndex).get(colIndex).equals("1")) {
                    continue;
                }

                int absIndex = getAbsIndex(rowIndex, numCols, colIndex);

                if (rowIndex > 0) {
                    int aboveIndex = getAbsIndex(rowIndex - 1, numCols, colIndex);
                    if (table.get(rowIndex - 1).get(colIndex).equals("1")) {
                        uf.union(absIndex, aboveIndex);
                    }
                }
                if (colIndex > 0) {
                    int leftIndex = getAbsIndex(rowIndex, numCols, colIndex - 1);
                    if (table.get(rowIndex).get(colIndex - 1).equals("1")) {
                        uf.union(absIndex, leftIndex);
                    }
                }
            }
        }

        // Step 3. Connect all of the cells in the last row to bottom.
        for (int colIndex = 0; colIndex < numCols; colIndex++) {
            if (table.get(numRows - 1).get(colIndex).equals("1")) {
                int absIndex = getAbsIndex(numRows - 1, numCols, colIndex);
                uf.union(bottomIndex, absIndex);
            }
        }
        return uf;
    }

    public static int getAbsIndex(int rowIndex, int numCols, int colIndex) {
        return (rowIndex * numCols) + colIndex;

    }

    public static ArrayList<ArrayList<String>> readFile(String filepath) throws FileNotFoundException, IOException {
        ArrayList<ArrayList<String>> rows = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filepath));
        String line;

        while(true) {
            line = reader.readLine();
            if (line == null) break;

            ArrayList<String> cols = new ArrayList<>();
            cols.addAll(Arrays.asList(line.split(" ")));
            rows.add(cols);
        }

        return rows;
    }
}
