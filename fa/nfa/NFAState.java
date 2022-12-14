package fa.nfa;
import fa.State;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Tuesday, November 22, 2022 
 * Represents an NFA state extended
 * from the state class. Each state is
 * stored with its transitions in a 
 * LinkedHashMap. 
 * @author Drew Marshall
 * @author Steven Lineses
 */


 /**
  * Representation of a single NFA state.
  * State also included all transitions related
  * to the state. 
  * @author Drew Marshall
  */
public class NFAState extends State {
    // Create a linked HashMap to represent the NFA transitions 
    private LinkedHashMap<Character, HashSet<NFAState>> NFATransitionMap;
    // Need flag to keep track of final states
    private boolean finalStateFlag;

    /**
     * Constructor to represent an NFA state
     * 
     * @param stateName String name of the state to be created
     */
    public NFAState(String stateName) { // Constructor for NFAState
        // Set the name passed in from state to create the new NFAState
        this.name = stateName;
        // Create the transition map for the new name 
        NFATransitionMap = new LinkedHashMap<Character, HashSet<NFAState>>();
        // Initialize final state flag to false 
        this.finalStateFlag = false;
    }

    
    /** 
     * Attaches a states transitions to the state
     * 
     * @param onSymb character transition 
     * @param toState state to be transitioned to from onsymb
     */
    public void addTransition(char onSymb, NFAState toState) { // Constructor to add a transition to a state based on an input symbol/character
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

    
    /** 
     * Returns the state set that would result from a certain transition
     * 
     * @param onsymb character representation of a single transition
     * @return Set<NFAState> the set of states for a particular
     * character transition from the alphabet 
     */
    public Set<NFAState> getStateOnSymb(char onSymb) { // Get the next transition 
        // Check if the transition table contains the character symbol 
        if (NFATransitionMap.containsKey(onSymb)) {
            // If there is a transition return the state set that can be transitioned to
            return NFATransitionMap.get(onSymb);
        } else {
            // Otherwise return an emtpy set as there are no transitions 
            return new HashSet<NFAState>();
        }
    }

    /**
     * Constructor to represent an NFA final/accept state
     * 
     * @param stateName String name of the state to be created
     * @param finalFlag Flag to set a state as final
     */
    public NFAState(String stateName, boolean finalFlag) { // Constructor for final state
        // Set final flag to be set to boolean passed in 
        this.finalStateFlag = finalFlag;
        // Set the name passed in from state to create the new NFAState
        this.name = stateName;
        // Create the transition map for the new name 
        NFATransitionMap = new LinkedHashMap<Character, HashSet<NFAState>>();
    }

    /**
     * Returns true is state is final else false
     * 
     * @return if a state is a final state or not
     */
    public boolean isFinalFlag() {//
        // Return if the NFAState is a final state or not
        return finalStateFlag;
    }

    /**
     * Returns transitions for given sigma element.
     * 
     * @return LinkedHashMap that holds the transitions for a state
     */
    public LinkedHashMap<Character, HashSet<NFAState>> getTransitions() { // Constructor to get transitions 
        return NFATransitionMap;
    }
}
