package fa.nfa;

import java.util.ArrayDeque;
import java.util.LinkedHashSet;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Iterator;

import fa.State;
import fa.dfa.DFA;

public class NFA implements NFAInterface {

    private Set<NFAState> Q, F; // States
    private Set<Character> sigma; // Alphabet
    private NFAState q0; // Start state 

    public NFA() {
        sigma = new LinkedHashSet<Character>();
        Q = new LinkedHashSet<NFAState>();
        F = new LinkedHashSet<NFAState>();
        q0 = null;
        eClosure(q0); // for testing purposes
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
        statesToVisit.push(s);
        Set<NFAState> statesVisited = new LinkedHashSet<NFAState>();
        eClosureTraversal(statesToVisit, statesVisited);

        return null;
    }

    private boolean eClosureTraversal(ArrayDeque<NFAState> statesToVisit, Set<NFAState> statesVisited) {
        NFAState currState = statesToVisit.pop();
        statesVisited.add(currState);

        // Check if state on top of stack has child nodes
        LinkedHashMap<Character, HashSet<NFAState>> adjacentStates = currState.getTransitions();

        if(adjacentStates.isEmpty()) {
            return false;
        } else {
            while(!adjacentStates.isEmpty()) {
                statesToVisit.push(adjacentStates.get());
            }
            eClosureTraversal(statesToVisit, statesVisited);
            return true;
        }
    }
}
