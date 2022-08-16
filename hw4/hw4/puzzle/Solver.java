package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Comparator;

public class Solver {
    private MinPQ<SearchNode> PQ;
    private ArrayList<WorldState> solutions;
    private int moves;

    private static class SearchNode {
        int initialToThis;
        SearchNode parent;
        WorldState wordStates;


        public SearchNode(int m, SearchNode p, WorldState w) {
            initialToThis = m;
            parent = p;
            wordStates = w;
        }
    }

    private static class SearchNodeComparator implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode s1, SearchNode s2) {
            return s1.initialToThis + s1.wordStates.estimatedDistanceToGoal()
                    - s2.initialToThis - s2.wordStates.estimatedDistanceToGoal();
        }
    }


    /**
     * Constructor which solves the puzzle, computing
     * everything necessary for moves() and solution() to
     * not have to solve the problem again. Solves the
     * puzzle using the A* algorithm. Assumes a solution exists.
     */
    public Solver(WorldState initial) {
        SearchNode initialNode = new SearchNode(0, null, initial);
        PQ = new MinPQ<>(new SearchNodeComparator());
        solutions = new ArrayList<>();
        PQ.insert(initialNode);
        Astar();
    }

    private void Astar() {
        while (!PQ.isEmpty()) {
            SearchNode p = PQ.delMin();
            solutions.add(p.wordStates);
            if (p.wordStates.isGoal()) {
                moves = p.initialToThis;
                return;
            } else {
                relax(p);
            }
        }
    }

    private void relax(SearchNode p) {
        for (WorldState q : p.wordStates.neighbors()) {
            if (p.parent != null && q.equals(p.parent.wordStates)) {
                continue;
            }
            SearchNode ns = new SearchNode(p.initialToThis + 1, p, q);
            PQ.insert(ns);
        }
    }


    /**
     * Returns the minimum number of moves to solve the puzzle starting
     * at the initial WorldState.
     */
    public int moves() {
        return moves;
    }

    /**
     * Returns a sequence of WorldStates from the initial WorldState
     * to the solution.
     */
    public Iterable<WorldState> solution() {
        return solutions;
    }
}
