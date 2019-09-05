package calculatorfx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class FXMLDocumentController implements Initializable {
    
    //Variables
    double firstNumber=0;
    double secondNumber=0;
    boolean writing = false;
    boolean writing2 = false;
    String numComma;
    boolean [] operations = new boolean[4];
    int opIndex = -1;
    boolean opPressed = false;
    
    @FXML
    private Label label;
    @FXML
    private AnchorPane form;
    @FXML
    private Button btnMinus;
    @FXML
    private Button btnEqual;
    @FXML
    private Button btnPlus;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnMult;
    @FXML
    private Button btnC;
    @FXML
    private Button btnDivision;
    @FXML
    private Button btn7;
    @FXML
    private Button btn8;
    @FXML
    private Button btn9;
    @FXML
    private Button btn4;
    @FXML
    private Button btn5;
    @FXML
    private Button btn6;
    @FXML
    private Button btn0;
    @FXML
    private Button btnComma;
    @FXML
    private Button btnNeg;
    @FXML
    private Button btn3;
    @FXML
    private Button btn2;
    @FXML
    private Button btn1;
    @FXML
    private TextField txtNums;
       
    //Event Handlers    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        form.requestFocus();
        txtNums.setStyle("-fx-text-inner-color: black;");
    } 
    
    @FXML
    private void clickNumber(ActionEvent event) {
        Button button = (Button) event.getSource();
        writeNumber(button.getText());
        form.requestFocus();
    }
    
    @FXML
    private void clickComma(ActionEvent event) {
        numComma=txtNums.getText();
        numComma+=".";
        if (isNumeric(txtNums.getText())) {
            txtNums.setText(txtNums.getText()+".");
            writing=true;
        }
        form.requestFocus();
    }
    
    @FXML
    private void clickNegative(ActionEvent event) {
        if (isNumeric(txtNums.getText())) {
            if (compareDoubleToInteger()) txtNums.setText(String.valueOf(getIntFromText()*-1));
            else txtNums.setText(String.valueOf(getDoubleFromText()*-1));
        }
        form.requestFocus();
    }    
    

    @FXML
    private void clickOperator(ActionEvent event) {
        Button button = (Button) event.getSource();
        
        if (!opPressed && (operations[0]||operations[1]||operations[2]||operations[3])) {
            calculate();
        }
        
        switch (button.getText()) {
            case "+":
                opIndex=0;
                break;
            case "-":
                opIndex=1;
                break;
            case "*":
                opIndex=2;
                break;
            case "/":
                opIndex=3;
                break;                       
        }
        operation(opIndex);
        assignNumber();
        form.requestFocus();
    }

    @FXML
    private void clickEqual(ActionEvent event) {
        calculate();
        firstNumber=0;
        if (secondNumber==0) secondNumber=0;
        else secondNumber=getDoubleFromText();
        operations[0]=false;
        operations[1]=false;
        operations[2]=false;
        operations[3]=false;
        form.requestFocus();
    }

    @FXML
    private void clickDelete(ActionEvent event) {
         if (isNumeric(txtNums.getText())) {
            if (getDoubleFromText()!=0) {
               if (txtNums.getText().length()>1) {
                   txtNums.setText(txtNums.getText().substring(0, txtNums.getText().length()-1));
               } else {
                   txtNums.setText("0");
                   writing = false;
                   writing2 = false;
               }
            } else {
                txtNums.setText("0");
                writing = false;
                writing2 = false;
            }
        } else {
            clickC(event);
        }
        form.requestFocus();
    }

    @FXML
    private void clickC(ActionEvent event) {
        txtNums.setText("0");
        firstNumber=0;
        secondNumber=0;
        operations[0] = false;
        operations[1] = false;
        operations[2] = false;
        operations[3] = false;
        writing = false;
        writing2 = false;
        numComma = "0";
        opIndex=-1;
        form.requestFocus();
    }

    @FXML
    private void pressKey(KeyEvent e) {
         String ch = e.getText();
        if (isNumeric(ch)) writeNumber(ch);
        KeyCode ke = e.getCode();
        if (ke.equals(KeyCode.ENTER)) calculate();
        if (ke.equals(KeyCode.ADD) || ke.equals(KeyCode.SUBTRACT) || 
                ke.equals(KeyCode.MULTIPLY) || ke.equals(KeyCode.DIVIDE)) {
                    if (!opPressed && (operations[0]||operations[1]||operations[2]||operations[3])) {
                        calculate();          
                    }
            switch (ke) {
                case ADD:
                    opIndex = 0;
                    break;      
                case SUBTRACT:
                    opIndex = 1;
                    break;
                case MULTIPLY:
                    opIndex = 2;
                    break;
                case DIVIDE:
                    opIndex = 3;
                    break;
                default:
                    break;
            }
        operation(opIndex);
        assignNumber();
        }
        form.requestFocus();
    }
    
    //Methods   
    private void writeNumber(String number) {
        if (firstNumber==0) {
            if (!writing) {
                txtNums.setText(number);
                if (!txtNums.getText().equals("0") ) {
                    writing=true;
                }
            } else {
                txtNums.setText(txtNums.getText() + number);
            }
        } else if (!writing2) {
            txtNums.setText(number);
            secondNumber=getDoubleFromText();
            if (secondNumber!=0) writing2=true;
        } else {
            txtNums.setText(txtNums.getText() + number);
            secondNumber=getDoubleFromText();
        }
        opPressed = false;
    }
    
    public static boolean isNumeric(String str) { 
        try {  
          Double.parseDouble(str);  
          return true;
        } catch(NumberFormatException e){  
          return false;  
        }  
    }  
    
    private void operation(int opIndex) {
        for (int i=0 ; i<4 ; i++) {
            if (i == opIndex) operations[i] = true;
            else operations[i] = false;
        }
        opPressed = true;
        writing = false;
        writing2 = false;
    }
    
    private void assignNumber() {
        if (isNumeric(txtNums.getText())) {
            if (firstNumber==0) {
                firstNumber=getDoubleFromText();
                secondNumber = 0;
            } else {
                if (!opPressed) secondNumber = getDoubleFromText();
                else secondNumber=0;
            }
        }           
    }
    
    private void calculate() {
        if (operations[0]) txtNums.setText(addition());
        if (operations[1]) txtNums.setText(subtraction());
        if (operations[2]) txtNums.setText(multiplication());
        if (operations[3]) txtNums.setText(division());
        firstNumber=0;
    }    
    
    private Double getDoubleFromText () {
        return Double.parseDouble(txtNums.getText());
    }
    
    private int getIntFromText () {
        return (int) Double.parseDouble(txtNums.getText());
    }
    
    private boolean compareDoubleToInteger () {
        if (getDoubleFromText()==getIntFromText()) return true;
        else return false;        
    }
    
    private boolean compareDoubleToInteger (double number) {
        int tmpInt = (int) number;
        if (number==tmpInt) return true;
        else return false;        
    }
    
    private String addition() {
        double result = firstNumber + secondNumber;
        if (compareDoubleToInteger(result)) return String.valueOf((int)result);
        else return String.valueOf(result);
    }
    
    private String subtraction() {
        double result = firstNumber - secondNumber;
        if (compareDoubleToInteger(result)) return String.valueOf((int)result);
        else return String.valueOf(result);
    }
    
    private String multiplication() {
        double result = firstNumber * secondNumber;
        if (compareDoubleToInteger(result)) return String.valueOf((int)result);
        else return String.valueOf(result);
    }
    
    private String division() {
        if (secondNumber==0) {
            firstNumber=0;
            secondNumber=0;
            opPressed = false;
            writing = false;
            writing2 = false;
            return "Error";
        } else {
            double result = firstNumber / secondNumber;
            if (compareDoubleToInteger(result)) return String.valueOf((int)result);
            else return String.valueOf(result);
        }
    }
    
}
