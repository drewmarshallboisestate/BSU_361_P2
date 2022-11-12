package fa.nfa;
import fa.State;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;



public class NFAState extends State {
    

    // Create a linked HashMap to represent the NFA transitions 
    private LinkedHashMap<Character, Set<NFAState>> NFATransitionMap;
    // Need flag to keep track of final states
    private boolean finalStateFlag;

    public NFAState(String stateName) {
        // Set the name passed in from state
        this.name = name;
        // Create the transition map for the new name 
        NFATransitionMap = new LinkedHashMap<Character, Set<NFAState>>();
        // Initialize final state flag to false 
        finalStateFlag = false;
    }

    public void addTransition(char onSymb, NFAState toState) {
        // Need to check if the value from the alphabet is alread in our transition map
        if (NFATransitionMap.containsKey(onSymb)) {
            // If so then we can just add the toState to the onSymb key
            NFATransitionMap.get(onSymb).add(toState);
        } else {
            // Otherwise we need to initalize the new onSymb and add the transition
            Set<NFAState> onSymbSet = new LinkedHashSet<>();
            // Add the state to transition to based on the input alphabet symbol
            onSymbSet.add(toState);
            // Finally add this set to the transition map 
            NFATransitionMap.put(onSymb, onSymbSet);

        }
    }
}
