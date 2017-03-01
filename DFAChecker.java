import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
/**
 * This application will take in a binary string of any random length and run it through a simulated
 * DFA machine. The Graphical User Interface allows for the user to input the states in the graph
 * along with their respective destinations via user input in the text boxes.
 * The program will assert if the proper destination was reached and show the patht the binary string created
 * when traveling throughout the DFA.
 * 
 * @author Joseph E. Cenci
 * February 28th, 2017. @ Joseph E. Cenci 2017
 */
public class DFAChecker extends Frame implements ActionListener
{
    int currentState = 0; // Current state 
    int stateCounter = 0; // The number of states in the DFA
    int desiredEndState; // The destination state that shows the input string has reached the desired state.
    String inputString = null; // The binary string that is read into the DFA machine
    DFAStateObj dfaStateObj; // A state object.
    ArrayList<DFAStateObj> stateList = new ArrayList<DFAStateObj>(); // List of DFAStates
    ArrayList<Integer> allInputs = new ArrayList<Integer>(); // List of all possible destinations
    
    // GUI Labels and other necessary functionality components
    Button buttonAddState, buttonCheckValid, buttonFinish, buttonClear, buttonExit; // Buttons in the GUI
    Label lblInput, lblFinish, lblStateNumber, lblSequence, lblResult, lblMatch, lblState0, lblState1, lblCredit; // Labels in the GUI
    TextField tfInputString, tfFinishState, tfPath, tfResult, tfMatch, tfState0, tfState1; // TextFields in the GUI
    /**
     * Main: Runs the DFA program by initializing the interactive GUI that the user can manipulate to their liking
     * This will also prompt the user to enter a binary string of any length greater than 1 and run it appropriately.
     *    
     */
    public static void main(String args[])
    {
        DFAChecker DFAC = new DFAChecker();
        DFAC.createGUI();
    }

    /**
     * Constructor for objects of class DFAChecker
     */
    public DFAChecker() {}

    /**
     * Method endState - Runs the binary string input through the list of DFA states and returns the final state the string
     * has terminated on. If a kill state is reached, indicated by a -1, the method ends with a return of -1.
     * 
     * @param  testString  The binary string that is input by the user to be broken down and passed through the DFA.
     * @return  int     The state number the program terminated on.
     */
    public int endState(String testString)
    {   currentState = 0;
        DFAStateObj currentStateObj = stateList.get(currentState);
        String stringPath = "" + currentState;
        for (int i = 0; i < testString.length(); i++) {
            char input = testString.charAt(i);
            int stateNumber = currentStateObj.takeInput(Character.getNumericValue(input));
            if (stateNumber < 0) { // Detects if a kill state is reached (state value less than 0)
                stringPath += "=> Kill State Reached.";
                tfResult.setText("Killed");
                break;
            }
            currentStateObj = stateList.get(stateNumber); // Gets the correct DFAStateObj
            currentState = currentStateObj.stateNumber; // Gets the number it holds
            stringPath += "=>" + currentState; // Appends to the path string 
            tfResult.setText(Integer.toString(currentState)); // Sets the result text to it.
        }
        tfPath.setText(stringPath);

        return currentState;
    }

