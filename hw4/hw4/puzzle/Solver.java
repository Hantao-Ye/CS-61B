package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.List;

public class Solver {
    private class SearchNode implements Comparable<SearchNode> {
        private WorldState cur;
        private SearchNode prev;
        private int moves;

        SearchNode(WorldState cur, SearchNode prev, int moves) {
            this.cur = cur;
            this.prev = prev;
            this.moves = moves;
        }

        public WorldState getCurrent() {
            return this.cur;
        }

        public SearchNode getPrevious() {
            return this.prev;
        }

        public int moves() {
            return moves;
        }

        @Override
        public int compareTo(SearchNode other) {
            return this.moves() + this.getCurrent().estimatedDistanceToGoal()
                    - other.moves() - other.getCurrent().estimatedDistanceToGoal();
        }
    }

    private MinPQ<SearchNode> minPQ = new MinPQ<>();
    private List<WorldState> answer;
    private int ansMoves;

    private void getAnswer(SearchNode goal) {
        ansMoves = goal.moves();
        answer = new ArrayList<>();
        SearchNode temp = goal;
        while (temp != null) {
            answer.add(temp.getCurrent());
            temp = temp.getPrevious();
        }
    }

    public Solver(WorldState initial) {
        minPQ.insert(new SearchNode(initial, null, 0));
        while (true) {
            SearchNode node = minPQ.delMin();
            if (node.getCurrent().isGoal()) {
                getAnswer(node);
                return;
            } else {
                for (WorldState neighbor : node.getCurrent().neighbors()) {
                    if (node.getPrevious() == null
                            || !node.getPrevious().getCurrent().equals(neighbor)) {
                        minPQ.insert(new SearchNode(neighbor, node, node.moves() + 1));
                    }
                }
            }
        }
    }

    public int moves() {
        return ansMoves;
    }

    public Iterable<WorldState> solution() {
        List<WorldState> ans = new ArrayList<>();
        for (int i = moves(); i >= 0; i--) {
            ans.add(answer.get(i));
        }

        return ans;
    }
}
