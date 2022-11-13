package fa.nfa;
import fa.State;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;



public class NFAState extends State {
    

    // Create a linked HashMap to represent the NFA transitions 
    private LinkedHashMap<Character, HashSet<NFAState>> NFATransitionMap;
    // Need flag to keep track of final states
    private boolean finalStateFlag;

    public NFAState(String stateName) { // Constructor for NFAState
        // Set the name passed in from state to create the new NFAState
        this.name = name;
        // Create the transition map for the new name 
        NFATransitionMap = new LinkedHashMap<Character, HashSet<NFAState>>();
        // Initialize final state flag to false 
        this.finalStateFlag = false;
    }

    public void addTransition(char onSymb, NFAState toState) {
        // Need to check if the value from the alphabet is alread in our transition map
        if (NFATransitionMap.containsKey(onSymb)) {
            // If so then we can just add the toState to the onSymb key
            NFATransitionMap.get(onSymb).add(toState);
        } else {
            // Otherwise we need to initalize the new onSymb and add the transition
            HashSet<NFAState> onSymbSet = new HashSet<NFAState>();
            // Add the state to transition to based on the input alphabet symbol
            onSymbSet.add(toState);
            // Finally add this set to the transition map 
            NFATransitionMap.put(onSymb, onSymbSet);
        }
    }

    public Set<NFAState> getStateOnSymb(char onSymb) { // Get the next transition 
        // Check if the transition table contains the character symbol 
        if (NFATransitionMap.containsKey(onSymb)) {
            // If there is a transition return the state set that can be transitioned to
            return NFATransitionMap.get(onSymb);
        } else {
            // Otherwise return an emtpy set as there are no transitions 
            return new LinkedHashSet<>();
        }
    }

    public NFAState(String name, boolean finalFlag) { // Constructor for final state
        // Set final flag to be set to boolean passed in 
        this.finalStateFlag = finalFlag;
        // Set the name passed in from state to create the new NFAState
        this.name = name;
        // Create the transition map for the new name 
        NFATransitionMap = new LinkedHashMap<Character, HashSet<NFAState>>();
    }

    public boolean isFinalFlag() {//
        // Return if the NFAState is a final state or not
        return finalStateFlag;
    }

    public LinkedHashMap<Character, HashSet<NFAState>> getTransitions() {
        return NFATransitionMap;
    }
}
