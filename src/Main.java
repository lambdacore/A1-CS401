import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        String filePath = "/Users/lairos/IdeaProjects/SoilProblem/src/test.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            String[] tokens = line.split(" ");
            int n = tokens.length;

            WeightedQuickUnion uf = new WeightedQuickUnion(n * n + 1); // n*n cells plus a virtual top node
            int top = n * n;

            // connect the top node to the first row of cells
            for (int j = 0; j < n; j++) {
                if (tokens[j].equals("1")) {
                    uf.union(top, j);
                }
            }

            // read the remaining rows and connect adjacent cells
            for (int lines = 0; lines < n; lines++) {
                line = reader.readLine();
                if (line == null) {
                    break;
                }
                System.out.println(line);
                tokens = line.split(" ");

                for (int j = 0; j < n; j++) {
                    int index = lines * n + j;

                    if (tokens[j].equals("1")) {
                        if (j > 0 && tokens[j - 1].equals("1")) {
                            uf.union(index, index - 1);
                        }

                        if (lines > 0 && tokens[j].equals("1")) {
                            uf.union(index, index - n);
                        }

                        if (j < n - 1 && tokens[j + 1].equals("1")) {
                            uf.union(index, index + 1);
                        }
                    }
                }
            }

            // check if the soil allows water to drain or not
            boolean allowDrainage = false;
            for (int j = 0; j < n; j++) {
                if (tokens[j].equals("1") && uf.isConnected(j, top)) {
                    allowDrainage = true;
                    break;
                }
            }

            // print the result
            if (allowDrainage) {
                System.out.println("Allow water to drain");
            } else {
                System.out.println("Don't allow water to drain");
            }
        } catch (IOException e) {
            System.out.println("Error reading the input file: " + e.getMessage());
        }
    }
}
