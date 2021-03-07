
public class MinimumSteinerTree {
    /*
    Given an undirected graph with non-negative edge weights and a subset of vertices
    (terminals), the Steiner Tree in graph is an MST “T” of minimum weight that contains
    all given terminals (but may include additional vertices).

    Adjacency Matrix Implementation:
                   0, 7, 0, 0, 0, 6
                   7, 0, 1, 0, 0, 5
                   0, 1, 0, 1, 3, 0
                   0, 0, 1, 0, 1, 4
                   0, 0, 3, 1, 0, 10
                   6, 5, 0, 1, 10, 0
              key = [0,inf,inf,inf,inf,inf]
              mstSet = [false,false,false,false,false,false]
              parent = [-1,null,null,null,null,null]
    we initially set parent of 0 is -1
    and key for 0 is also 0
    After we always choose minimum key but key is not visited before and get their index.
    So we get minimum key values is 0 and index is 0.
    So we start iterating through row 0 in our matrix.
    And make index 0 as visited true in mstSet.
    And we set parent of 1 is 0.
    and key of 1 as 7;
    key = [0,7,inf,inf,inf,inf]
    mstSet = [true,false,false,false,false,false]
    parent = [-1,0,null,null,null,null]

    and now we reach at first row and last column
    key = [0,7,inf,inf,inf,6]
    mstSet = [true,false,false,false,false,false]
    parent = [-1,0,null,null,null,0]

    and in next iteration we get min value as 6 and index is 5.
    and we start iterating through our 5th row.
    and we update index 1 in key as 5 because 5 is smaller than previous value of index-1 (7) and
    also update the parent of 1 as 5
    key = [0,5,inf,1,inf,6]
    mstSet = [true,false,false,false,false,true]
    parent = [-1,5,null,5,null,0]

    we also update index 3 as 1 because 1 is smaller inf and also update the parent of 3 as 5.
    and update index 4 also because 4 is not visited before and 10 is smaller than inf and make 5 parent of 4
    key = [0,5,inf,1,10,6]
    mstSet = [true,false,false,false,false,true]
    parent = [-1,5,null,5,5,0]

    In next iteration we get min value as 1 and index of 1 is 3.
    and start iterating through 3rd row
    and we make key[2] == 1 because 1 < inf make parent of 2 as 1
    key = [0,5,1,1,10,6]
    mstSet = [true,false,false,true,false,true]
    parent = [-1,5,3,5,5,0]

    and we also update key[4] because 1 < key[4] because key[4] is 10.
    and also update the parent as 3.
    key = [0,5,1,1,1,6]
    mstSet = [true,false,false,true,false,true]
    parent = [-1,5,3,5,3,0]

    In this iteration we get min as 1 on index 2.
    and start iterating through row 2.
    and update key[1] as 1 because we have 5 on key[1] and also update the parent.
    key = [0,1,1,1,1,6]
    mstSet = [true,false,true,true,false,true]
    parent = [-1,2,3,5,3,0]

    In last iteration we get minimum as 1 on index 1.
    and start iterating through row 1.
    but nothing changes because we got over minimum steiner tree in our last iteration.
    key = [0,1,1,1,1,6]
    mstSet = [true,true,true,true,false,true]
    parent = [-1,2,3,5,3,0]

    edge     weight
    2 - 1    1
    3 - 2    1
    5 - 3    4
    3 - 4    1
    0 - 5    6
     */
    public static int V;
    final static int INF = 99999;
    public static Boolean[] mstSet;
    public static int[] key;
    public static int[] parent;

    public MinimumSteinerTree(int V) {
        this.V = V;
    }

    // A helper function to find the vertex with minimum key
    // value, from the set of vertices not yet included in MST
    public static int minKey(int key[], Boolean mstSet[]) {
        // Initialize min value
        int min = INF, minIndex = -1;

        for (int v = 0; v < V; v++)
            if (mstSet[v] == false && key[v] < min) {
                min = key[v];
                minIndex = v;
            }

        return minIndex;
    }

    // Our main function for steiner tree
    public static int[] primMST(int[][] graph) {
        // Array to store constructed MST
        parent = new int[V];

        // Key values used to pick minimum weight edge in cut
        key = new int[V];

        // To represent set of vertices not yet included in MST
        mstSet = new Boolean[V];

        // Initialize all keys as INFINITE
        for (int i = 0; i < V; i++) {
            key[i] = INF;
            mstSet[i] = false;
        }

        // Always include first 1st vertex in MST.
        key[0] = 0;     // Make key 0 so that this vertex is
        // picked as first vertex
        parent[0] = -1; // First node is always root of MST

        // The MST will have V vertices
        for (int count = 0; count < V - 1; count++) {
            // Pick the minimum key vertex from the set of vertices
            // not yet included in MST
            int u = minKey(key, mstSet);

            // Add the picked vertex to the MST Set
            mstSet[u] = true;

            // Update key value and parent index of the adjacent
            // vertices of the picked vertex. Consider only those
            // vertices which are not yet included in MST
            for (int v = 0; v < V; v++)

                // graph[u][v] is non zero only for adjacent vertices of m
                // mstSet[v] is false for vertices not yet included in MST
                // Update the key only if graph[u][v] is smaller than key[v]
                if (graph[u][v] != 0 && mstSet[v] == false && graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
        }

        // print the constructed MST
        printMST(parent, V, graph);
        return parent;
    }


    public static void printMST(int parent[], int n, int graph[][]) {
        System.out.println("Edge   Weight of the Minimum Steiner Tree on new graph");
        for (int i = 1; i < V; i++)
            System.out.println(parent[i] + " - " + i + "    " + graph[i][parent[i]]);
    }


    // Main Function
    public static void main(String[] args) {
        MinimumSteinerTree mst = new MinimumSteinerTree(6);
        int[][] grid = {
                {0, 7, 0, 0, 0, 6},
                {7, 0, 1, 0, 0, 5},
                {0, 1, 0, 1, 3, 0},
                {0, 0, 1, 0, 1, 4},
                {0, 0, 3, 1, 0, 10},
                {6, 5, 0, 1, 10, 0},
        };
        mst.primMST(grid);

    }
}



