package fa.nfa;

import java.util.LinkedHashSet;
import java.util.Set;

import fa.State;
import fa.dfa.DFA;

public class NFA implements NFAInterface {

    private Set<NFAState> Q, F; // States
    private Set<Character> sigma; // Alphabet
    private NFAState q0; // Start state 

    public NFA() {
        sigma = new LinkedHashSet<Character>();
        Q = new LinkedHashSet<NFAState>();
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
        
        
    }

    @Override
    public Set<? extends State> getStates() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<? extends State> getFinalStates() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public State getStartState() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<Character> getABC() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DFA getDFA() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<NFAState> getToState(NFAState from, char onSymb) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<NFAState> eClosure(NFAState s) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
