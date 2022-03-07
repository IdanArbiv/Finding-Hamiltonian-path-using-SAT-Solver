package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here


    }
    // task 1
    public static boolean isSquareMatrix(boolean[][] matrix) {
        boolean isSquare = true;
        if(matrix == null){
            isSquare = false;
        }
        else{
            for(int i=0; i<matrix.length & isSquare; i++){
                if(matrix[i] == null || matrix[i].length != matrix.length){
                    isSquare = false;
                }
            }
        }
        return isSquare;
    }

    // task 2
    public static boolean isSymmetricMatrix(boolean[][] matrix) {
        boolean isSymmetric = true;
        for(int i=0; i<matrix.length & isSymmetric; i++){
            for(int j=i+1; j<matrix.length & isSymmetric ; j++){
                if(matrix[i][j] != matrix[j][i]){
                    isSymmetric = false;
                }
            }
        }
        return isSymmetric;
    }

    // task 3
    public static boolean isAntiReflexiveMatrix(boolean[][] matrix) {
        boolean isAntiReflex = true;
        for(int i=0; i<matrix.length & isAntiReflex; i++){
            if(matrix[i][i]){
                isAntiReflex = false;
            }
        }
        return isAntiReflex;
    }

    // task 4
    public static boolean isLegalInstance(boolean[][] matrix) {
        boolean isLegal = false;
        // Keep checking only if matrix is valid.
        if(isSquareMatrix(matrix) && (isSymmetricMatrix(matrix) & isAntiReflexiveMatrix(matrix))){
            isLegal = true;
        }
        return isLegal;
    }

    // task 5
    public static boolean isPermutation(int[] array) {
        boolean isPermutation = true;
        // Checking by histogram of array's values.
        int[] histogram = new int[array.length];
        for(int i=0; i<histogram.length & isPermutation; i++){
            if(array[i] <0 | array[i] >= array.length){
                isPermutation = false;
            }
            else{
                histogram[array[i]] += 1;
            }

        }
        for(int i=0; i<histogram.length & isPermutation; i++){
            if(histogram[i] != 1){
                isPermutation  = false;
            }
        }
        return isPermutation;
    }

    // task 6
    public static boolean hasLegalSteps(boolean[][] flights, int[] tour) {
        boolean legalSteps = true;
        //Checking connections from each country to the next.
        for(int i=0; i<tour.length-1; i++){
            if(!flights[tour[i]][tour[i+1]]){
                legalSteps = false;
            }
        }
        //Checking connection from the last country to the first.
        if(!flights[tour[0]][tour[tour.length-1]]){
            legalSteps = false;
        }
        return legalSteps;
    }

    // task 7
    public static boolean isSolution(boolean[][] flights, int[] tour) {
        // Checking if the array if valid
        if(tour == null || tour.length != flights.length){
            throw new IllegalArgumentException("Tour array is null or not the same length like flights array");
        }
        boolean isSolution = true;
        //Only if !isPermutation keep checking.
        if(!isPermutation(tour) || (tour[0] != 0 | !hasLegalSteps(flights, tour)) ){
            isSolution = false;
        }
        return isSolution;
    }

    // task 8
    public static boolean evaluate(int[][] cnf, boolean[] assign) {
        boolean isEvaluate = true;
        for(int i=0; i<cnf.length & isEvaluate; i++){
            isEvaluate = false;
            for(int j=0; j<cnf[i].length & !isEvaluate; j++ ){
                int assignValueIndex = cnf[i][j];
                if(assignValueIndex>0){
                    isEvaluate = assign[assignValueIndex];
                }
                else{
                    isEvaluate = !assign[-assignValueIndex];
                }
            }
        }
        return isEvaluate;
    }

    // task 9
    public static int[][] atLeastOne(int[] lits) {
        //By using OR operator, we make sure that at least one if the literals must be T ((x1 | x2 | x3))
        int[][] cnf = new int[1][lits.length];
        cnf[0] = lits;
        return cnf;
    }

    // task 10
    public static int[][] atMostOne(int[] lits) {
        // ((x1 | x2 | x3 ))
        //for all the combinations : Sn = (a1 + an) * n / 2
        int combinations = (lits.length)*(lits.length-1)/2;

        int[][] cnf = new int[combinations][2];
        // checking for every couple of literals : ((!x1 | !x2) & ( !x1 | !x3) & ( !x2 | !x3 ))
        int counter = -1;
        int i =0;
        while(i< cnf.length){
            counter++;
            for(int j=counter +1; j< lits.length; j++){
                cnf[i][0] = -lits[counter];
                cnf[i][1] = -lits[j];
                i++;
            }
        }
        return cnf;
    }

    // task 11
    public static int[][] exactlyOne(int[] lits) {
        // : ((!x1 | !x2) & ( !x1 | !x3) & ( !x2 | !x3 ) & (x1 | x2 | x3))
        int[][] tmp = atMostOne(lits);
        int[][] cnf = new int[tmp.length+1][];
        // Putting the last clause in the last place in the array. '(x1 | x2 | x3)'
        cnf[cnf.length-1] = lits;
        // Putting the all the left clauses from "AtMostOne" punc. ' (!x1 | !x2) & ( !x1 | !x3) & ( !x2 | !x3 )'
        for(int i=0; i<cnf.length-1; i++){
            cnf[i] = tmp[i];
        }
        return cnf;
    }
    /*------------------------
     *| Part B - tasks 12-20 |
     * -----------------------*/

    // task 12a
    public static int map(int i, int j, int n) {
        int k = 1 + n*i + j;
        return k;
    }

    // task 12b
    public static int[] reverseMap(int k, int n) {
        int[] pair = new int[2];
        pair[0] = (k-1)/n;
        pair[1] = (k-1)%n;
        return pair;

    }

    // task 13
    public static int[][] oneCityInEachStep(int n) {
        int combinations = (n)*(n-1)/2; // Sum all the relevant combinations.
        int[][] cnf = new int[(combinations+1)*n][]; // Create cnf with the right length.
        for(int j=0; j<n; j++){ // Creating the lits.
            int[] lits = new int[n];
            for(int i=0; i< lits.length; i++){
                lits[i] = i+1+n*j; // One on each city
            }
            int[][] tmp = exactlyOne(lits); // Using 'exactlyOne' to get all the combinations on each step.
            for(int i=0; i< tmp.length; i++){  // Add clauses to the CNF.
                cnf[i + (combinations+1)*j] = tmp[i];
            }
        }
        return cnf;
    }


    // task 14
    public static int[][] eachCityIsVisitedOnce(int n) {
        int combinations = (n)*(n-1)/2; // Sum all the relevant combinations.
        int[][] cnf = new int[(combinations+1)*n][]; // Create cnf with the right length.
        for(int j=0; j<n; j++){ // Creating the lits.
            int[] lits = new int[n];
            for(int i=0; i<lits.length; i++){
                lits[i] = ((j+1) + (n*i)); // Each city visited once
            }
            int[][] temp = exactlyOne(lits); // Using 'exactlyOne' to get all the combinations
            for(int i=0; i< temp.length; i++){ // Add clauses to the CNF.
                cnf[i + (combinations+1)*j] = temp[i];
            }
        }
        return cnf;

    }

    // task 15
    public static int[][] fixSourceCity(int n) {
        int[][] cnf = new int[1][1];
        cnf[0][0] = map(0,0,n);
        return cnf;
    }

    // task 16
    public static int[][] noIllegalSteps(boolean[][] flights) {

        int n = flights[0].length; // All the cities.
        int flightsCounter = 0; // counter the missing flights.
        for(int i =0; i<flights.length; i++) {
            for(int j = i+1; j<flights[i].length; j++) {
                if(!flights[i][j]) {
                    flightsCounter++;
                }
            }
        }

        int[][] cnf = new int[2*n*flightsCounter][]; // Build the CNF
        int index = 0; // CNF index
        for(int i =0; i<flights.length; i++) { // Searching for missing flights.
            for(int j = i+1; j<flights[i].length; j++) {
                if(!flights[i][j]) {
                    // Creating 2 clause for each step
                    for(int k=0; k<n; k++) {
                        int[] clause1 = {-map(k,j,n), -map((k+1)%n,i,n)}; // Mapping the missing flights
                        int[] clause2 = {-map(k,i,n), -map((k+1)%n,j,n)};
                        // Add clause to CNF
                        cnf[index] = clause1;
                        cnf[index+1] = clause2;
                        index=index+2;
                    }
                }
            }
        }
        return cnf;

    }

    // task 17
    public static int[][] encode(boolean[][] flights) {
        int n = flights.length;
        // Creating tmp1-4 - to mix between all the clauses.
        int[][] tmp1 = eachCityIsVisitedOnce(n);
        int[][] tmp2 = fixSourceCity(n);
        int[][] tmp3 = noIllegalSteps(flights);
        int[][] tmp4 = oneCityInEachStep(n);
        int[][] cnf = new int[tmp1.length + tmp2.length + tmp3.length+ tmp4.length] []; // The length of the new cnf includes tmp1-4.
        for(int i=0; i<cnf.length; i++){ // Fill the CNF with all the clauses from tmp1-4
            if(i<tmp1.length){
                cnf[i] = tmp1[i];
            }
            else if(i- tmp1.length< tmp2.length){
                cnf[i] = tmp2[i- tmp1.length];
            }
            else if(i- tmp1.length- tmp2.length< tmp3.length){
                cnf[i] = tmp3[i- tmp1.length- tmp2.length];
            }
            else{
                cnf[i] = tmp4[i- tmp1.length- tmp2.length- tmp3.length];
            }
        }
        return cnf;

    }

    // task 18
    public static int[] decode(boolean[] assignment, int n) {
        //Check if null
        if (assignment == null) {
            throw new IllegalArgumentException("Assignment array is null");
            // Check if the assignment array is in the right length.
        } else if (assignment.length != n * n + 1) {
            throw new IllegalArgumentException("Assignment array is not right length");
        } else {
            int[] tour = new int[n];
            for (int k = 0; k < assignment.length; k++) { // Find the city in each step.
                if (assignment[k]) {
                    int[] pair = reverseMap(k, n); // Using revereMap to get the in each step.
                    int i = pair[0];
                    int j = pair[1];
                    tour[i] = j; // Fill the 'tour' array.
                }
            }
            return tour;
        }
    }

    // task19
    public static int[] solve(boolean[][] flights) {
        // Checks if the matrix is legal instance.
        if(!isLegalInstance(flights)){
            throw new IllegalArgumentException("Not legal instance");
        }
        int[][] cnf = encode(flights); // Create the CNF.
        int nVars = flights.length * flights.length;
        // Using SAT Solver to get the answer.
        SATSolver.init(nVars);
        SATSolver.addClauses(cnf); // Adding all the clauses.
        boolean[] assignment = SATSolver.getSolution(); // Send the answer to array.
        int[] tour;
        if(assignment == null){
            throw new IllegalArgumentException("TIMEOUT");
        }
        else if(assignment.length == nVars+1){ // Answer is legal.
            tour = decode(assignment , flights.length); // Using decode the translate the answer
        }
        else{
            throw new IllegalArgumentException("UNSAT");
        }
        return tour;


    }

    // task20
    public static boolean solve2(boolean[][] flights) {
        // Checks if the matrix is legal instance
        if(!isLegalInstance(flights)){
            throw new IllegalArgumentException("Not legal instance");
        }
        boolean haveMultiSolutions = false;
        int[] solution = solve(flights);
        // Check if the we have solution
        if(solution != null){
            int[] mapedSolution = new int[solution.length]; // Creating new clause with the solution.
            int[] reversedSolution = new int[solution.length]; // Creating new clause with the reversed solution.
            reversedSolution[0] = -1;
            for(int i=0; i< solution.length; i++){
                mapedSolution[i] = -map(i,solution[i], flights.length); // fill the solution clause
                if(i>0){
                    reversedSolution[i] = -map(i,solution[solution.length-i] , flights.length); // fill the reversed solution clause
                }
            }
            //Using SAT solver - Adding the CNF + clause sol + reversed + sol .
            int[][] cnf = encode(flights);
            int nVars = flights.length * flights.length;
            SATSolver.init(nVars);
            SATSolver.addClauses(cnf);
            SATSolver.addClause(mapedSolution);
            SATSolver.addClause(reversedSolution);
            boolean[] assignment = SATSolver.getSolution(); // Saving the SAT solution.
            int[] secondSolution;
            if(assignment == null){
                throw new IllegalArgumentException("TIMEOUT");
            }
            else if(assignment.length == nVars+1){ // Check if the solution is legal
                secondSolution = decode(assignment , flights.length);
                if(isSolution(flights, secondSolution)){
                    haveMultiSolutions = true;
                }
            }
        }
        return haveMultiSolutions;

    }
}
