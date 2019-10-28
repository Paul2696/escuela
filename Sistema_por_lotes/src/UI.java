import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Queue;
import java.util.List;

public class UI extends JFrame implements OperationListener, KeyListener{
    //private JLabel nombre;
    private JLabel processes;
    //private JLabel operacion;
    //private JLabel time;
    private JLabel tt = new JLabel();
    //private JTextField nombreText = new JTextField();
    private JTextField processesText = new JTextField();
    //private JTextField timeText = new JTextField();
    //private JTextField number1Text = new JTextField();
    //private JTextField number2Text = new JTextField();
    //private JComboBox operacionBox;
    //private JButton agregarOperacion;
    private JButton iniciarSimulacion;
    private JTextArea salidaOeste;
    private JTextArea salidaCentro;
    private JTextArea salidaEste;
    private JTextArea salidaBloqueados;
    private BatchProcessor batchProcessor = new BatchProcessor(this);
    private String previousText;
    private Thread threadBatchProcessor;
    private boolean blockedCalled = false;

    public UI(){
        super("Simulacion de sistema por lotes");

        setExtendedState(MAXIMIZED_BOTH);
        setResizable(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Setting Labels
        //Setting registration labels
        //nombre = new JLabel("Nombre: ");
        processes = new JLabel("Numero de procesos: ");
        //operacion = new JLabel("Operacion: ");
        //time = new JLabel("Tiempo: ");
        //Setting simulation labels

        //Setting Text fields
        //nombreText.setMaximumSize(new Dimension(Integer.MAX_VALUE, nombreText.getPreferredSize().height));
        processesText.setMaximumSize(new Dimension(Integer.MAX_VALUE, processesText.getPreferredSize().height));
        //number1Text.setMaximumSize(new Dimension(Integer.MAX_VALUE, processesText.getPreferredSize().height));
        //number2Text.setMaximumSize(new Dimension(Integer.MAX_VALUE, processesText.getPreferredSize().height));
        //timeText.setMaximumSize(new Dimension(Integer.MAX_VALUE, timeText.getPreferredSize().height));

        //Setting combo box
        /*operacionBox = new JComboBox();
        operacionBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, operacionBox.getPreferredSize().height));
        operacionBox.addItem(new ComboItem("+", Operation.ADDITION));
        operacionBox.addItem(new ComboItem("-", Operation.SUBSTRACTION));
        operacionBox.addItem(new ComboItem("*", Operation.MULTIPLICATION));
        operacionBox.addItem(new ComboItem("/", Operation.DIVISION));
        operacionBox.addItem(new ComboItem("^", Operation.POWER));
        operacionBox.addItem(new ComboItem("Mod", Operation.MODULE));*/

        //Setting Button
        /*agregarOperacion = new JButton("Agregar Operacion");
        agregarOperacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Operation operation = new Operation();
                        operation.setId(Integer.parseInt(processesText.getText()));
                        operation.setNumber1(Integer.parseInt(number1Text.getText()));
                        operation.setNumber2(Integer.parseInt(number2Text.getText()));
                        operation.setTime(Integer.parseInt(timeText.getText()));
                        operation.setOperator(((ComboItem)operacionBox.getSelectedItem()).getValue());
                        batchProcessor.addToBatch(Integer.parseInt(processesText.getText()));
                        cleanForm();
                    }
                });
                thread.start();
            }
        });*/
        iniciarSimulacion = new JButton("Iniciar simulacion");
        iniciarSimulacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //agregarOperacion.setEnabled(false);
                batchProcessor.addToProcesses(Integer.parseInt(processesText.getText()));
                threadBatchProcessor = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        batchProcessor.runBatch();
                    }
                });
                Thread timer = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ttHandle();
                    }
                });
                threadBatchProcessor.start();
                timer.start();
            }
        });

        addKeyListener(this);

        //Setting Main Panels
        setLayout(new BorderLayout());
        JPanel registrationPanel = new JPanel();
        registrationPanel.setLayout(new BorderLayout());
        add(registrationPanel, BorderLayout.NORTH);
        JPanel simulationPanel = new JPanel();
        simulationPanel.setLayout(new GridLayout(1, 3));
        add(simulationPanel);
        registrationPanel.addKeyListener(this);
        simulationPanel.addKeyListener(this);

        //Setting components
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(15, 0));
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        //leftPanel.add(nombre);
        //rightPanel.add(nombreText);
        rightPanel.add(Box.createRigidArea(new Dimension(0,5)));
        leftPanel.add(processes);
        rightPanel.add(processesText);
        rightPanel.add(Box.createRigidArea(new Dimension(0,5)));
        //leftPanel.add(new JLabel("Numero 1:"));
        //rightPanel.add(number1Text);
        rightPanel.add(Box.createRigidArea(new Dimension(0,5)));
        //leftPanel.add(operacion);
        //rightPanel.add(operacionBox);
        rightPanel.add(Box.createRigidArea(new Dimension(0,5)));
        //leftPanel.add(new JLabel("Numero 2: "));
        //rightPanel.add(number2Text);
        //rightPanel.add(Box.createRigidArea(new Dimension(0,5)));
        //leftPanel.add(time);
        //rightPanel.add(timeText);
        rightPanel.add(Box.createRigidArea(new Dimension(0,5)));
        //leftPanel.add(agregarOperacion);
        rightPanel.add(iniciarSimulacion);
        leftPanel.addKeyListener(this);
        rightPanel.addKeyListener(this);
        registrationPanel.add(leftPanel, BorderLayout.WEST);
        registrationPanel.add(rightPanel, BorderLayout.CENTER);
        registrationPanel.setVisible(true);

        //Simulation Panel
        JPanel westPanel = new JPanel();
        westPanel.setLayout(new GridLayout(2,1));
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2,1));
        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new GridLayout(2,1));
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(2, 1));
        westPanel.addKeyListener(this);
        centerPanel.addKeyListener(this);
        eastPanel.addKeyListener(this);
        southPanel.addKeyListener(this);
        simulationPanel.setFocusable(true);
        simulationPanel.add(westPanel, BorderLayout.WEST);
        simulationPanel.add(centerPanel, BorderLayout.CENTER);
        simulationPanel.add(eastPanel, BorderLayout.EAST);
        simulationPanel.add(southPanel, BorderLayout.SOUTH);

        //Setting components
        salidaOeste = new JTextArea();
        salidaCentro = new JTextArea();
        salidaEste = new JTextArea();
        salidaBloqueados = new JTextArea();
        westPanel.add(new JScrollPane(salidaOeste));
        centerPanel.add(new JScrollPane(salidaCentro));
        eastPanel.add(new JScrollPane(salidaEste));
        southPanel.add(new JScrollPane(salidaBloqueados));
        westPanel.add(tt);

        salidaOeste.addKeyListener(this);
        salidaCentro.addKeyListener(this);
        salidaEste.addKeyListener(this);
        /*salidaOeste.setEnabled(false);
        salidaCentro.setEnabled(false);
        salidaEste.setEnabled(false);*/
        salidaOeste.requestFocusInWindow();
        pack();
        setVisible(true);
    }

    public void cleanForm(){
        processesText.setText("");
        //nombreText.setText("");
        //timeText.setText("");
        //number1Text.setText("");
        //number2Text.setText("");
    }


    @Override
    public void operationDone(Operation operation) {
        StringBuilder sb = new StringBuilder();
        sb.append("ID \t");
        sb.append("Operacion \t");
        sb.append("Resultado \t");
        //sb.append("Número de proceso \n");
        sb.append("\n");
        sb.append(operation.getId());
        sb.append("\t");
        sb.append(operation.getNumber1());
        sb.append(operation.getOperatorSymbol(operation.getOperator()));
        sb.append(operation.getNumber2());
        sb.append("\t");
        sb.append(operation.getResult());
        sb.append("\t");
        //sb.append(operation.getBatchId());
        sb.append("\n");
        salidaEste.append(sb.toString());
    }

    public void ttHandle(){
        while(!batchProcessor.isFinished()){
            try{
                Thread.sleep(1000);
                this.tt.setText("Tiempo Transcurrido: " + Integer.toString(batchProcessor.getTt()));
                batchProcessor.setTt(batchProcessor.getTt() + 1);
            }
            catch(Exception e){
                e.printStackTrace();
            }

        }
    }

    @Override
    public void batchDone() {

    }



    @Override
    public void operationRunning(Operation operation, int seconds) {
        StringBuilder sb = new StringBuilder();
        int tiempoTranscurrido = 0;
        sb.append("Nombre:\t");
        sb.append("\n");
        sb.append("ID:\t");
        sb.append(operation.getId());
        sb.append("\n");
        sb.append("Operacion:\t");
        sb.append(operation.getNumber1());
        sb.append(operation.getOperatorSymbol(operation.getOperator()));
        sb.append(operation.getNumber2());
        sb.append("\n");
        sb.append("Tiempo estimado: \t");
        sb.append(operation.getTime());
        sb.append("\n");
        sb.append("Tiempo transcurrido: \t");
        sb.append(seconds);
        salidaCentro.setText(sb.toString());
    }

    @Override
    public void updateBatchList(Queue<Operation> operations) {
        StringBuilder sb = new StringBuilder();
        int counter = 0;
        sb.append("Procesos Pendientes:\t");
        if(batchProcessor.getProcessesLeft() > 0){
            sb.append(batchProcessor.getProcessesLeft());
        }
        else{
            sb.append("0");
        }
        sb.append("\n");
        sb.append("ID: \t");
        sb.append("Time: \t");
        sb.append("Time Left: \n");
        Operation actualOperation = operations.peek();
        for(Operation operation : operations){
            if(counter != 4){
                sb.append(operation.getId());
                sb.append("\t");
                sb.append(operation.getTime());
                sb.append("\t");
                sb.append(operation.getTranscurrentTime());
                sb.append("\n");
                counter++;
            }
            else{
                break;
            }
        }
        salidaOeste.setText(sb.toString());
    }

    @Override
    public void operationDoneWithError(Operation operation) {
        StringBuilder sb = new StringBuilder();
        sb.append("ID \t");
        sb.append("Operacion \t");
        sb.append("Resultado \t");
        //sb.append("Número de proceso \n");
        sb.append("\n");
        sb.append(operation.getId());
        sb.append("\t");
        sb.append("ERROR");
        sb.append("\t");
        sb.append("ERROR");
        sb.append("\t");
        //sb.append(operation.getBatchId());
        sb.append("\n");
        salidaEste.append(sb.toString());
    }

    @Override
    public void operationBlocked(Queue<Operation> operations) {
        StringBuilder sb = new StringBuilder();
        sb.append("ID\t");
        sb.append("Tiempo bloqueado\n");
        for(Operation operation : operations){
            sb.append(operation.getId());
            sb.append("\t");
            sb.append(operation.getBlockedTime());
            sb.append("\n");
        }
        salidaBloqueados.setText(sb.toString());
    }

    @Override
    public void finishedProcesses(Queue<Operation> operations) {
        StringBuilder sb = new StringBuilder();
        sb.append("ID \t");
        sb.append("Tiempo de llegada \t");
        sb.append("Tiempo de finalización \t");
        sb.append("Tiempo de retorno \t");
        sb.append("Tiempo de servicio \t");
        sb.append("Tiempo de espera \t");
        sb.append("Tiempo de respuesta \n");
        for(Operation operation : operations){
            sb.append(operation.getId());
            sb.append("\t \t");
            sb.append(operation.getArrivalTime());
            sb.append("\t \t");
            sb.append(operation.getFinishedTime());
            sb.append("\t \t");
            sb.append(operation.getReturnTime());
            sb.append("\t \t");
            sb.append(operation.getServiceTime());
            sb.append("\t \t");
            sb.append(operation.getWaitTime());
            sb.append("\t \t");
            sb.append(operation.getAnswerTime());
            sb.append("\n");
        }
        salidaBloqueados.setText(sb.toString());
    }

    public static void main(String[] args) throws Exception{
        UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new UI();
            }
        });
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        JTextArea eventSource = (JTextArea)keyEvent.getSource();
        previousText = eventSource.getText();
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        synchronized (batchProcessor){
            try{
                switch (keyCode){
                    case KeyEvent.VK_W:
                        System.out.println("Presionamos W");
                        batchProcessor.errorOperation();
                        break;
                    case KeyEvent.VK_E:
                        System.out.println("Presionamos E");
                        batchProcessor.interruptedOperation();
                        /*
                        if(batchProcessor.getBlockedThread().isAlive()){
                            break;
                        }
                        else{
                            batchProcessor.getBlockedThread().start();
                        }*/
                        break;
                    case KeyEvent.VK_P:
                        System.out.println("Presionamos P");
                        batchProcessor.pauseOperation();
                        break;
                    case KeyEvent.VK_C:
                        System.out.println("Presionamos C");
                        batchProcessor.resumeOperation();
                        break;
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        JTextArea eventSource = (JTextArea)keyEvent.getSource();
        eventSource.setText(previousText);
    }
}
