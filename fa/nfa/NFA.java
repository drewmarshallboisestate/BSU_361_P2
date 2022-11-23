package fa.nfa;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

import fa.State;
import fa.dfa.DFA;
import fa.dfa.DFAState;

/**
 * Tuesday, November 22, 2022 
 * Implementation of an NFA machine using 
 * the NFAInterface. Creates an NFA and the 
 * five-tuple associated with it. Can also 
 * return a DFA converted from an NFA.
 * @author Drew Marshall
 * @author Steven Lineses
 */


 /**
  * Class that provides the 5-tuple of a compete NFA.
  * Provides common methods applicable to an NFA. Can
  * convert a given NFA into a DFA by combining states
  * and removing epsilon transitions. 
  */
public class NFA implements NFAInterface {

    private Set<NFAState> Q, F; // States, Final states 
    private Set<Character> sigma; // Alphabet
    private NFAState q0; // Start state 

    /**
     * Constructor representation of a complete NFA
     * Creates a transition map later on
     */
    public NFA() {
        sigma = new LinkedHashSet<Character>();
        Q = new LinkedHashSet<NFAState>();
        F = new LinkedHashSet<NFAState>();
        q0 = null;
    }

    
    /** 
     * Converts a string into the starting NFA state
     * @param name String representation of state
     */
    @Override
    public void addStartState(String name) {
        //Create empty state to begin
        NFAState startState = null;
        boolean exists = false;  //Flag to see if the state already exists
        for (NFAState state: Q) {
            if (name.equals(state.getName())) {
                exists = true; //we dont need to add a start state, just set it
                startState = state;  //set the startstate to be the state already in our Q
            }
        }

        //If the state doesn't exist we create it as a new state and add it to our states (Q)
        if (!exists) {
            startState = new NFAState(name);
            Q.add(startState);
        }

        q0 = startState;  //Finally set the startState as either the new state or an already created state
    }

    
    /** 
     * Gets a particular state 
     * @param name of state to retrieve
     * @return NFAState that matches the name
     */
    private NFAState getState(String name) {
        NFAState testState = null;
        for (NFAState state: Q) {
            if (name.equals(state.getName())) {
                testState = state;
                return testState;
            }
        }
        return testState;
    }

    
    /** 
     * Creates and adds a state to the NFA
     * @param name of state to be added 
     */
    @Override
    public void addState(String name) {
        NFAState state = new NFAState(name);
        Q.add(state);       
    }

    
    /** 
     * Creates and adds a final state to the NFA
     * @param name of state to be added as a final state
     */
    @Override
    public void addFinalState(String name) {
        NFAState finalState = null;
        finalState = new NFAState(name, true);
        Q.add(finalState);
        F.add(finalState);
        
    }

    
    /** 
     * Creates and adds a transition from the fromState to the toState
     * on the given character transition
     * @param fromState state being transitioned from
     * @param onSymb character in alphabet 
     * @param toState state being transitioned to
     */
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
    
 
    /** 
     * Returns Q (representation of all states)
     * @return the set of states in the NFA
     */
    @Override
    public Set<? extends State> getStates() {
        return Q;
    }

    
    /** 
     * Returns F (representation of final states)
     * @return the set of final states in NFA
     */
    @Override
    public Set<? extends State> getFinalStates() {  
        return F;
    }
   
    /** 
     * Returns q0 (start state representation in NFA)
     * @return the start state of the NFA
     */
    @Override
    public State getStartState() {
        return q0;
    }
   
    /** 
     * Returns sigma (set representation of alphabet/transition characters)
     * @return Set of characters in the alphabet
     */
    @Override
    public Set<Character> getABC() {
        return sigma;
    }
   
