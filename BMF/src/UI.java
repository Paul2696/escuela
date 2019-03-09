import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class UI extends JFrame implements MapListener, TrainingListener{
    JSlider obstaclesSlider;
    JSlider mountainsSlider;
    JSlider riversSlider;
    JSlider plainSlider;
    JSlider ravineSlider;
    JLabel label;
    JButton setAgentBtn;
    JButton setHouseBtn;
    JButton initBtn;
    JPanel mapPanel = new JPanel();
    JProgressBar trainingBar = new JProgressBar(0, 100);
    static JLabel agentIcon = new JLabel();
    JCheckBox training;
    AddIconListener iconListener = new AddIconListener();
    static final Mapa map = new Mapa();
    static final Agent agent = new Agent(0, null);
    static Coord agentsCoord = new Coord(0, 0);
    static Node initialNode;
    static Node houseNode;
    static boolean trainingDone = false;
    static Graph graph = new Graph();
    static Agent mombo = new Agent(Mapa.MOMBO, graph);
    static Agent pirolo = new Agent(Mapa.PIROLO, graph);
    static Agent lucas = new Agent(Mapa.LUCAS, graph);
    JButton[][] buttons = new JButton[map.getDimensionX()][map.getDimensionY()];

    public UI(){
        super("Busqueda en el mundo fantasia");
        label = new JLabel("percentage of obstacles: ");
        setExtendedState(MAXIMIZED_BOTH);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Setting Sliders
        obstaclesSlider = new JSlider(0, 100, 0);
        obstaclesSlider.setMajorTickSpacing(10);
        obstaclesSlider.setPaintTicks(true);
        obstaclesSlider.setPaintLabels(true);
        obstaclesSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                label.setText("percentage of obstacles: " + obstaclesSlider.getValue() + "%");
                map.setObstacles(obstaclesSlider.getValue());
            }
        });
        mountainsSlider = new JSlider(0, 100, 0);
        mountainsSlider.setMajorTickSpacing(10);
        mountainsSlider.setPaintLabels(true);
        mountainsSlider.setPaintTicks(true);
        mountainsSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                map.setMountains(mountainsSlider.getValue());
            }
        });
        riversSlider = new JSlider(0, 100, 0);
        riversSlider.setMajorTickSpacing(10);
        riversSlider.setPaintLabels(true);
        riversSlider.setPaintTicks(true);
        riversSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                map.setRivers(riversSlider.getValue());
            }
        });
        plainSlider = new JSlider(0, 100, 0);
        plainSlider.setMajorTickSpacing(10);
        plainSlider.setPaintTicks(true);
        plainSlider.setPaintLabels(true);
        plainSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                map.setPlain(plainSlider.getValue());
            }
        });
        ravineSlider = new JSlider(0, 100, 0);
        ravineSlider.setMajorTickSpacing(10);
        ravineSlider.setPaintTicks(true);
        ravineSlider.setPaintLabels(true);
        ravineSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                map.setRavine(ravineSlider.getValue());
            }
        });
        Dimension d = obstaclesSlider.getPreferredSize();
        obstaclesSlider.setPreferredSize(new Dimension(d.width+100,d.height));
        mountainsSlider.setPreferredSize(new Dimension(d.width+100,d.height));
        riversSlider.setPreferredSize(new Dimension(d.width+100,d.height));
        plainSlider.setPreferredSize(new Dimension(d.width+100,d.height));
        ravineSlider.setPreferredSize(new Dimension(d.width+100,d.height));

        //Setting buttons
        setAgentBtn = new JButton("Set Agent");
        setHouseBtn = new JButton("Set House");
        initBtn = new JButton("Start training!");
        mombo.setListener(this);
        pirolo.setListener(this);
        lucas.setListener(this);
        initBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Thread t;
                if(!trainingDone){
                     t = new Thread() {
                        public void run() {
                            try {
                                Image image;
                                ImageIcon io;
                                graph.addNode(initialNode);
                                mombo.setActualCoordinate(agentsCoord);
                                mombo.setActualNode(initialNode);
                                mombo.setMap(map);
                                image = ImageIO.read(getClass().getResource("mombo.png"));
                                io = new ImageIcon(image);
                                agentIcon.setIcon(io);
                                mombo.startTraining();
                                pirolo.setActualCoordinate(agentsCoord);
                                pirolo.setActualNode(initialNode);
                                pirolo.setMap(map);
                                image = ImageIO.read(getClass().getResource("pirolo.png"));
                                io = new ImageIcon(image);
                                agentIcon.setIcon(io);
                                pirolo.startTraining();
                                lucas.setActualCoordinate(agentsCoord);
                                lucas.setActualNode(initialNode);
                                lucas.setMap(map);
                                image = ImageIO.read(getClass().getResource("lucas.png"));
                                io = new ImageIcon(image);
                                agentIcon.setIcon(io);
                                lucas.startTraining();
                                initBtn.setText("Start house searching!");
                                trainingDone = true;
                            }catch(IllegalArgumentException e){
                                label.setText("I got stuck!");
                                e.printStackTrace();
                            }catch (Exception e) {
                                label.setText("Add Kibus and his house first");
                                e.printStackTrace();
                            }
                        }
                    };
                }
                else{
                    t = new Thread(){
                        public void run(){
                            try{
                                Image image;
                                ImageIcon io;
                                image = ImageIO.read(getClass().getResource("mombo.png"));
                                io = new ImageIcon(image);
                                agentIcon.setIcon(io);
                                mombo.searchHouse(houseNode, initialNode);
                                image = ImageIO.read(getClass().getResource("pirolo.png"));
                                io = new ImageIcon(image);
                                agentIcon.setIcon(io);
                                pirolo.searchHouse(houseNode, initialNode);
                                image = ImageIO.read(getClass().getResource("lucas.png"));
                                io = new ImageIcon(image);
                                agentIcon.setIcon(io);
                                lucas.searchHouse(houseNode, initialNode);
                            }catch(Exception e){
                                e.printStackTrace();
                            }

                        }
                    };
                }
                t.start();

            }
        });
        setAgentBtn.addActionListener(iconListener);
        setHouseBtn.addActionListener(iconListener);

        //Setting checkbox
        training = new JCheckBox("Show Training");

        //Setting Panels
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(15, 0));
        rightPanel.add(setAgentBtn);
        rightPanel.add(setHouseBtn);
        rightPanel.add(new JLabel("Percentage of Obstacles:"));
        rightPanel.add(obstaclesSlider);
        rightPanel.add(new JLabel("Percentage of Mountains"));
        rightPanel.add(mountainsSlider);
        rightPanel.add(new JLabel("Percentage of Rivers"));
        rightPanel.add(riversSlider);
        rightPanel.add(new JLabel("Percentage of Ravines"));
        rightPanel.add(ravineSlider);
        rightPanel.add(training);
        rightPanel.add(initBtn);
        JPanel progressBarPanel = new JPanel();
        progressBarPanel.setLayout(new FlowLayout());
        progressBarPanel.add(agentIcon);
        progressBarPanel.add(trainingBar);
        rightPanel.add(progressBarPanel);
        trainingBar.setStringPainted(true);
        mapPanel.setLayout(new GridLayout(15, 15));
        initMap();
        update();
        add(mapPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
        add(label, BorderLayout.NORTH);
        pack();
        map.setListener(this);
        setVisible(true);
    }

    private void initMap() {
        int counter = 0;
        for(int i =0; i < buttons.length; i++) {
            for(int j = 0; j < buttons[i].length; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].addActionListener(iconListener);
                buttons[i][j].setActionCommand(Integer.toString(counter));
                mapPanel.add(buttons[i][j]);
                counter++;
            }
        }
    }

    public void update(){
        try {
            for (int i = 0; i < map.getDimensionX(); i++) {
                for (int j = 0; j < map.getDimensionY(); j++) {
                    int coordinate = map.get(i, j);
                    JButton button = buttons[i][j];
                    Icon icon = button.getIcon();
                    if(icon != null){
                        ImageIcon imageicon = (ImageIcon)icon;
                        if(imageicon.getDescription().equals(Integer.toString(coordinate))){
                            continue;
                        }
                    }
                    button.setIcon(null);
                    Image image;
                    ImageIcon io;
                    switch (coordinate) {
                        case Mapa.OBSTACLE:
                            image = ImageIO.read(getClass().getResource("Obstacle.png"));
                            io = new ImageIcon(image);
                            io.setDescription(Integer.toString(coordinate));
                            button.setIcon(io);
                            break;

                        case Mapa.MOUNTAIN:
                            image = ImageIO.read(getClass().getResource("mountain.png"));
                            io = new ImageIcon(image);
                            io.setDescription(Integer.toString(coordinate));
                            button.setIcon(io);
                            break;

                        case Mapa.RIVER:
                            image = ImageIO.read(getClass().getResource("river.png"));
                            io = new ImageIcon(image);
                            io.setDescription(Integer.toString(coordinate));
                            button.setIcon(io);
                            break;

                       /* case Mapa.PLAIN:
                            image = ImageIO.read(getClass().getResource("plain.png"));
                            io = new ImageIcon(image);
                            io.setDescription(Integer.toString(coordinate));
                            button.setIcon(io);
                            break;*/

                        case Mapa.RAVINE:
                            image = ImageIO.read(getClass().getResource("ravine.png"));
                            io = new ImageIcon(image);
                            io.setDescription(Integer.toString(coordinate));
                            button.setIcon(io);
                            break;

                        case Mapa.AGENT:
                            image = ImageIO.read(getClass().getResource("agent.png"));
                            io = new ImageIcon(image);
                            io.setDescription(Integer.toString(coordinate));
                            button.setIcon(io);
                            break;

                        case Mapa.MOMBO:
                            image = ImageIO.read(getClass().getResource("mombo.png"));
                            io = new ImageIcon(image);
                            io.setDescription(Integer.toString(coordinate));
                            button.setIcon(io);
                            break;

                        case Mapa.PIROLO:
                            image = ImageIO.read(getClass().getResource("pirolo.png"));
                            io = new ImageIcon(image);
                            io.setDescription(Integer.toString(coordinate));
                            button.setIcon(io);
                            break;

                        case Mapa.LUCAS:
                            image = ImageIO.read(getClass().getResource("lucas.png"));
                            io = new ImageIcon(image);
                            io.setDescription(Integer.toString(coordinate));
                            button.setIcon(io);
                            break;

                        case Mapa.HOUSE:
                            image = ImageIO.read(getClass().getResource("house.png"));
                            io = new ImageIcon(image);
                            io.setDescription(Integer.toString(coordinate));
                            button.setIcon(io);
                            break;

                        case Mapa.FREE:
                            break;

                    }
                }
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void finishRoute(int iteration){
        int percentage = iteration * 100 / (Agent.ITERATIONS - 1);
        trainingBar.setValue(percentage);
    }

    @Override
    public void mapChanged(boolean withDelay){
        if (training.isSelected() && !trainingDone){
            update();
        }
        else if(trainingDone){
            update();
            if(withDelay) {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void objectsSetted(){
        update();
    }

    static class AddIconListener implements ActionListener {
        boolean settingKibus = false;
        boolean settingHouse = false;
        JButton actualKibus = null;
        JButton actualHouse = null;

        public void actionPerformed(ActionEvent event){
            JButton button = (JButton)event.getSource();
            if(!trainingDone){
                String name = button.getText();
                if(name.contains("Agent") && !settingKibus){
                    settingHouse = false;
                    settingKibus = true;
                }
                else if(name.contains("House") && !settingHouse){
                    settingKibus = false;
                    settingHouse = true;
                }
                else{
                    try{
                        int position = Integer.parseInt(event.getActionCommand());
                        if(settingKibus){
                            int pastPosition = -1;
                            if(actualKibus != null){
                                pastPosition = Integer.parseInt(actualKibus.getActionCommand());
                            }
                            Coord coord = map.setObjects(position, pastPosition, Mapa.AGENT);
                            agentsCoord = coord;
                            initialNode = new Node(agentsCoord);
                            actualKibus = button;
                            settingKibus = false;

                        }
                        else if(settingHouse) {
                            int pastPosition = -1;
                            if(actualHouse != null){
                                pastPosition = Integer.parseInt(actualHouse.getActionCommand());
                            }
                            Coord coord = map.setObjects(position, pastPosition, Mapa.HOUSE);
                            houseNode = new Node(coord);
                            actualHouse = button;
                            settingHouse = false;
                        }
                    }catch(Exception e){
                        throw new RuntimeException(e);
                    }
                }
            }
            else{
                int position = Integer.parseInt(event.getActionCommand());
                Coord coord = graph.getNode(position);
                Node node = graph.getNode(coord);
                Thread t = new Thread(){
                    public void run(){
                        try{
                            Image image;
                            ImageIcon io;
                            image = ImageIO.read(getClass().getResource("mombo.png"));
                            io = new ImageIcon(image);
                            agentIcon.setIcon(io);
                            mombo.searchHouse(houseNode, node);
                            image = ImageIO.read(getClass().getResource("pirolo.png"));
                            io = new ImageIcon(image);
                            agentIcon.setIcon(io);
                            pirolo.searchHouse(houseNode, node);
                            image = ImageIO.read(getClass().getResource("lucas.png"));
                            io = new ImageIcon(image);
                            agentIcon.setIcon(io);
                            lucas.searchHouse(houseNode, node);
                        }catch(Exception e){
                            e.printStackTrace();
                        }

                    }
                };
                t.start();
            }
        }

    }

    public static void main(String args[]) throws Exception{
        UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new UI();
            }
        });
    }
}
