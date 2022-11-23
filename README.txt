# Project 2: Nondeterministic Finite Automata
* Author: Drew Marshall, Steven Lineses
* Class: CS361 Section #002
* Semester: Fall 2022

## Overview

Our program takes in a formatted .txt file and converts it
into a Nondeterministic Finite Automata (NFA). It creates
the standard 5-tuple containing states, final states, alphabet,
start state, and transitions. As well, our program allows the 
user to convert the input NFA into its respective DFA using both
a breadth first and depth first search to process the conversion.

## Reflection

Write a brief (2-3 paragraph) reflection describing your experience with 
this
project. Answer the following questions (but feel free to add other 
insights):
- What worked well and what was a struggle?
- What concepts still aren't quite clear?
- What techniques did you use to make your code easy to debug and modify?
- What would you change about your design process?
- If you could go back in time, what would you tell yourself about doing 
this project?

### Drew's Reflection:
Beginning the project was probably the most difficult part. Understanding 
how to organize the NFA and how it would differ from the previous DFA was
a large step to take. I found that the NFAState was pretty similar to the
way the first project was set up, using a LinkedHashMap to store each state
and their transitions. After conversing with other students and past students
I found that having two different methods to create an NFA state and an NFA
final state were helpful later on. 

I think most concepts are clear and it was good practice having to use lists
and maps to store and retrieve information. Steven implemented eClosure and 
its depth first traversal and learning how to use that to create the new 
states for the NFA based on epsilon transitions took some time, but I was able
to help organize it and understand how the new set of states was being returned. 
Debugging was a big process for us and having to set multiple breakpoints throughout
was the best way to understand where our code was breaking. I also had to 
implement several print statements in order to understand what was being returned, 
and figure out how formatting would work/impact our output. 

For me personally, I would like to start using a whiteboard more when creating
the design aspect of my code. I think this would not only help me debug,
but visually understand how the code should work and be implemented rather
than simply trying to do it all immediately as code. Drawing things out 
would certainly help with things such as certain formatted searches. It 
would certainly be a useful skill to have for interviews and working 
with large groups. Finally, if I could go back it would be the same thing
I always say start early. I think what I need to work on is breaking my
programs down into small portions instead of trying to do it all at once.
Like building a puzzle, build it up piece by piece, don't try and put them 
all together at once. 

### Steven's Reflection

Watching videos on BFS and DFS search helped write the code to implement the functions.
I am a visual learner so trying to abstractly think about an NFA in Java code did not work
well.

The concept of converting an NFA to a DFA is pretty clear now. However, I don't think I'd know
how to make a minimal DFA from an NFA. I am not sure what kind of algorithm that entails.

For debugging, I made multiple breakpoints in the code. Any time a change was made to the program,
I reran the tests to ensure that the same output was being generated. Also, I created a python script
to convert a test case into an equivalent JFLAP file which I could then view the NFA in JFLAP.
That helped the debugging process alot and helped to verify which inputs should be accepted or
rejected.

Next time, I should read up on the required algorithms first. Then also research a general approach
to the problem. Also, drawing the process out would probably help as well since I am a visual learner.

Going back in time, I would definitely start the project earlier. This project was more involved
than Project 1, which we were warned about, but I think taking an incremental approach would have been
more viable.

## Compiling and Using

Compile program:
`javac fa/nfa/NFADriver.java`

Run program for test case 0. (Can replace `./tests/p2tc0.txt` with path to a different test):
`java fa.nfa.NFADriver ./tests/p2tc0.txt`

## Sources used

### Drew's Sources
I used alot of different sources throught the programmnig process. The 
resource I found myelf using the most was talking and working with other
groups within the class to debug and understand how certain parts of
the program should be implemented and foramtted. As well, I met serveral
times with my friends who have completed their CS degree to get 
guidance on how to approach and build the program. My friends Casey and 
Trevor were useful in helping me understand how both searches should work
and how I would implement them based on how we foramtted our NFA. 

Online sources I used:
https://docs.oracle.com/javase/8/docs/api/java/util/List.html
https://docs.oracle.com/javase/8/docs/api/java/util/Set.html
https://docs.oracle.com/javase/8/docs/api/java/util/Map.html
https://www.geeksforgeeks.org/map-interface-java-examples/
https://www.programiz.com/dsa/graph-bfs
https://www.geeksforgeeks.org/breadth-first-search-or-bfs-for-a-graph/
https://www.javatpoint.com/automata-conversion-from-nfa-to-dfa

The following link is a Github page that is a tutorial for how NFA's work
and how to think of them when implemented using java
https://grrinchas.github.io/posts/nfa

# Steven's Sources

Recursion
https://www.programiz.com/java-programming/recursion

Java Sets
https://www.geeksforgeeks.org/set-in-java/

DFS explanation
https://www.youtube.com/watch?v=Urx87-NMm6c


----------

## Jokes

