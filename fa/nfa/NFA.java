package fa.nfa;

import java.util.ArrayDeque;
import java.util.LinkedHashSet;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Iterator;

import fa.State;
import fa.dfa.DFA;
import fa.dfa.DFAState;

public class NFA implements NFAInterface {

    private Set<NFAState> Q, F; // States
    private Set<Character> sigma; // Alphabet
    private NFAState q0; // Start state 

    public NFA() {
        sigma = new LinkedHashSet<Character>();
        Q = new LinkedHashSet<NFAState>();
        F = new LinkedHashSet<NFAState>();
        q0 = null;
    }

    // Need to 
    @Override
    public void addStartState(String name) {
        NFAState startState = null;
        if (getState(name) == null) {
            startState = new NFAState(name);
            Q.add(startState);
        }
        q0 = startState;
    }

    private NFAState getState(String name) {
        NFAState testState = null;
        for (NFAState state: Q) {
            if (name.equals(state.getName())) {
                testState = state;
                break;
            }
        }
        return testState;
    }

    @Override
    public void addState(String name) {
        NFAState state = new NFAState(name);
        Q.add(state);       
    }

    @Override
    public void addFinalState(String name) {
        NFAState finalState = new NFAState(name, true);
        Q.add(finalState);
        F.add(finalState);
    }

    @Override
    public void addTransition(String fromState, char onSymb, String toState) {
        NFAState stateFrom = null;
        NFAState stateTo = null;

        stateFrom = getState(fromState);
        stateTo = getState(toState);

        // If transition is valid, stateFrom and stateTo must not be null.
        if (stateFrom != null && stateTo != null) {
            stateFrom.addTransition(onSymb, stateTo);
        }

        // Add character to alphabet if it doesn't already exist.
        if(!sigma.contains(onSymb)) {
            sigma.add(onSymb);
        }
    }
    

    @Override
    public Set<? extends State> getStates() {
        Set<NFAState> allStates = new LinkedHashSet<NFAState>();
        allStates.addAll(Q);
        allStates.addAll(F);
        
        return allStates;
    }

    @Override
    public Set<? extends State> getFinalStates() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public State getStartState() {
        return q0;
    }

    @Override
    public Set<Character> getABC() {
        return sigma;
    }

    @Override
    public DFA getDFA() {
        DFAState q0 = q0toDFAStartState(this.q0);
        Iterator<NFAState> it = Q.iterator();
        it.next();
        eClosure(it.next()); // for testing purposes
        return null;
    }

    @Override
    public Set<NFAState> getToState(NFAState from, char onSymb) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<NFAState> eClosure(NFAState s) {
        ArrayDeque<NFAState> statesToVisit = new ArrayDeque<>();
        statesToVisit.add(s);
        
        // Used to track the states that are visited during traversal
        Set<NFAState> statesVisited = new LinkedHashSet<NFAState>();
        Set<NFAState> eTransitionStates = new LinkedHashSet<NFAState>();
        eTransitionStates = eClosureTraversal(statesToVisit, statesVisited, eTransitionStates);
        
        return eTransitionStates;
    }

    /**
     * Uses recursive, depth-first search to find all accessible states from given state using only epsilon transitions.
     * A stack is used to keep track of which states must be visited. If the current state has adjacent states accessible
     * by epsilon transition, then those states are pushed onto the stack.
     * 
     * Then, method is called again passing in the stack with the newly pushed adjacent states and search for adjacents states
     * accessible by epsilon transition is performed again.
     * 
     * Method returns once there are no more states to visit.
     * 
     * @param statesToVisit  States to traverse through during search. Initially contains only the given state.
     * @param statesVisited States that have been visited during the search.
     * @param eTransitionStates States accessible from provided state. Initially empty.
     * 
     * @return Set of NFA states accessible from provided state only on epsilon transitions.
     * 
     * @author Steven Lineses
     */
    private Set<NFAState> eClosureTraversal(ArrayDeque<NFAState> statesToVisit, Set<NFAState> statesVisited, Set<NFAState> eTransitionStates) {
        if(statesToVisit.isEmpty()) {
            return eTransitionStates;
        } else {
            NFAState currState = statesToVisit.pop();
            statesVisited.add(currState);
    
            // Check if state on top of stack has child nodes
            LinkedHashMap<Character, HashSet<NFAState>> adjacentStates = currState.getTransitions();
    
            // Ensure that curr state has adjacent nodes with at least one e-transition.
            if(!adjacentStates.isEmpty() && adjacentStates.keySet().contains('e')) {
                for(NFAState adjacentState : adjacentStates.get('e')) {

                    // Ensure that the current state has not already been visited. Don't need to revisit
                    // a state including the current state itself.
                    if(!statesVisited.contains(adjacentState) && !statesToVisit.contains(adjacentState)) {
                        statesToVisit.add(adjacentState);
                    }
    
                    eTransitionStates.add(adjacentState);
                }
    
                eClosureTraversal(statesToVisit, statesVisited, eTransitionStates);
            }
        }

        return eTransitionStates;
    }

    /**
     * Converts an NFA start state to a DFA start state.
     * 
     * @param q0 This NFA's start state.
     * @return DFA start state converted from supplied NFA start state.
     * 
     * @author Steven Lineses
     */
    private DFAState q0toDFAStartState(NFAState q0) {
        // Find all states accessible by start state using epsilon transitions.
        Set<NFAState> eTransitions = eClosure(q0);

        // Create new DFA start state name based on results from e closure traversal.
        StringBuilder sb = new StringBuilder();
        for(NFAState s : eTransitions) {
            sb.append(s.getName());
        }
        
        return new DFAState(sb.toString());
    }
}