    /**
     * Method validString -  Checks to see that all possiblly reached states have their own respective outward edges.
     * This prevents errors being thrown from having out of bounds exceptions when checking for the next state.
     *
     * @return     True if all states have been satisfied, False if not.
     */
    public boolean validString()
    {
        String binaryString = tfInputString.getText();
        for(int i = 0; i < binaryString.length(); i++) {
            char input = binaryString.charAt(i);
            if (input != '0' && input != '1') return false; // Checks to assure all numbers are 0 or 1
        }
        return true;
    }

    
    /**
     * Method createGUI Creates the GUI for the user to interact with.
     *
     */
    public void createGUI() {
        setLayout(new FlowLayout());    // Utilizes the standard Flow Layout
        Color backgroundColor = new Color(160, 229, 255); // Light blue color for background
        Color lightYellowColor = new Color(255, 233, 137); // Light yellow color for textfields that should hold the same value
        setBackground(backgroundColor); // Makes the GUI background the light blue color

        lblInput = new Label("Input Binary String:"); // construct the Label component
        add(lblInput); // Adds the label to the GUI

        tfInputString = new TextField("", 20);
        tfInputString.setEditable(true); // Makes the text field editable
        add(tfInputString);

        lblFinish = new Label("Input Desired Final State:");// construct the Label component
        add(lblFinish); 

        tfFinishState = new TextField("", 20);
        tfFinishState.setEditable(true); // Makes the text field editable
        tfFinishState.setBackground(lightYellowColor);
        add(tfFinishState);

        lblStateNumber = new Label(" Setting State: 0            "); // construct the Label component
        add(lblStateNumber);
        
        lblState0 = new Label("State Reachable with 0:");  // construct the Label component
        add(lblState0); 
        tfState0 = new TextField("", 10); // construct the TextField component
        tfState0.setEditable(true);       // Makes the text field editable
        add(tfState0);                    // "super" Frame adds TextField
        lblState1 = new Label("State Reachable with 1:");  // construct the Label component
        add(lblState1); 
        tfState1 = new TextField("", 10); // construct the TextField component
        tfState1.setEditable(true);       // Makes the text field editable
        add(tfState1);                      

        lblSequence = new Label("                Sequence of States:               "); // construct the Label component
        add(lblSequence);
        tfPath = new TextField("", 78); // construct the TextField component
        tfPath.setEditable(false);        // set to read-only
        add(tfPath);  

        lblResult = new Label("Final State Reached:"); // construct the Label component
        add(lblResult);
        tfResult = new TextField("", 5);
        tfResult.setEditable(false);    // set to read-only
        tfResult.setBackground(lightYellowColor);
        add(tfResult);

        lblMatch = new Label("Match?"); // construct the Label component
        add(lblMatch);
        tfMatch = new TextField("", 3);
        tfMatch.setEditable(false); // set to read-only
        add(tfMatch);

        buttonCheckValid = new Button("Check Valid"); // Creates a new button
        add(buttonCheckValid);
        buttonCheckValid.addActionListener(this);

        buttonAddState = new Button("Add State"); // Creates a new button
        add(buttonAddState);
        buttonAddState.addActionListener(this);
        buttonAddState.setEnabled(false);

        buttonFinish = new Button("Run DFA"); // Creates a new button
        add(buttonFinish);
        buttonFinish.addActionListener(this);
        buttonFinish.setEnabled(false);

        buttonClear = new Button("Clear");
        add(buttonClear);
        buttonClear.addActionListener(this);
        buttonExit = new Button("Exit"); // Creates a new button
        add(buttonExit);
        buttonExit.addActionListener(this);

        lblCredit = new Label("Created by Joseph E. Cenci 2017");
        add(lblCredit);
        setTitle("DFA Checker"); // Sets the title for the GUI
        setSize(650,190); // Sets the size of the GUI
        setResizable(false); // Makes sure the GUI is not resizeable
        setVisible(true); // Makes the GUI visible
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getActionCommand().equals("Clear")) {
            // Clears all previously set variables, allowing the DFA to be reset.
            stateCounter = 0;
            stateList.clear();
            allInputs.clear();
            buttonFinish.setEnabled(false);
            buttonAddState.setEnabled(false);
            
            tfInputString.setText("");
            tfFinishState.setText("");
            
            tfState0.setText("");
            tfState1.setText("");
            
            tfPath.setText("");
            tfResult.setText("");
            tfMatch.setText("");
            tfMatch.setBackground(Color.WHITE);
            lblStateNumber.setText(" Setting State: " + stateCounter + "            ");
        }
        if (evt.getActionCommand().equals("Exit")) System.exit(0);
        if (evt.getActionCommand().equals("Check Valid")) {
            if (tfState0.getText().trim().isEmpty() || tfState1.getText().trim().isEmpty()) buttonAddState.setEnabled(false);
            else buttonAddState.setEnabled(true);
            boolean statesLinked = true;
            for (int i = 0; i < allInputs.size(); i++) {
                if (allInputs.get(i) > stateCounter - 1) statesLinked = false;
            }
            for (int i = 0; i < stateList.size(); i++) {
                if (stateList.get(i).dest0 > stateCounter - 1 || stateList.get(i).dest1 > stateCounter - 1) statesLinked = false;
            }
            if (!validString() || stateCounter == 0 || !statesLinked) buttonFinish.setEnabled(false); 
            else buttonFinish.setEnabled(true);
        }
        if (evt.getActionCommand().equals("Add State")) {
            dfaStateObj = new DFAStateObj(stateCounter);
            stateList.add(dfaStateObj);

            int destState0 = Integer.parseInt(tfState0.getText());

            int destState1 = Integer.parseInt(tfState1.getText());
            allInputs.add(destState0);
            allInputs.add(destState1);
            dfaStateObj.setDestinations(destState0, destState1);

            tfState0.setText("");
            tfState1.setText("");

            buttonAddState.setEnabled(false);
            stateCounter++;
            if (!tfFinishState.getText().trim().isEmpty()) {
                boolean statesLinked = true;
                for (int i = 0; i < allInputs.size(); i++) {
                    if (allInputs.get(i) > stateCounter) statesLinked = false;
                }
                for (int i = 0; i < stateList.size(); i++) {
                    if (stateList.get(i).dest0 > stateCounter - 1 || 
                        stateList.get(i).dest1 > stateCounter - 1) statesLinked = false;
                }
                if (stateCounter - 1 < Integer.parseInt(tfFinishState.getText()) || 
                    !statesLinked) buttonFinish.setEnabled(false);
                else buttonFinish.setEnabled(true);
            }
            lblStateNumber.setText(" Setting State: " + stateCounter + "            "); // Updates the state in the GUI
        }
        if (evt.getActionCommand().equals("Run DFA")) {
            // Runs the program and asserts the finish state of the DFA once the binary string has been passed.
            desiredEndState = Integer.parseInt(tfFinishState.getText());
            inputString = tfInputString.getText();
            int dfaValue = endState(inputString);
            
            buttonFinish.setEnabled(false);
            
            if (dfaValue == desiredEndState) { // Succeeded in reaching the desired state.
                tfMatch.setText("YES");
                tfMatch.setBackground(Color.GREEN);
            }
            else { // Failed in reaching the desired state.
                tfMatch.setText("NO");
                tfMatch.setBackground(Color.RED);
            }
        }
    }
    /**
     * A DFAStateObj is an object that represents a state within the DFA graph.
     * It is created each time a new state is instantiated within the GUI.
     * Holds the state numbers accessible via a 0 or 1 input.
     * 
     * Joseph E. Cenci
     * February 28th, 2017. @ Joseph E. Cenci 2017
     */
    public class DFAStateObj
    {
        int stateNumber, dest0, dest1;
        /**
         * Constructor for objects of class DFAStateObj
         */
        public DFAStateObj()
        {

        }

        /**
         * Constructor for DFAStateObj that includes the number state it is
         *
         * @param   stateNumber   The number state the object is in the DFA state list.
         */
        public DFAStateObj(int stateNumber)
        {
            this.stateNumber = stateNumber;
        }

        /**
         * Method setDestinations - Sets up the states reached from a 0 or 1.
         *
         * @param  dest0   State reached via a 0 input.
         * @param  dest1   State reached via a 1 input.
         */
        public void setDestinations(int dest0, int dest1)
        {
            this.dest0 = dest0;
            this.dest1 = dest1;
        }

        /**
         * Method takeInput - Takes a 0 or 1 as an input and returns that state reached via that binary character.
         * 
         * @param   input   The number 0 or 1, based upon the current value at the location in the binary string input. 
         * @return     The state reached from either an input of 0 or 1.
         */
        public int takeInput(int input)
        {
            if (input == 0) return dest0;
            return dest1;
        }
    }
}