    /** 
     * Creates a DFA from an NFA following the convertion steps.
     * Walks through the NFA state by state and finds each transition.
     * If an epsilon transition exists the states that the epsilon transitions
     * to will be combined with from the transitioning state. This will create
     * the new states in the DFA and allow to remove epsilon transitions.
     * @return DFA converted from NFA
     */
    @Override
    public DFA getDFA() {

        DFA dfa = new DFA(); //DFA to be returned 
        LinkedList<Set<NFAState>> NFAList = new LinkedList<>();  //list/queue representation of the NFA states
        HashMap<Set<NFAState>, String> NFAMap = new LinkedHashMap<>();  //map to track each state passed over from the NFA, allows us to step through and create each new state/transition
        Set<NFAState> NFAStates = eClosure(q0);  //gets first set state from NFA to create DFA
      
        NFAList.add(NFAStates);  //adds the set of states created from eClosure of q0 to the queue

        //First check if the initial state created from eClosure contains a final state in its set
        if (hasFinal(NFAStates)) {
            String addFinalState = NFAStates.toString();
            DFAState dfaState = new DFAState(addFinalState, true);
            dfa.addFinalState(dfaState.toString());
        } 

        String addStartState = NFAStates.toString();
        dfa.addStartState(addStartState);  //Adds the starting state set to the DFA
        NFAMap.put(NFAStates, NFAStates.toString());  //put the set of states into the map with the set as the key and its toString representation as its value


        while (!NFAList.isEmpty()) {  //loop through the queue/list that holds NFA states
            NFAStates = NFAList.remove(0); //removes the first NFA state in the list as it has been added or will be added later, process of moving through the queue
     
            for (char trans: sigma) {  //loop through each transition in the alphabet

                //We don't want to include epsilon transitions in our DFA
                if (trans == 'e') {
                    continue;
                }

                /*
                 * This implements a breadth-first search because it 
                 * takes a single state from the queue and finds all
                 * possible transitions out of that state and then spreads
                 * to each following state rather than following a transition
                 * entirely through each state. It processes through state and
                 * expands from all possible transitions.
                 */

                //Set representation of states remaing to transition to based on each transition character
                Set<NFAState> tempSet = new LinkedHashSet<>();
                for (NFAState state: NFAStates) {
                    Set<NFAState> newSet = state.getStateOnSymb(trans);
                    tempSet.addAll(newSet);  //add all the states that can be transitioned to based on a transition character     
                }
                    //Set representation of states remaing to transition to based on each transition character
                    Set<NFAState> secondTempSet = new LinkedHashSet<>();
                    for (NFAState state: tempSet) {
                        Set<NFAState> newSetEpsilon = eClosure(state);
                        secondTempSet.addAll(newSetEpsilon);  //add all the sets of states that can be transitioned to based on epsilon, created from eClosure which returns the set of states that can be transitioned to from an epsilon transition
                    }
            
                //If our map does not contains the set created from eclosure (it's a new state created from eclosure that will be in the DFA)
                boolean hasKey = NFAMap.containsKey(secondTempSet);
                if (!hasKey) {
                    NFAList.add(secondTempSet);  //add that set to our list/queue to process through later
                    NFAMap.put(secondTempSet, secondTempSet.toString());  //Add this newly created state set to our map and have it contain the toString value representation of the state in order to create it as a DFA state
                    
                    boolean isFinal = hasFinal(secondTempSet);  //Search the state set to see if it contains a final state from the NFA
                    //If it contains a final state add it to the DFA as a final state
                    if (isFinal) {
                        String stateToAdd = NFAMap.get(secondTempSet);
                        dfa.addFinalState(stateToAdd);   //Get will return the value from the state set, which is just the string representation of the state set, again allowing us to create a DFA state
                    } else {  //Otherwise add it as a normal state 
                        String stateToAdd = NFAMap.get(secondTempSet);
                        dfa.addState(stateToAdd);  //Get will return the value from the state set, which is just the string representation of the state set, again allowing us to create a DFA state
                    }
                }

                String fromState = NFAMap.get(NFAStates);  //Create the string representation of the state set we are on
                String toState = NFAMap.get(secondTempSet);  //Create the string representation of the state set we will create a transition to
                dfa.addTransition(fromState, trans, toState);   //Add the transition to the DFA with the newly created state sets
            }
        }
        return dfa;
    }

    
    /** 
     * Searches the set of states to see if it contains a final state in the DFA
     * @param searchSet set of states 
     * @return whether or not the set contains a state that is in the NFA's final set of states
     */
    public boolean hasFinal(Set<NFAState> searchSet) {
        //Loop through each state in the passed in set
        for (NFAState state: searchSet) {
            //If the state is in the NFA's final set of states return true
            if (F.contains(state)) { 
                return true;
            }
        } 
        return false;
    }

    
    /** 
     * Returns the states that will be transitioned to based on passed 
     * in state and alphabet symbol 
     * @param from state currently at 
     * @param onSymb character from alphabet
     * @return Set of NFA states that will be transitioned to 
     */
    @Override
    public Set<NFAState> getToState(NFAState from, char onSymb) {  
        return from.getStateOnSymb(onSymb);
    }

    
    /** 
     * @param s
     * @return Set<NFAState>
     */
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
            // eTransitionStates.add(currState);  //For every transition to a state with an epsilon it will include the original state in the result state
    
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

            eTransitionStates.add(currState);  //For every transition to a state with an epsilon it will include the original state in the result state
        }

        return eTransitionStates;
    }

}
